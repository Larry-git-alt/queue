package cn.queue.online_judge.mapper;

import cn.queue.online_judge.pojo.Example;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExampleMapper {


    /**
     * 新增用例
     * @param examples
     */

    void insert(@Param("examples") List<Example> examples);

    /**
     * 修改用例
     * @param example
     */
    void update(@Param("example") Example example);

    /**
     * 查找用例
     * @param id
     * @return
     */
    @Select("select * from example where problem_id = #{id}")
    List<Example> getById(Integer id);
}
