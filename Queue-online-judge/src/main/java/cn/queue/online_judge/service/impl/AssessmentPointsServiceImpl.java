package cn.queue.online_judge.service.impl;

import cn.queue.online_judge.mapper.AssessmentPointsMapper;
import cn.queue.online_judge.pojo.AssessmentPoints;
import cn.queue.online_judge.service.AssessmentPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssessmentPointsServiceImpl implements AssessmentPointsService {
   @Autowired
   private AssessmentPointsMapper assessmentPointsMapper;

    @Override
    public List<AssessmentPoints> getById(Long userId, Long comId) {
        return assessmentPointsMapper.getById(userId,comId);
    }




}
