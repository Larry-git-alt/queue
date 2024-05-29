package cn.queue.online_judge.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Competition {
    private Long id;//id
    private String title;//比赛名称
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;//开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;//结束时间
    private String organizer;//举办人
    private String type;//比赛类型
    private Integer difficulty;//难度
    private String content;//比赛描述
    private String invitationCode;//邀请码
    private Integer isPublic;//是否公开 0不公开 1公开
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间
    private Integer deleted;//是否删除 0未删除 1删除
}
