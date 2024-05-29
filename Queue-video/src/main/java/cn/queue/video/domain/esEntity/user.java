package cn.queue.video.domain.esEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.easyes.annotation.IndexField;
import org.dromara.easyes.annotation.IndexId;
import org.dromara.easyes.annotation.IndexName;
import org.dromara.easyes.annotation.rely.FieldType;
import org.dromara.easyes.annotation.rely.IdType;

import java.util.Date;

/**
 * @author Ccoo
 * 2024/5/21
 */
@Data
@EqualsAndHashCode
@IndexName(value = "user")
public class user {

	public static final String INDEX = "user";

	@IndexId(type = IdType.CUSTOMIZE)
	private Integer id;

	@IndexField(fieldType = FieldType.KEYWORD)
	private String name;

	@IndexField(fieldType = FieldType.KEYWORD)
	private String gender;

	@IndexField(fieldType = FieldType.DATE)
	private Date createTime;

	@IndexField(fieldType = FieldType.DATE)
	private Date updateTime;

}
