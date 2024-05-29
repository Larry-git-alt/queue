package cn.queue.online_judge.controller;

import cn.queue.common.domain.CommonResult;
import cn.queue.online_judge.pojo.Answer;
import cn.queue.online_judge.pojo.Course;
import cn.queue.online_judge.pojo.PageBean;
import cn.queue.online_judge.pojo.Problem;
import cn.queue.online_judge.service.AnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @GetMapping("/{id}")
    public CommonResult getByAnsId(@PathVariable Integer id){
        log.info("根据题解id查询题解,id:{}",id);
        Answer answer = answerService.getByAnsId(id);

        return CommonResult.success(answer);

    }

    @GetMapping
    public CommonResult page(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(value = "userId",required = false) Integer userId,
                             @RequestParam(value = "proId",required = false) Integer proId){
        log.info("分页查询，参数：{},{},{},{}",page,pageSize,userId,proId);
        //调用service分页查询
        PageBean pageBean = answerService.page(page,pageSize,userId,proId);
        return CommonResult.success(pageBean);

    }

    @PostMapping("/create")
    public CommonResult create(@RequestBody Answer answer){
        log.info("新增题解,answer:{}",answer);
        answerService.create(answer);
        return CommonResult.success();
    }

}
