package ink.ulricj.service;

import ink.ulricj.entity.Url;

import java.util.List;
import java.util.Map;

/**
 * create by Ulric on 2018/3/8
 */

public interface ICrawlService {
    /**
     * 根据输入的初始 url 开始爬取网页，并返回访问过的所有 url 的统计结果
     * 由于 Url 类中含有相应页面的标签统计结果 List，所以返回的集合只需要包含 Url 即可
     *
     * @author Ulric
     * @date 2018/3/8
     */
    List<Url> crawlThePages(Url originUrl);
}
