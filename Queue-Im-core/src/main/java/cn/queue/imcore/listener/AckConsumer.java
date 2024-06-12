package cn.queue.imcore.listener;

import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.domain.pack.MessageReadContent;
import cn.queue.domain.valueObj.Constants;
import cn.queue.domain.valueObj.ImMsgCodeEnum;
import cn.queue.domain.valueObj.TopicConstant;
import cn.queue.imcore.config.RocketMQConsumerProperties;
import cn.queue.imcore.service.ConversationService;
import cn.queue.util.RedisSequence;
import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: Larry
 * @Date: 2024 /06 /05 / 21:22
 * @Description:
 */
@Configuration
public class AckConsumer implements InitializingBean {
    @Resource
    private RocketMQConsumerProperties rocketMQConsumerProperties;
    @Resource
    private ConversationService conversationService;
    @Resource
    private  RedisSequence redisSequence;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
        mqPushConsumer.setVipChannelEnabled(false);
        //设置我们的namesrv地址
        mqPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameServer());
        //声明消费组
        mqPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroup() + "_" + MessageAddConsumer.class.getSimpleName());
        //每次只拉取一条消息---防止重复消费
        mqPushConsumer.setConsumeMessageBatchMaxSize(1);
        mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        mqPushConsumer.subscribe(TopicConstant.IMAGE_ACK_TOPIC,"");
        mqPushConsumer.setMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            //获得消息体
            String json = new String(msgs.get(0).getBody());
            MessageReadContent messageReadContent = JSON.parseObject(json, MessageReadContent.class);
            //更新消息seq
            conversationService.messageMarkRead(messageReadContent);
//            int retryTimes = msgAckCheckService.getMsgAckTimes(imMsgBody.getMsgId(), imMsgBody.getUserId());
//            LOGGER.info("retryTimes is {},msgId is {}", retryTimes, imMsgBody.getMsgId());
//            if (retryTimes < 0) {
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//            //只支持一次重发
//            if (retryTimes < 2) {
//                msgAckCheckService.recordMsgAck(imMsgBody, retryTimes + 1);
//                msgAckCheckService.sendDelayMsg(imMsgBody);
//                routerHandlerService.sendMsgToClient(imMsgBody);
//            } else {
//                msgAckCheckService.doMsgAck(imMsgBody);
//            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        mqPushConsumer.start();
//        LOGGER.info("mq消费者启动成功,namesrv is {}", rocketMQConsumerProperties.getNameServer());
    }
}

