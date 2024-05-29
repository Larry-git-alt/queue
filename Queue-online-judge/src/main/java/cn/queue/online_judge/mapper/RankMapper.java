package cn.queue.online_judge.mapper;

import cn.queue.online_judge.pojo.Rank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RankMapper extends BaseMapper<Rank> {
    /**
     * 根据课程排行
      * @param courseId
     * @return
     */
    @Select("select * from `rank` where course_id = #{coursrId} order by nums desc,update_time asc")
     List<Rank> rankByCoId(Long courseId);

    /**
     * 查找有无记录
     * @param userId
     * @param courseId
     * @return
     */
    @Select("select * from `rank` where user_id = #{userId} and course_id = #{courseId}" )
    Object select(Long userId, Long courseId);

    /**
     * 更新刷题数
     * @param userId
     * @param courseId
     */
    @Update("update `rank` set nums = nums + 1 where user_id = #{userId} and course_id in (0,#{courseId})")
    void updateNums(Long userId, Long courseId);
}
