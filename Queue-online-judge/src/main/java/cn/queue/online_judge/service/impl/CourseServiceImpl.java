package cn.queue.online_judge.service.impl;

import cn.queue.online_judge.mapper.CourseMapper;
import cn.queue.online_judge.mapper.ModuleMapper;
import cn.queue.online_judge.mapper.RelationMapper;
import cn.queue.online_judge.pojo.*;
import cn.queue.online_judge.pojo.Module;
import cn.queue.online_judge.service.CourseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private RelationMapper relationMapper;
    @Override
    public Course getById(Integer id) {
        Course course = courseMapper.getById(id);
        List<Integer> modulesIds = relationMapper.getByCoId(id);
        //HashSet<Integer> newModulesIds = new HashSet<>(modulesIds);
        List<Module> modules = new ArrayList<>();

        //modulesIds.forEach(m -> modules.add(moduleMapper.getById(m)));

        for (Integer m : modulesIds) {
            modules.add(moduleMapper.getById(m));
        }

        course.setModules(modules);
        return course;
    }

    @Override
    public void create(Course course) {
        LocalDateTime now = LocalDateTime.now();
        course.setCreateTime(now);
        course.setUpdateTime(now);
        course.setCreateBy("y");
        course.setUpdateBy("y");
        courseMapper.insert(course);

        long courseId = course.getId();
        for (Module module : course.getModules()) {
            relationMapper.createCo_Mo(courseId,module.getId());
        }
    }

    @Override
    public PageBean page(Integer page, Integer pageSize) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、执行查询
        List<Course> courseList = courseMapper.list();
        Page<Course> c = (Page<Course>) courseList;

        //3、封装PageBean对象
        PageBean pageBean = new PageBean(c.getTotal(),c.getResult());
        return pageBean;
    }


//    @Override
//    public List<Module> getMOById(Integer id) {
//        List<Module> modules = moduleMapper.getMoById(id);
//        return modules;
//    }
}
