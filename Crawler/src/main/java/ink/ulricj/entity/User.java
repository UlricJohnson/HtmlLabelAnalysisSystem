package ink.ulricj.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户表，测试用
 * <p>
 * created by Ulric on 2018/2/26
 */


public class User {
    private Long uid; // 主键ID
    private String username; // 用户名
    private String sex; // 性别
    private String age; // 年龄

    private List<Car> carList;

    /***** ===== getter setter ===== *****/
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<Car> getCarList() { return carList; }

    public void setCarList(List<Car> carList) { this.carList = carList; }

    /*** === equals()、hashCode()、toString() === ***/
    @Override // username、sex、age 都相等则相等
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!getUsername().equals(user.getUsername())) return false;
        if (getSex() != null ? !getSex().equals(user.getSex()) : user.getSex() != null) return false;
        return getAge() != null ? getAge().equals(user.getAge()) : user.getAge() == null;
    }

    @Override
    public int hashCode() {
        int result = getUsername().hashCode();
        result = 31 * result + (getSex() != null ? getSex().hashCode() : 0);
        result = 31 * result + (getAge() != null ? getAge().hashCode() : 0);
        return result;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "username='" + username + '\'' +
//                ", sex='" + sex + '\'' +
//                ", age='" + age + '\'' +
//                '}';
//    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", carList=" + carList +
                '}';
    }
}
