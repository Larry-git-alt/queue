package cn.queue.online_judge.service;

import cn.queue.online_judge.pojo.Rank;

import java.util.List;

public interface RankService {
    List<Rank> rankByCoId(Long courseId);

    void updateNums(Long userId, Long courseId);
}
