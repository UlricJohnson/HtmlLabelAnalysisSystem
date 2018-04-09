//package ink.ulricj.task;
//
//import ink.ulricj.entity.Url;
//
//import java.util.List;
//
///**
// * create by Ulric on 2018/3/6
// */
//
//public interface Task {
//
//
//    /**
//     * 设置起始URL，一个url时
//     *
//     * @author Ulric
//     * @date 2018/3/6
//     */
//    void setOriginUrl(Url originUrl);
//
//    /**
//     * 设置初始 Url，多个
//     *
//     * @author Ulric
//     * @date 2018/3/15
//     */
//    void setOriginUrl(List<Url> urlList);
//
//    /**
//     * 获取初始 Url，多个
//     *
//     * @author Ulric
//     * @date 2018/3/15
//     */
//    List<Url> getOriginUrl();
//
//
//    /**
//     * 设置爬取任务的目标：url 的数量
//     *
//     * @author Ulric
//     * @date 2018/3/15
//     */
//    void setTargetByUrls(int numOfUrl);
//
//    /**
//     * 设置爬取任务的目标：网站的数量
//     *
//     * @author Ulric
//     * @date 2018/3/15
//     */
//    void setTargetBySites(int numOfSite);
//
//    /**
//     * 获取爬取任务的目标：url 数量
//     *
//     * @author Ulric
//     * @date 2018/3/15
//     */
//    int getTargetOnUrl();
//
//    /**
//     * 获取爬取任务的目标：网站数量
//     *
//     * @author Ulric
//     * @date 2018/3/15
//     */
//    int getTargetOnSite();
//}
