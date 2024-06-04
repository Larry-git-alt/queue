package cn.queue.online_judge.service;

import cn.queue.online_judge.pojo.Course;
import cn.queue.online_judge.pojo.PageBean;

import java.util.List;

public interface CourseService {
    Course getById(Long id);

    void create(Course course);

    PageBean page(Integer page, Integer pageSize);

    boolean unlockCourse(Long courseId, Long userId);


    //  List<Module> getMOById(Integer id);
}
