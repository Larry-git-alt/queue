package cn.queue.online_judge.controller;

import cn.queue.common.domain.CommonResult;
import cn.queue.online_judge.pojo.AssessmentPoints;
import cn.queue.online_judge.pojo.Module;
import cn.queue.online_judge.service.AssessmentPointsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/assessmentpoints")
public class AssessmentPointsController {
    @Autowired
    private AssessmentPointsService assessmentPointsService;

    @GetMapping
    public CommonResult getById(Long userId,Long comId){
        log.info("根据userid和comId查询最新测试点,userid:{},comId{}",userId,comId);
        List<AssessmentPoints> assessmentPoints = assessmentPointsService.getById(userId,comId);
        return CommonResult.success(assessmentPoints);
    }

}
