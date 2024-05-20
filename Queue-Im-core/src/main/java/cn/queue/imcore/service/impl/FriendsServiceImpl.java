package cn.queue.imcore.service.impl;

import cn.queue.common.util.RedisUtil;
import cn.queue.imcore.domain.entity.FriendsEntity;
import cn.queue.imcore.dao.IFriendsDao;
import cn.queue.imcore.service.IFriendsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /19 / 16:08
 * @Description:
 */
@Service
public class FriendsServiceImpl implements IFriendsService {
    @Resource
    private IFriendsDao friendsDao;
    @Resource
    private RedisUtil redisUtil;

    public List<FriendsEntity> getList(Long id) {
        LambdaQueryWrapper<FriendsEntity> friendsEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        friendsEntityLambdaQueryWrapper.eq(id!=null,FriendsEntity::getFromId,id);
        return friendsDao.selectList(friendsEntityLambdaQueryWrapper);
    }

}
