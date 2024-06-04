package cn.queue.online_judge.service;

import cn.queue.online_judge.pojo.Answer;
import cn.queue.online_judge.pojo.PageBean;

public interface AnswerService {
    Answer getByAnsId(Long id);

    PageBean page(Integer page, Integer pageSize, Long userId, Long proId);

    void create(Answer answer);
}
