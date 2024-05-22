package cn.queue.forum.service.ServiceImpl;
import cn.queue.forum.domain.dto.DynamicDTO;
import cn.queue.forum.domain.dto.PageDTO;
import cn.queue.forum.domain.entity.Dynamic;
import cn.queue.forum.domain.vo.DynamicVO;
import cn.queue.forum.domain.vo.UserVO;
import cn.queue.forum.mapper.DynamicMapper;
import cn.queue.forum.service.DynamicService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** 动态
 * @author doar
 */
@Slf4j
@Builder
@Service
public class DynamicServiceImpl implements DynamicService {

@Autowired
public DynamicMapper dynamicMapper;
@Resource
private StringRedisTemplate stringRedisTemplate;

/**
 * 添加动态
 * @author doar
 * @param dynamicDTO 新增动态
 * @return 插入结果
 */
    @Override
    public String addDynamic(DynamicDTO dynamicDTO) {

        //TODO 从线程获取用户ID
        //获取用户ID
        Long userid = 114514L;

        //对数据进行处理
        Long pid = dynamicDTO.getPid();
        if(pid == null) {
            pid = -1L;
        }

        //封装要插入的动态对象
        Dynamic dynamic = Dynamic.builder()
                .userId(userid)
                .content(dynamicDTO.getContent())
                .targetId(dynamicDTO.getTargetId())
                .url(dynamicDTO.getUrl())
                .likes(0L)
                .pid(pid)
                .createTime(LocalDateTime.now())
                .createBy(userid).build();
//        log.info("dynamic:{}",dynamic);

        //插入数据
        dynamicMapper.add(dynamic);

        return "sueccess";
    }


    /**
     * 获取动态
     * @param pageDTO 分页数据
     * @return 动态列表
     */
    @Override
    public List<DynamicVO> getDynamic(PageDTO pageDTO) {

        List<DynamicVO> dynamicVOs = new ArrayList<>();

        Integer page = pageDTO.getPage();
        Integer size = pageDTO.getSize();
        //封装分页条件 查询一级动态
        Page<Dynamic> dynamicPage = new Page<>(page,size);
        Page<Dynamic> dynamicPage1 = dynamicMapper.selectPage(dynamicPage, Wrappers.<Dynamic>lambdaQuery().eq(Dynamic::getPid, -1L).orderByDesc(Dynamic::getCreateTime));

        //遍历一级动态 封装为vo
        for(Dynamic dynamic : dynamicPage1.getRecords()){

            //获取发布人信息 todo
            //User dynamicuser= dynamicMapper.selectById(dynamic.getUserId());
            UserVO userVO = UserVO.builder()
                    .userId(114514L)
                    .nickname("doar")
                    .img("扣1送地狱火.jpg")
                    .build();

            //封装vo
             DynamicVO dynamicVO = DynamicVO.builder()
                     .id(dynamic.getId())
                    .userId(dynamic.getUserId())
                    .content(dynamic.getContent())
                    .url(dynamic.getUrl())
                    .pid(dynamic.getPid())
                    .likes(dynamic.getLikes())
                    .targetId(dynamic.getTargetId())
                    .updateTime(dynamic.getUpdateTime())
                    .createTime(dynamic.getCreateTime())
                    .createBy(dynamic.getCreateBy())
                    .userVO(userVO)
                    .children(new ArrayList<>())
                    .build();

            //查询当前动态的所有回复  封装children
            List<DynamicVO> children = dynamicMapper.selectDynamicChildren(dynamic.getId());
            for (DynamicVO child : children){

                //获取回复发布人信息 todo
                //User dynamicuser= dynamicMapper.selectById(child.getUserId());
                UserVO childrenuser = UserVO.builder()
                        .userId(114514L)
                        .nickname("doar")
                        .img("扣1送地狱火.jpg")
                        .build();
                child.setUserVO(childrenuser);
            }
            dynamicVO.setChildren(children);
            dynamicVOs.add(dynamicVO);
        }

        return dynamicVOs;
    }


    /**
     * 点赞
     * @param dynamicId 动态id
     * @return 点赞结果
     */
    @Override
    public String belike(Long dynamicId) {
        //获取当前用户id  todo
        Long userId = 114514L;
        //拼接key
        String key = "dynamic:likes:"+dynamicId;
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        if(score != null) {
        //已经点过赞
            stringRedisTemplate.opsForZSet().remove(key,userId.toString());
            Dynamic dynamic = dynamicMapper.selectById(dynamicId);
            dynamic.setLikes(dynamic.getLikes()-1);
            dynamicMapper.update(dynamic, Wrappers.<Dynamic>lambdaQuery().eq(Dynamic::getId,dynamicId));
        }else{
            //未点过赞
            stringRedisTemplate.opsForZSet().add(key,userId.toString(),System.currentTimeMillis());
            //点赞
            //更新点赞数
            Dynamic dynamic = dynamicMapper.selectById(dynamicId);
            dynamic.setLikes(dynamic.getLikes()+1);
            dynamicMapper.update(dynamic, Wrappers.<Dynamic>lambdaQuery().eq(Dynamic::getId,dynamicId));

        }
        return "success";
    }

    /**
     * 根据动态id获取动态
     * @param dynamicId 动态id
     * @return 动态
     */
    @Override
    public DynamicVO getDynamicById(Long dynamicId) {
        Dynamic dynamic = dynamicMapper.selectById(dynamicId);
        DynamicVO dynamicVO = DynamicVO.builder().userId(dynamic.getUserId())
                .id(dynamic.getId())
                .content(dynamic.getContent())
                .url(dynamic.getUrl())
                .pid(dynamic.getPid())
                .targetId(dynamic.getTargetId())
                .likes(dynamic.getLikes())
                .updateTime(dynamic.getUpdateTime())
                .createTime(dynamic.getCreateTime())
                .likes(dynamic.getLikes())
                .build();
        return dynamicVO;
    }

    @Override
    public String deleteDynamicById(Long dynamicId) {
        dynamicMapper.deleteByIds(dynamicId);
        return "seccess";
    }


}
