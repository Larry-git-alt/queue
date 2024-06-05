package cn.queue.online_judge.service.impl;

import cn.queue.common.exception.define.BizException;
import cn.queue.common.util.HttpUtils;
import cn.queue.online_judge.constant.ThreadConstant;
import cn.queue.online_judge.dto.DebugDTO;
import cn.queue.online_judge.pojo.Answer;
import cn.queue.online_judge.pojo.DebugResult;
import cn.queue.online_judge.pojo.QuestionPack;
import cn.queue.online_judge.service.AnswerService;
import cn.queue.online_judge.service.JudgeService;
import cn.queue.online_judge.utils.ThreadPoolUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

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

    @Override
    public DebugResult debug(DebugDTO debugDTO) {
        int i = new Random().nextInt(3);

        String key = "judge0" + i;

        String url = "http://" + key + "/debug";

        DebugResult result = (DebugResult) HttpUtils.doPost(url, debugDTO);

        return result;
    }


    public void toJudge(QuestionPack questionPack) throws InterruptedException {
        for (int i = 1; i <= 3; i ++ ) {
            String key = "judger0" + i;
            String status = stringRedisTemplate.opsForValue().get(key);
            if (status.equals(ThreadConstant.OCCUPIED_STATUS)) {
                i %= 3;

            } else if (status.equals(ThreadConstant.FREE_STATUS)) {
                Integer count = Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get("FREE:" + key)));
                if (count < ThreadConstant.THRESHOLD)
                    stringRedisTemplate.opsForValue().set("FREE:" + key, String.valueOf(count + 1));
                else {
                    stringRedisTemplate.opsForValue().set(key, ThreadConstant.OCCUPIED_STATUS);
                    i %= 3;
                    continue;
                }
                String url = "http://" + key + "/judge";

                Answer answer = (Answer) HttpUtils.doPost(url, questionPack);
               // answer.setSubmissionTime(LocalDateTime.now());
                // TODO 改为动态用户
                answer.setUserId(1L);
                Thread.sleep(500);

                //  持久化数据
                answerService.create(answer);

                Integer countEnd = Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get("FREE:" + key)));
                stringRedisTemplate.opsForValue().set("FREE:" + key, String.valueOf(countEnd - 1));
                stringRedisTemplate.opsForValue().set(key, ThreadConstant.FREE_STATUS);
                return;
            }
        }
    }
}
