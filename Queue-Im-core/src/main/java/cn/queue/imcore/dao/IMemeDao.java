package cn.queue.imcore.dao;

import cn.queue.domain.entity.MemeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemeDao extends BaseMapper<MemeEntity> {
}
