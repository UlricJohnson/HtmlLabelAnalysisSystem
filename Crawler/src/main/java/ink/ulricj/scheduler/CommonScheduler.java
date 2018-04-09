package ink.ulricj.scheduler;

import ink.ulricj.entity.Url;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.net.URL;
import java.util.*;

/**
 * URL 管理器，使用 Redis 实现
 * <p>
 * 待访问队列使用 zset（priority属性值充当其中的'分数'作排序依据），已访问队列使用 set（无序）
 * <p>
 * created by Ulric on 2018/3/6
 */

@Component
public class CommonScheduler implements Scheduler {

    /*** ===redis数据库的相关信息=== ***/
    private static final String HOST = "localhost"; // 主机
    private static final int PORT = 6379; // 端口
    private static final int TIMEOUT = 2000; // 超时时间：2秒

//    @Resource
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//     zset 操作对象
//    @Resource
//    private ZSetOperations zSetOperations = stringRedisTemplate.opsForZSet();
//     set 操作对象
//    @Resource
//    private SetOperations setOperations = stringRedisTemplate.opsForSet();

    private final String VISITED_SET_KEY = "visited_set"; // 已访问 URL 集合的键
    private final String NON_VISIT_ZSET_KEY = "non_visit_zset"; // 待访问 URL 集合的键

    private Double currPriority; // 当前访问页面的优先级，当属于该优先级的页面全部访问完才自增

    /**
     * 设置初始 url，并初始化 currPriority 为初始 url 的 priority 值
     *
     * @author Ulric
     * @date 2018/3/15
     */
    @Override
    public void setOriginUrl(List<Url> urlList) {
        setCurrPriority(urlList.get(0).getPriority());
        addNonVisitUrl(urlList);
    }

    /**
     * 从 *未* 访问队列中获取下一个 url
     *
     * @author Ulric
     * @date 2018/3/7
     */
    @Override
    public Url getNextNonVisitUrl() {
        /*** ===SpringDataRedis 版本=== ***/
//        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
//        ZSetOperations zSetOperations = stringRedisTemplate.opsForZSet();

        // 取分数等于 当前优先级 的元素
//        Set<ZSetOperations.TypedTuple<String>> set = zSetOperations.rangeByScoreWithScores(NON_VISIT_ZSET_KEY,
//                currPriority, currPriority);
        /*** ===jedis 版本=== ***/
//        Jedis jedis = JedisUtil.getJedis();
        Jedis jedis = new Jedis(HOST, PORT, TIMEOUT);
        Set<Tuple> set = jedis.zrangeByScoreWithScores(NON_VISIT_ZSET_KEY, currPriority, currPriority);

//        System.out.println("============获取到redis中优先级为" + currPriority + "的页面有" + set.size() + "个");

        if (set == null || set.size() == 0) {
            System.out.println("不存在分数为" + currPriority + "的元素");
            increaseCurrentPriority();
            return null;
        }

        // 获取到的是最后一个属于当前优先级的页面url
        if (set.size() <= 1) {
            System.out.println("最后一个优先级为" + currPriority + "的页面");
            increaseCurrentPriority(); // 当前优先级加1
        }

        // 将url集合set转为list，然后获取第1个url
        Iterator setIterator = set.iterator();
        List<Url> urlList = new ArrayList<>();
        while (setIterator.hasNext()) {
            /*** ===SpringDataRedis 版本=== ***/
//            ZSetOperations.TypedTuple<String> typedTuple = (ZSetOperations.TypedTuple) setIterator.next();
//            Url url = new Url(typedTuple.getValue());
//            url.setPriority(typedTuple.getScore().intValue());

            /*** ===jedis 版本=== ***/
            Tuple tuple = (Tuple) setIterator.next();
            Url url = new Url(tuple.getElement());
            url.setPriority((int) tuple.getScore()); // double --> int

            urlList.add(url);
        }

        // 获取集合的第一个url
        Url firstUrl = urlList.get(0);

        // 获取到后从 zset 中删除该url
        jedis.zrem(NON_VISIT_ZSET_KEY, firstUrl.getUrl()); // 为什么有时候会删除不止一个url

        // 将 Jedis 连接对象放回到连接池
//        JedisUtil.close(jedis);

        // 回收当前线程的 jedis 对象
//        JedisUtil.close();

        // 关闭 jedis 连接对象
        jedis.close();

        return firstUrl; // 返回集合的首个url
    }

