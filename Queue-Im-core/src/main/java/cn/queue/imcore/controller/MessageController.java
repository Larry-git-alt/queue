package cn.queue.imcore.controller;

import cn.queue.common.annnotation.LarryController;
import cn.queue.domain.dto.CreateConversationReq;
import cn.queue.domain.dto.GetMessageDTO;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.domain.pack.MessageReadContent;
import cn.queue.domain.valueObj.CacheConstant;
import cn.queue.domain.valueObj.Constants;
import cn.queue.domain.vo.SessionVO;
import cn.queue.imcore.dao.ImConversationSetMapper;
import cn.queue.imcore.repository.SessionRepository;
import cn.queue.imcore.service.ConversationService;
import cn.queue.imcore.service.IMessageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /23 / 19:46
 * @Description:
 */
@LarryController
public class MessageController {
    @Resource
    private ConversationService conversationService;
    @Resource
    private IMessageService messageService;
    @Resource
    private SessionRepository sessionRepository;
    @GetMapping("/getMessage")
    public List<ImMsgEntity> imMsgEntities(){
        GetMessageDTO getMessageDTO = new GetMessageDTO();
        getMessageDTO.setTargetId(312121212123L);
        getMessageDTO.setUserId(111122221212L);
        getMessageDTO.setCode(1003);
        getMessageDTO.setPageNum(1L);
        getMessageDTO.setPageSize(2L);
       return messageService.getHistoryMessage(getMessageDTO);
    }
    @GetMapping("/session")
    public List<SessionVO> getSessionVo(){
      return sessionRepository.getSessionVOList(3333L);
    }
    @PostMapping("/test")
    public void set(){
        CreateConversationReq createConversationReq = CreateConversationReq
                .builder()
                .appId(1)
                .conversationType(1)
                .fromId("3333")
                .toId("1111")
                .build();
          conversationService.createConversation(createConversationReq);
    }
    @PostMapping("/mark")
    public void messageMarkRead(){
        MessageReadContent messageReadContent = MessageReadContent.builder()
                        .messageSequence(2)
                                .fromId("3333")
                                    .toId("1111")
                                            .conversationType(2)
                                                    .build();
        conversationService.messageMarkRead(messageReadContent);
        System.err.println(conversationService.count(3333L,1111L));
    }
}
