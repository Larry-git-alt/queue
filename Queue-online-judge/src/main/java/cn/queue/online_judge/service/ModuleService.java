package cn.queue.online_judge.service;

import cn.queue.online_judge.pojo.Module;

import java.util.List;

public interface ModuleService {
    Module getById(Integer id);


    void create(Module module);
}
