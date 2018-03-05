package ink.ulricj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * created by Ulric on 2018/2/26
 */

@Controller
public class IndexController {

    /**
     * 跳转到首页
     *
     * @author Ulric
     * @date 2018/2/26
     */
    @RequestMapping("/")
    public String index(ModelMap modelMap) {
        System.out.println("====IndexController--index()");

        modelMap.addAttribute("msg","欢迎光临");

        return "index";
    }

}
