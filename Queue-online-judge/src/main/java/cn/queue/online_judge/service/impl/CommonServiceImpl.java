package cn.queue.online_judge.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.queue.online_judge.mapper.RelationMapper;
import cn.queue.online_judge.pojo.AssessmentPoints;
import cn.queue.online_judge.pojo.Competition;
import cn.queue.online_judge.pojo.Relation;
import cn.queue.online_judge.service.AssessmentPointsService;
import cn.queue.online_judge.service.CommonService;
import cn.queue.online_judge.service.CompetitionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private CompetitionService competitionService;

    @Resource
    private AssessmentPointsService assessmentPointsService;

    @Resource
    private RelationMapper relationMapper;

    public boolean checkCompetition(Long comId, Long userId) {
        Relation relation =
                relationMapper.selectOne(new LambdaQueryWrapper<Relation>()
                        .eq(Relation::getUserId, userId)
                            .eq(Relation::getType, 1)
                                .eq(Relation::getTypeId, comId));
        if (ObjectUtil.isEmpty(relation))
            return false;

        Competition one = competitionService.getOne(new LambdaQueryWrapper<Competition>().eq(Competition::getId, comId));

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime begin = one.getStartTime();
        LocalDateTime end = one.getEndTime();

        return now.isBefore(end) && now.isAfter(begin);
    }

    public boolean checkUnlock(Long comId) {
        Competition one = competitionService.getOne(new LambdaQueryWrapper<Competition>().eq(Competition::getId, comId));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime begin = one.getStartTime();
        return now.isBefore(begin);
    }

    public int judgeScore(Long comId, Long userId) {
        List<AssessmentPoints> points = assessmentPointsService.getById(userId, comId);
        Competition competition = competitionService.getOne(new LambdaQueryWrapper<Competition>().eq(Competition::getId, comId));

        String[] scores = competition.getScores().split(",");

        String[] ids = competition.getProId().split(",");

        int sum = 0;
        for (int i = 0; i < ids.length; i ++ ) {
            for (AssessmentPoints point : points) {
                if (point.getProId() == Long.parseLong(ids[i])) {
                    double persent = (double) point.getPass() / point.getTotal();
                    if (competition.getType().equals("ACM")) {
                        sum += persent == 1 ? Integer.parseInt(scores[i]) : 0;
                    } else if (competition.getType().equals("OI")) {
                        sum += (int) Math.round(persent * Integer.parseInt(scores[i]));
                    }
                }
            }

        }

        return sum;
    }
}
