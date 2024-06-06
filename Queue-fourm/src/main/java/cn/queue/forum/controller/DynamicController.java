package cn.queue.forum.controller;

import cn.queue.common.annnotation.LarryController;
import cn.queue.forum.domain.dto.DynamicDTO;
import cn.queue.forum.domain.dto.PageDTO;
import cn.queue.forum.domain.vo.DynamicVO;
import cn.queue.forum.service.DynamicService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**  动态 / 题解 / 讨论
 * @author doar
 */
@LarryController
public class DynamicController {

    @Resource
    public DynamicService dynamicService;

    /**
     * 添加动态 / 题解 / 讨论
     *
     * @param dynamicDTO 动态
     * @return 添加结果
     */
    @PostMapping("/addDynamic")
    public String addDynamic(@RequestBody DynamicDTO dynamicDTO) {
        return dynamicService.addDynamic(dynamicDTO);
    }

    /**
     * 查询所有动态
     *
     * @param pageDTO 分页
     * @return 动态列表
     */
    @PostMapping("/getDynamic")
    public List<DynamicVO> getDynamic(@RequestBody PageDTO pageDTO) {
        return dynamicService.getDynamic(pageDTO);
    }

    /**
     * 点赞
     *
     * @param dynamicId 动态id
     * @return 结果
     */
    @PostMapping("/belike/{dynamicId}")
    public String belike(@PathVariable Long dynamicId) {
        return dynamicService.belike(dynamicId);
    }

    /**
     * 根据id查询动态 / 题解 / 讨论
     *
     * @param dynamicId 动态id
     * @return 动态
     */
    @PostMapping("/getDynamicById/{dynamicId}")
    public DynamicVO getDynamicById(@PathVariable Long dynamicId) {
        return dynamicService.getDynamicById(dynamicId);
    }

    /**
     * 删除动态
     *
     * @param dynamicId 删除动态的id
     * @return 删除结果
     */
    @DeleteMapping("/deleteDynamicById/{dynamicId}")
    public String deleteDynamicById(@PathVariable Long dynamicId) {
        return dynamicService.deleteDynamicById(dynamicId);
    }

}