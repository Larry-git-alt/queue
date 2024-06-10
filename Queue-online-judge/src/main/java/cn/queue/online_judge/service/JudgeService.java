package cn.queue.online_judge.service;

import cn.queue.common.domain.CommonResult;
import cn.queue.online_judge.dto.DebugDTO;
import cn.queue.online_judge.pojo.Answer;
import cn.queue.online_judge.pojo.DebugResult;
import cn.queue.online_judge.pojo.QuestionPack;

public interface JudgeService {


    Answer normalJudge(QuestionPack questionPack) throws InterruptedException;

    DebugResult debug(DebugDTO debugDTO);

    void comJudge(QuestionPack questionPack,Long comId);
}
