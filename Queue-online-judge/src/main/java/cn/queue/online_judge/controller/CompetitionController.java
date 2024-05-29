package cn.queue.online_judge.controller;

import cn.queue.common.domain.CommonResult;
import cn.queue.online_judge.pojo.Competition;
import cn.queue.online_judge.service.CompetitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/competition")
public class CompetitionController {
    @Autowired
    private CompetitionService competitionService;



    @PostMapping("/create")
    public CommonResult creat(@RequestBody Competition competition){
        log.info("新增比赛,competition:#{}",competition);
        competitionService.create(competition);
        return CommonResult.success();
    }
}
