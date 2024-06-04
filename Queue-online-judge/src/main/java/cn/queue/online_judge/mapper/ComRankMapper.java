package cn.queue.online_judge.mapper;

import cn.queue.online_judge.pojo.ComRank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ComRankMapper {
    /**
     * 根据比赛id排行
     * @param comId
     * @return
     */
    @Select("select * from com_rank where com_id = #{comId} order by total_score desc,total_time asc")
    List<ComRank> rankByComId(Long comId);
}
