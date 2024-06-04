package cn.queue.online_judge.controller;

import cn.queue.common.domain.CommonResult;
import cn.queue.online_judge.pojo.QuestionPack;
import cn.queue.online_judge.service.JudgeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JudgeController {

    @Resource
    private JudgeService judgeService;

    @PostMapping("/normalJudge")
    public CommonResult normalJudge(@RequestBody QuestionPack questionPack) {
        judgeService.normalJudge(questionPack);
        return CommonResult.success();
    }
}
