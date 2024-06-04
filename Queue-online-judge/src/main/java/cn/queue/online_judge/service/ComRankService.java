package cn.queue.online_judge.service;

import cn.queue.online_judge.pojo.ComRank;
import cn.queue.online_judge.pojo.PageBean;

import java.util.List;

public interface ComRankService {
//    List<ComRank> rankByComId(Long comId);

    PageBean page(Integer page, Integer pageSize, Long comId);
}
