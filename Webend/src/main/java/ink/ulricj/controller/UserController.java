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
     * 根据用户名模糊搜索用户
     *
     * @author Ulric
     * @date 2018/3/4
     */
    @RequestMapping("/findUserByUsername")
    public ModelAndView findUserByUsername(String username) {
        ModelAndView modelAndView = new ModelAndView();

        // 传递的数据
        ModelMap modelMap = new ModelMap();

        System.out.println("接收到的username: " + username);

        List<User> userList = iUserService.findUserByUsername(username);

        System.out.println("userList: " + userList);

        // 转为 json 字符串
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String result = objectMapper.writeValueAsString(userList);

            // 没有异常，跳到首页并返回搜索结果
            modelAndView.setViewName("index");
//            modelAndView.addObject("result", result);
            modelAndView.addAllObjects(modelMap.addAttribute("result",result));
        } catch (JsonProcessingException e) {
            // 发生异常，转到错误页面
            modelAndView.setViewName("error");
//            modelAndView.addObject("errorMsg", e);
            modelAndView.addAllObjects(modelMap.addAttribute("errorMsg",e));

            e.printStackTrace();
        } finally {
            return modelAndView;
        }

    }
}
