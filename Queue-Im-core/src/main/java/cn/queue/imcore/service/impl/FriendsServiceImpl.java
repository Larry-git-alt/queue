package cn.queue.imcore.service.impl;

import cn.queue.common.util.RedisUtil;
import cn.queue.imcore.domain.entity.FriendsEntity;
import cn.queue.imcore.repository.IFriendsDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /19 / 16:08
 * @Description:
 */
@Service
public class FriendsServiceImpl {
    @Resource
    private IFriendsDao friendsDao;
    @Resource
    private RedisUtil redisUtil;

    public List<FriendsEntity> test(){
        redisUtil.set("test",6);
        return friendsDao.selectList(null);
    }

}
