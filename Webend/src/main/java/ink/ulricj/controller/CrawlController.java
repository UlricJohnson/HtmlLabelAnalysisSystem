package ink.ulricj.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ink.ulricj.crawler.Crawler;
import ink.ulricj.entity.Url;
import ink.ulricj.service.ICrawlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * created by Ulric on 2018/3/3
 */

@RestController
@RequestMapping("/crawl")
public class CrawlController {
    @Resource
    private ICrawlService iCrawlService;

    /**
     * 根据输入的初始url开始爬取该站点的所有网页
     *
     * @author Ulric
     * @date 2018/3/3
     */
    @RequestMapping("/crawlThePages")
    public ModelAndView crawlThePages(String url) {
        System.out.println("====CrawlController--crawlOnePage()###url:" + url);

        ModelAndView modelAndView = new ModelAndView();

        Url originUrl = new Url(url);

        List result = iCrawlService.crawlThePages(originUrl);

        /*
        Crawler crawler = new Crawler().setOriginUrl(originUrl);
        Crawler.startCrawling(crawler);

        // 循环等待任务结束
        while (true) {
            // 任务结束则跳出循环
            if (crawler.isTaskFinish()) {
                break;
            }
        }

        // 获取爬虫中的结果集
        Map result = crawler.getResult();
        */

        // 将结果集转换成 json 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String crawlResult = null;
        try {
            crawlResult = objectMapper.writeValueAsString(result);
            modelAndView.addObject("crawlResult", crawlResult);
            modelAndView.setViewName("index");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            modelAndView.addObject("errorMsg", e.getMessage());
            modelAndView.setViewName("error");
        } finally {
            return modelAndView;
        }

    }

}
