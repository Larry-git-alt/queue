package cn.queue.online_judge.service.impl;

import cn.queue.online_judge.mapper.ExampleMapper;
import cn.queue.online_judge.mapper.ProblemMapper;
import cn.queue.online_judge.pojo.Example;
import cn.queue.online_judge.pojo.PageBean;
import cn.queue.online_judge.pojo.Problem;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.queue.online_judge.service.ProblemService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private ExampleMapper exampleMapper;

    @Override
    public void create(Problem problem) {
        LocalDateTime now = LocalDateTime.now();
        problem.setCreateTime(now);
        problem.setUpdateTime(now);
        problem.setCreateBy("y");
        problem.setUpdateBy("y");
        problemMapper.insert(problem);

        List<Example> examples = problem.getExamples();

        examples.forEach(e -> {
            e.setProblemId(problem.getId());
        });
        exampleMapper.insert(examples);
    }

//    @Override
//    public List<Problem> getByTag(String tags) {
//        return problemMapper.getByTag(tags);
//    }

    @Override
    public Problem getById(Long id) {
        Problem problem = problemMapper.getById(id);
        List<Example> examples = exampleMapper.getById(id);
        problem.setExamples(examples);
        return problem;
    }

//    @Override
//    public List<Problem> getByTitle(String title) {
//        return problemMapper.getByTitle(title);
//    }

//    @Override
//    public List<Problem> getByDifficulty(Short difficulty) {
//        return problemMapper.getByDifficulty(difficulty);
//    }

    @Override
    public void delete(List<Long> ids) {
        problemMapper.deleteBatchIds(ids);
    }

    @Override
    public void update(Problem problem) {
        problem.setUpdateTime(LocalDateTime.now());
        problemMapper.update(problem);
        List<Example> examples = problem.getExamples();

        examples.forEach(e -> {
            e.setProblemId(problem.getId());
            exampleMapper.update(e);
        });
        //exampleMapper.update(examples);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String tags, String title, Short difficulty) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、执行查询
        List<Problem> empList = problemMapper.list(tags,title,difficulty);

        Long userId = 1L;


        Page<Problem> p = (Page<Problem>) empList;

        //3、封装PageBean对象
        PageBean pageBean = new PageBean((int)p.getTotal(),p.getResult());
        return pageBean;
    }
}
