package cn.queue.imcore.util;

import io.netty.util.AttributeKey;

/**
 * @author: Larry
 * @Date: 2024 /05 /04 / 0:11
 * @Description:
 */
public class ImContextAttr {

    /**
     * 绑定用户id
     */
    public static AttributeKey<Long> USER_ID = AttributeKey.valueOf("userId");

    /**
     * 绑定appId
     */
    public static AttributeKey<Integer> APP_ID = AttributeKey.valueOf("appId");

    /**
     * 绑定群聊id
     */
    public static AttributeKey<Integer> GROUP_ID = AttributeKey.valueOf("groupId");
}
