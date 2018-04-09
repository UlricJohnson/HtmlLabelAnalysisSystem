package ink.ulricj.mapper;

import ink.ulricj.entity.Car;
import ink.ulricj.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * create by Ulric on 2018/3/8
 */

public interface CarMapper {
    /**
     * 根据用户ID查找车辆
     *
     * @author Ulric
     * @date 2018/3/8
     */
    @Select("select * from tb_car where user_id = #{user_id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "brand", column = "brand"),
            @Result(property = "user", column = "USER_ID", javaType = User.class,
                    one = @One(select = "ink.ulricj.mapper.UserMapper.findUserById")
            )
    })
    List<Car> findCarByUserId(@Param("user_id") Long userId);

    /**
     * 根据 ID 查询汽车
     *
     * @author Ulric
     * @date 2018/3/24
     */
    @Select("select * from tb_car where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "brand", column = "brand")
    })
    List<Car> findCarById(@Param("id") Long id);

    /**
     * 添加一辆汽车
     *
     * @author Ulric
     * @date 2018/3/24
     */
    @Results({
//            @Result(id = true, property = "id", column = "id"),
            @Result(property = "brand", column = "brand")
            /** 忽略 User 对象 ***/
//            @Result(property = "user", column = "USER_ID", javaType = User.class,
//                    one = @One(select = "ink.ulricj.mapper.UserMapper.insertUser")
//            )
    })
    @Insert("INSERT INTO tb_car VALUES(NULL, #{car.brand})")
    void insertCar(Car car);

}
