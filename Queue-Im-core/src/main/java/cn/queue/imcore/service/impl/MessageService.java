package cn.queue.imcore.service.impl;

import cn.queue.domain.dto.GetMessageDTO;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.imcore.cache.MessageCache;
import cn.queue.imcore.dao.IMessageDao;
import cn.queue.imcore.service.IMessageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /23 / 11:27
 * @Description:
 */
@Service
public class MessageService implements IMessageService {
    @Resource
    private MessageCache messageCache;

    @Override
    public List<ImMsgEntity> getHistoryMessage(GetMessageDTO getMessageDTO) {
        return messageCache.getListByTargetId(getMessageDTO);
    }

    @Resource
    private IMessageDao messageDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMessage(ImMsgEntity imMsg) {
        messageCache.addCache(imMsg);
        messageDao.insert(imMsg);
    }

    @Override
    public void updateRead(Long targetId, LocalDateTime time) {
        messageDao.updateIsRead(targetId,time);
    }
}
