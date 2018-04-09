package ink.ulricj.user$car;

import ink.ulricj.entity.User;
import ink.ulricj.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试：查询用户
 * <p>
 * created by Ulric on 2018/3/28
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSelect {
    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户名查询用户及其车辆
     *
     * @author Ulric
     * @date 2018/3/30
     */
    @Test
    public void testSelectUserByName() {
        List<User> userList = userMapper.selectUserByUsernameXML("a");

        for (User user : userList) {
            System.out.println(user);
        }
//        System.out.println(userList);
    }
}
