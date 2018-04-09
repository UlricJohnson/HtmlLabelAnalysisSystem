package ink.ulricj.crawler;

import ink.ulricj.downloader.Downloader;
import ink.ulricj.downloader.JsoupDownloader;
import ink.ulricj.entity.LabelAndNumber;
import ink.ulricj.entity.Url;
import ink.ulricj.pipeline.CommonPipeline;
import ink.ulricj.pipeline.Pipeline;
import ink.ulricj.processor.CommonPageProcessor;
import ink.ulricj.processor.PageProcessor;
import ink.ulricj.scheduler.CommonScheduler;
import ink.ulricj.scheduler.Scheduler;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * created by Ulric on 2018/3/6
 */

public class Crawler {

    private Downloader downloader; // 页面下载器

    private PageProcessor processor; // 页面处理器

    private Scheduler scheduler; // url管理调度器

    private Pipeline pipeline; // 数据持久化

    private List<Url> originUrlList; // 初始 url 集合

    private final int targetNumOfSite; // 爬取目标：网站域名的数量
    private final int targetNumOfUrl; // 爬取目标：网页 url 的数量

    private List<Url> taskResultList; // 本次爬取任务的结果集，只有getter

    // 实例化变量：下载器、页面处理器、url管理器、数据持久化、初始url集合、爬取结果集
    {
        this.downloader = new JsoupDownloader();
        this.processor = new CommonPageProcessor();
        this.scheduler = new CommonScheduler();
        this.pipeline = new CommonPipeline();

        this.originUrlList = new ArrayList<>();

        this.taskResultList = new LinkedList<>();
    }

    /*** ====== constructors，设置初始 url 的 priority 为 0 ==== ***/
    public Crawler(Url originUrl, int targetNumOfUrl, int targetNumOfSite) {
        // 由于 Url 类的优先级 priority 默认值为 -1，所以需要手动设置初始 Url 的 priority 值为 0
        originUrl.setPriority(0);
        originUrlList.add(originUrl);
        this.targetNumOfUrl = targetNumOfUrl;
        this.targetNumOfSite = targetNumOfSite;
    }

    public Crawler(List<Url> originUrlList, int targetNumOfUrl, int targetNumOfSite) {
        for (Url url : originUrlList) {
            // 由于 Url 类的优先级 priority 默认值为 -1，所以需要手动设置初始 Url 的 priority 值为 0
            url.setPriority(0);
        }
        originUrlList.addAll(originUrlList);
        this.targetNumOfUrl = targetNumOfUrl;
        this.targetNumOfSite = targetNumOfSite;
    }

    /**
     * 传入一只爬虫，然后开始执行爬取操作
     *
     * @author Ulric
     * @date 2018/3/7
     */
    public static void startCrawling(Crawler crawler) { crawler.crawling(); }

    /**
     * 爬虫执行的具体步骤
     *
     * @author Ulric
     * @date 2018/3/9
     */
    private void crawling() {
        // 给 Scheduler 设置初始url，并放入未访问队列
        scheduler.setOriginUrl(originUrlList);

        // 只要任务没完成（未访问队列不为空 且 爬取url/网站数量未达到设定值），就持续进行操作
        while (!isTaskFinish()) {
            /*** 从 Scheduler 中获取一个未访问的页面 url ***/
//            Url currUrl = downloader.getNextUrl();
            Url currUrl = scheduler.getNextNonVisitUrl();

            /*** 根据 url 使用 Downloader 下载页面 ***/
            Document document = null;
            if (currUrl != null) {
                downloader.setCurrentUrl(currUrl);
                document = downloader.download(currUrl);
            } else {
                continue;
            }

            // 下载失败就跳过，url 已经从 nonVisitUrlZSet 中删除，且没有添加到 visitedUrlSet 中，所以不用处理
            if (document == null) {
                continue;
            }

            /*** 使用 PageProcessor 进行分析提取 ***/
//            processor.setCurrentUrl(currUrl);
            processor.init(currUrl); // 初始化 currUrl，清空 taskResultList、urlList、vector
            List<LabelAndNumber> pageProcessResult = processor.process(document);

            // 将 url 添加到 Scheduler 的已访问队列中
            scheduler.addVisitedUrl(currUrl);

            // 将本页面的统计结果（标签及其数量的集合）封装到 Url 对象中
            currUrl.setLabelAndNumberList(pageProcessResult);

            /**
             * 在这里将 url 对象的相关信息(url字符串，是否为H5，访问时间，标签数量集合)保存到数据库
             */
            pipeline.save(currUrl);

            // 将url对象添加到本次任务的结果集中
            taskResultList.add(currUrl);

            // 获取页面中的新的 a 链接（已封装为Url对象）的集合
            List pageUrls = processor.getPageUrls();

            /*** 将新的 Url 对象集合添加到 Scheduler 中的未访问队列（期间会去重）  ***/
            scheduler.addNonVisitUrl(pageUrls);
        }

        // 爬取任务完成，删除redis中的两个url集合
        scheduler.deleteRedis();
    }

    /**
     * 判断本次任务是否完成
     *
     * @author Ulric
     * @date 2018/3/8
     */
    public Boolean isTaskFinish() {
        /*** 条件1：调度器中的未访问队列为空 ***/
        if (scheduler.isNonVisitZSetEmpty()) {
            return true;
        }

        /*** 条件2：达到设定的爬取目标数量：网站数量或url数量 ***/

        // 2.1、Task中设置了要访问的网站数量且 大于等于 已访问队列中url的网站域名总数
        long countSite = scheduler.getNumOfVisitedSetBySite();
//        System.out.println("=========当前爬取了" + countSite + "个网站");
        if ((targetNumOfSite > 0) && (countSite >= targetNumOfSite)) {
            return true;
        }

        // 2.2、Task中设置了要访问的 url 数量且 大于等于 已访问队列中 url 总数
        if ((targetNumOfUrl > 0) && (scheduler.getNumOfVisitedSetByUrl() >= targetNumOfUrl)) {
            return true;
        }

        return false;
    }

    /**
     * 获取本次爬取任务的结果集
     *
     * @author Ulric
     * @date 2018/3/8
     */
    public List<Url> getResult() {
        return getTaskResultList();
    }


    /***** ===== getter setter ===== *****/
    public Downloader getDownloader() { return downloader; }

    public void setDownloader(Downloader downloader) { this.downloader = downloader; }

    public PageProcessor getProcessor() { return processor; }

    public void setProcessor(PageProcessor processor) { this.processor = processor; }

    public Scheduler getScheduler() { return scheduler; }

    public void setScheduler(Scheduler scheduler) { this.scheduler = scheduler; }

//    public Task getTask() { return task; }

//    public void setTask(Task task) { this.task = task; }

    private List<Url> getTaskResultList() { return taskResultList; }
}
