package ink.ulricj.crawler;

import ink.ulricj.entity.Url;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 测试：ArrayList 实现冒泡排序
 * <p>
 * created by Ulric on 2018/3/15
 */

public class TestBubbleSortOnArrayList {

    /**
     * 测试：基本数据类型
     *
     * @author Ulric
     * @date 2018/3/15
     */
    @Test
    public void testBase() {
        List<Integer> list = Arrays.asList(565, 7, 173, 19, 15, 657, 354, 35, 67, 27);

        for (int i = 0; i < list.size() - 1; i++) { // 外层循环，控制排序趟数
            for (int j = 0; j < list.size() - 1 - i; j++) { // 内层循环，控制每一趟要排序的次数
                if (list.get(j) > list.get(j + 1)) {
                    Integer temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }

        System.out.println(list);
    }

    /**
     * 测试：根据对象的某个属性进行排序，以 Url 为例
     *
     * @author Ulric
     * @date 2018/3/15
     */
    @Test
    public void testObject() {
        List<Url> list = new ArrayList<>();

        // 初始化数据
        for (int i = 0; i < 10; i++) {
            Url url = new Url("url-" + (i + 1));
            url.setPriority(new Random().nextInt(21)); // 将 priority 属性随机赋值为 [0,20] 之间的数
            list.add(url);
            System.out.print(url + "=" + url.getPriority() + "\t");
        }

        System.out.println();

        for (int i = 0; i < list.size() - 1; i++) { // 外层循环，控制排序趟数
            for (int j = 0; j < list.size() - 1 - i; j++) { // 内层循环，控制每一趟要排序的次数
                if (list.get(j).getPriority() > list.get(j + 1).getPriority()) { // priority 属性小的优先级高，排在前面
                    Url temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }

        System.out.println("排序后：");
        for (Url url : list) {
            System.out.print(url + "=" + url.getPriority() + "\t");
        }
    }
}
