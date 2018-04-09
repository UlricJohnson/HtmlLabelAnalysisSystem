package ink.ulricj.processor;

import ink.ulricj.entity.LabelAndNumber;
import ink.ulricj.entity.Url;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * created by Ulric on 2018/3/6
 */

//@Component
public interface PageProcessor {
    /**
     * 初始化数据（当前页面url、处理结果集 pageResultList、链接集合urlList、迭代器vector），每次执行process()之前调用
     * @author Ulric
     * @date 2018/3/14
     */
    void init(Url currUrl);

    /**
     * 获取从页面上抓取的 a 标签
     * @author Ulric
     * @date 2018/3/15
     */
    List<Url> getPageUrls();


    /**
     * 设置当前正在处理的页面
     *
     * @author Ulric
     * @date 2018/3/12
     */
//    void setCurrentUrl(Url url);

    /**
     * 处理分析从 Downloader 传过来的页面，期间会发现潜在的链接，返回本页面的处理结果
     *
     * @author Ulric
     * @date 2018/3/7
     */
    List<LabelAndNumber> process(Document document);


}
