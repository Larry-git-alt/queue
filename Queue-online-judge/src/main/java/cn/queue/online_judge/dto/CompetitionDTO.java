package cn.queue.online_judge.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompetitionDTO {
    private Long userId;//id
    @TableField("`title`")
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
    private String proId ;
    private String scores;//每题得分
    private String times;//每题用时
    private Integer isPublic;//是否公开 0不公开 1公开

}
