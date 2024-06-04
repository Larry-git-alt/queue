package cn.queue.online_judge.service.impl;

import cn.queue.common.domain.CommonResult;
import cn.queue.online_judge.mapper.CompetitionMapper;
import cn.queue.online_judge.mapper.RelationMapper;
import cn.queue.online_judge.pojo.Competition;
import cn.queue.online_judge.pojo.Course;
import cn.queue.online_judge.pojo.PageBean;
import cn.queue.online_judge.pojo.Relation;
import cn.queue.online_judge.service.CompetitionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition> implements CompetitionService {
   @Autowired
   private CompetitionMapper competitionMapper;

   @Autowired
   private RelationMapper relationMapper;


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
}
