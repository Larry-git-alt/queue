package cn.queue.online_judge.service.impl;

import cn.queue.online_judge.mapper.CompetitionMapper;
import cn.queue.online_judge.pojo.Competition;
import cn.queue.online_judge.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CompetitionServiceImpl implements CompetitionService {
   @Autowired
   private CompetitionMapper competitionMapper;


    @Override
    public void create(Competition competition) {
        LocalDateTime now = LocalDateTime.now();
        competition.setCreateTime(now);
        competition.setUpdateTime(now);
        competitionMapper.insert(competition);
    }
}
