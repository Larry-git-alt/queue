package cn.queue.domain.valueObj;

import lombok.Data;

public enum GroupStatusEnum {

    // 未审核
    NO_AUDIT(0),
    // 通过
    ACCEPT(1),
    // 未通过
    UNACCPET(2);

    private Integer code;

    GroupStatusEnum(Integer code) {
        this.code = code;
    }
    public Integer getCode() {
        return code;
    }
}
