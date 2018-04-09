package ink.ulricj.crawler;

import ink.ulricj.entity.Url;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 测试整个爬虫模块
 * <p>
 * created by Ulric on 2018/3/16
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCrawler {

    @Test
    public void test() {
        Crawler crawler = new Crawler(new Url("http://www.sina.com.cn/"), 100, 0); // 初始url，目标url数量，目标网站数量
        Crawler.startCrawling(crawler);

        // 获取爬虫中的结果集
        List<Url> taskResultList = crawler.getResult();
        System.out.println("爬取任务结束，总共爬取" + taskResultList.size() + "个页面：" + taskResultList);
    }

}
