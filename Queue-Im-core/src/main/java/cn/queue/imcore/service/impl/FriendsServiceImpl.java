package cn.queue.imcore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.queue.base.user.domain.entity.User;
import cn.queue.common.util.RedisUtil;
import cn.queue.domain.valueObj.CacheConstant;
import cn.queue.domain.vo.FriendVO;
import cn.queue.domain.entity.AddRecordEntity;
import cn.queue.domain.entity.ClazzEntity;
import cn.queue.domain.entity.FriendsEntity;
import cn.queue.domain.vo.AddRecordVO;
import cn.queue.imcore.cache.AddListCache;
import cn.queue.imcore.dao.IApplyDao;
import cn.queue.imcore.dao.IClazzDao;
import cn.queue.imcore.dao.IFriendsDao;
import cn.queue.imcore.feign.UserFeign;
import cn.queue.imcore.service.IFriendsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Larry
 * @Date: 2024 /05 /19 / 16:08
 * @Description:
 */
@Service
@Slf4j
public class FriendsServiceImpl implements IFriendsService {
    @Resource
    private IFriendsDao friendsDao;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserFeign userFeign;
    @Resource
    private IApplyDao applyDao;
    @Resource
    private AddListCache addListCache;
    @Resource
    private IClazzDao clazzDao;
//    @Resource
//    private RedisTemplate<String, AddRecordVO> redisTemplate;

    @Override
    public List<FriendsEntity> getList(Long id) {
        LambdaQueryWrapper<FriendsEntity> friendsEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        friendsEntityLambdaQueryWrapper.eq(id!=null,FriendsEntity::getFromId,id);
        return friendsDao.selectList(friendsEntityLambdaQueryWrapper);
    }

    /**
     * 发起好友申请
     *
     * @param fromId
     * @param toId
     * @param note
     */
    @Override
    @Transactional
    public String addFriend(Long fromId, Long toId, String note) {

        //不能添加自己为好友
        if (fromId.equals(toId)) {
            return "不能添加自己为好友";
        }
        //判断对方是否存在
        User toUser = userFeign.getById(toId);
        if (toUser == null) {
            return "用户不存在";
        }
        //是否已经是好友
        if (isFriend(fromId, toId)) {
            return "已经成为好友，不需要再添加了";
        }

        //判断是否已经申请过了
        LambdaQueryWrapper<AddRecordEntity> addQuery = new LambdaQueryWrapper<>();
        addQuery.eq(AddRecordEntity::getFromId, fromId).eq(AddRecordEntity::getToId, toId);
        AddRecordEntity recordEntity = applyDao.selectOne(addQuery);
        if (recordEntity != null && recordEntity.getStatus() == 1) {
            return "已经申请过了，等待验证中";
        }else if(recordEntity != null && (recordEntity.getStatus() == 3 || recordEntity.getStatus() == 4)){
            recordEntity.setStatus(1);
            applyDao.updateById(recordEntity);
            return "申请已发送";
        }

        AddRecordEntity addRecordEntity = AddRecordEntity.builder()
                .fromId(fromId)
                .toId(toId)
                .status(1)
                .note(note)
                .createTime(new Date())
                .build();
        applyDao.insert(addRecordEntity);
        AddRecordVO addRecordVO = new AddRecordVO();
        BeanUtil.copyProperties(addRecordEntity, addRecordVO);
        addRecordVO.setUsername(toUser.getUsername());
        addRecordVO.setPhoto(toUser.getImg());
        addRecordVO.setType(0);
        addListCache.addToApplyList(addRecordVO, toId);
        User fromUser = userFeign.getById(fromId);
        addRecordVO.setUsername(fromUser.getUsername());
        addRecordVO.setPhoto(fromUser.getImg());
        addRecordVO.setType(1);
        addListCache.addToApplyList(addRecordVO, fromId);

        return "好友申请发送成功";
    }

