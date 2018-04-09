//package ink.ulricj.task;
//
//import ink.ulricj.entity.Url;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 每次用户输入一个url，都将开启一个爬虫任务，这个任务中始终携带着用户输入的初始url
// * <p>
// * created by Ulric on 2018/3/6
// */
//
//public class CommonTask implements Task {
//    private List<Url> originUrlList; // 初始 url 集合
//    private Integer numOfUrl; // 爬取目标：url 的数量
//    private Integer numOfSite; // 爬取目标：网站的数量
//
//    /*** ===== constructors ===== ***/
//    // 默认爬取目标都为 null
//    public CommonTask() { originUrlList = new ArrayList<>(); numOfUrl = null; numOfSite = null; }
//
//    /**
//     * 设置初始URL，一个
//     *
//     * @author Ulric
//     * @date 2018/3/6
//     */
//    @Override
//    public void setOriginUrl(Url originUrl) { originUrlList.add(originUrl); }
//
//    /**
//     * 设置初始 Url，多个
//     *
//     * @param urlList
//     * @author Ulric
//     * @date 2018/3/15
//     */
//    @Override
//    public void setOriginUrl(List<Url> urlList) { originUrlList.addAll(urlList); }
//
//    /**
//     * @description
//     * @author Ulric
//     * @date 2018/3/15
//     */
//    public List<Url> getOriginUrl() { return originUrlList; }
//
//    /**
//     * 设置爬取任务的目标：url 的数量
//     *
//     * @author Ulric
//     * @date 2018/3/15
//     */
//    @Override
//    public void setTargetByUrls(int numOfUrl) { setNumOfUrl(numOfUrl); }
//
//    /**
//     * 设置爬取任务的目标：网站的数量
//     *
//     * @author Ulric
//     * @date 2018/3/15
//     */
//    @Override
//    public void setTargetBySites(int numOfSite) { setNumOfSite(numOfSite); }
//
//    /**
//     * 获取爬取任务的目标：url 数量
//     *
//     * @author Ulric
//     * @date 2018/3/15
//     */
//    @Override
//    public int getTargetOnUrl() { return getNumOfUrl(); }
//
//    /**
//     * 获取爬取任务的目标：网站数量
//     *
//     * @author Ulric
//     * @date 2018/3/15
//     */
//    @Override
//    public int getTargetOnSite() { return getNumOfSite(); }
//
//
//    /*** ===== get set ===== ***/
//    public List<Url> getOriginUrlList() { return originUrlList; }
//
//    public void setOriginUrlList(List<Url> originUrlList) { this.originUrlList = originUrlList; }
//
//    public Integer getNumOfUrl() { return numOfUrl; }
//
//    public void setNumOfUrl(Integer numOfUrl) { this.numOfUrl = numOfUrl; }
//
//    public Integer getNumOfSite() { return numOfSite; }
//
//    public void setNumOfSite(Integer numOfSite) { this.numOfSite = numOfSite; }
//
//}
