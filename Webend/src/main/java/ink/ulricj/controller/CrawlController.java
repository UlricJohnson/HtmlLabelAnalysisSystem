package ink.ulricj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * created by Ulric on 2018/3/3
 */

@RestController
@RequestMapping("/crawl")
public class CrawlController {

    /**
     * 只爬取指定的单个页面
     *
     * @author Ulric
     * @date 2018/3/3
     */
    @RequestMapping("/crawlOnePage")
    public ModelAndView crawlOnePage(String url) {
        System.out.println("====CrawlController--crawlOnePage()###url:" + url);

        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

}
