package cn.queue.online_judge.service.impl;

import cn.queue.online_judge.mapper.ComRankMapper;
import cn.queue.online_judge.pojo.ComRank;
import cn.queue.online_judge.pojo.PageBean;
import cn.queue.online_judge.pojo.Rank;
import cn.queue.online_judge.service.ComRankService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComRankServiceImpl implements ComRankService {
    @Autowired
    private ComRankMapper comRankMapper;
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

}
