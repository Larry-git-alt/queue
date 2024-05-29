package cn.queue.online_judge.controller;

import cn.queue.common.domain.CommonResult;
import cn.queue.online_judge.pojo.Problem;
import cn.queue.online_judge.pojo.Rank;
import cn.queue.online_judge.service.RankService;
import com.alibaba.nacos.api.model.v2.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rank")
public class RankController {
    @Autowired
    private RankService rankService;


    @GetMapping("/{courseId}")
    public CommonResult getById(@PathVariable Long courseId){
        log.info("根据课程id查询排行榜,course_id:{}",courseId);
        List<Rank> ranks = rankService.rankByCoId(courseId);
        return CommonResult.success(ranks);
    }

    @PutMapping
    public CommonResult update(Long userId,Long courseId){
        //  log.info("更新员工信息 : {}", );
        rankService.updateNums(userId,courseId);
        return CommonResult.success();
    }

}
