package cn.queue.online_judge.service.impl;

import cn.queue.common.domain.CommonResult;
import cn.queue.online_judge.mapper.ComRankMapper;
import cn.queue.online_judge.mapper.CompetitionMapper;
import cn.queue.online_judge.mapper.RelationMapper;
import cn.queue.online_judge.pojo.*;
import cn.queue.online_judge.service.CompetitionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition> implements CompetitionService {
   @Autowired
   private CompetitionMapper competitionMapper;

   @Autowired
   private RelationMapper relationMapper;

   @Autowired
   private ComRankMapper comRankMapper;


    @Override
    public void create(Competition competition,Long userId) {
        LocalDateTime now = LocalDateTime.now();
        competition.setCreateTime(now);
        competition.setUpdateTime(now);
        String invitationCode = UUID.randomUUID().toString();
        competition.setInvitationCode(invitationCode);
        competitionMapper.insert(competition);

        Long comId = competition.getId();
        Relation relation = new Relation();
        relation.setType(1);
        relation.setTypeId(comId);
        relation.setUserId(userId);
        relationMapper.insert(relation);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);

        List<Competition> competitionList = competitionMapper.list();
        Page<Competition> c = (Page<Competition>) competitionList;

        PageBean pageBean = new PageBean((int)c.getTotal(),c.getResult());
        return pageBean;

    }

    @Override
    public boolean verifyCode(Long comId, String invitationCode) {
        LambdaQueryWrapper<Competition> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Competition::getId,comId);
        Competition competition = competitionMapper.selectOne(lqw);
        return competition.getInvitationCode().equals(invitationCode);
    }

    @Override
    public void unlockCom(Long comId, Long userId) {
        Relation relation = new Relation();
        relation.setType(1);
        relation.setTypeId(comId);
        relation.setUserId(userId);
        relationMapper.insert(relation);


    }

    @Override
    public LocalDateTime time(Long comId) {
        LambdaQueryWrapper<Competition> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Competition::getId,comId);
        Competition competition = competitionMapper.selectOne(lqw);
        return competition.getEndTime();
    }

    @Override
    public void submit(Long comId,Long userId) {
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<Competition> lqw = new LambdaQueryWrapper<>();
        Competition competition = competitionMapper.selectOne(lqw.eq(Competition::getId,comId));

        long hour = ChronoUnit.HOURS.between(competition.getStartTime(), now);
        long minute = ChronoUnit.MINUTES.between(competition.getStartTime(), now);
        long seconds = ChronoUnit.SECONDS.between(competition.getStartTime(), now);

        String result = hour + "h" + minute + "min" + seconds + "s";

        LambdaQueryWrapper<ComRank> lqwCR = new LambdaQueryWrapper<>();
        ComRank comRank = comRankMapper.selectOne(lqwCR.eq(ComRank::getComId, comId).eq(ComRank::getUserId, userId));
        comRank.setTotalTime(result);
        comRank.setUpdateTime(now);
        comRankMapper.update(comRank,lqwCR);
    }
}
