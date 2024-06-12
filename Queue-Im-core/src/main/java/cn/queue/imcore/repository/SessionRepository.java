package cn.queue.imcore.repository;
import cn.queue.cache.ChannelHandlerContextCache;
import cn.queue.domain.dto.GetMessageDTO;
import cn.queue.domain.entity.FriendsEntity;
import cn.queue.domain.entity.GroupEntity;
import cn.queue.domain.entity.ImConversationSetEntity;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.domain.valueObj.CacheConstant;
import cn.queue.domain.valueObj.ConversationTypeEnum;
import cn.queue.domain.valueObj.ImMsgCodeEnum;
import cn.queue.domain.vo.SessionVO;
import cn.queue.imcore.cache.MessageCache;
import cn.queue.imcore.service.ConversationService;
import cn.queue.imcore.service.IFriendsService;
import cn.queue.imcore.service.IGroupService;

import cn.queue.util.ThreadPoolUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: Larry
 * @Date: 2024 /05 /20 / 11:14
 * @Description:
 */
@Repository
public class SessionRepository {
    private static final String MODULE_NAME = "GROUP";
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    static {
        THREAD_POOL_EXECUTOR = ThreadPoolUtil.getIoTargetThreadPool(MODULE_NAME);
    }

    @Resource
    private IFriendsService friendsService;
    @Resource
    private IGroupService groupService;
    @Resource
    private MessageCache messageCache;
    @Resource
    private ConversationService conversationService;
   public List<SessionVO> getSessionVOList(Long userId){
       List<SessionVO> sessionVOS = new ArrayList<>();
       List<FriendsEntity> friendlist = friendsService.getList(userId);
       List<GroupEntity> groupList = groupService.getGroupList(userId);
       friendsSession(userId, sessionVOS, friendlist);
       groupSession(userId, sessionVOS, groupList);
       return sessionVOS;
   }
    private void groupSession(Long userId, List<SessionVO> sessionVOS, List<GroupEntity> groupList) {
        groupList.forEach(
                groupEntity -> {
                    GetMessageDTO getMessageDTO = GetMessageDTO.builder()
                            .userId(userId)
                            .targetId(groupEntity.getId())
                            .code(ImMsgCodeEnum.IM_GROUP_MSG.getCode())
                            .build();
                    ImMsgEntity lastMessage = messageCache.getLastMessage(getMessageDTO);
                    Long count = conversationService.count(getMessageDTO.getUserId(),getMessageDTO.getTargetId(), ConversationTypeEnum.GROUP.getCode());
                    transformSession(groupEntity.getId(),getMessageDTO.getCode(),groupEntity.getGroupName(), groupEntity.getPhoto(),lastMessage.getContent(),lastMessage.getCreateTime(), sessionVOS,1,count);
                }
        );
    }

    public CompletableFuture<List<SessionVO>> getSession(Long userId) {
        List<SessionVO> sessionVOS = new ArrayList<>();
        // 定义用于收集CompletableFuture的列表
        List<CompletableFuture<?>> futures = new ArrayList<>();

        // 异步获取朋友列表并处理
        CompletableFuture<Void> userFuture = CompletableFuture.runAsync(
                () -> {
                    List<FriendsEntity> friendlist = friendsService.getList(userId);
                    friendsSession(userId, sessionVOS, friendlist);
                }, THREAD_POOL_EXECUTOR
        );
        futures.add(userFuture); // 将userFuture添加到futures列表

        // 异步获取群组列表并处理
        CompletableFuture<Void> groupFuture = CompletableFuture.runAsync(
                () -> {
                    List<GroupEntity> groupList = groupService.getGroupList(userId);
                    groupSession(userId, sessionVOS, groupList);
                }, THREAD_POOL_EXECUTOR
        );
        futures.add(groupFuture); // 将groupFuture添加到futures列表

        // 使用CompletableFuture.allOf等待所有futures完成
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    // 所有异步操作完成后返回sessionVOS列表
                    return sessionVOS;
                });
    }




    private void friendsSession(Long userId, List<SessionVO> sessionVOS, List<FriendsEntity> friendlist) {
        friendlist.forEach(
                friendsEntity ->{
                    System.out.println("test");
                    Integer isOnline = ChannelHandlerContextCache.get(friendsEntity.getToId()) ==null ? 0 : 1;
                    GetMessageDTO getMessageDTO = GetMessageDTO.builder()
                            .userId(userId)
                            .targetId(friendsEntity.getToId())
                            .code(ImMsgCodeEnum.IM_USER_MSG.getCode())
                            .build();
                    ImMsgEntity lastMessage = messageCache.getLastMessage(getMessageDTO);
                    Long count = conversationService.count(getMessageDTO.getUserId(),getMessageDTO.getTargetId(), ConversationTypeEnum.P2P.getCode());
                    String content = lastMessage !=null ? lastMessage.getContent() : null;
                    String time = lastMessage !=null ? lastMessage.getCreateTime() :null;
                    transformSession(friendsEntity.getToId(),getMessageDTO.getCode(), friendsEntity.getRemark(), friendsEntity.getPhoto(),content,time,sessionVOS,isOnline,count);
                }

        );
    }

    private void transformSession(Long id, Integer type, String name, String photo, String content, String time, List<SessionVO> sessionVOS,Integer isOnline,Long count) {
        SessionVO sessionVO = SessionVO.builder()
                .toId(id)
                .type(type)
                .name(name)
                .photo(photo)
                .lastMessageContent(content)
                .lastTime(time)
                .isOnline(isOnline)
                .noReadMessage(count)
                .build();
        sessionVOS.add(sessionVO);
    }
}
