package cn.queue.imcore.service.impl;

import cn.queue.base.user.domain.entity.User;
import cn.queue.common.util.RedisUtil;
import cn.queue.domain.dto.AddRecordDTO;
import cn.queue.domain.entity.AddRecordEntity;
import cn.queue.domain.entity.FriendsEntity;
import cn.queue.imcore.dao.IApplyDao;
import cn.queue.imcore.dao.IFriendsDao;
import cn.queue.imcore.feign.UserFeign;
import cn.queue.imcore.service.IFriendsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    //TODO : 加不加事务
    @Override
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
        if (applyDao.exists(addQuery)) {
            return "已经申请过了，等待验证中";
        }

        AddRecordEntity addRecordEntity = AddRecordEntity.builder()
                .fromId(fromId)
                .toId(toId)
                .status(1)
                .note(note)
                .createTime(LocalDateTime.now())
                .build();
        applyDao.insert(addRecordEntity);

        return "好友申请发送成功";
    }

    /**
     * 处理好友申请
     *
     * @param fromId
     * @param toId
     */
    @Override
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
        addQuery.eq(AddRecordEntity::getStatus, 1);
        //判断该申请是否已经处理过了
        if (applyDao.exists(addQuery)) {
            return "该申请已经处理过了";
        }

        if (status == 1) {
            return "还可继续验证";
        }

        addRecordEntity.setStatus(status);
        addRecordEntity.setUpdateTime(LocalDateTime.now());
        applyDao.update(addRecordEntity, addQuery);

        FriendsEntity friendsEntity = FriendsEntity.builder()
                .fromId(fromId)
                .toId(toId)
                .status(1)
                .black(1)
                .createTime(LocalDateTime.now())
                .build();
        friendsDao.insert(friendsEntity);

        friendsEntity = FriendsEntity.builder()
                .fromId(toId)
                .toId(fromId)
                .status(1)
                .black(1)
                .createTime(LocalDateTime.now())
                .build();
        friendsDao.insert(friendsEntity);

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
        //只有两个人对应的两条数据中的所有状态都为1，且都不处于拉黑状态才能判定为好友
        //return friend.getStatus() == 1 && toFriend.getStatus() == 1 && toFriend.getBlack() == 1 && friend.getBlack() == 1;
        queryWrapper.eq(FriendsEntity::getStatus, 1).eq(FriendsEntity::getBlack, 1);
        toQueryWrapper.eq(FriendsEntity::getStatus, 1).eq(FriendsEntity::getBlack, 1);
        return friendsDao.exists(queryWrapper) && friendsDao.exists(toQueryWrapper);
    }


    @Override
    public List<AddRecordDTO> getApplyList(Long id) {

        //两种，一种是被申请，一种是申请
        LambdaQueryWrapper<AddRecordEntity> toQueryWrapper = new LambdaQueryWrapper<>();
        toQueryWrapper.eq(AddRecordEntity::getToId, id);

        LambdaQueryWrapper<AddRecordEntity> queryWrapper = new LambdaQueryWrapper<AddRecordEntity>()
                .eq(AddRecordEntity::getFromId, id);

        List<AddRecordEntity> list = applyDao.selectList(queryWrapper);
        List<AddRecordDTO> addRecordDTOS = new ArrayList<>();
        list.forEach(addRecordEntity -> {

        });

        List<AddRecordEntity> beList = applyDao.selectList(toQueryWrapper);


        return addRecordDTOS;
    }

}
