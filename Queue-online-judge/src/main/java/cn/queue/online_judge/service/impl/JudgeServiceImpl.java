package cn.queue.online_judge.service.impl;

import cn.queue.common.exception.define.BizException;
import cn.queue.common.util.HttpUtils;
import cn.queue.online_judge.constant.ThreadConstant;
import cn.queue.online_judge.mapper.ComRankMapper;
import cn.queue.online_judge.mapper.RelationMapper;
import cn.queue.online_judge.pojo.*;
import cn.queue.online_judge.service.AnswerService;
import cn.queue.online_judge.service.CommonService;
import cn.queue.online_judge.service.JudgeService;
import cn.queue.online_judge.utils.ThreadPoolUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    @Autowired
    private RelationMapper relationMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ComRankMapper comRankMapper;

    @Override
    public void normalJudge(QuestionPack questionPack) {
        threadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    toNorJudge(questionPack);
                } catch (Exception e) {
                    throw new BizException(500, ThreadConstant.JUDGER_ERROR);
                }
            }
        });


    }

    @Override
    public void comJudge(QuestionPack questionPack,Long comId) {
        threadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    toComJudge(questionPack,comId);
                } catch (Exception e) {
                    throw new BizException(500, ThreadConstant.JUDGER_ERROR);
                }
            }
        });
    }


    public void toNorJudge(QuestionPack questionPack) throws InterruptedException {
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
                Long userId = 1L;
                answer.setUserId(userId);
                Thread.sleep(500);

                //如果全过
                double persent = (double)answer.getPass()/answer.getTotal();
                if (persent == 1){
                    Relation relation = new Relation();
                    relation.setUserId(userId);
                    relation.setProblemId(questionPack.getQusId());
                    relationMapper.insert(relation);
                }

                //  持久化数据
                answerService.create(answer);

                stringRedisTemplate.opsForValue().set(key, ThreadConstant.FREE_STATUS);
                return;
            }
        }
    }



    public void toComJudge(QuestionPack questionPack,Long comId) throws InterruptedException {
        for (int i = 1; i <= 3; i ++ ) {
            String key = "judger0" + i;
            String status = stringRedisTemplate.opsForValue().get(key);
            if (status.equals(ThreadConstant.OCCUPIED_STATUS)) {
                i %= 3;

            } else if (status.equals(ThreadConstant.FREE_STATUS)) {
                stringRedisTemplate.opsForValue().set(key, ThreadConstant.OCCUPIED_STATUS);

                String url = "http://" + key + "/judge";

                AssessmentPoints assessmentPoints = (AssessmentPoints) HttpUtils.doPost(url, questionPack);
                assessmentPoints.setComId(comId);
                // TODO 改为动态用户
                Long userId = 1L;
                assessmentPoints.setUserId(userId);
                Thread.sleep(500);

                int totalScore = commonService.judgeScore(comId,userId);
                LambdaQueryWrapper<ComRank> lqw = new LambdaQueryWrapper<>();
                ComRank comRank = comRankMapper.selectOne(lqw.eq(ComRank::getComId,comId).eq(ComRank::getUserId,userId));
                comRank.setTotalScore(totalScore);
                comRankMapper.update(comRank,lqw);


                //  持久化数据
                stringRedisTemplate.opsForValue().set(key, ThreadConstant.FREE_STATUS);
                return;
            }
        }
    }
}
