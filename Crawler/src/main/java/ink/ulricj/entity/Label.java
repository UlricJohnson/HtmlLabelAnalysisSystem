package ink.ulricj.entity;

import java.io.Serializable;

/**
 * created by Ulric on 2018/2/26
 */

public class Label implements Serializable {
    private Long lId; // 主键ID
    private String labelName; // 标签名
    private Boolean isH5Version; // 是否为 HTML5 版本
    private String type; // 标签类型，用枚举类型中对象的值表示，有的标签属于不止一种类型，中间使用逗号隔开

    /**
     * 枚举类，表示该标签的类型
     */
//    enum LabelType {
//        METADATA(1, "Metadata（元数据类型）"),     // 元数据类型
//        FLOW(2, "Flow（流类型）"),                // 流类型
//        SECTIONING(3, "Sectioning（分区类型）"),  // 分区类型
//        HEADING(4, "Heading（标题类型）"),        // 标题类型
//        PHRASING(5, "Phrasing（短语类型）"),      // 短语类型
//        EMBEDDED(6, "Embedded（内嵌类型）"),      // 内嵌类型
//        INTERACTIVE(7, "Interactive（交互类型）"),// 交互类型
//        PALPABLE(8, "Palpable（可感知类型）"),    // 可感知类型
//        SCRIPT_SUPPORTING(9, "Script-Supporting（脚本支持元素）");   // 脚本支持元素
//
//        private int value; // 每个枚举对象代表的值
//        private String typeName; // 类型名称，包括中英文
//
//        // 构造函数（默认为private），创建枚举对象时候调用
//        LabelType(int value, String typeName) {
//            this.value = value;
//            this.typeName = typeName;
//        }
//
//        public int getValue() { return value; }
//
//        public String getTypeName() {return typeName;}
//    }

//    private Set<Label$Number> labelAndNumberSet = new HashSet<>(0); // 初始容量设置为0

    /***** ====== get set ===== *****/
    public Long getlId() { return lId; }

    public void setlId(Long lId) { this.lId = lId; }

    public String getLabelName() { return labelName; }

    public void setLabelName(String labelName) { this.labelName = labelName; }

    public Boolean isH5Version() { return isH5Version; }

    public void setH5Version(Boolean h5Version) { isH5Version = h5Version; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    //    public Set<Label$Number> getLabelAndNumberSet() {
//        return labelAndNumberSet;
//    }
//
//    public void setLabelAndNumberSet(Set<Label$Number> labelAndNumberSet) {
//        this.labelAndNumberSet = labelAndNumberSet;
//    }

    /***** ===== equals、hashCode、toString ===== *****/
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
