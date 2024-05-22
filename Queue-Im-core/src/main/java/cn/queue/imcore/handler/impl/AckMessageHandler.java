package cn.queue.imcore.handler.impl;
import cn.queue.common.util.SnowUtil;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.imcore.handler.SimplyHandler;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.SimpleAssociationHandler;

/**
 * @author: Larry
 * @Date: 2024 /05 /22 / 10:35
 * @Description: ack消息确认类
 */
public class AckMessageHandler implements SimplyHandler {
    @Override
    public void handler(ChannelHandlerContext ctx, ImMsgEntity imMsg) {
         imMsg.setId(SnowUtil.getSnowflakeNextId());
         
    }
}
