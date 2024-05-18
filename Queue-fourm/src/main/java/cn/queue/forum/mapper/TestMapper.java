package cn.queue.forum.mapper;

import cn.queue.forum.domain.Test;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.Mapping;

/**
 * @author 马兰友
 * @Date: 2024/05/18/16:45
 */
@Mapper
public interface TestMapper extends BaseMapper<Test> {
}
