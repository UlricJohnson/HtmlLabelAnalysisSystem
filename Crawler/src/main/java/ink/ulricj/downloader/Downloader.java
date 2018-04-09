package ink.ulricj.downloader;

import ink.ulricj.entity.Url;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

/**
 * created by Ulric on 2018/3/6
 */

//@Component
public interface Downloader {

    /**
     * 从 Scheduler 的未访问队列中获取下一个 url
     *
     * @author Ulric
     * @date 2018/3/7
     */
//    Url getNextUrl();

    /**
     * 设置当前下载的页面
     * @author Ulric
     * @date 2018/3/15
     */
    void setCurrentUrl(Url currUrl);

    /**
     * 根据 url 下载页面
     *
     * @author Ulric
     * @date 2018/3/7
     */
    Document download(Url url);
}
