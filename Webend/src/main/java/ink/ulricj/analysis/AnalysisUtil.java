package ink.ulricj.analysis;

import ink.ulricj.entity.Label;
import ink.ulricj.entity.LabelAndNumber;
import ink.ulricj.entity.LabelType;
import ink.ulricj.entity.Url;
import ink.ulricj.mapper.LabelMapper;
import ink.ulricj.util.SpringContextUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by Ulric on 2018/4/9
 */

public class AnalysisUtil {
    private static LabelMapper labelMapper;

    static {
        labelMapper = (LabelMapper) SpringContextUtil.getBeanById("labelMapper");
    }

    /**
     * 统计所给的 Url 集合的页面中出现的所有标签，及其出现的次数
     *
     * @author Ulric
     * @date 2018/4/9
     */
    public static Map<String, Integer> countLabels(List<Url> urlList) {
        // 键为标签名，值为数量
        Map<String, Integer> map = new HashMap<>();

        // 遍历 Url 集合
        for (Url url : urlList) {
            // 取出当前 Url 的标签数量集合
            List<LabelAndNumber> l$nList = url.getLabelAndNumberList();

            // 有的页面没有搜集到标签信息
            if (l$nList == null || l$nList.size() <= 0) {
                continue;
            }

            // 遍历标签数量集合
            for (LabelAndNumber l$n : l$nList) {
                // 当前的标签名称
                String labelName = l$n.getLabel().getLabelName();

                // 如果当前的标签名已统计过，则在原来数量基础上增加
                if (map.containsKey(labelName)) {
                    Integer currCount = map.get(labelName); // 已经统计的数量
                    map.put(labelName, Integer.valueOf(l$n.getNumber()) + currCount); // 当前 Url 对象中该标签的数量 + 已经统计的数量
                } else { // 如果是第一次统计到该标签，则直接赋值
                    map.put(labelName, Integer.valueOf(l$n.getNumber()));
                }
            }
        }

        return map;
    }

    /**
     * 统计每一类标签
     *
     * @author Ulric
     * @date 2018/4/9
     */
    public static Map<String, Integer> countLabelByType(List<Url> urlList) {
        // 先将全部标签逐个统计出来，再将标签分类统计
        Map<String, Integer> countLabelMap = countLabels(urlList);

        // 统计标签类别的 Map 集合，键为标签类别名，值为数量
        Map<String, Integer> countTypeMap = new HashMap<>();

        // 初始化统计标签类别集合，总共有9种类别，有的标签没有分类
        LabelType[] labelTypes = LabelType.values();

        // 遍历 标签类别集合，按照9种标签类别先后逐个统计，这样不会遗漏，因为一个标签所属类别可能不止一种
        for (int i = 0; i < labelTypes.length; i++) {
            countTypeMap.put(labelTypes[i].getTypeName(), 0); // 先将标签类别初始化为0个

            // 然后遍历标签集合，逐个判断标签是否属于当前标签类别
            for (Map.Entry<String, Integer> labelEntry : countLabelMap.entrySet()) {
                // 返回的是字符串，如： "1,2,3"，数字代表 LabelType 枚举类对象中的值
                String typeOfThis = labelMapper.judgeTypeByLabelName(labelEntry.getKey());

                // 如果当前标签没有分类就跳过
                if (typeOfThis == null || "".equals(typeOfThis)) {
                    continue;
                }

                // 如果当前的标签（名）属于该类别
                if (typeOfThis.contains(String.valueOf(labelTypes[i].getValue()))) {
                    int numOfCurrLabel = labelEntry.getValue(); // 当前标签的数量
                    int numOfCurrType = countTypeMap.get(labelTypes[i].getTypeName()); // 当前标签类别的数量
                    countTypeMap.put(labelTypes[i].getTypeName(), numOfCurrLabel + numOfCurrType);
                }
            }

        }
//        countLabelMap.put("Unclassified", 0); // 最后添加一个自定义的 “无类别” 类，除去上面的几种类别之外的标签就算入 “无类别”


        return countTypeMap;
    }
}
