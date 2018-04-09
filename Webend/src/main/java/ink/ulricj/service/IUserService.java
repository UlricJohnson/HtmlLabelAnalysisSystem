package ink.ulricj.service;

import ink.ulricj.entity.User;

import java.util.List;

/**
 * create by Ulric on 2018/3/3
 */

public interface IUserService {

    /**
     * 根据用户名查询用户
     *
     * @author Ulric
     * @date 2018/3/3
     */
    List<User> findUserByUsername(String username);

    /**
     * 根据用户ID搜索用户及其车辆
     *
     * @author Ulric
     * @date 2018/3/9
     */
    List<User> findUserById(Long id);
}
