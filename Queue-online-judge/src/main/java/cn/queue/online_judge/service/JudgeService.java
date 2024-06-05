package cn.queue.online_judge.service;

import cn.queue.online_judge.dto.DebugDTO;
import cn.queue.online_judge.pojo.DebugResult;
import cn.queue.online_judge.pojo.QuestionPack;

public interface JudgeService {


    void normalJudge(QuestionPack questionPack);

    DebugResult debug(DebugDTO debugDTO);

    void comJudge(QuestionPack questionPack,Long comId);
}
