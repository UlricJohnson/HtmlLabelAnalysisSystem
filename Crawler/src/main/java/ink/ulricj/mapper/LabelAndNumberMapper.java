package ink.ulricj.mapper;

import ink.ulricj.entity.LabelAndNumber;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * create by Ulric on 2018/3/22
 */

@Component
public interface LabelAndNumberMapper {
    /**
     * 插入 Label$Number 集合（在爬取完某个页面后，该页面上的标签及其数量的集合）
     *
     * @author Ulric
     * @date 2018/4/7
     */
    void insertLabelAndNumber(List<LabelAndNumber> labelAndNumberList);
}
