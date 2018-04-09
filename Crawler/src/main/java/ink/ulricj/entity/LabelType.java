package ink.ulricj.entity;

/**
 * HTML 标签所属的类型，共 9 种
 * <p>
 * created by Ulric on 2018/4/8
 */

public enum LabelType {
    METADATA(1, "Metadata（元数据类型）"),     // 元数据类型
    FLOW(2, "Flow（流类型）"),                // 流类型
    SECTIONING(3, "Sectioning（分区类型）"),  // 分区类型
    HEADING(4, "Heading（标题类型）"),        // 标题类型
    PHRASING(5, "Phrasing（短语类型）"),      // 短语类型
    EMBEDDED(6, "Embedded（内嵌类型）"),      // 内嵌类型
    INTERACTIVE(7, "Interactive（交互类型）"),// 交互类型
    PALPABLE(8, "Palpable（可感知类型）"),    // 可感知类型
    SCRIPT_SUPPORTING(9, "Script-Supporting（脚本支持元素）");   // 脚本支持元素

    private int value; // 每个枚举对象代表的值
    private String typeName; // 类型名称，包括中英文

    // 构造函数（默认为private），创建枚举对象时候调用
    LabelType(int value, String typeName) {
        this.value = value;
        this.typeName = typeName;
    }

    public int getValue() { return value; }

    public String getTypeName() {return typeName;}
}
