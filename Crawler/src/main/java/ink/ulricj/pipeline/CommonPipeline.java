package ink.ulricj.pipeline;

import ink.ulricj.entity.LabelAndNumber;
import ink.ulricj.entity.Url;
import ink.ulricj.mapper.LabelAndNumberMapper;
import ink.ulricj.mapper.UrlMapper;
import ink.ulricj.util.SpringContextUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * created by Ulric on 2018/3/24
 */

//@Component
public class CommonPipeline implements Pipeline {

    @Resource
    private UrlMapper urlMapper;

    @Resource
    private LabelAndNumberMapper labelAndNumberMapper;

    /***** ===== constructor ===== *****/
    public CommonPipeline() {
        // 注入 mapper 接口的 bean
        this.urlMapper = (UrlMapper) SpringContextUtil.getBeanById("urlMapper");
        this.labelAndNumberMapper = (LabelAndNumberMapper) SpringContextUtil.getBeanById("labelAndNumberMapper");
    }

    /**
     * 保存 Url 和 LabelAndNumber 集合，外部方法只调用这个就行了
     *
     * @author Ulric
     * @date 2018/4/8
     */
    @Override
    public void save(Url url) {
        // 1、插入 Url 信息
        insertUrl(url);

        // 2、获取刚插入的 Url 数据的主键
        Long uId = selectLastInsertUId();

        // 3、将 uid 赋值给 Url 对象中的 LabelAndNumber 对象
        List<LabelAndNumber> labelAndNumberList = url.getLabelAndNumberList();
        if (labelAndNumberList != null & labelAndNumberList.size() > 0) { // 判断处理
            for (LabelAndNumber l$n : labelAndNumberList) {
                l$n.setUrlId(uId);
            }
        } else {
            // 如果该页面没有搜集到标签数据，就只保存 Url 信息
            return;
        }

        // 4、将全部 LabelAndNumber 对象存入数据库
        insertLabelAndNumber(labelAndNumberList);
    }

    /**
     * 获取数据库中所有的 Url 及其对应的 LabelAndNumber
     *
     * @author Ulric
     * @date 2018/4/7
     */
    @Override
    public List<Url> selectAllUrl() { return null; }

    /**
     * 将一个页面的分析结果保存到 mysql 数据库
     *
     * @author Ulric
     * @date 2018/3/22
     */
    private void insertUrl(Url url) { urlMapper.insertUrl(url); }


    /**
     * 插入 tb_url 表的最新插入的主键 uid（即最大的 uid），用于赋值给 Url 对象中的 Laben$Number 对象
     *
     * @author Ulric
     * @date 2018/4/8
     */
    private Long selectLastInsertUId() { return urlMapper.selectLastInsertUId(); }

    /**
     * 插入 LabelAndNumber 集合（在爬取完某个页面后，该页面上的标签及其数量的集合）
     *
     * @author Ulric
     * @date 2018/4/7
     */
    private void insertLabelAndNumber(List<LabelAndNumber> labelAndNumberList) {
        labelAndNumberMapper
                .insertLabelAndNumber(labelAndNumberList);
    }
}
