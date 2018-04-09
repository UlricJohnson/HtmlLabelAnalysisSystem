package ink.ulricj.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ink.ulricj.entity.User;
import ink.ulricj.service.IUserService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * created by Ulric on 2018/3/4
 */
@RestController // 自动以 json 格式返回数据
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService iUserService;

    /**
     * 根据用户名模糊搜索用户（不包括拥有的车辆）
     *
     * @author Ulric
     * @date 2018/3/4
     */
    @RequestMapping("/findUserByUsername")
    public ModelAndView findUserByUsername(String username) {
        ModelAndView modelAndView = new ModelAndView();

        System.out.println("接收到的username: " + username);

        List<User> userList = iUserService.findUserByUsername(username);

        System.out.println("userList: " + userList);

        // 转为 json 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String result = objectMapper.writeValueAsString(userList);

            // 没有异常，跳到首页并返回搜索结果
            modelAndView.setViewName("index");
            modelAndView.addObject("result", result);
        } catch (JsonProcessingException e) {
            // 发生异常，转到错误页面
            modelAndView.setViewName("error");
            modelAndView.addObject("errorMsg", e.getMessage());
            e.printStackTrace();
        } finally {
            return modelAndView;
        }

    }

    /**
     * 根据用户ID搜索用户及其车辆
     *
     * @author Ulric
     * @date 2018/3/9
     */
    @RequestMapping("/findUserById")
    public ModelAndView findUserById(Long id) {
        System.out.println("你输入的数据是：" + id);

        ModelAndView modelAndView = new ModelAndView();

        List<User> userList = iUserService.findUserById(id);

        System.out.println("搜索结果：" + userList);

        // 转为 json 字符串
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String result = objectMapper.writeValueAsString(userList);
            modelAndView.setViewName("index");
            modelAndView.addObject("result", result);
        } catch (JsonProcessingException e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("errorMsg", e.getMessage());
            e.printStackTrace();
        } finally {
            return modelAndView;
        }
    }
}
