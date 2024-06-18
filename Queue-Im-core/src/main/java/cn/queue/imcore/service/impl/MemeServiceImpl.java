package cn.queue.imcore.service.impl;

import cn.hutool.core.date.DateTime;
import cn.queue.domain.entity.MemeEntity;
import cn.queue.domain.vo.MemeVO;
import cn.queue.imcore.dao.IMemeDao;
import cn.queue.imcore.service.IMemeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


@Service
@Slf4j
public class MemeServiceImpl implements IMemeService  {

    @Resource
    private IMemeDao memeDao;

    /**
     * 新增表情包
     * @param userId
     * @param name
     * @param content
     * @return
     */
    public String addMeme(Long userId, String name, String content) {

        //先查询用户表情包个数，限制不超过500个
        LambdaQueryWrapper<MemeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemeEntity::getUserId, userId);
        if (memeDao.selectCount(wrapper) >= 500) {
            return "表情包个数超过限制";
        }

        MemeEntity meme = new MemeEntity().builder()
                .userId(userId)
                .name(name)
                .content(content)
                .createTime(new DateTime())
                .updateTime(new DateTime())
                .build();

        memeDao.insert(meme);

        return "表情包添加成功";
    }

    /**
     * 删除表情包
     * @param userId
     * @param memeId
     * @return
     */
    public String deleteMeme(Long userId, Long memeId) {

        LambdaQueryWrapper<MemeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemeEntity::getId, memeId).eq(MemeEntity::getUserId, userId);

        memeDao.delete(wrapper);

        return "表情包删除成功";
    }

    /**
     * 查询用户表情包列表
     * @param userId
     * @return
     */
    public List<MemeVO> getMemeList(Long userId) {

        LambdaQueryWrapper<MemeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemeEntity::getUserId, userId);

        List<MemeEntity> memeList = memeDao.selectList(wrapper)
                .stream()
                .sorted(Comparator.nullsLast(Comparator.comparing(MemeEntity::getCreateTime)))
                .toList();

        List<MemeVO> list = memeList.stream().map(meme -> {
            new MemeVO();
            return MemeVO.builder()
                    .id(meme.getId())
                    .name(meme.getName())
                    .content(meme.getContent()).build();
        }).toList();

        return list;
    }

    /**
     * 获取单个表情包
     * @param userId
     * @param memeId
     * @return
     */
    public String getOneMeme(Long userId, Long memeId) {

        LambdaQueryWrapper<MemeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemeEntity::getId, memeId).eq(MemeEntity::getUserId, userId);

        MemeEntity meme = memeDao.selectOne(wrapper);

        return meme.getContent();
    }

}
