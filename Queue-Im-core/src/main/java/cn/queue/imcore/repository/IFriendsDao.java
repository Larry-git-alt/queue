package cn.queue.imcore.repository;

import cn.queue.imcore.domain.entity.FriendsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author: Larry
 * @Date: 2024 /05 /19 / 16:07
 * @Description:
 */
@Mapper
public interface IFriendsDao extends BaseMapper<FriendsEntity> {
}
