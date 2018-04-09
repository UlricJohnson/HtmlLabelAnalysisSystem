package ink.ulricj.pipeline;

import ink.ulricj.entity.Url;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * create by Ulric on 2018/3/16
 */

//@Component
public interface Pipeline {
    /**
     * 保存 Url 和 LabelAndNumber 集合，外部方法只调用这个就行了
     *
     * @author Ulric
     * @date 2018/4/8
     */
    void save(Url url);

    /**
     * 获取数据库中所有的 Url 及其对应的 LabelAndNumber
     *
     * @author Ulric
     * @date 2018/4/7
     */
    List<Url> selectAllUrl();


    /***** =====  ===== *****/


}
