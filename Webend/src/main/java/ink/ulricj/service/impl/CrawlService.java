package ink.ulricj.service.impl;

import ink.ulricj.crawler.Crawler;
import ink.ulricj.entity.Url;
import ink.ulricj.service.ICrawlService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * created by Ulric on 2018/3/8
 */

@Service
public class CrawlService implements ICrawlService {

    /**
     * 根据输入的初始url开始爬取该站点的所有网页
     *
     * @author Ulric
     * @date 2018/3/8
     */
    @Override
    public List<Url> crawlThePages(Url originUrl) {

        Crawler crawler = new Crawler(originUrl, 30, 0); // 初始url，目标url数量，目标网站数量
        Crawler.startCrawling(crawler);

        // 获取爬虫中的结果集
        List<Url> resultList = crawler.getResult();

        // 将结果集保存到数据库中


        return resultList;
    }
}
