package ink.ulricj.entity;

/**
 * created by Ulric on 2018/2/26
 */

public class LabelAndNumber {
    private Long id; // 主键ID
    private String labelName; // 本对象所代表的标签名
    private String number; // 本对象所代表的标签在某页面上的个数
    private Url url; // 本对象所代表的标签所属的页面

    /* ===== get set ===== */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    /* === equals()、hashCode()、toString() === */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LabelAndNumber that = (LabelAndNumber) o;

        if (getLabelName() != null ? !getLabelName().equals(that.getLabelName()) : that.getLabelName() != null)
            return false;
        if (getNumber() != null ? !getNumber().equals(that.getNumber()) : that.getNumber() != null) return false;
        return getUrl() != null ? getUrl().equals(that.getUrl()) : that.getUrl() == null;
    }

    @Override
    public int hashCode() {
        int result = getLabelName() != null ? getLabelName().hashCode() : 0;
        result = 31 * result + (getNumber() != null ? getNumber().hashCode() : 0);
        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
        return result;
    }
}
