package cn.queue.imcore.dao;

import cn.queue.domain.entity.ImMsgEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

/**
 * @author: Larry
 * @Date: 2024 /05 /23 / 11:28
 * @Description:
 */
@Mapper
public interface IMessageDao extends BaseMapper<ImMsgEntity> {
    @Update("update message set is_read = 1 where target_id = #{targetId} and create_time <#{time} and is_read = 0 ")
    void updateIsRead(Long targetId,LocalDateTime time);
    @Select("select count(0) from message where target_id = #{targetId} and is_read = 0 ")
    Integer getNoReadNum(Long targetId);
}
