package cn.queue.online_judge.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.queue.common.domain.CommonResult;
import cn.queue.online_judge.dto.DebugDTO;
import cn.queue.online_judge.dto.QusPackDTO;
import cn.queue.online_judge.pojo.Answer;
import cn.queue.online_judge.pojo.QuestionPack;
import cn.queue.online_judge.service.JudgeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class JudgeController {

    @Resource
    private JudgeService judgeService;

    @PostMapping("/normalJudge")
    public CommonResult<Answer> normalJudge(@RequestBody QuestionPack questionPack) throws InterruptedException {
        return CommonResult.success(judgeService.normalJudge(questionPack));
    }

    @PostMapping("/debug")
    public CommonResult debugJudge(@RequestBody DebugDTO debugDTO) {
        return CommonResult.success(judgeService.debug(debugDTO));
    }




    @PostMapping("/comJudge")
    public CommonResult comJudge(@RequestBody QusPackDTO qusPackDTO) {
        log.info("比赛判分,qusPackDTO:{}",qusPackDTO);
        QuestionPack questionPack = new QuestionPack();
        BeanUtil.copyProperties(qusPackDTO,questionPack);
        judgeService.comJudge(questionPack,qusPackDTO.getComId());
        return CommonResult.success();
    }



}
