package cn.queue.imcore.publisher;

import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.domain.pack.MessageReadContent;
import com.alibaba.fastjson.JSON;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author: Larry
 * @Date: 2024 /06 /05 / 21:51
 * @Description:
 */
@Component
@Slf4j
public class AckPublish {
    @Setter(onMethod_ = @Autowired)
    private RocketMQTemplate rocketmqTemplate;

    /**
     * 普通消息
     *
     * @param topic   主题
     */
    public void publish(String topic, MessageReadContent messageReadContent) {
        try {
            String mqMessage = JSON.toJSONString(messageReadContent);
            log.info("发送MQ消息 topic:{} message:{}", topic, mqMessage);
            rocketmqTemplate.convertAndSend(topic, mqMessage);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, JSON.toJSONString(messageReadContent), e);
            // 大部分MQ发送失败后，会需要任务补偿
        }
    }

    /**
     * 延迟消息
     *
     * @param topic          主题
     * @param message        消息
     * @param delayTimeLevel 延迟时长
     */
//    public void publishDelivery(String topic, ImMsgEntity message, int delayTimeLevel) {
//        try {
//            String mqMessage = JSON.toJSONString(message);
//            log.info("发送MQ延迟消息 topic:{} message:{}", topic, mqMessage);
//            rocketmqTemplate.syncSend(topic, MessageBuilder.withPayload(message).build(), 1000, delayTimeLevel);
//        } catch (Exception e) {
//            log.error("发送MQ延迟消息失败 topic:{} message:{}", topic, JSON.toJSONString(message), e);
//            // 大部分MQ发送失败后，会需要任务补偿
//        }
//    }
}
