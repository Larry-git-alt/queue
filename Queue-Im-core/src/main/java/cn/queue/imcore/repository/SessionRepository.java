package cn.queue.imcore.repository;
import cn.queue.cache.ChannelHandlerContextCache;
import cn.queue.domain.dto.GetMessageDTO;
import cn.queue.domain.entity.FriendsEntity;
import cn.queue.domain.entity.GroupEntity;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.domain.valueObj.CacheConstant;
import cn.queue.domain.valueObj.ImMsgCodeEnum;
import cn.queue.domain.vo.SessionVO;
import cn.queue.imcore.cache.MessageCache;
import cn.queue.imcore.service.IFriendsService;
import cn.queue.imcore.service.IGroupService;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /20 / 11:14
 * @Description:
 */
@Repository
public class SessionRepository {
    @Resource
    private IFriendsService friendsService;
    @Resource
    private IGroupService groupService;
    @Resource
    private MessageCache messageCache;
   public List<SessionVO> getSessionVOList(Long userId){
       List<SessionVO> sessionVOS = new ArrayList<>();
       List<FriendsEntity> friendlist = friendsService.getList(userId);
       List<GroupEntity> groupList = groupService.getGroupList(userId);

       friendlist.forEach(

               friendsEntity ->{
                   Integer isOnline = ChannelHandlerContextCache.get(friendsEntity.getToId()) ==null ? 0 : 1;
                   GetMessageDTO getMessageDTO = GetMessageDTO.builder()
                           .userId(userId)
                           .targetId(friendsEntity.getToId())
                           .code(ImMsgCodeEnum.IM_USER_MSG.getCode())
                           .build();
                   ImMsgEntity lastMessage = messageCache.getLastMessage(getMessageDTO);
                   String content = lastMessage !=null ? lastMessage.getContent() : null;
                   String time = lastMessage !=null ? lastMessage.getCreateTime() :null;
                   transformSession(friendsEntity.getToId(),getMessageDTO.getCode(), friendsEntity.getRemark(), friendsEntity.getPhoto(),content,time,sessionVOS,isOnline);
               }

       );
       groupList.forEach(
               groupEntity -> {
                   GetMessageDTO getMessageDTO = GetMessageDTO.builder()
                           .userId(userId)
                           .targetId(groupEntity.getId())
                           .code(ImMsgCodeEnum.IM_GROUP_MSG.getCode())
                           .build();
                   ImMsgEntity lastMessage = messageCache.getLastMessage(getMessageDTO);
                   transformSession(groupEntity.getId(),getMessageDTO.getCode(),groupEntity.getGroupName(), groupEntity.getPhoto(),lastMessage.getContent(),lastMessage.getCreateTime(), sessionVOS,1);
               }
       );
       return sessionVOS;
   }

    private void transformSession(Long id, Integer type, String name, String photo, String content, String time, List<SessionVO> sessionVOS,Integer isOnline) {
        SessionVO sessionVO = SessionVO.builder()
                .toId(id)
                .type(type)
                .name(name)
                .photo(photo)
                .lastMessageContent(content)
                .lastTime(time)
                .isOnline(isOnline)
                .build();
        sessionVOS.add(sessionVO);
    }
}
