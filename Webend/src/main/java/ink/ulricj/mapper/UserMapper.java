package ink.ulricj.mapper;

import ink.ulricj.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * create by Ulric on 2018/2/26
 */

public interface UserMapper {
    /**
     * 查询全部用户
     *
     * @author Ulric
     * @date 2018/2/26
     */
    @Select("SELECT * FROM tb_user")
    @Results({ // 配置每个属性和表中字段名的关系
            @Result(property = "id", column = "ID"),
            @Result(property = "username", column = "USERNAME"),
            @Result(property = "sex", column = "SEX"),
            @Result(property = "age", column = "AGE")
    })
    public List<User> findAllUsers();

    /**
     * 根据输入名字模糊搜索用户
     * @author Ulric
     * @date 2018/3/3
     */
    @Select("SELECT * FROM tb_user WHERE username LIKE '%${username}%'")
    @Results({ // 配置每个属性和表中字段名的关系
            @Result(property = "id", column = "ID"),
            @Result(property = "username", column = "USERNAME"),
            @Result(property = "sex", column = "SEX"),
            @Result(property = "age", column = "AGE")
    })
    public List<User> findUserByUsername(@Param("username") String username);

}
