package cn.queue.online_judge.service.impl;

import cn.queue.online_judge.mapper.ModuleMapper;
import cn.queue.online_judge.mapper.ProblemMapper;
import cn.queue.online_judge.mapper.RelationMapper;
import cn.queue.online_judge.pojo.Module;
import cn.queue.online_judge.pojo.Problem;
import cn.queue.online_judge.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private RelationMapper relationMapper;

    @Autowired
    private ProblemMapper problemMapper;

    @Override
    public Module getById(Integer id) {
        Module module = moduleMapper.getById(id);
        List<Integer> problemIds = relationMapper.getByMoId(id);
        //HashSet<Integer> newProblemIds = new HashSet<>(problemIds);
        List<Problem> problems = new ArrayList<>();
        for (Integer p : problemIds) {
            problems.add(problemMapper.getById(p));
        }
        module.setProblems(problems);
        return module;
    }

    @Override
    public void create(Module module) {
        log.info("{}",module);
        LocalDateTime now = LocalDateTime.now();
        module.setCreateTime(now);
        module.setUpdateTime(now);
        module.setCreateBy("f");
        module.setUpdateBy("f");
        moduleMapper.insert(module);

        long moduleId = module.getId();
        for (Problem problem : module.getProblems()) {
            relationMapper.createMo_Pr(moduleId,problem.getId());
        }
    }


}
