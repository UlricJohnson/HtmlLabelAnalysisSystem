package ink.ulricj.entity;

/**
 * created by Ulric on 2018/2/26
 */

public class LabelAndNumber {
    private Long lnId; // 主键ID
    private String labelName; // 本对象所代表的标签名
    private String number; // 本对象所代表的标签在某页面上的个数
    private Long urlId; // 对应的 url 的 lnId

    private Label label; //
    private Url url; // 本对象所代表的标签所属的页面

    /*** ===== get set ===== ***/
    public Long getLnId() {
        return lnId;
    }

    public void setLnId(Long lnId) {
        this.lnId = lnId;
    }

    public String getLabelName() { return labelName; }

    public void setLabelName(String labelName) { this.labelName = labelName; }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getUrlId() { return urlId; }

    public void setUrlId(Long urlId) { this.urlId = urlId; }

    public Url getUrl() { return url; }

    public void setUrl(Url url) { this.url = url; }

    public Label getLabel() { return label; }

    public void setLabel(Label label) { this.label = label; }

    /*** === equals()、hashCode()、toString() === ***/
    /*** 使用 Label 类对象作为属性 ***/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LabelAndNumber that = (LabelAndNumber) o;

        if (getNumber() != null ? !getNumber().equals(that.getNumber()) : that.getNumber() != null) return false;
        if (getLabel() != null ? !getLabel().equals(that.getLabel()) : that.getLabel() != null) return false;
        return getUrl() != null ? getUrl().equals(that.getUrl()) : that.getUrl() == null;
    }

    @Override
    public int hashCode() {
        int result = getNumber() != null ? getNumber().hashCode() : 0;
        result = 31 * result + (getLabel() != null ? getLabel().hashCode() : 0);
        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "L&N{'" +
                label.getLabelName() + '\'' +
                ":" + number +
                '}';
    }

    /*** labelName 字符串作为属性 ***/
//    @Override // 字段：labelName、number、url
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Label$Number that = (Label$Number) o;
//
//        if (getLabelName() != null ? !getLabelName().equals(that.getLabelName()) : that.getLabelName() != null)
//            return false;
//        if (getNumber() != null ? !getNumber().equals(that.getNumber()) : that.getNumber() != null) return false;
//        return getUrl() != null ? getUrl().equals(that.getUrl()) : that.getUrl() == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = getLabelName() != null ? getLabelName().hashCode() : 0;
//        result = 31 * result + (getNumber() != null ? getNumber().hashCode() : 0);
//        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return "L&N{'" +
//                labelName + '\'' +
//                ":" + number +
//                '}';
//    }

}
