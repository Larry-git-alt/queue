package cn.queue.online_judge.service;

import cn.queue.online_judge.pojo.PageBean;
import cn.queue.online_judge.pojo.Problem;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProblemService {
    /**
     * 新增题目
     * @param problem
     */
    void create(Problem problem);

    /**
     * 根据标签查询题目
     * @param tags
     * @return
     */
  //  List<Problem> getByTag(String tags);

    /**
     * 根据id查询题目
      * @param id
     * @return
     */
    Problem getById(Integer id);

    /**
     * 根据标题查询题目
     * @param title
     * @return
     */
   // List<Problem> getByTitle(String title);

    /**
     * 根据难度查询题目
     * @param difficulty
     * @return
     */
   // List<Problem> getByDifficulty(Short difficulty);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 修改题目
     * @param problem
     */

    void update(Problem problem);

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param tags
     * @param title
     * @param difficulty
     * @return
     */
    PageBean page(Integer page, Integer pageSize, String tags, String title, Short difficulty);
}
