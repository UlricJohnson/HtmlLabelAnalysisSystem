package ink.ulricj.service.impl;

import ink.ulricj.entity.User;
import ink.ulricj.mapper.UserMapper;
import ink.ulricj.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * created by Ulric on 2018/3/3
 */

@Service
public class UserService implements IUserService {

    //    @Autowired
    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户名查询用户
     *
     * @author Ulric
     * @date 2018/3/3
     */
    @Override
    public List<User> findUserByUsername(String username) {
        System.out.println("====UserService--findUserByUsername()");

        List<User> userList = userMapper.findUserByUsername(username);

        return userList;
    }

    /**
     * 根据用户ID搜索用户及其车辆
     *
     * @param id
     * @author Ulric
     * @date 2018/3/9
     */
    @Override
    public List<User> findUserById(Long id) {
        System.out.println("====UserService--findUserById()");
        return userMapper.findUserById(id);
    }
}
