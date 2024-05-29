package cn.queue.imcore.dao;

import cn.queue.domain.dto.SeqResDTO;
import cn.queue.domain.entity.ImConversationSetEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author BanTanger 半糖
 * @Date 2023/4/8 14:32
 */
@Mapper
public interface ImConversationSetMapper extends BaseMapper<ImConversationSetEntity> {

    void readMark(ImConversationSetEntity imConversationSetEntity);

    Long getConversationSetMaxSeq(Integer appId, String userId);

    /**
     * 增量拉取会话消息一次最大条目数
     * @param appId
     * @return
     */
    Long getConversationMaxSeq(Integer appId);
    @Select("select im.im_conversation_set.sequence,im.im_conversation_set.read_sequence from im.im_conversation_set where conversation_id =#{conversationId}")
    SeqResDTO getMessageSeq(String conversationId);
}
