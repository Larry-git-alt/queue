package cn.queue.online_judge.controller;

import cn.queue.common.domain.CommonResult;
import cn.queue.online_judge.pojo.*;
import cn.queue.online_judge.pojo.Module;
import cn.queue.online_judge.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程Controller
 */
@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @GetMapping("/{id}")
    public CommonResult getById(@PathVariable Long id){
        log.info("根据id查询课程,id:{}",id);
        Course course = courseService.getById(id);

        return CommonResult.success(course);

    }

    @PostMapping("/create")
    public CommonResult create(@RequestBody Course course){
        log.info("新增课程,course:{}",course);
        courseService.create(course);
        return CommonResult.success();
    }


    @GetMapping
    public CommonResult page(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("课程分页查询，参数：{},{}",page,pageSize);
        //调用service分页查询
        PageBean pageBean = courseService.page(page,pageSize);
        return CommonResult.success(pageBean);
    }


    @PostMapping
    public CommonResult unlockCourse(Long courseId,Long userId){
        log.info("解锁课程,courseId:{},userId:{}",courseId,userId);
        boolean isSuccess = courseService.unlockCourse(courseId,userId);
        if (isSuccess){
            return CommonResult.success();
        }
        else {
            return CommonResult.fail("解锁失败");
        }

    }


}
