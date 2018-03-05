package ink.ulricj.entity;

import java.io.Serializable;

/**
 * created by Ulric on 2018/2/26
 */

public class Url implements Serializable {
    private Long id; // 主键ID
    private String url; // url 全称，包括协议
    private Boolean isVisited; // 是否已经被访问



    /* ===== get set ===== */


    /* === equals()、hashCode()、toString() === */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Url url1 = (Url) o;

        return url != null ? url.equals(url1.url) : url1.url == null;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "[" + url + "]";
    }
}
