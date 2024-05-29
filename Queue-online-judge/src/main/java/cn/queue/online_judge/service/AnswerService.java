package cn.queue.online_judge.service;

import cn.queue.online_judge.pojo.Answer;
import cn.queue.online_judge.pojo.PageBean;

public interface AnswerService {
    Answer getByAnsId(Integer id);

    PageBean page(Integer page, Integer pageSize, Integer userId, Integer proId);

    void create(Answer answer);
}
