package cn.queue.online_judge.mapper;

import cn.queue.online_judge.pojo.Competition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CompetitionMapper extends BaseMapper<Competition> {
   @Select("select id, title, start_time, end_time, organizer, type, difficulty, content, pro_id, scores, times, is_public, create_time, update_time, deleted" +
           " from competition")
    List<Competition> list();
}
