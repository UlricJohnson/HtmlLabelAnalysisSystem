package ink.ulricj.scheduler;

import ink.ulricj.entity.Url;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Queue;

/**
 * url 管理调度器接口，管理已/未访问的 url，以及相关的去重工作
 * <p>
 * created by Ulric on 2018/3/6
 */

//@Component
public interface Scheduler {

    /**
     * 设置初始 url，多个
     *
     * @author Ulric
     * @date 2018/3/15
     */
    void setOriginUrl(List<Url> urlList);

    /**
     * 从 *未* 访问队列中获取下一个 url
     *
     * @author Ulric
     * @date 2018/3/7
     */
    Url getNextNonVisitUrl();

    /**
     * 添加一个 url 到 *未* 访问队列中
     *
     * @author Ulric
     * @date 2018/3/7
     */
    void addNonVisitUrl(Url nonVisitUrl);

    /**
     * 添加一个 url 集合到 *未* 访问队列中
     *
     * @return 返回添加失败的数目
     * @author Ulric
     * @date 2018/3/7
     */
    void addNonVisitUrl(List<Url> nonVisitUrlList);

    /**
     * 添加一个 url 到 *已* 访问队列中
     *
     * @author Ulric
     * @date 2018/3/7
     */
    void addVisitedUrl(Url visitedUrl);

    /**
     * 判断 *未* 访问队列是否为空，用于判断本次任务是否已经完成，若队列为空则任务完成
     *
     * @author Ulric
     * @date 2018/3/8
     */
    Boolean isNonVisitZSetEmpty();

    /**
     * 获取 已 访问队列中的 url数量
     *
     * @author Ulric
     * @date 2018/3/15
     */
    Long getNumOfVisitedSetByUrl();

    /**
     * 获取 已 访问队列中的 网站 数量（需要获取里面的 Url 对象中的 url 属性，然后提取其中域名进行统计）
     *
     * @author Ulric
     * @date 2018/3/15
     */
    Long getNumOfVisitedSetBySite();

    /**
     * 当当前优先级的所有页面访问完后，代表当前优先级的属性自增
     *
     * @author Ulric
     * @date 2018/3/20
     */
    Double increaseCurrentPriority();

    /**
     * 删除redis中的两个集合，只在爬取任务完成后执行一次
     * @author Ulric
     * @date 2018/3/22
     */
    void deleteRedis();
}
