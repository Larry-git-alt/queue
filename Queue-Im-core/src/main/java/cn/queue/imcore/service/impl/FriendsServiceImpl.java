package cn.queue.imcore.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.queue.base.user.domain.entity.User;
import cn.queue.common.util.RedisUtil;
import cn.queue.domain.dto.AddRecordDTO;
import cn.queue.domain.entity.AddRecordEntity;
import cn.queue.domain.entity.FriendsEntity;
import cn.queue.imcore.cache.AddListCache;
import cn.queue.imcore.dao.IApplyDao;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private RedisTemplate<String, AddRecordDTO> redisTemplate;

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
        AddRecordDTO addRecordDTO = new AddRecordDTO();
        BeanUtil.copyProperties(addRecordEntity, addRecordDTO);
        addRecordDTO.setUsername(toUser.getUsername());
        addRecordDTO.setPhoto(toUser.getImg());
        addRecordDTO.setType(0);
        addListCache.addToApplyList(addRecordDTO, toId);
        User fromUser = userFeign.getById(fromId);
        addRecordDTO.setUsername(fromUser.getUsername());
        addRecordDTO.setPhoto(fromUser.getImg());
        addRecordDTO.setType(1);
        addListCache.addToApplyList(addRecordDTO, fromId);

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
                    .createTime(new Date())
                    .build();
            friendsDao.insert(friendsEntity);

            friendsEntity = FriendsEntity.builder()
                    .fromId(toId)
                    .toId(fromId)
                    .status(1)
                    .black(1)
                    .createTime(new Date())
                    .build();
            friendsDao.insert(friendsEntity);
        }
        //查询是否有从toId 到fromId的请求， 如果有则改变状态为已失效
        LambdaQueryWrapper<AddRecordEntity> opAddQuery = new LambdaQueryWrapper<>();
        opAddQuery.eq(AddRecordEntity::getFromId, toId)
                .eq(AddRecordEntity::getToId, fromId);
        AddRecordEntity opAddRecordEntity = applyDao.selectOne(opAddQuery);
        if (opAddRecordEntity != null && opAddRecordEntity.getStatus() == 1) {
            opAddRecordEntity.setStatus(4);
            opAddRecordEntity.setUpdateTime(new Date());
            applyDao.update(opAddRecordEntity, opAddQuery);
        }
        redisTemplate.delete("queue:im:addFriendsApply:" + fromId);
        redisTemplate.delete("queue:im:addFriendsApply:" + toId);

        return "处成理功";
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
        //只有两个人对应的两条数据中的所有状态都为1，且都不处于拉黑状态才能判定为好友
        //return friend.getStatus() == 1 && toFriend.getStatus() == 1 && toFriend.getBlack() == 1 && friend.getBlack() == 1;
        queryWrapper.eq(FriendsEntity::getStatus, 1).eq(FriendsEntity::getBlack, 1);
        toQueryWrapper.eq(FriendsEntity::getStatus, 1).eq(FriendsEntity::getBlack, 1);
        return friendsDao.exists(queryWrapper) && friendsDao.exists(toQueryWrapper);
    }


    /**
     * 查询申请列表
     * @param id
     * @return
     */
    @Override
    public List<AddRecordDTO> getApplyList(Long id) {

        //判断用户是否存在
        User user = userFeign.getById(id);
        if (user == null) {
            return null;
        }

        log.info("查询申请列表");
        RedisOperations<String, AddRecordDTO> operations = redisTemplate.opsForList().getOperations();
        List<AddRecordDTO> range = redisTemplate.opsForList().range("queue:im:addFriendsApply:" + id, 0, -1);
        //assert range != null;
        if ( !range.isEmpty()){
            return range;
        }

        log.info("没有缓存，查询数据库");
        //两种，一种是被申请，一种是申请
        LambdaQueryWrapper<AddRecordEntity> toQueryWrapper = new LambdaQueryWrapper<>();
        toQueryWrapper.eq(AddRecordEntity::getToId, id);

        List<AddRecordEntity> beList = applyDao.selectList(toQueryWrapper);
        List<AddRecordDTO> addRecordDTOS = new ArrayList<>();
        AddRecordDTO addRecordDTO = new AddRecordDTO();
        beList.forEach(addRecordEntity -> {
            BeanUtil.copyProperties(addRecordEntity, addRecordDTO);
            User fromUser = userFeign.getById(addRecordEntity.getFromId());
            addRecordDTO.setPhoto(fromUser.getImg());
            addRecordDTO.setUpdateTime(new Date());
            addRecordDTO.setType(0);
            addRecordDTO.setUsername(fromUser.getUsername());
            addRecordDTOS.add(addRecordDTO);
            //放入redis缓存
            addListCache.addToApplyList(addRecordDTO, id);
        });

        LambdaQueryWrapper<AddRecordEntity> queryWrapper = new LambdaQueryWrapper<AddRecordEntity>()
                .eq(AddRecordEntity::getFromId, id);
        List<AddRecordEntity> list = applyDao.selectList(queryWrapper);
        list.forEach(addRecordEntity -> {
            BeanUtil.copyProperties(addRecordEntity, addRecordDTO);
            User toUser = userFeign.getById(addRecordEntity.getToId());
            addRecordDTO.setPhoto(toUser.getImg());
            addRecordDTO.setUpdateTime(new Date());
            addRecordDTO.setType(1);
            addRecordDTO.setUsername(toUser.getUsername());
            addRecordDTOS.add(addRecordDTO);
            //放入redis缓存
            addListCache.addToApplyList(addRecordDTO, id);
        });
        //addListCache.addToApplyList(addRecordDTOS, id);
        return addRecordDTOS;
    }

}
