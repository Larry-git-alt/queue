package cn.queue.domain.valueObj;

public enum GroupTypeEnum {

    // 主动申请
    PROACTIVE(0),
    // 受邀请
    INVITED(1);

    private Integer code;

    GroupTypeEnum(Integer code) {
        this.code = code;
    }
    public Integer getCode() {
        return code;
    }
}
