package cn.queue.imcore.service;

import cn.queue.domain.dto.GetMessageDTO;
import cn.queue.domain.entity.ImMsgEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /23 / 11:27
 * @Description:
 */
public interface IMessageService {
    void updateRead(Long targetId, LocalDateTime time);
    void addMessage(ImMsgEntity imMsg);
    List<ImMsgEntity> getHistoryMessage(GetMessageDTO getMessageDTO);
}
