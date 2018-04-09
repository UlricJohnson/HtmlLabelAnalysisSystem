package ink.ulricj.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * created by Ulric on 2018/3/21
 */

public class JedisUtil {
    private static JedisPool jedisPool; // jedis 的连接池
    private static String maxTotal; // 最大连接数
    private static String maxWaitMillis; // 最大等待时间
    private static String host; // 主机
    private static String port; // 端口，默认为 6379

    private static ThreadLocal<Jedis> threadLocal = new ThreadLocal<Jedis>(); // 让每个jedis对象与一个线程一一对应

    /*** 读取 jedis 属性文件并初始化 jedis 对象的基本属性 ***/
    static {
        // JDK 的工具类 ResourceBundle 用于读取 .properties 属性文件
        ResourceBundle resBundle = ResourceBundle.getBundle("jedis"); // 读取 jedis.properties 文件

        // 根据 key 查找 value
        maxTotal = resBundle.getString("maxTotal");
        maxWaitMillis = resBundle.getString("maxWaitMillis");
        host = resBundle.getString("host");
        port = resBundle.getString("port");
    }

    /*** 创建 jedis 连接池 ***/
    static {
        // 设置连接池属性
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(Integer.parseInt(maxTotal)); // 设置最大连接数
        poolConfig.setMaxWaitMillis(Integer.parseInt(maxWaitMillis)); // 设置最大等待时间
        jedisPool = new JedisPool(poolConfig, host); // 设置主机，使用默认端口
    }

    /**
     * 从连接池中获取 jedis 对象
     *
     * @author Ulric
     * @date 2018/3/21
     */
    /*public static Jedis getJedis() {
        return jedisPool.getResource();
    }*/
    public static Jedis getJedis() {
        // 从当前线程中获取 jedis 对象
        Jedis jedis = threadLocal.get();

        // 如果当前线程没有绑定一个 jedis 对象，就从连接池中获取
        if (jedis == null) {
            jedis = jedisPool.getResource();

            // 将该 jedis 对象绑定到当前线程
            threadLocal.set(jedis);
        }

        return jedis;
    }

    /**
     * 关闭指定的 jedis 连接对象
     *
     * @author Ulric
     * @date 2018/3/21
     */
    /*public static void close(Jedis jedis) {
        *//**
         * 只用 JedisPool 时调用 close() 方法并不会真正关闭连接，而是将它返回到连接池
         *
         * 源码会判断是否存在连接池，如果存在则放到连接池内，否则才调用 client.close() 关闭连接
         *//*
        if (jedis != null) {
            jedis.close();
        }
    }*/

    /**
     * 关闭当前线程的 jedis 对象
     * @author Ulric
     * @date 2018/3/22
     */
    public static void close(){
        // 从当前线程中获取 jedis 对象
        Jedis jedis = threadLocal.get();

        // 如果非空，则放回到连接池（如果没有连接池则关闭）
        if(jedis != null){
            jedis.close();

            // 使 jedis 对象与当前线程分离
            threadLocal.remove();
        }
    }

}
