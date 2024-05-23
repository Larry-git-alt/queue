//package cn.queue.imcore;
//import cn.hutool.core.img.Img;
//import cn.queue.domain.entity.ImMsgEntity;
//import cn.queue.domain.event.BaseEvent;
//import cn.queue.imcore.publisher.EventPublisher;
//import jakarta.annotation.Resource;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class QueueImCoreApplicationTests {
// @Resource
// private EventPublisher eventPublisher;
//	@Test
//	void contextLoads() {
//		ImMsgEntity imMsgEntity = new ImMsgEntity();
//		imMsgEntity.setCode(1);
//		imMsgEntity.setContent("adbcefa");
//		eventPublisher.publish("im-group",imMsgEntity);
//	}
//
//}
