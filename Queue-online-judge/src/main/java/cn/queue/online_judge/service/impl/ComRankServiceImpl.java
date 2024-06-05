package cn.queue.online_judge.service.impl;

import cn.queue.online_judge.mapper.ComRankMapper;
import cn.queue.online_judge.mapper.CompetitionMapper;
import cn.queue.online_judge.pojo.ComRank;
import cn.queue.online_judge.pojo.Competition;
import cn.queue.online_judge.pojo.PageBean;
import cn.queue.online_judge.pojo.Rank;
import cn.queue.online_judge.service.ComRankService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ComRankServiceImpl implements ComRankService {
    @Autowired
    private ComRankMapper comRankMapper;

    @Autowired
    private CompetitionMapper competitionMapper;
    //    @Override
//    public List<ComRank> rankByComId(Long comId) {
//        return comRankMapper.rankByComId(comId);
//    }
    @Override
    public PageBean page(Integer page, Integer pageSize, Long comId) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、执行查询
        List<ComRank> comRanks = comRankMapper.rankByComId(comId);
        Page<ComRank> cr = (Page<ComRank>) comRanks;

        //3、封装PageBean对象
        PageBean pageBean = new PageBean((int)cr.getTotal(),cr.getResult());
        return pageBean;
    }

    @Override
    public void create(Long comId, Long userId) {
        ComRank comRank = new ComRank();
        LambdaQueryWrapper<Competition> lqw = new LambdaQueryWrapper<>();
        Competition competition = competitionMapper.selectOne(lqw.eq(Competition::getId,comId));
        //交卷时间默认比赛截止时间
        comRank.setUpdateTime(competition.getEndTime());

        long hour = ChronoUnit.HOURS.between(competition.getStartTime(), competition.getEndTime());
        long minute = ChronoUnit.MINUTES.between(competition.getStartTime(), competition.getEndTime());
        long seconds = ChronoUnit.SECONDS.between(competition.getStartTime(), competition.getEndTime());

        String result = hour + "h" + minute + "min" + seconds + "s";
        comRank.setTotalTime(result);
    }

}
