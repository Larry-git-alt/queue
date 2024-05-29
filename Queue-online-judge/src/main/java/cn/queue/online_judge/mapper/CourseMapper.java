package cn.queue.online_judge.mapper;

import cn.queue.online_judge.pojo.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.queue.online_judge.pojo.Module;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {

   @Select("select * from course where id = #{id} and deleted = 0")
    Course getById(Integer id);

   @Select("select * from course")
    List<Course> list();
}
