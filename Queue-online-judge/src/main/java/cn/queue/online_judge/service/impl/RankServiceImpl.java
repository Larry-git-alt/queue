package cn.queue.online_judge.service.impl;

import cn.queue.online_judge.mapper.RankMapper;
import cn.queue.online_judge.pojo.PageBean;
import cn.queue.online_judge.pojo.Problem;
import cn.queue.online_judge.pojo.Rank;
import cn.queue.online_judge.service.RankService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankServiceImpl implements RankService {
    @Autowired
    private RankMapper rankMapper;
//    @Override
//    public List<Rank> rankByCoId(Long courseId) {
//            return rankMapper.rankByCoId(courseId);
//    }

    @Override
    public void updateNums(Long userId, Long courseId) {
        if(rankMapper.select(userId,courseId) == null){
              Rank r1 = new Rank();
              r1.setUserId(userId);
              r1.setCourseId(courseId);

              rankMapper.insert(r1);
              Rank r2 = new Rank();
              r2.setUserId(userId);
              r2.setCourseId(0L);
              rankMapper.insert(r2);
        }
        rankMapper.updateNums(userId,courseId);
        // TODO 奖励机制

    }

    @Override
    public PageBean page(Integer page, Integer pageSize, Long courseId) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、执行查询
        List<Rank> rankList = rankMapper.rankByCoId(courseId);
        Page<Rank> r = (Page<Rank>) rankList;

        //3、封装PageBean对象
        PageBean pageBean = new PageBean((int)r.getTotal(),r.getResult());
        return pageBean;
    }
}
