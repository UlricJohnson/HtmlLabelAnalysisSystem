package ink.ulricj.mapper;

import ink.ulricj.entity.Label;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * created by Ulric on 2018/3/14
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMappers {

    @Resource
    private LabelMapper labelMapper;

    @Test
    public void testLabelMapper() {
        Label label = new Label();
        label.setLabelName("div");
        Boolean flag = labelMapper.isLabelH5(label);
//        boolean flag = labelMapper.isLabelH5("div");
//        boolean flag = labelMapper.isLabelH5(24L);
        System.out.println(flag);


        /*Label label1 = labelMapper.findLabelById(24L);
        System.out.println(label1);*/
    }
}
