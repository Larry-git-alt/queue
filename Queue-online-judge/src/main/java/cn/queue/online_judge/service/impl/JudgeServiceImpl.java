package cn.queue.online_judge.service.impl;

import cn.queue.common.exception.define.BizException;
import cn.queue.common.util.HttpUtils;
import cn.queue.online_judge.constant.ThreadConstant;
import cn.queue.online_judge.pojo.Answer;
import cn.queue.online_judge.pojo.QuestionPack;
import cn.queue.online_judge.service.AnswerService;
import cn.queue.online_judge.service.JudgeService;
import cn.queue.online_judge.utils.ThreadPoolUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Autowired
    private AnswerService answerService;

    @Resource
    private ThreadPoolUtils threadPoolUtils;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void normalJudge(QuestionPack questionPack) {
        threadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    toJudge(questionPack);
                } catch (Exception e) {
                    throw new BizException(500, ThreadConstant.JUDGER_ERROR);
                }
            }
        });


    }


    public void toJudge(QuestionPack questionPack) throws InterruptedException {
        for (int i = 1; i <= 3; i ++ ) {
            String key = "judger0" + i;
            String status = stringRedisTemplate.opsForValue().get(key);
            if (status.equals(ThreadConstant.OCCUPIED_STATUS)) {
                i %= 3;

            } else if (status.equals(ThreadConstant.FREE_STATUS)) {
                stringRedisTemplate.opsForValue().set(key, ThreadConstant.OCCUPIED_STATUS);

                String url = "http://" + key + "/judge";

                Answer answer = (Answer) HttpUtils.doPost(url, questionPack);
               // answer.setSubmissionTime(LocalDateTime.now());
                // TODO 改为动态用户
                answer.setUserId(1L);
                Thread.sleep(500);

                //  持久化数据
                answerService.create(answer);

                stringRedisTemplate.opsForValue().set(key, ThreadConstant.FREE_STATUS);
                return;
            }
        }
    }
}
