package cn.queue.online_judge.controller;

import cn.queue.common.domain.CommonResult;
import cn.queue.online_judge.pojo.ComRank;
import cn.queue.online_judge.pojo.PageBean;
import cn.queue.online_judge.pojo.Rank;
import cn.queue.online_judge.service.ComRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comrank")
public class ComRankController {
    @Autowired
    private ComRankService comRankService;

//    @GetMapping("/{comId}")
//    public CommonResult rankByComId(@PathVariable Long comId){
//        log.info("根据比赛id查询排行榜,com_id:{}",comId);
//        List<ComRank> comRanks = comRankService.rankByComId(comId);
//        return CommonResult.success(comRanks);
//    }

    @GetMapping("/{comId}")
    public CommonResult page(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @PathVariable Long comId){
        log.info("比赛排行榜分页查询，参数：{},{},{}",page,pageSize,comId);
        //调用service分页查询
        PageBean pageBean = comRankService.page(page,pageSize,comId);
        return CommonResult.success(pageBean);
    }

}
