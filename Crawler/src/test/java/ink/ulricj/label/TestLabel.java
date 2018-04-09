package ink.ulricj.label;

import ink.ulricj.entity.Label;
import ink.ulricj.mapper.LabelMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * created by Ulric on 2018/4/7
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestLabel {

    @Resource
    private LabelMapper labelMapper;

    /**
     * 测试：传入标签名称，查询是否为 H5 标签
     *
     * @author Ulric
     * @date 2018/4/7
     */
    @Test
    public void testQueryLabelVersion() {
        Label label = new Label();
        label.setLabelName("nav");

        Boolean isLabelH5 = labelMapper.isLabelH5(label);

        System.out.println(isLabelH5);
    }



}
