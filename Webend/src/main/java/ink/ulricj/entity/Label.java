package ink.ulricj.entity;

import java.io.Serializable;

/**
 * created by Ulric on 2018/2/26
 */

public class Label implements Serializable {
    private Long id; // 主键ID
    private String labelName; // 标签名
    private Boolean isH5Version; // 是否为 HTML5 版本

//    private Set<LabelAndNumber> labelAndNumberSet = new HashSet<>(0); // 初始容量设置为0

    /* ====== get set ===== */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Boolean isH5Version() {
        return isH5Version;
    }

    public void setH5Version(Boolean h5Version) {
        isH5Version = h5Version;
    }

//    public Set<LabelAndNumber> getLabelAndNumberSet() {
//        return labelAndNumberSet;
//    }
//
//    public void setLabelAndNumberSet(Set<LabelAndNumber> labelAndNumberSet) {
//        this.labelAndNumberSet = labelAndNumberSet;
//    }

    /* === equals()、hashCode()、toString() === */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Label label = (Label) o;

        return getLabelName().equals(label.getLabelName());
    }

    @Override
    public int hashCode() {
        return getLabelName().hashCode();
    }

    @Override
    public String toString() {
        return "<" + labelName + "/>\t";
    }
}
