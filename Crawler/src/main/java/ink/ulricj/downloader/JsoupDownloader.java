package ink.ulricj.downloader;

import ink.ulricj.entity.Url;
import ink.ulricj.scheduler.Scheduler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 使用 Jsoup 当做下载工具
 * <p>
 * created by Ulric on 2018/3/7
 */

@Component
public class JsoupDownloader implements Downloader {
    private static final int TIMEOUT = 10000; // 下载页面时的超时时间（单位为毫秒）

    // log4j 日志对象
    private static final Logger LOGGER = LogManager.getLogger(JsoupDownloader.class);

    private Url currUrl; // 当前要下载的页面链接

//    private Scheduler scheduler; // 调度器


    /*** ====== constructors ==== ***/
    public JsoupDownloader() {}

//    public JsoupDownloader(Scheduler scheduler) {
//        this.scheduler = scheduler;
//    }

    /**
     * 从 Scheduler 的未访问队列中获取下一个 url
     *
     * @author Ulric
     * @date 2018/3/7
     */
    /*@Override
    public Url getNextUrl() {
        Url url = scheduler.getNextNonVisitUrl();
        setCurrUrl(url);
        return url;
    }*/

    /**
     * 设置当前下载的页面
     *
     * @param currUrl
     * @author Ulric
     * @date 2018/3/15
     */
    @Override
    public void setCurrentUrl(Url currUrl) {
        setCurrUrl(currUrl);
    }

    /**
     * 根据 url 下载页面
     *
     * @param url
     * @author Ulric
     * @date 2018/3/7
     */
    @Override
    public Document download(Url url) {
        Document document = null;
        try {
            LOGGER.info("正在下载：" + url);

            // 下载成功
            document = Jsoup.connect(url.getUrl()).timeout(TIMEOUT).get(); // 设置超时时间为10秒钟

            // 在控制台输出信息
            LOGGER.info("页面下载成功：" + url);
        } catch (IOException e) {
            LOGGER.error("页面下载失败：" + url);
//            e.printStackTrace();
        } finally {
            return document;
        }

    }

    /*** ===== getter setter ===== ***/
    public Url getCurrUrl() { return currUrl; }

    private void setCurrUrl(Url currUrl) { this.currUrl = currUrl; }
}
