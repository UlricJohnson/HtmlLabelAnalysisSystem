package ink.ulricj.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * created by Ulric on 2018/3/9
 */

//@JsonIgnoreProperties
public class Car {
    private Long cid;
    private String brand;
    private Long userId;

    private User user;

    /*** === constructors === ***/
    public Car() {}

    public Car(String brand) { this.brand = brand; }

    /*** ===== getter setter ===== ***/
    public Long getCid() { return cid; }

    public void setCid(Long cid) { this.cid = cid; }

    public String getBrand() { return brand; }

    public void setBrand(String brand) { this.brand = brand; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    /*** ===== equals、hashCode、toString ===== ***/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (getBrand() != null ? !getBrand().equals(car.getBrand()) : car.getBrand() != null) return false;
        return getUser() != null ? getUser().equals(car.getUser()) : car.getUser() == null;
    }

    @Override
    public int hashCode() {
        int result = getBrand() != null ? getBrand().hashCode() : 0;
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        return result;
    }

    @Override // 只输出品牌，不能输出对应车主user，不然会栈溢出（User类也有输出对应的 carList）
    public String toString() {
        return "Car{" +
                "cid=" + cid +
                ", brand='" + brand + '\'' +
                ", userId=" + userId +
                '}';
    }


//    @Override
//    public String toString() {
//        return "Car{" +
//                "brand='" + brand + '\'' +
//                ", user=" + user +
//                '}';
//    }
}
