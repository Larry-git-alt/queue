package cn.queue.imcore.handler.impl;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.domain.valueObj.ImMsgCodeEnum;
import cn.queue.imcore.handler.ImHandlerFactory;
import cn.queue.imcore.handler.SimplyHandler;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Larry
 * @Date: 2024 /05 /14 / 14:17
 * @Description: 消息包处理工厂，对不同类型的消息进行不同的处理
 */
@Component
public class ImHandlerFactoryImpl implements ImHandlerFactory, InitializingBean {


    private static final Map<Integer, SimplyHandler> simplyHandlerMap = new HashMap<>();
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public String test() {
        return "testedta";
    }

    @Override
    public void doMsgHandler(ChannelHandlerContext channelHandlerContext, ImMsgEntity imMsg) {
        SimplyHandler simplyHandler = simplyHandlerMap.get(imMsg.getCode());
        if (simplyHandler == null) {
            throw new IllegalArgumentException("msg code is error,code is :" + imMsg.getCode());
        }
        simplyHandler.handler(channelHandlerContext, imMsg);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        simplyHandlerMap.put(ImMsgCodeEnum.IM_USER_MSG.getCode(),applicationContext.getBean(UserMessageHandler.class));
//        simplyHandlerMap.put(ImMsgCodeEnum.IM_HEARTBEAT_MSG.getCode(), applicationContext.getBean(HeartBeatHandler.class));
//        simplyHandlerMap.put(ImMsgCodeEnum.IM_GROUP_MSG.getCode(),applicationContext.getBean(GroupMessageHandler.class));
    }


}
