package cn.queue.online_judge.mapper;

import cn.queue.online_judge.pojo.Problem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProblemMapper extends BaseMapper<Problem>{
    /**
     * 新增题目
     * @param problem
     */
//    void insert(Problem problem);

    /**
     *根据标签查询题目
     * @param tags
     * @return
     */
   // List<Problem> getByTag(@Param("tags") String tags);

    /**
     * 根据id查询题目
      * @param id
     * @return
     */
    @Select("select * from problem where id = #{id}")
    Problem getById(Long id);

    /**
     * 根据标题查询题目
     * @param title
     * @return
     */
   // List<Problem> getByTitle(@Param("title") String title);

    /**
     * 根据难度查询题目
     * @param difficulty
     * @return
     */
//    @Select("select * from problem where difficulty = #{difficulty}")
//    List<Problem> getByDifficulty(Short difficulty);

    /**
     * 批量删除
     * @param ids
     */
    void delete(@Param("ids") List<Integer> ids);

    /**
     * 修改题目
     * @param problem
     */
    void update(Problem problem);

    /**
     * 分页查询
     * @param tags
     * @param title
     * @param difficulty
     * @return
     */
    List<Problem> list(String tags, String title, Short difficulty);



}
