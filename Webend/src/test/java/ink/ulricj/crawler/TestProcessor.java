package ink.ulricj.crawler;

import ink.ulricj.entity.LabelAndNumber;
import ink.ulricj.entity.Url;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * created by Ulric on 2018/3/11
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProcessor {
    //    private String currUrl = "http://www.sina.com.cn";
    private Url currUrl = new Url("http://www.sina.com.cn");

    private List<LabelAndNumber> resultList = new ArrayList<>(); // 处理结果集

    private List<Url> urlList = new ArrayList<>(); // 保存本次处理的页面中的链接

    private Vector<Element> vector = new Vector(); // 使用迭代器代替递归中的栈

    @Test
    public void testProcessPage() {
//        String filePath = "F:\\WebProjectFiles\\HBuilder\\Test\\h4.html";
//        Document document = getDocumentByFile(filePath);
        Document document = getDocumentByUrl(currUrl.getUrl());

        /** 1、获取第1行的文档类型声明 **/
        String htmlDocType = document.toString().split("\n")[0];
        // html5 只有一种文档类型声明方式：<!DOCTYPE html>
        if ("<!DOCTYPE html>".equalsIgnoreCase(htmlDocType)) {
            System.out.println("本文档为HTML5！");
            currUrl.setH5(true);
        } else {
            System.out.println("本文档为HTML4！");
            currUrl.setH5(false);
        }

        /** 2、统计body内的标签 **/
        // 2.1、获取 body 标签，并添加到迭代器中
        Element eleBody = document.body();
        vector.add(eleBody);
//        System.out.println("body: " + eleBody);
        //2.2、调用方法分析
        Map<String, Integer> resultMap = analysis(new HashMap()); // 用于暂时存放本页面的标签及其数量，最后再转为 List<Label$Number>，因为使用
        // Map 比较方便
        analysis(resultMap);

        System.out.println("resultMap(size:" + resultMap.size() + "): " + resultMap);

        /** 3、将标签分析结果添加到 resultList 中 **/
        resultList.addAll(map2List(resultMap));

        System.out.println("resultList(size:" + resultList.size() + "): " + resultList);

        System.out.println("urlList(size:" + urlList.size() + "): " + urlList);

        /** 4、最后设置本页面的 url 为已访问状态 **/
        currUrl.setVisited(true);

    }

    /**
     * 分析标签节点
     *
     * @author Ulric
     * @date 2018/3/12
     */
    private int countA = 0; // 统计 a 标签的个数

    private Map analysis(Map resultMap) {

        while (!vector.isEmpty()) {
            // 获取栈顶元素并移除
            Element parentEle = vector.remove(0);

            // 获取该节点下的所有子节点
            List childElementList = parentEle.children();
//        System.out.println(eleParent.tagName() + "节点的所有子节点：");

//        for (Element childEle:childElementList ) {
            for (int i = 0; i < childElementList.size(); i++) {
                Element childEle = (Element) childElementList.get(i);

                // 1、跳过标签：<script/> 、<style/>、<link/>
                if ("script".equalsIgnoreCase(childEle.tagName())) { continue; }
                if ("style".equalsIgnoreCase(childEle.tagName())) { continue; }
                if ("link".equalsIgnoreCase(childEle.tagName())) { continue; }

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
                        System.out.println("超链接###" + (++countA) + "：" + absHref);

                        /**
                         * 这里要进行 超链接 的收集工作
                         */
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
            labelAndNumber.setNumber(entry.getValue().toString()); // 数目，转为字符串
            labelAndNumber.setUrl(currUrl); // 设置该对象对应的页面 Url 为当前页

            // 添加进本页面的结果集 resultList 中
            list.add(labelAndNumber);
        }
        return list;
    }

    /*@Test
    public void testNewUrl(){
        Url url = new Url("bb");
        System.out.println(url);
    }*/


    /**
     * 通过 URL 获取文档
     *
     * @author Ulric
     * @date 2018/3/12
     */
    private Document getDocumentByUrl(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 加载本地HTML文档
     *
     * @author Ulric
     * @date 2018/3/13
     */
    private Document getDocumentByFile(String filePath) {
        File file = new File(filePath);

        try {
            return Jsoup.parse(file, "utf-8");
        } catch (IOException e) {
            return null;
        }
    }
}