    /**
     * 添加一个 url 到 *未* 访问队列中
     *
     * @author Ulric
     * @date 2018/3/7
     */
    @Override
    public void addNonVisitUrl(Url nonVisitUrl) {
        /*** ===SpringDataRedis 版本=== ***/

//        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
//        SetOperations setOperations = stringRedisTemplate.opsForSet();

        // 如果这个 url 已经访问过，则跳过
//        if (setOperations.isMember(VISITED_SET_KEY, nonVisitUrl.getUrl())) {
//            return;
//        }

        // 否则直接保存到待访问队列ZSet中
//        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
//        ZSetOperations zSetOperations = stringRedisTemplate.opsForZSet();

//        zSetOperations.add(NON_VISIT_ZSET_KEY, nonVisitUrl.getUrl(), nonVisitUrl.getPriority());

        /*** ===jedis 版本=== ***/

        /*** 检查已访问队列中是否已经存在该url ***/
//        Jedis jedis = JedisUtil.getJedis();
        Jedis jedis = new Jedis(HOST, PORT, TIMEOUT);

        // 如果这个 url 已经访问过，则跳过
        if (jedis.sismember(VISITED_SET_KEY, nonVisitUrl.getUrl())) {
            return;
        }

        // 否则直接保存到待访问队列ZSet中
        jedis.zadd(NON_VISIT_ZSET_KEY, nonVisitUrl.getPriority(), nonVisitUrl.getUrl());

        // 将 Jedis 连接对象放回到连接池
//        JedisUtil.close(jedis);

        // 回收当前线程的 jedis 对象
//        JedisUtil.close();

        //
        jedis.close();
    }

    /**
     * 添加一个 url 集合到 *未* 访问队列中，添加完成后会根据 Url 对象的 priority 属性进行升序排序
     *
     * @author Ulric
     * @date 2018/3/7
     */
    @Override
    public void addNonVisitUrl(List<Url> nonVisitUrlList) {
        // 如果传进来的集合为 null 或没有内容，则直接结束方法
        if (nonVisitUrlList == null || nonVisitUrlList.size() <= 0) {
            return;
        }

        for (Url url : nonVisitUrlList) {
            addNonVisitUrl(url);
        }
    }

    /**
     * 添加一个 url 到 *已* 访问队列中
     *
     * @param visitedUrl
     * @author Ulric
     * @date 2018/3/7
     */
    @Override
    public void addVisitedUrl(Url visitedUrl) {
        /*** ===SpringDataRedis 版本=== ***/
//        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
//        SetOperations setOperations = stringRedisTemplate.opsForSet();
//        setOperations.add(VISITED_SET_KEY, visitedUrl.getUrl());

        /*** ===jedis 版本=== ***/
//        Jedis jedis = JedisUtil.getJedis();
        Jedis jedis = new Jedis(HOST, PORT, TIMEOUT);
        jedis.sadd(VISITED_SET_KEY, visitedUrl.getUrl());

        // 将 Jedis 连接对象放回到连接池
//        JedisUtil.close(jedis);

        // 回收当前线程的 jedis 对象
//        JedisUtil.close();

        //
        jedis.close();
    }

    /**
     * 判断 *未* 访问队列是否为空，用于判断本次任务是否已经完成，若队列为空则任务完成
     *
     * @author Ulric
     * @date 2018/3/8
     */
    @Override
    public Boolean isNonVisitZSetEmpty() {
        /*** ===SpringDataRedis 版本=== ***/
//        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
//        ZSetOperations zSetOperations = stringRedisTemplate.opsForZSet();

//        if (zSetOperations.size(NON_VISIT_ZSET_KEY) <= 0) {
//            return true;
//        }

        /*** ===jedis 版本=== ***/
//        Jedis jedis = JedisUtil.getJedis();
        Jedis jedis = new Jedis(HOST, PORT, TIMEOUT);
        if (jedis.zcard(NON_VISIT_ZSET_KEY) <= 0) { // zset 集合中已经没有成员
            return true;
        }

        // 将 Jedis 连接对象放回到连接池
//        JedisUtil.close(jedis);

        // 回收当前线程的 jedis 对象
//        JedisUtil.close();

        //
        jedis.close();

        return false;
    }

