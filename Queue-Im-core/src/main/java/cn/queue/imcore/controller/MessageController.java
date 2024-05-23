package cn.queue.imcore.controller;

import cn.queue.common.annnotation.LarryController;
import cn.queue.domain.dto.GetMessageDTO;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.imcore.service.IMessageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /23 / 19:46
 * @Description:
 */
@LarryController
public class MessageController {
    @Resource
    private IMessageService messageService;
    @GetMapping("/getMessage")
    public List<ImMsgEntity> imMsgEntities(){
        GetMessageDTO getMessageDTO = new GetMessageDTO();
        getMessageDTO.setTargetId(312121212123L);
        getMessageDTO.setUserId(111122221212L);
        getMessageDTO.setCode(1003);
       return messageService.getHistoryMessage(getMessageDTO);
    }
}
