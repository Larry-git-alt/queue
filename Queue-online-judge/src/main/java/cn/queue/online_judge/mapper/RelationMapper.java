package cn.queue.online_judge.mapper;

import cn.queue.online_judge.pojo.Problem;
import cn.queue.online_judge.pojo.Relation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RelationMapper extends BaseMapper<Relation> {

   @Select("select problem_id from relation where module_id = #{id} and problem_id is not null")
    List<Long> getByMoId(Long id);

    @Select("select module_id from relation where type = 0 and type_id = #{id} and module_id is not null")
    List<Long> getByCoId(Long id);

    /**
     * 新增模块
     * @param moduleId
     * @param problemId
     */
   @Insert("insert into relation (module_id,problem_id) values (#{moduleId},#{problemId})")
    void createMo_Pr(long moduleId, Long problemId);

    /**
     * 新增课程
     * @param courseId
     * @param moduleId
     */
    @Insert("insert into relation(type_id,type,module_id) values (#{courseId},0,#{moduleId})")
    void createCo_Mo(long courseId, Long moduleId);
}
