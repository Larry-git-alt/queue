package cn.queue.video.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ccoo
 * 2024/5/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private String gender;

	private Date createTime;

	private Date updateTime;

}
