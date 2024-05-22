package cn.queue.imcore.service;


import cn.queue.domain.entity.FriendsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /19 / 16:08
 * @Description:
 */
public interface IFriendsService  {
    List<FriendsEntity> getList(Long id);
}
