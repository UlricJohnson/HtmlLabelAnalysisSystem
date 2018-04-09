package ink.ulricj.user$car;

import ink.ulricj.entity.Car;
import ink.ulricj.entity.User;
import ink.ulricj.mapper.CarMapper;
import ink.ulricj.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试：添加用户及其车辆
 * <p>
 * created by Ulric on 2018/3/24
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestInsertUser {
    @Resource
    private CarMapper carMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 添加一个用户及其车辆（需分步骤）
     *
     * @author Ulric
     * @date 2018/3/28
     */
    @Test
    public void testInsert() {
        /*** === 初始化数据 === ***/
        List<Car> carList = new ArrayList();
        carList.add(new Car("Chevrolet"));
        carList.add(new Car("McLaren"));
        carList.add(new Car("Mercedes-AMG"));

        User user = new User();
        user.setAge("19");
        user.setSex("female");
        user.setUsername("Bitch");
        user.setCarList(carList);
        /*** === 初始化 end === ***/

        // 添加用户，注解实现
//        userMapper.insertUserAnno(user);

        // 添加用户，XML
        // 1、先单独添加用户信息
        userMapper.insertUserXML(user);

        // 2、然后获取刚添加的 “一” 方的主键ID
        Long newUId = userMapper.selectLastInsertUId();

        // 3、再将 “一” 方的主键 ID 赋值给 “多” 方的全部对象
        List<Car> list = user.getCarList();
        for (Car car:list) {
            car.setUserId(newUId);
        }

        // 4、最后将 “多” 方的全部对象插入
        carMapper.insertCarBatchXML(list);
    }
}
