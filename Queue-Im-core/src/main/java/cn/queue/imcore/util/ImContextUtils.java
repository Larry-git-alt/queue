package cn.queue.imcore.util;

import io.netty.channel.ChannelHandlerContext;
/**
 * @author: Larry
 * @Date: 2024 /05 /04 / 0:16
 * @Description:
 */
public class ImContextUtils {

    public static Integer getGroupId(ChannelHandlerContext ctx) {
        return ctx.attr(ImContextAttr.GROUP_ID).get();
    }

    public static void setGroupId(ChannelHandlerContext ctx, int groupId) {
        ctx.attr(ImContextAttr.GROUP_ID).set(groupId);
    }

    public static void setAppId(ChannelHandlerContext ctx, int appId) {
        ctx.attr(ImContextAttr.APP_ID).set(appId);
    }

    public static Integer getAppId(ChannelHandlerContext ctx) {
        return ctx.attr(ImContextAttr.APP_ID).get();
    }

    public static void setUserId(ChannelHandlerContext ctx, Long userId) {
        ctx.attr(ImContextAttr.USER_ID).set(userId);
    }

    public static Long getUserId(ChannelHandlerContext ctx) {
        return ctx.attr(ImContextAttr.USER_ID).get();
    }

    public static void removeUserId(ChannelHandlerContext ctx) {
        ctx.attr(ImContextAttr.USER_ID).remove();
    }

    public static void removeAppId(ChannelHandlerContext ctx) {
        ctx.attr(ImContextAttr.APP_ID).remove();
    }

}
