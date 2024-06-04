package cn.queue.online_judge.controller;

import cn.queue.common.domain.CommonResult;
import cn.queue.online_judge.pojo.PageBean;
import cn.queue.online_judge.pojo.Problem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.queue.online_judge.service.ProblemService;

import java.util.List;

/**
 * 题目Controller
 */
@Slf4j
@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @PostMapping("/create")
    public CommonResult create(@RequestBody Problem problem){
         log.info("新增题目,problem:{}",problem);
         problemService.create(problem);
         return CommonResult.success();
    }

//    @GetMapping("/list")
//    public CommonResult getByTag(String tags){
//        log.info("根据标签查询题目,tags:{}",tags);
//        List<Problem> problems = problemService.getByTag(tags);
//        return CommonResult.success(problems);
//    }

    @GetMapping("/{id}")
    public CommonResult getById(@PathVariable Long id){
        log.info("根据id查询题目,id:{}",id);
        Problem problem = problemService.getById(id);
        return CommonResult.success(problem);
    }


//    @GetMapping("/title")
//    public CommonResult getByTitle(String title){
//        log.info("根据标题查询题目,title:{}",title);
//        List<Problem> problems = problemService.getByTitle(title);
//        return CommonResult.success(problems);
//    }

    @GetMapping
    public CommonResult page(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             String tags,String title,Short difficulty){
        log.info("分页查询，参数：{},{},{},{},{}",page,pageSize,tags,title,difficulty);
        //调用service分页查询
        PageBean pageBean = problemService.page(page,pageSize,tags,title,difficulty);
        return CommonResult.success(pageBean);
    }

//    @GetMapping("/difficulty")
//    public CommonResult getByDifficulty( Short difficulty){
//        log.info("根据难度查询题目,difficulty:{}",difficulty);
//        List<Problem> problems = problemService.getByDifficulty(difficulty);
//        return CommonResult.success(problems);
//    }

    @DeleteMapping("/{ids}")
    public CommonResult delete(@PathVariable List<Long> ids){
        log.info("批量删除操作,ids:{}",ids);
        problemService.delete(ids);
        return CommonResult.success();
    }



    @PutMapping
    public CommonResult update(@RequestBody Problem problem){
        log.info("修改题目信息：{}",problem);
        problemService.update(problem);
        return CommonResult.success();
    }




}
