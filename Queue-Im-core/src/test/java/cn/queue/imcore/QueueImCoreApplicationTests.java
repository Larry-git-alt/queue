package cn.queue.imcore;

import cn.queue.imcore.domain.event.BaseEvent;
import publisher.EventPublisher;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QueueImCoreApplicationTests {
 @Resource
 private EventPublisher eventPublisher;
	@Test
	void contextLoads() {
		BaseEvent<String> stringBaseEvent = new BaseEvent<>();
		stringBaseEvent.setData("test");
		eventPublisher.publish("test-consumer-group",stringBaseEvent);
	}

}
