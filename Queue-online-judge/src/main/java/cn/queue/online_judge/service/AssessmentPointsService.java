package cn.queue.online_judge.service;

import cn.queue.online_judge.pojo.AssessmentPoints;

import java.util.List;

public interface AssessmentPointsService {
    List<AssessmentPoints> getById(Long userId, Long comId);
}
