package cn.queue.online_judge.mapper;

import cn.queue.online_judge.pojo.Module;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ModuleMapper extends BaseMapper<Module> {
//    @Select("select * from module where course_id = #{id}")
//    List<Module> getMoById(Integer id);

    @Select("select * from module where course_id = #{id} and deleted = 0")
    List<Module> getByCoId(Long id);

    @Select("select * from module where id = #{id} and deleted = 0")
    Module getById(Long id);
    @Select("select * from module")
    List<Module> getAll();
}
