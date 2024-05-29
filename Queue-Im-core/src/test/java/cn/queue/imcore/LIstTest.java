package cn.queue.imcore;

import cn.queue.domain.dto.GetMessageDTO;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.imcore.service.IMessageService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /23 / 19:40
 * @Description:
 */
@SpringBootTest
public class LIstTest {
    @Resource
    private IMessageService messageService;
    @Test
    public void test(){
        GetMessageDTO getMessageDTO = new GetMessageDTO();
        getMessageDTO.setTargetId(22L);
        getMessageDTO.setUserId(33L);
        getMessageDTO.setCode(1003);
        List<ImMsgEntity> historyMessage = messageService.getHistoryMessage(getMessageDTO);
        System.out.println(historyMessage.get(0));
    }
}
