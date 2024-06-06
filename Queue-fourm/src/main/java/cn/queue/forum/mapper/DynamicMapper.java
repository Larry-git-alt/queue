package cn.queue.forum.mapper;

import cn.queue.forum.domain.dto.DynamicDTO;
import cn.queue.forum.domain.entity.Dynamic;

import cn.queue.forum.domain.vo.DynamicVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author doar
 */
@Mapper
public interface DynamicMapper extends BaseMapper<Dynamic>{

    @Insert("insert into fourm.dynamic(user_id,content, url,type,topic_id,likes, pid, target_id, create_time, update_time, create_by, update_by)" +
            " values (#{userId},#{content},#{url},#{type},#{topicId},#{likes},#{pid},#{targetId},#{createTime},#{updateTime},#{createBy},#{updateBy})")
    void add(Dynamic dynamic);
@Select("select id, user_id, content, url, pid, target_id, likes, create_time, " +
        "update_time, create_by, update_by from fourm.dynamic where pid = #{id} order by create_time")
    List<DynamicVO> selectDynamicChildren(Long id);
@Delete("delete from dynamic where id = #{dynamicId} or pid = #{dynamicId}")
    void deleteByIds(Long dynamicId);
}
