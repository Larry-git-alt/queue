package cn.queue.imcore;
//import cn.hutool.core.img.Img;
//import cn.queue.domain.entity.ImMsgEntity;
//import cn.queue.domain.event.BaseEvent;
//import cn.queue.imcore.publisher.EventPublisher;
import cn.queue.domain.dto.AddRecordDTO;
import cn.queue.domain.vo.AddRecordVO;
import cn.queue.domain.vo.FriendVO;
import cn.queue.imcore.service.IFriendsService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class QueueImCoreApplicationTests {
    @Resource
    private IFriendsService friendsService;

    @Test
    void test() {
        List<AddRecordVO> applyList = friendsService.getApplyList(1L);

        List<FriendVO> friendVOS = friendsService.queryFriendByClazz(1L, 0);

        System.out.println(friendVOS);
    }


}
