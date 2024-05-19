package cn.queue.imcore.domain.aggregate;

import cn.queue.imcore.domain.dto.FriendDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /19 / 17:28
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendsAggregate {
    //好友数量
    private Integer num;
    //好友成员
    private List<FriendDTO> friendDTOS;

}
