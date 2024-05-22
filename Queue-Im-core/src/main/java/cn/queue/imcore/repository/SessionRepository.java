package cn.queue.imcore.repository;
import cn.queue.domain.entity.FriendsEntity;
import cn.queue.domain.entity.GroupEntity;
import cn.queue.imcore.service.IFriendsService;
import cn.queue.imcore.service.IGroupService;
import cn.queue.imcore.vo.SessionVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

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
   public List<SessionVO> getSessionVOList(Long userId){
       List<SessionVO> sessionVOS = new ArrayList<>();
       List<FriendsEntity> friendlist = friendsService.getList(userId);
       List<GroupEntity> groupList = groupService.getGroupList(userId);
       friendlist.forEach(
               friendsEntity -> transformSession(friendsEntity.getId(), friendsEntity.getRemark(), friendsEntity.getPhoto(), sessionVOS)
       );
       groupList.forEach(
               groupEntity -> transformSession(groupEntity.getId(), groupEntity.getGroupName(), groupEntity.getPhoto(), sessionVOS)
       );
       return sessionVOS;
   }

    private  void transformSession(Long groupEntity, String groupEntity1, String groupEntity2, List<SessionVO> sessionVOS) {
        SessionVO sessionVO = SessionVO.builder()
                .id(groupEntity)
                .name(groupEntity1)
                .photo(groupEntity2)
                .build();
        sessionVOS.add(sessionVO);
    }
}
