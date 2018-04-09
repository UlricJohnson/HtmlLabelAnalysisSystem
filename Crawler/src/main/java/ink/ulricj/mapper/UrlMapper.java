package ink.ulricj.mapper;

import ink.ulricj.entity.Url;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * create by Ulric on 2018/3/22
 */

@Component
public interface UrlMapper {
    /**
     * 单独插入一个 Url 对象的信息
     *
     * @author Ulric
     * @date 2018/4/8
     */
    void insertUrl(Url url);

    /**
     * 获取数据库中所有的 Url 及其对应的 Label$Number
     *
     * @author Ulric
     * @date 2018/4/7
     */
    List<Url> selectAllUrl();

    /**
     * 获取插入 tb_url 表的最新插入的主键 uid（即最大的 uid），用于赋值给 Url 对象中的 Laben$Number 对象
     *
     * @author Ulric
     * @date 2018/4/8
     */
    Long selectLastInsertUId();
}
