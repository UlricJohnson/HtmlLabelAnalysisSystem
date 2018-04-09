package ink.ulricj.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * created by Ulric on 2018/2/26
 */

public class Url implements Serializable {
    private Long uid; // 主键ID
    private String url; // url 全称，包括协议
    private Boolean h5; // 是否为HTML5版本
    private Timestamp analysisTime; // 本页面的爬取分析时间

    /*** === 数据库表中没有以下字段 === ***/
    private Boolean visited; // 是否已经被访问
    private Integer priority; // 优先级，从 0 开始，起始 URL 优先级为 0，为最高

    private List<LabelAndNumber> labelAndNumberList = new LinkedList<>();


    /*** ===== constructors ===== ***/
    public Url() { /*this.analysisTime = new Timestamp(System.currentTimeMillis());*/
        this.visited = false; this.priority = -1;
    } // 默认设置为 未访问状态，优先级为 -1，表示没有赋值

    public Url(String url) { this(); this.url = url; }


    /***** ===== get set ===== *****/
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isH5() { return h5; }

    public Timestamp getAnalysisTime() { return analysisTime; }

    public void setAnalysisTime(Timestamp analysisTime) { this.analysisTime = analysisTime; }

    public void setH5(Boolean h5) { this.h5 = h5; }

    public Boolean isVisited() { return visited; }

    public void setVisited(Boolean visited) { this.visited = visited; }

    public Integer getPriority() { return priority; }

    public void setPriority(Integer priority) { this.priority = priority; }

    public List<LabelAndNumber> getLabelAndNumberList() { return labelAndNumberList; }

    public void setLabelAndNumberList(List<LabelAndNumber> labelAndNumberList) {
        this.labelAndNumberList =
                labelAndNumberList;
    }

    /*** === equals()、hashCode()、toString() === ***/
    @Override // 只判断 url
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Url url1 = (Url) o;

        return getUrl() != null ? getUrl().equals(url1.getUrl()) : url1.getUrl() == null;
    }

    @Override
    public int hashCode() {
        return getUrl() != null ? getUrl().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "[" + url + "]";
    }
}
