package cn.queue.online_judge.service.impl;

import cn.queue.online_judge.mapper.AnswerMapper;
import cn.queue.online_judge.pojo.Answer;
import cn.queue.online_judge.pojo.PageBean;
import cn.queue.online_judge.pojo.Problem;
import cn.queue.online_judge.service.AnswerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
   @Autowired
   private AnswerMapper answerMapper;

    @Override
    public Answer getByAnsId(Long id) {
        LambdaQueryWrapper<Answer> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Answer::getId,id);
        Answer answer = answerMapper.selectOne(lqw);
        return answer;
      //  return answerMapper.getAnsId(id);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, Long userId, Long proId) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、执行查询
        List<Answer> ansList = answerMapper.list(userId,proId);
        Page<Answer> a = (Page<Answer>) ansList;

        //3、封装PageBean对象
        PageBean pageBean = new PageBean((int) a.getTotal(),a.getResult());
        return pageBean;
    }

    @Override
    public void create(Answer answer) {
        LocalDateTime now = LocalDateTime.now();
        answer.setCreateTime(now);
        answer.setUpdateTime(now);
        answer.setSubmissionTime(now);
        answerMapper.insert(answer);
    }
}
