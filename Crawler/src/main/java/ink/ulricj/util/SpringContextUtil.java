package ink.ulricj.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 实现在普通类中获取 Spring 上下文对象和 Spring Bean
 * <p>
 * created by Ulric on 2018/4/9
 */

//@SuppressWarnings({})
@Component
public class SpringContextUtil implements ApplicationContextAware {

    // Spring 容器接口，跟 BeanFactory 类似，可以加载 Spring 容器中所有的 bean
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        applicationContext =
                appContext;
    }

    /**
     * 获取applicationContext对象
     *
     * @author Ulric
     * @date 2018/4/9
     */
    public static ApplicationContext getApplicationContext() { return applicationContext; }

    /**
     * 根据bean的id来查找对象
     *
     * @author Ulric
     * @date 2018/4/9
     */
    public static Object getBeanById(String id) { return applicationContext.getBean(id); }

    /**
     * 根据bean的class来查找对象
     *
     * @author Ulric
     * @date 2018/4/9
     */
    public static Object getBeanByClass(Class c) { return applicationContext.getBean(c); }

    /**
     * 根据bean的class来查找所有的对象(包括子类)
     *
     * @author Ulric
     * @date 2018/4/9
     */
    public static Map getBeansByClass(Class c) { return applicationContext.getBeansOfType(c); }
}
