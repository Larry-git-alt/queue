package cn.queue.online_judge.mapper;

import cn.queue.online_judge.pojo.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnswerMapper extends BaseMapper<Answer> {
    /**
     * 根据userid和proid查询
     * @param userId
     * @param proId
     * @return
     */
    List<Answer> list(Long userId, Long proId);
}
