package cn.queue.imcore;
import cn.queue.domain.dto.AddRecordDTO;
import cn.queue.domain.event.BaseEvent;
import cn.queue.imcore.publisher.EventPublisher;
import cn.queue.imcore.service.IFriendsService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;

@SpringBootTest
class QueueImCoreApplicationTests {
	 @Resource
	 private EventPublisher eventPublisher;
	 @Resource
	 private IFriendsService friendsService;


	 @Test
	 void contextLoads() {
		 //List<AddRecordDTO> applyList = friendsService.getApplyList(2L);

		 String s = friendsService.addFriend(3L, 1L, "加个好友吧，一起打防，菜狗不嫌弃菜狗的");
		 s = friendsService.receiveAdd(3L, 1L, 2);
		 //boolean friend = friendsService.isFriend(3L, 1L);
		 System.out.println(s);
	 }

}
