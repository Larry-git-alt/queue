package cn.queue.domain.entity;


import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable {
    //记录数
    private long total;
    //当前页数据
    private List<?> records;

    public PageResult(long total, List<?> records) {
        this.total = total;
        this.records = records;
    }

    public PageResult() {
    }

    public long getTotal() {
        return this.total;
    }

    public List<?> getRecords() {
        return this.records;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setRecords(List<?> records) {
        this.records = records;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PageResult)) return false;
        final PageResult other = (PageResult) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getTotal() != other.getTotal()) return false;
        final Object this$records = this.getRecords();
        final Object other$records = other.getRecords();
        if (this$records == null ? other$records != null : !this$records.equals(other$records)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PageResult;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $total = this.getTotal();
        result = result * PRIME + (int) ($total >>> 32 ^ $total);
        final Object $records = this.getRecords();
        result = result * PRIME + ($records == null ? 43 : $records.hashCode());
        return result;
    }

    public String toString() {
        return "PageResult(total=" + this.getTotal() + ", records=" + this.getRecords() + ")";
    }
}
