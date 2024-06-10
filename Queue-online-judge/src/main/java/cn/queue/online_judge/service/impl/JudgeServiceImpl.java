package cn.queue.online_judge.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.queue.common.domain.CommonResult;
import cn.queue.common.exception.define.BizException;
import cn.queue.common.util.HttpUtils;
import cn.queue.online_judge.constant.ThreadConstant;
import cn.queue.online_judge.dto.DebugDTO;
import cn.queue.online_judge.pojo.Answer;
import cn.queue.online_judge.pojo.DebugResult;
import cn.queue.online_judge.pojo.QuestionPack;
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
import org.springframework.util.ObjectUtils;

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

    @Autowired
    private RelationMapper relationMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ComRankMapper comRankMapper;

    @Override
    public Answer normalJudge(QuestionPack questionPack) throws InterruptedException {

        return toNorJudge(questionPack);
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
    @Override
    public DebugResult debug(DebugDTO debugDTO) {
        int i = new Random().nextInt(3);

        String key = "judge0" + i + ":808" + i;

        String url = "http://" + key + "/debug";

        JSONObject jsonObject = JSONUtil.parseObj(HttpUtils.doPost(url, debugDTO));
        jsonObject = JSONUtil.parseObj(jsonObject.get("data"));

        DebugResult result = DebugResult.builder()
                .output(String.valueOf(jsonObject.get("output")))
                .errorReason(String.valueOf(jsonObject.get("errorReason")))
                .build();

        return result;
    }



    public Answer toNorJudge(QuestionPack questionPack) throws InterruptedException {
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
                String url = "http://" + key + ":808" + i + "/judge";
//                String url = "http://182.92.167.230:1001/judge";
                JSONObject jsonObject = JSONUtil.parseObj(HttpUtils.doPost(url, questionPack));
                jsonObject = JSONUtil.parseObj(jsonObject.get("data"));

                System.out.println("json=>" + jsonObject.toString());

                Answer answer = Answer.builder()
                                            .pass((Integer) jsonObject.get("pass"))
                                                .total((Integer) jsonObject.get("total"))
                                                    .status((String) jsonObject.get("status"))
                                                        .runtime((Integer) jsonObject.get("time"))
                                                            .runspace((Integer) jsonObject.get("memory"))
                                                                .reason(String.valueOf(jsonObject.getObj("reason", " ")))
                                                                    .code(questionPack.getCode())
                                                                        .build();

                System.out.println("answer=>" + answer.toString());
               // answer.setSubmissionTime(LocalDateTime.now());
                // TODO 改为动态用户
                Long userId = 1L;
                answer.setUserId(userId);
                answer.setProId(questionPack.getQusId());
                answer.setLanguage(questionPack.getLanguage());
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
                Integer countEnd = Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get("FREE:" + key)));
                stringRedisTemplate.opsForValue().set("FREE:" + key, String.valueOf(countEnd - 1));
                stringRedisTemplate.opsForValue().set(key, ThreadConstant.FREE_STATUS);
                return answer;

            }
        }
        return null;
    }



    public void toComJudge(QuestionPack questionPack,Long comId) throws InterruptedException {
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

                String url = "http://" + key + ":808" + i + "/judge";

                JSONObject jsonObject = JSONUtil.parseObj(HttpUtils.doPost(url, questionPack));
                jsonObject = JSONUtil.parseObj(jsonObject.get("data"));

                AssessmentPoints assessmentPoints = AssessmentPoints.builder()
                        .pass((Integer) jsonObject.get("pass"))
                        .total((Integer) jsonObject.get("total"))
                        .status((String) jsonObject.get("status"))
                        .runtime((Integer) jsonObject.get("time"))
                        .runspace((Integer) jsonObject.get("memory"))
                        .language(questionPack.getLanguage())
                        .build();
                assessmentPoints.setComId(comId);
                // TODO 改为动态用户
                Long userId = 1L;
                assessmentPoints.setUserId(userId);
                assessmentPoints.setProId(questionPack.getQusId());
                Thread.sleep(500);

                int totalScore = commonService.judgeScore(comId,userId);
                LambdaQueryWrapper<ComRank> lqw = new LambdaQueryWrapper<>();
                ComRank comRank = comRankMapper.selectOne(lqw.eq(ComRank::getComId,comId).eq(ComRank::getUserId,userId));
                comRank.setTotalScore(totalScore);
                comRankMapper.update(comRank,lqw);



                //  持久化数据
                Integer countEnd = Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get("FREE:" + key)));
                stringRedisTemplate.opsForValue().set("FREE:" + key, String.valueOf(countEnd - 1));
                stringRedisTemplate.opsForValue().set(key, ThreadConstant.FREE_STATUS);
                return;
            }
        }
    }
}