    /**
     * 处理好友申请
     *
     * @param fromId
     * @param toId
     */
    @Override
    @Transactional
    public String receiveAdd(Long fromId, Long toId, Integer status) {

        //判断当前用户是否为toUser


        //判断该申请是否存在
        LambdaQueryWrapper<AddRecordEntity> addQuery = new LambdaQueryWrapper<>();
        addQuery.eq(AddRecordEntity::getFromId, fromId)
                .eq(AddRecordEntity::getToId, toId);
        AddRecordEntity addRecordEntity = applyDao.selectOne(addQuery);
        if (!applyDao.exists(addQuery)) {
            return "该申请不存在";
        }
        //判断两人是否已经是好友
        if (isFriend(fromId, toId)) {
            return "已经是好友，不可重复添加";
        }
        //判断该申请是否已经处理过了
        if (addRecordEntity.getStatus() != 1) {
            return "该申请已经处理过了";
        }

        addRecordEntity.setStatus(status);
        addRecordEntity.setUpdateTime(new Date());
        applyDao.update(addRecordEntity, addQuery);

        if (status == 1) {
            return "还可继续验证";
        } else if (status == 3) {
            return "已拒绝";
        }

        //判断之前是否有好友记录, 如果有只改变状态即可，没有则需将新的好友记录插入
        LambdaQueryWrapper<FriendsEntity> friendsQuery = new LambdaQueryWrapper<>();
        friendsQuery.eq(FriendsEntity::getFromId, fromId).eq(FriendsEntity::getToId, toId);
        LambdaQueryWrapper<FriendsEntity> eq = new LambdaQueryWrapper<FriendsEntity>()
                .eq(FriendsEntity::getFromId, toId).eq(FriendsEntity::getToId, fromId);
        FriendsEntity friends = friendsDao.selectOne(friendsQuery);
        FriendsEntity toFriends = friendsDao.selectOne(eq);
        if (friends != null && toFriends != null) {
            friends.setStatus(1);
            friends.setBlack(1);
            toFriends.setStatus(1);
            toFriends.setBlack(1);
            friendsDao.update(toFriends, eq);
            friendsDao.update(friends, friendsQuery);

        }else {
            FriendsEntity friendsEntity = FriendsEntity.builder()
                    .fromId(fromId)
                    .toId(toId)
                    .status(1)
                    .black(1)
                    .remark(userFeign.getById(toId).getUsername())
                    .createTime(new Date())
                    .build();
            friendsDao.insert(friendsEntity);

            friendsEntity = FriendsEntity.builder()
                    .fromId(toId)
                    .toId(fromId)
                    .status(1)
                    .black(1)
                    .remark(userFeign.getById(fromId).getUsername())
                    .createTime(new Date())
                    .build();
            friendsDao.insert(friendsEntity);
        }
//        //查询是否有从toId 到fromId的请求， 如果有则改变状态为已失效
//        LambdaQueryWrapper<AddRecordEntity> opAddQuery = new LambdaQueryWrapper<>();
//        opAddQuery.eq(AddRecordEntity::getFromId, toId)
//                .eq(AddRecordEntity::getToId, fromId);
//        AddRecordEntity opAddRecordEntity = applyDao.selectOne(opAddQuery);
//        if (opAddRecordEntity != null && opAddRecordEntity.getStatus() == 1) {
//            opAddRecordEntity.setStatus(4);
//            opAddRecordEntity.setUpdateTime(new Date());
//            applyDao.update(opAddRecordEntity, opAddQuery);
//        }
        addListCache.cleanCache(fromId);
        addListCache.cleanCache(toId);

        return "处理成功";
    }

    /**
     * 判断是否为好友
     *
     * @param fromId
     * @param toId
     * @return
     */
    @Override
    public boolean isFriend(Long fromId, Long toId) {

        //先判断用户是否存在

        User fromUser = userFeign.getById(fromId);
        User toUser = userFeign.getById(toId);
        if (toUser == null || fromUser == null) {
            return false;
        }

        //查询是否有好友记录
        LambdaQueryWrapper<FriendsEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FriendsEntity::getFromId, fromId).eq(FriendsEntity::getToId, toId);

        //FriendsEntity friend = friendsDao.selectOne(queryWrapper);

        LambdaQueryWrapper<FriendsEntity> toQueryWrapper = new LambdaQueryWrapper<>();
        toQueryWrapper.eq(FriendsEntity::getFromId, toId).eq(FriendsEntity::getToId, fromId);
        //FriendsEntity toFriend = friendsDao.selectOne(toQueryWrapper);

        if (!friendsDao.exists(queryWrapper) || !friendsDao.exists(toQueryWrapper)) {
            return false;
        }