    /**
     * 获取 已 访问队列中的 url数量
     *
     * @author Ulric
     * @date 2018/3/15
     */
    @Override
    public Long getNumOfVisitedSetByUrl() {
        /*** ===SpringDataRedis 版本=== ***/
//        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
//        SetOperations setOperations = stringRedisTemplate.opsForSet();
//        return setOperations.size(VISITED_SET_KEY);

        /*** ===jedis 版本=== ***/
//        Jedis jedis = JedisUtil.getJedis();
        Jedis jedis = new Jedis(HOST, PORT, TIMEOUT);
        Long count = jedis.scard(VISITED_SET_KEY);

        // 将 Jedis 连接对象放回到连接池
//        JedisUtil.close(jedis);

        // 回收当前线程的 jedis 对象
//        JedisUtil.close();

        //
        jedis.close();

        return count;
    }

    /**
     * 获取 已 访问队列中的 网站 数量（需要获取里面的 Url 对象中的 url 属性，然后提取其中域名进行统计）
     *
     * @author Ulric
     * @date 2018/3/15
     */
    @Override
    public Long getNumOfVisitedSetBySite() {
        /*** ===SpringDataRedis 版本=== ***/
//        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
//        SetOperations setOperations = stringRedisTemplate.opsForSet();


        // 获取所有成员
//        Set<String> visitedUrlSet = setOperations.members(VISITED_SET_KEY);

        /*** ===jedis 版本=== ***/
//        Jedis jedis = JedisUtil.getJedis();
        Jedis jedis = new Jedis(HOST, PORT, TIMEOUT);

        // 获取所有成员
        Set<String> visitedUrlSet = jedis.smembers(VISITED_SET_KEY);
        /**=========================**/

        // 存放访问过的 url 中的网站（即域名）
        Set<String> hostSet = new HashSet<>();

        Iterator<String> iterator = visitedUrlSet.iterator();
        while (iterator.hasNext()) {
            try {
                URL javaUrl = new URL(iterator.next());
                hostSet.add(javaUrl.getHost());
            } catch (Exception ex) {
//                ex.printStackTrace();
                continue;
            }
        }

        // 将 Jedis 连接对象放回到连接池
//        JedisUtil.close(jedis);

        // 回收当前线程的 jedis 对象
//        JedisUtil.close();

        //
        jedis.close();

        return (long) hostSet.size();
    }

    /**
     * 当当前优先级的所有页面访问完后，代表当前优先级的属性自增
     *
     * @author Ulric
     * @date 2018/3/20
     */
    @Override
    public Double increaseCurrentPriority() { return ++currPriority; }

    /**
     * 删除redis中两个集合，只在爬取任务完成后执行一次
     *
     * @author Ulric
     * @date 2018/3/22
     */
    @Override
    public void deleteRedis() {
        Jedis jedis = new Jedis(HOST, PORT, TIMEOUT);

        // 当待访问url集合中有元素的时候，循环删除其中的元素，每次删100个，全部元素删除完则大键也会被删除
        while (jedis.zcard(NON_VISIT_ZSET_KEY) > 0) {
            jedis.zremrangeByRank(NON_VISIT_ZSET_KEY, 0, 99); // 删除前100个元素
        }

        // set 集合，随机弹出指定个数的元素
        while (jedis.scard(VISITED_SET_KEY) > 0) {
            jedis.spop(VISITED_SET_KEY, 100);
        }

        jedis.close();
    }

    /***** ===== getter setter ===== *****/
    public double getCurrPriority() { return currPriority; }

    public void setCurrPriority(double currPriority) { this.currPriority = currPriority; }

//    public Queue<Url> getVisitedUrlQueue() { return visitedUrlQueue; }

//    public void setVisitedUrlQueue(Queue<Url> visitedUrlQueue) { this.visitedUrlQueue = visitedUrlQueue; }

//    public Queue<Url> getNonVisitUrlQueue() { return nonVisitUrlQueue; }

//    public void setNonVisitUrlQueue(Queue<Url> nonVisitUrlQueue) { this.nonVisitUrlQueue = nonVisitUrlQueue; }
}
