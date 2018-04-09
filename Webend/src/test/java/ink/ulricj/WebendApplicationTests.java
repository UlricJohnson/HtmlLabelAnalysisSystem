package ink.ulricj;

import ink.ulricj.controller.UserController;
import ink.ulricj.entity.User;
import ink.ulricj.mapper.CarMapper;
import ink.ulricj.mapper.UserMapper;
import ink.ulricj.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * created by Ulric on 2018/3/4
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class WebendApplicationTests {
    @Resource
    private UserMapper userMapper;

    @Resource
    private CarMapper carMapper;

    /**
     * 测试根据用户ID查找用户及其拥有的车辆
     *
     * @author Ulric
     * @date 2018/3/8
     */
    @Test
    public void testFindUserById() {
        List<User> userList = userMapper.findUserById(3L);

        System.out.println(userList);
    }

    /**
     * 测试根据用户ID查找车辆
     *
     * @author Ulric
     * @date 2018/3/8
     */
    @Resource
    private UserController userController;

    @Resource
    private IUserService iUserService;

    @Test
    public void testFindCarByUserId() {
//        List<Car> carList = carMapper.findCarByUserId(1L);
        System.out.println(userController.findUserById(3L));
//        System.out.println(iUserService.findUserById(3L));
//        System.out.println(userMapper.findUserById(3L));
    }

}
