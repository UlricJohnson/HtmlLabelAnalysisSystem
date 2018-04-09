package ink.ulricj.mapper;

import ink.ulricj.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * create by Ulric on 2018/2/26
 */

public interface UserMapper {
    /*****===== 注解方式 =====*****/

    /**
     * 查询全部用户
     *
     * @author Ulric
     * @date 2018/2/26
     */
//    @Select("SELECT * FROM tb_user")
//    @Results({ // 配置每个属性和表中字段名的关系
//            @Result(property = "id", column = "ID", id = true),
//            @Result(property = "username", column = "USERNAME"),
//            @Result(property = "sex", column = "SEX"),
//            @Result(property = "age", column = "AGE")
//    })
//    List<User> findAllUsers();

    /**
     * 根据输入名字模糊搜索用户，及其拥有的车
     *
     * @author Ulric
     * @date 2018/3/3
     */
//    @Select("SELECT * FROM tb_user WHERE username LIKE '%${username}%'")
//    @Results({ // 配置每个属性和表中字段名的关系
//            @Result(property = "id", column = "ID"),
//            @Result(property = "username", column = "USERNAME"),
//            @Result(property = "sex", column = "SEX"),
//            @Result(property = "age", column = "AGE")
//    })
//    List<User> findUserByUsername(@Param("username") String username);

    /**
     * 根据用户ID搜索用户及其拥有的车辆
     *
     * @author Ulric
     * @date 2018/3/8
     */
//    @Select("SELECT * FROM tb_user WHERE id = #{id}")
//    @Results({
//            @Result(id = true, property = "id", column = "id"),
//            @Result(property = "username", column = "username"),
//            @Result(property = "sex", column = "sex"),
//            @Result(property = "age", column = "age"),
//            @Result(property = "carList", column = "id", javaType = List.class,
//                    many = @Many(
//                            select = "ink.ulricj.mapper.CarMapper.findCarByUserId",
//                            fetchType = FetchType.EAGER
//                    ))
//    })
//    List<User> findUserById(@Param("id") Long id);

    /**
     * 使用注解，添加用户及其车辆
     *
     * @author Ulric
     * @date 2018/3/24
     */
//    @Results({
////            @Result(id = true, property = "id", column = "id"),
//            @Result(property = "username", column = "username"),
//            @Result(property = "sex", column = "sex"),
//            @Result(property = "age", column = "age"),
//            @Result(property = "carList", column = "id", javaType = List.class,
//                    many = @Many(
//                            select = "ink.ulricj.mapper.CarMapper.insertCar",
//                            fetchType = FetchType.EAGER
//                    ))
//    })
//    @Insert("INSERT INTO tb_user VALUES(NULL, #{user.username}, #{user.sex}, #{user.age})")
//    void insertUserAnno(@Param("user") User user);

    /*****===== XML方式 =====*****/

    /**
     * 根据用户名查询用户及其车辆
     *
     * @author Ulric
     * @date 2018/3/31
     */
    List<User> selectUserByUsernameXML(String username);

    /**
     * 查询上一个插入的主键ID（可能是由于主键名不是"id"，所以使用mysql的LAST_INSERT_ID()方法无法获取）
     *
     * @author Ulric
     * @date 2018/4/7
     */
    Long selectLastInsertUId();

    /**
     * 查询用户及其车辆
     *
     * @author Ulric
     * @date 2018/3/30
     */
    void insertUserXML(User user);
}
