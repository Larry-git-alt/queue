package cn.queue.online_judge.mapper;

import cn.queue.online_judge.pojo.AssessmentPoints;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AssessmentPointsMapper {

//   @Select("select * from assessment_points where user_id = #{userId} and com_id = #{comId} group by pro_id order by MAX(submission_time) desc ")
@Select("select * from assessment_points "
        + "where user_id = #{userId} "
        + "and com_id = #{comId} "
        + "and submission_time = ("
        + "select MAX(submission_time) "
        + "  from assessment_points ap "
        + "  where ap.user_id = #{userId} "
        + "  and ap.com_id = #{comId} "
        + "  and ap.pro_id = assessment_points.pro_id"
        + ")order by pro_id asc")
    List<AssessmentPoints> getById(Long userId, Long comId);
}
