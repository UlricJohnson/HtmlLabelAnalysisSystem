package ink.ulricj;

import ink.ulricj.crawler.Crawler;
import ink.ulricj.entity.Url;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import javax.annotation.Resource;
import java.util.*;

/**
 * created by Ulric on 2018/3/20
 */

@SpringBootTest
@RunWith(SpringRunner.class)
//@MapperScan("ink.ulricj.mapper")
//@EntityScan("ink.ulricj.entity")
public class CrawlerApplicationTests {

    //    @Autowired
//    @Resource
//    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    private final String VISITED_SET_KEY = "visited_url_set"; // 已访问 URL 集合（set 集合，无序）
    private final String NON_VISIT_ZSET_KEY = "non_visit_url_zset"; // 待访问 URL集合（使用有序集合zset，priority属性值充当其中的'分数'作排序依据）

    /**
     * 测试1：SpringDataRedis 的使用
     * <p>
     * 两个类：StringRedisTemplate 和 RedisTemplate
     *
     * @author Ulric
     * @date 2018/3/20
     */
    @Test
    public void testSDR() {
        /*** 1、RedisTemplate ***/

        /**
         * 使用 JdkSerializationRedisSerializer 作序列化类，在存入数据的时候会先序列化成字节数组，
         *
         * 这样使用 RedisDesktopManager 查看的时候只能看到字节数组的形式，而不是可读形式（即调用方法的时候
         *
         * 设置的字符串内容），而使用 RedisTemplate 也无法获取该数据（获取到 NULL）
         */
//        SetOperations setOperations = redisTemplate.opsForSet();
//        setOperations.add("setKey", "setVal-1", "setVal-2", "setVal-3", "setVal-1", "中文");

        /*** 2、StringRedisTemplate ***/
        /**
         * 使用 StringRedisSerializer 作序列化类，
         */
        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();

        setOperations.add("setKey", "val-1");
        setOperations.add("setKey", "val-2");
        setOperations.add("setKey", "val-3");
        System.out.println(setOperations.members("setKey"));
    }

    /**
     * 测试2：向 zset 中存入 url 对象，并按 priority 排序，然后取出来
     *
     * @author Ulric
     * @date 2018/3/20
     */
    @Test
    public void testZSet() {
        // 获取 zset 的操作对象
        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();

        /*** 保存数据 ***/
//        zSetOperations.add(NON_VISIT_ZSET_KEY, "url-1", 0);
//        zSetOperations.add(NON_VISIT_ZSET_KEY, "url-2", 0);
//        zSetOperations.add(NON_VISIT_ZSET_KEY, "url-3", 0);
//        zSetOperations.add(NON_VISIT_ZSET_KEY, "url-4", 1);
//        zSetOperations.add(NON_VISIT_ZSET_KEY, "url-5", 1);
//        zSetOperations.add(NON_VISIT_ZSET_KEY, "url-4", 2);
//        zSetOperations.add(NON_VISIT_ZSET_KEY, "url-6", 2);

        /*** 获取数据 ***/
        // 1、rangeWithScores()
//        Set<ZSetOperations.TypedTuple<String>> typedTuples = zSetOperations.rangeWithScores(NON_VISIT_ZSET_KEY, 1, 4);

        //
        System.out.println("=================删除前：");
        Set<ZSetOperations.TypedTuple<String>> typedTuples = zSetOperations.rangeByScoreWithScores
                (NON_VISIT_ZSET_KEY, 0, 4);
        Iterator<ZSetOperations.TypedTuple<String>> typedTupleIterator = typedTuples.iterator();
        List<String> list = new ArrayList();
        while (typedTupleIterator.hasNext()) {
            ZSetOperations.TypedTuple<String> typedTuple = typedTupleIterator.next();
            list.add(typedTuple.getValue());
        }
        System.out.println("================list: " + list);
//        for (ZSetOperations.TypedTuple typedTuple : typedTuples) {
//            System.out.println(typedTuple.getValue() + "=" + typedTuple.getScore());
//        }

        // 删除第1个数据
        zSetOperations.remove(NON_VISIT_ZSET_KEY, list.get(0));
        System.out.println("=================删除后：");
        for (ZSetOperations.TypedTuple typedTuple : zSetOperations.rangeByScoreWithScores
                (NON_VISIT_ZSET_KEY, 0, 4)) {
            System.out.println(typedTuple.getValue() + "=" + typedTuple.getScore());
        }

    }



    /**
     * 获取指定分数的元素，并取第1个
     *
     * @author Ulric
     * @date 2018/3/20
     */
    @Test
    public void testZSet2() {
        ZSetOperations zSetOperator = stringRedisTemplate.opsForZSet();

        double curr = 3;
//        while (true) {
        // 取分数在 [curr, curr+1) 区间的元素，即取分数等于 curr 的元素
        Set<ZSetOperations.TypedTuple<String>> set = zSetOperator.rangeByScoreWithScores(NON_VISIT_ZSET_KEY, curr,
                curr);
        if (set == null || set.size() <= 0) {
            System.out.println("不存在分数为" + curr + "的元素");
            return;
        }

        if (set.size() > 1) {
            System.out.println("不止一个哟");
        } else {
            System.out.println("只有一个窝");
        }

        Map<String, Double> map = new HashMap<>();
        Iterator setIterator = set.iterator();
        while (setIterator.hasNext()) {
            ZSetOperations.TypedTuple<String> typedTuple = (ZSetOperations.TypedTuple) setIterator.next();
            map.put(typedTuple.getValue(), typedTuple.getScore());
        }

        System.out.println("map:" + map);

//        }
    }


    @Test
    public void testJedis() {
        // 这个方法专用的 jedis 数据类型键
        final String SET_KEY = "set_testJedis";
        final String ZSET_KEY = "zset_testJedis";

        Jedis jedis = new Jedis("localhost"); // 设置服务端主机
        System.out.println("PING...\n" + jedis.ping());

        /** 操作 set **/
        jedis.sadd(SET_KEY, "setVal-1", "setVal-2", "setVal-3");
        System.out.println("set集合：" + jedis.smembers(SET_KEY));

        /** 操作 zset **/
        Map<String, Double> zMap = new HashMap<>();
        zMap.put("zVal-1", 0D);
        zMap.put("zVal-2", 0D);
        zMap.put("zVal-3", 1D);
        zMap.put("zVal-4", 1D);
        zMap.put("zVal-5", 2D);
        zMap.put("zVal-6", 3D);
        zMap.put("zVal-7", 4D);
        jedis.zadd(ZSET_KEY, zMap);
        Set<Tuple> tupleSet = jedis.zrangeByScoreWithScores(ZSET_KEY, 0D, 10D);
        Iterator<Tuple> tupleIterator = tupleSet.iterator();
        System.out.println("zset集合：\n{");
        while (tupleIterator.hasNext()) {
            Tuple tuple = tupleIterator.next();
            System.out.println("\t" + tuple.getElement() + "=" + tuple.getScore());
        }
        System.out.println("}");

    }
}
