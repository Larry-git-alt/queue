package cn.queue.imcore.dao;

import cn.queue.imcore.domain.entity.GroupMemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Larry
 * @Date: 2024 /05 /20 / 10:15
 * @Description:
 */
@Mapper
public interface IGroupUserDao extends BaseMapper<GroupMemberEntity> {
}
