package ink.ulricj.processor;

import ink.ulricj.entity.Label;
import ink.ulricj.entity.LabelAndNumber;
import ink.ulricj.entity.Url;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

/**
 * 基本的页面处理器，从 Scheduler 中获取一个 Url，分析统计该页面的标签并把结果存到集合中
 * <p>
 * created by Ulric on 2018/3/7
 */

@Component
public class CommonPageProcessor implements PageProcessor {
    private Url currUrl; // 当前正在处理的页面

    private List<LabelAndNumber> pageResultList = new ArrayList<>(); // 本次处理页面的结果集

    private List<Url> urlList = new ArrayList<>(); // 保存本次处理的页面中的链接

    private Vector<Element> vector = new Vector(); // 使用迭代器代替递归中的栈

    /**
     * 初始化数据（当前页面url、页面处理结果集 pageResultList、链接集合urlList、迭代器vector），每次执行process()之前调用
     *
     * @author Ulric
     * @date 2018/3/14
     */
    @Override
    public void init(Url currUrl) {
        setCurrUrl(currUrl);
        pageResultList.clear();
        urlList.clear();
        vector.clear();
    }

    /**
     * 获取从页面上抓取的 a 标签
     *
     * @author Ulric
     * @date 2018/3/15
     */
    @Override
    public List<Url> getPageUrls() { return getUrlList(); }

    /**
     * 处理分析从 Downloader 传过来的页面，发现潜在的链接，返回本页面的处理结果
     *
     * @author Ulric
     * @date 2018/3/7
     */
    @Override
    public List<LabelAndNumber> process(Document document) {
        /** 1、获取第1行的文档类型声明（html5 只有一种文档类型声明方式（不区分大小写）：<!DOCTYPE html>） **/
        String htmlDocType = document.toString().split("\n")[0];
        // 检查该页面是否声明了文档类型，没有的话先直接当成 HTML5
        if (htmlDocType != null && htmlDocType.toLowerCase().startsWith("<!doctype")) {
            //声明了文档类型则检查
            if ("<!DOCTYPE html>".equalsIgnoreCase(htmlDocType)) {
                System.out.println("本文档为HTML5！");
                currUrl.setH5(true);
            } else {
                System.out.println("本文档为HTML4！");
                currUrl.setH5(false);
            }
        } else {
            // 没有声明文档类型
            System.out.println("本文档为HTML5！");
            currUrl.setH5(true);
        }


        /** 2、统计body内的标签 **/
        // 2.1、获取 body 标签，并添加到迭代器中
        Element eleBody = document.body();
        if (eleBody != null) { // 有的页面会没有 body
            vector.add(eleBody);
        } else {
            return null;
        }
//        System.out.println("body: " + eleBody);
        //2.2、调用方法分析，先使用 Map，再转为 List<Label$Number>，因为使用 Map 比较方便
        Map<String, Integer> resultMap = analysis(new HashMap()); // 用于暂时存放本页面的标签及其数量

//        System.out.println("resultMap: " + resultMap);

        /** 3、将标签分析结果添加到 pageResultList 中 **/
        pageResultList.addAll(map2List(resultMap));

        System.out.println("pageResultList(size=" + pageResultList.size() + "): " + pageResultList);

        System.out.println("urlList(size=" + urlList.size() + "): " + urlList);

        /** 4、最后设置本页面的 url 为已访问状态，同时设置爬取时间 **/
        currUrl.setVisited(true);
        currUrl.setAnalysisTime(new Timestamp(System.currentTimeMillis()));

        return pageResultList;
    }

    /**
     * Map 集合转为 List
     *
     * @author Ulric
     * @date 2018/3/14
     */
    private List map2List(Map<String, Integer> map) {
        List list = new ArrayList();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            LabelAndNumber labelAndNumber = new LabelAndNumber();
//            labelAndNumber.setLabelName(entry.getKey()); // 标签名
            Label label = new Label();
            label.setLabelName(entry.getKey());
            labelAndNumber.setLabel(label);
            labelAndNumber.setNumber(entry.getValue().toString()); // 数目，转为字符串
            labelAndNumber.setUrl(currUrl); // 设置该对象对应的页面 Url 为当前页

            // 添加进本页面的结果集 pageResultList 中
            list.add(labelAndNumber);
        }
        return list;
    }

    /**
     * 分析标签节点，将结果存放到 map 集合中并返回
     *
     * @author Ulric
     * @date 2018/3/12
     */
//    private int countA = 0; // 统计 a 标签的个数
    private Map analysis(Map resultMap) {

        while (!vector.isEmpty()) {
            // 获取栈顶元素并移除
//            Element parentEle = vector.remove(0);
            Element parentEle = vector.firstElement();
            if (parentEle == null) { // 以防万一还是判断一下
                continue;
            }
            vector.remove(parentEle); // 移除栈顶元素

            // 获取该节点下的所有子节点
            List childElementList = parentEle.children();
            if (childElementList == null || childElementList.size() <= 0) {
                continue;
            }
//        System.out.println(eleParent.tagName() + "节点的所有子节点：");

//        for (Element childEle:childElementList ) {
            for (int i = 0; i < childElementList.size(); i++) {
                Element childEle = (Element) childElementList.get(i);

                // 1、跳过标签：<script/>
                if ("script".equalsIgnoreCase(childEle.tagName())) { continue; }

                // 2、统计本标签
                String currTagName = childEle.tagName(); // 当前标签的标签名
                if (resultMap.containsKey(currTagName)) { // 如果已经统计到本标签，则在数量上+1
                    resultMap.put(currTagName, Integer.valueOf((Integer) resultMap.get(currTagName) + 1));
                } else {
                    resultMap.put(currTagName, 1); // 如果是第一次统计到本标签，则添加进集合中，并把数量设置为1
                }

                // 3、如果是 a 标签，就要把超链接添加到 urlList 中
                if ("a".equalsIgnoreCase(currTagName)) {
                    // 获取 href 链接
//                String href = childEle.attr("href");
                    String absHref = childEle.absUrl("href"); // 获取绝对路径

                    // 如果可以拼成绝对路径url，则把超链接添加到本页面的url集合中。absUrl() 方法如果不能拼成绝对url的话，会返回空字符串""
                    if (!"".equals(absHref)) {
//                        System.out.println("超链接###" + (++countA) + "：" + absHref);

                        Url newUrl = new Url(absHref);
                        newUrl.setPriority(currUrl.getPriority() + 1);// 设置优先级，为当前页面优先级+1（数值越高优先级越低）

                        // 添加到 urlList 中
                        urlList.add(newUrl);
                    }
                }

                // 3、如果本标签下面还有子标签，则将本标签添加到迭代器中
                List elementList = childEle.getAllElements();
                if (elementList != null && elementList.size() > 0) {
                    vector.add(childEle);
                }
            }
        }
        return resultMap;
    }

    /**
     * 设置当前正在处理的页面
     *
     * @author Ulric
     * @date 2018/3/12
     */
//    @Override
//    public void setCurrentUrl(Url url) {
//        setCurrUrl(url);
//    }

    /*** ===== getter setter ===== ***/
    public Url getCurrUrl() { return currUrl; }

    private void setCurrUrl(Url currUrl) { this.currUrl = currUrl; }

    public List<LabelAndNumber> getPageResultList() { return pageResultList; }

//    public void setPageResultList(List<Label$Number> pageResultList) { this.pageResultList = pageResultList; }

    public List<Url> getUrlList() { return urlList; }

    public void setUrlList(List<Url> urlList) { this.urlList = urlList; }
}
