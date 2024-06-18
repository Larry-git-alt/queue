package cn.queue.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BanTanger 半糖
 * @Date 2023/3/25 12:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//后期拓展多端登陆
public class UserClientDto {

    private Integer appId;

    private String userId;

    private Integer clientType;

    private String imei;

}