        log.info("有好友记录");
        //只有两个人对应的两条数据中的所有状态都为1，
        queryWrapper.eq(FriendsEntity::getStatus, 1);
        toQueryWrapper.eq(FriendsEntity::getStatus, 1);
        return friendsDao.exists(queryWrapper) && friendsDao.exists(toQueryWrapper);
    }


    /**
     * 查询申请列表
     * @param id
     * @return
     */
    @Override
    public List<AddRecordVO> getApplyList(Long id) {

        //判断用户是否存在
        User user = userFeign.getById(id);
        if (user == null) {
            return null;
        }

        log.info("查询申请列表");
        List<AddRecordVO> range = addListCache.queryAddListCache(id);
        if ( !range.isEmpty()){
            return range;
        }

        log.info("没有缓存，查询数据库");
        //两种，一种是被申请，一种是申请
        LambdaQueryWrapper<AddRecordEntity> toQueryWrapper = new LambdaQueryWrapper<>();
        toQueryWrapper.eq(AddRecordEntity::getToId, id);

        List<AddRecordEntity> beList = applyDao.selectList(toQueryWrapper);
        List<AddRecordVO> addRecordVOS = new ArrayList<>();
        AddRecordVO addRecordVO = new AddRecordVO();
        beList.forEach(addRecordEntity -> {
            BeanUtil.copyProperties(addRecordEntity, addRecordVO);
            User fromUser = userFeign.getById(addRecordEntity.getFromId());
            addRecordVO.setPhoto(fromUser.getImg());
            addRecordVO.setUpdateTime(new Date());
            addRecordVO.setType(0);
            addRecordVO.setUsername(fromUser.getUsername());
            addRecordVOS.add(addRecordVO);
            //放入redis缓存
            //addListCache.addToApplyList(addRecordDTO, id);
        });

        LambdaQueryWrapper<AddRecordEntity> queryWrapper = new LambdaQueryWrapper<AddRecordEntity>()
                .eq(AddRecordEntity::getFromId, id);
        List<AddRecordEntity> list = applyDao.selectList(queryWrapper);
        list.forEach(addRecordEntity -> {
            BeanUtil.copyProperties(addRecordEntity, addRecordVO);
            User toUser = userFeign.getById(addRecordEntity.getToId());
            addRecordVO.setPhoto(toUser.getImg());
            addRecordVO.setUpdateTime(new Date());
            addRecordVO.setType(1);
            addRecordVO.setUsername(toUser.getUsername());
            addRecordVOS.add(addRecordVO);
            //放入redis缓存
            //addListCache.addToApplyList(addRecordDTO, id);
        });
        //根据时间排序
        List<AddRecordVO> collect = addRecordVOS.stream()
                .sorted(Comparator.comparing(AddRecordVO::getCreateTime))
                .collect(Collectors.toList());
        collect.forEach(addDTO->{
            addListCache.addToApplyList(addDTO, id);
        });
        return collect;
    }


    /**
     * 添加分组
     * @param id
     * @param clazzName
     * @return
     */
    @Override
    public String addClazz(Long id, String clazzName) {

        //一个用户最多设置地分组数进行限制
        LambdaQueryWrapper<ClazzEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClazzEntity::getUserId, id);
        Long count = clazzDao.selectCount(wrapper);
        if (count >= 12){
            return "分组设置上限了";
        }

        //分组名不能重复
        wrapper.eq(ClazzEntity::getClazzName, clazzName);
        boolean exists = clazzDao.exists(wrapper);
        if (exists){
            return "分组名已存在";
        }

        ClazzEntity clazz = new ClazzEntity().builder()
                .userId(id)
                .clazzId(count)
                .clazzName(clazzName)
                .build();
        clazzDao.insert(clazz);

        return "好友分组添加成功";
    }

    /**
     * 删除分组
     * @param id
     * @param clazzId
     * @return
     */
    @Override
    @Transactional
    public String deleteClazz(Long id, Long clazzId) {
        //原分组的好友设置到默认分组中--0
        LambdaQueryWrapper<FriendsEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FriendsEntity::getFromId, id).eq(FriendsEntity::getClazzId, clazzId);
        List<FriendsEntity> friendsEntities = friendsDao.selectList(queryWrapper);
        friendsEntities.forEach(friendsEntity -> {
            friendsEntity.setClazzId(0L);
            LambdaQueryWrapper<FriendsEntity> wrapper = new LambdaQueryWrapper<>();
            friendsDao.update(friendsEntity,
                    wrapper.eq(FriendsEntity::getFromId, id).eq(FriendsEntity::getClazzId, clazzId));
        });
        LambdaQueryWrapper<ClazzEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClazzEntity::getUserId, id).eq(ClazzEntity::getClazzId, clazzId);
        clazzDao.delete(wrapper);
        return "好友分组删除成功";
    }

    /**
     * 设置好友分组
     * @param fromId
     * @param toId
     * @param clazzId
     * @return
     */
    @Override
    public String setFriendsClazz(Long fromId, Long toId, Long clazzId){
        //判断是否为好友
        if (!isFriend(fromId, toId)) {
            return "不是好友，设置失败";
        }
        //判断是否有该分组
        LambdaQueryWrapper<ClazzEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClazzEntity::getUserId, fromId).eq(ClazzEntity::getClazzId, clazzId);
        boolean exists = clazzDao.exists(wrapper);
        if (!exists) {
            return "没有该分组，设置失败";
        }

        //设置分组
        LambdaQueryWrapper<FriendsEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FriendsEntity::getFromId, fromId).eq(FriendsEntity::getToId, toId);
        FriendsEntity friends = friendsDao.selectOne(queryWrapper);
        friends.setClazzId(clazzId);
        friendsDao.update(friends, queryWrapper);
        return "好友分组设置成功";
    }

    /**
     * 根据分组查询好友
     * @param id
     * @param clazzId
     * @return
     */
    @Override
    public List<FriendVO> queryFriendByClazz(Long id, Integer clazzId){
        LambdaQueryWrapper<FriendsEntity> listLambdaQueryWrapper = new LambdaQueryWrapper<>();
        listLambdaQueryWrapper.eq(FriendsEntity::getFromId, id).eq(FriendsEntity::getClazzId, clazzId);
        List<FriendsEntity> friendsEntities = friendsDao.selectList(listLambdaQueryWrapper);

        List<FriendVO> friendVOS = new ArrayList<>();
        friendsEntities.forEach(friendsEntity -> {
            FriendVO friendVO = new FriendVO();
            Long toId = friendsEntity.getToId();
            friendVO.setFriendId(toId);
            friendVO.setRemark(friendsEntity.getRemark());
            friendVO.setPhoto(friendsEntity.getPhoto());

            friendVOS.add(friendVO);
        });
        //根据备注排序

        return friendVOS.stream()
                .sorted(Comparator.comparing(FriendVO::getRemark))
                .collect(Collectors.toList());
    }

    /**
     * 设置好友备注
     * @param fromId
     * @param toId
     * @param remark
     * @return
     */
    @Override
    public String setRemark(Long fromId, Long toId, String remark){

        //是否是好友
        boolean friend = isFriend(fromId, toId);
        if ( !friend){
            return "不是好友，无法设置备注";
        }

        LambdaQueryWrapper<FriendsEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FriendsEntity::getFromId, fromId).eq(FriendsEntity::getToId, toId);
        FriendsEntity friends = friendsDao.selectOne(queryWrapper);
        friends.setRemark(remark);
        friendsDao.update(friends, queryWrapper);

        return "设置备注成功";

    }

    /**
     * 删除好友
     * @param fromId
     * @param toId
     * @return
     */
    @Override
    public String deleteFriend(Long fromId, Long toId) {
        //将friend表中的status改为2，black改为2
        LambdaQueryWrapper<FriendsEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FriendsEntity::getFromId, fromId).eq(FriendsEntity::getToId, toId);
        FriendsEntity friends = friendsDao.selectOne(queryWrapper);
        if (!isFriend(fromId, toId)) {
            return "不是好友，删除失败";
        }
        friends.setStatus(2);
        friends.setBlack(2);
        friendsDao.update(friends, queryWrapper);
        return "删除成功";
    }

    /**
     * 拉黑好友
     * @param fromId
     * @param toId
     * @return
     */
    @Override
    public String blackFriend(Long fromId, Long toId) {
        //判断两人是否是好友
        LambdaQueryWrapper<FriendsEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FriendsEntity::getFromId, fromId).eq(FriendsEntity::getToId, toId);
        if (!isFriend(fromId, toId)) {
            return "不是好友，拉黑失败";
        }
        //判断两人的好友状态
        FriendsEntity friends = friendsDao.selectOne(queryWrapper);
        if (friends.getStatus() == 2) {
            return "已经是拉黑状态";
        }
        //设置black为2
        friends.setBlack(2);
        friendsDao.update(friends, queryWrapper);

        return "拉黑成功";
    }

}
