package cn.queue.consumer;

import cn.queue.config.RocketMQConsumerProperties;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.domain.valueObj.TopicConstant;
import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author idea
 * @Date: Created in 22:54 2023/5/6
 * @Description
 */
@Configuration
public class ImAckConsumer implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImAckConsumer.class);

    @Resource
    private RocketMQConsumerProperties rocketMQConsumerProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.err.println(rocketMQConsumerProperties.getNameServer());
        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
        mqPushConsumer.setVipChannelEnabled(false);
        //设置我们的namesrv地址
        mqPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameServer());
        //声明消费组
        mqPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroup() + "_" + ImAckConsumer.class.getSimpleName());
        //每次只拉取一条消息
        mqPushConsumer.setConsumeMessageBatchMaxSize(1);
        mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        mqPushConsumer.subscribe(TopicConstant.IMAGE_ADD_TOPIC,"");
        mqPushConsumer.setMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            String json = new String(msgs.get(0).getBody());
            ImMsgEntity imMsgBody = JSON.parseObject(json, ImMsgEntity.class);
            System.out.println(imMsgBody);
//            int retryTimes = msgAckCheckService.getMsgAckTimes(imMsgBody.getMsgId(), imMsgBody.getUserId(), imMsgBody.getAppId());
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
        LOGGER.info("mq消费者启动成功,namesrv is {}", rocketMQConsumerProperties.getNameServer());
    }


}
