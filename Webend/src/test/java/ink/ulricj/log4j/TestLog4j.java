package ink.ulricj.log4j;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * created by Ulric on 2018/3/10
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestLog4j {

    // log4j 日志对象
    private static final Logger LOGGER = LogManager.getLogger(TestLog4j.class); // log4j-2.x 版本
//    private static final Logger LOGGER = Logger.getLogger(TestLog4j.class); // log4j-1.x 版本

    @Test
    public void testError(){
        String url = "http://www.sina.com.cn";

        Document document = null;

        LOGGER.info("正在下载页面：" + url);
        try {
            // 获取页面对象 Document （代表一个 HTML 文件）
            document = Jsoup.connect(url).get();

            LOGGER.info("页面下载成功：" + url);

            System.out.println(document.toString().split("\n")[0]); // 输出HTML文件的第1行
//            throw new Exception("这里抛出异常，作测试");
//        } catch (IOException e) {
//            e.printStackTrace();
        }catch (Exception e){
            LOGGER.error("测试 log4j 的 error() 方法");
//            System.out.println("================================================");
            e.printStackTrace();
        }
    }
}
