package cn.queue.imcore.domain.aggregate;
import cn.queue.imcore.domain.dto.MemberDTO;
import cn.queue.imcore.domain.entity.GroupEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /19 / 13:30
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupInfoAggregate {
    //群信息
    private GroupEntity group;
    //群成员信息
    private List<MemberDTO> memberList;

}
