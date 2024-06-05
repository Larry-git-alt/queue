package cn.queue.online_judge.controller;

import cn.queue.common.domain.CommonResult;
import cn.queue.online_judge.pojo.Course;
import cn.queue.online_judge.pojo.Module;
import cn.queue.online_judge.pojo.Problem;
import cn.queue.online_judge.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/module")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    @GetMapping("/{id}")
    public CommonResult getById(@PathVariable Long id){
        log.info("根据id查询模块,id:{}",id);
        Module module = moduleService.getById(id);
        return CommonResult.success(module);
    }

    @PostMapping("/create")
    public CommonResult create(@RequestBody Module module){
        log.info("新增模块,module:{}",module);
        moduleService.create(module);
        return CommonResult.success();
    }


    @GetMapping("all")
    public CommonResult getAll(){
        log.info("查询所有模块");
        List<Module> modules = moduleService.getAll();
        return CommonResult.success(modules);
    }


}
