package ink.ulricj.url$label;

import ink.ulricj.entity.Label;
import ink.ulricj.entity.LabelAndNumber;
import ink.ulricj.entity.Url;
import ink.ulricj.pipeline.Pipeline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * created by Ulric on 2018/4/8
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestInsertUrl {

//    @Resource
//    private UrlMapper urlMapper;

//    @Resource
//    private Label$NumberMapper l$nMapper;

//    @Resource
    @Autowired
    private Pipeline pipeline;

    /**
     * 测试分步插入url和label$Number
     *
     * @author Ulric
     * @date 2018/4/8
     */
    @Test
    public void testInsert() {
        /***** ===== 初始化数据 ===== *****/
        Url url = new Url("http://www.ulric.ink");
        url.setH5(true);
        url.setAnalysisTime(new Timestamp(System.currentTimeMillis()));

        List<LabelAndNumber> l$nList = new ArrayList<>();

        // 这里设置索引为偶数的为HTML5特有标签
        String[] labelNames = new String[]{"main", "span", "nav", "form", "header", "img", "section"};

        for (int i = 0; i < labelNames.length; i++) {
            LabelAndNumber ln = new LabelAndNumber();
            // ln.setLabelName("div");

            Label label = new Label();
            label.setLabelName(labelNames[i]);
            label.setH5Version(i % 2 == 0); // 根据数据数组特性，索引为偶数的为 HTML5 特有标签

            ln.setLabel(label);
            ln.setNumber(String.valueOf(new Random().nextInt(30))); // 数量就随机给

            l$nList.add(ln);
        }

        url.setLabelAndNumberList(l$nList);

        pipeline.save(url);
    }

}
