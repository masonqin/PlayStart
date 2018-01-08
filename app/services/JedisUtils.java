package services;

import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.typesafe.config.ConfigFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisUtils {
    private static final Logger logger = LoggerFactory.getLogger(JedisUtils.class);
    private static JedisPool pool;
    private static String host = ConfigFactory.load().getString("redis.host");
    private static int port = ConfigFactory.load().getInt("redis.port");
    private static int max_wait = ConfigFactory.load().getInt("redis.pool.max-wait");
    private static int max_total = ConfigFactory.load().getInt("redis.pool.max-active");
    private static int max_idle = ConfigFactory.load().getInt("redis.pool.max-idle");
    private static int min_idle = ConfigFactory.load().getInt("redis.pool.min-idle");
    private static String password = ConfigFactory.load().getString("redis.password");
    private static int timeout = ConfigFactory.load().getInt("redis.timeout");

    public static final String ADX_VIDEO_LABEL_PRE = "adx_video_label_";


    static  {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxWaitMillis(max_wait);
            config.setMaxTotal(max_total);
            config.setMaxIdle(max_idle);
            config.setMinIdle(min_idle);
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);
            config.setTestWhileIdle(true);
            config.setBlockWhenExhausted(true);
//            config.setNumTestsPerEvictionRun(max_wait);
            try {
                if (Strings.isNullOrEmpty(password)) {
                    pool = new JedisPool(config, host, port, timeout);
                } else {
                    pool = new JedisPool(config, host, port, timeout, password);
                }
            } catch (Exception e) {
                logger.error("jedis init error:", e);
            }
        }
    }

    public static Jedis getResource() {
        DateTime begin=DateTime.now();
        Jedis jedis = null;
        try {
            if (jedis == null) {
                jedis = pool.getResource();
            } else if (!jedis.isConnected()) {
                jedis.close();
                jedis = pool.getResource();
            }
        } catch (Exception e) {
            logger.error("jedis getResource error:", e);
            e.printStackTrace();
        }
        logger.debug("getResource time:"+ Long.toString(DateTime.now().getMillis()-begin.getMillis()));
        return jedis;
    }

    public static Set<String> smembers(String key){
        Jedis jedis=getResource();
        Set<String> set=null;
        try {
            DateTime begin=DateTime.now();
            set=jedis.smembers(key);
            logger.debug("smembers time:"+ Long.toString(DateTime.now().getMillis()-begin.getMillis()));
        } catch (Exception e) {
            logger.error("set error:"+e.getMessage());
        }
        close(jedis);
        return set;
    }

    public static void setex(final String key, final int seconds, final String value){
        Jedis jedis=getResource();
        try {
            DateTime begin=DateTime.now();
            jedis.setex(key,seconds,value);
            logger.debug("setex time:"+ Long.toString(DateTime.now().getMillis()-begin.getMillis()));
        } catch (Exception e) {
            logger.error("setex error:"+e.getMessage());
        }
        close(jedis);
    }

    public static void sadd(final String key, final String value){
        Jedis jedis=getResource();
        try {
            DateTime begin=DateTime.now();
            jedis.sadd(key,value);
            logger.debug("sadd time:"+ Long.toString(DateTime.now().getMillis()-begin.getMillis()));
        } catch (Exception e) {
            logger.error("sadd error:"+e.getMessage());
        }
        close(jedis);
    }

    public static String get(final String key){
        Jedis jedis=getResource();
        String result=null;
        try {
            DateTime begin=DateTime.now();
            result=jedis.get(key);
            logger.debug("gettime:"+ Long.toString(DateTime.now().getMillis()-begin.getMillis()));
        } catch (Exception e) {
            logger.error("get error:"+e.getMessage());
        }
        close(jedis);
        return result;
    }

    public static void del(final String key){
        Jedis jedis=getResource();
        try {
            DateTime begin=DateTime.now();
            jedis.del(key);
            logger.debug("deltime:"+ Long.toString(DateTime.now().getMillis()-begin.getMillis()));
        } catch (Exception e) {
            logger.error("del error:"+e.getMessage());
        }
        close(jedis);
    }

    public static void srem(final String key,final String item){
        Jedis jedis=getResource();
        try {
            DateTime begin=DateTime.now();
            jedis.srem(key,item);
            logger.debug("srem time:"+ Long.toString(DateTime.now().getMillis()-begin.getMillis()));
        } catch (Exception e) {
            logger.error("srem error:"+e.getMessage());
        }
        close(jedis);
    }

    public static String srandmember(final String key){
        logger.debug("srandmember key:"+key);
        Jedis jedis=getResource();
        String result=null;
        try {
            DateTime begin=DateTime.now();
            result=jedis.srandmember(key);
            logger.debug("srandmember time:"+ Long.toString(DateTime.now().getMillis()-begin.getMillis()));
        } catch (Exception e) {
            logger.error("srandmember error:"+e.getMessage());
        }
        close(jedis);
        return result;
    }

    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
    public static void destroy() {
        if (pool != null) {
            pool.destroy();
        }
    }

}
