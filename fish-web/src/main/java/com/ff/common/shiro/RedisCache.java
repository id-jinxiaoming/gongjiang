package com.ff.common.shiro;

import com.ff.system.model.User;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisCache<K, V> implements Cache<K, V> {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
		
	/**
     * The wrapped Jedis instance.
     */
	private RedisTemplate<String, Object> redisTemplate;	
	private ValueOperations<String, Object> valueOperations;
	/**
	 * The Redis key prefix for the sessions 
	 */
	private String keyPrefix ;
	private int expire;
	/**
	 * Returns the Redis session keys
	 * prefix.
	 * @return The prefix
	 */
	public String getKeyPrefix() {
		return keyPrefix;
	}

	/**
	 * Sets the Redis sessions key 
	 * prefix.
	 * @param keyPrefix The prefix
	 */
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}
	
	/**
	 * 通过一个JedisManager实例构造RedisCache
	 */
	public RedisCache(RedisTemplate<String, Object> redisTemplate){
		 if (redisTemplate == null) {
	         throw new IllegalArgumentException("Cache argument cannot be null.");
	     }
	     this.redisTemplate = redisTemplate;
	     valueOperations = redisTemplate.opsForValue();
	}
	
	/**
	 * Constructs a cache instance with the specified
	 * Redis manager and using a custom key prefix.
	 * @param prefix The Redis key prefix
	 */
	public RedisCache(RedisTemplate<String, Object> redisTemplate, 
				String prefix,int expire){
		 
		 this.redisTemplate = redisTemplate;
		 valueOperations = redisTemplate.opsForValue();
		// set the prefix
		this.keyPrefix = prefix;
		this.expire = expire;
	}
 	

	public V get(K key) throws CacheException {
		logger.debug("根据key从Redis中获取对象 key [" + key + "]");

		SimplePrincipalCollection a=  (SimplePrincipalCollection) key;
		User u=(User) a.getPrimaryPrincipal();



		try {
			if (u == null) {
	            return null;
	        }else{
				V value = (V)valueOperations.get(keyPrefix+u.getId());
	        	return value;
	        }
		} catch (Throwable t) {
			throw new CacheException(t);
		}

	}


	public V put(K key, V value) throws CacheException {
		SimplePrincipalCollection a=  (SimplePrincipalCollection) key;
		User u=(User) a.getPrimaryPrincipal();
		logger.debug("根据key从存储 key [" + key + "]");
		 try {
			 	valueOperations.set(keyPrefix+u.getId(), value,expire, TimeUnit.MILLISECONDS);
	            return value;
	        } catch (Throwable t) {
	            throw new CacheException(t);
	        }
	}

	public V remove(K key) throws CacheException {
		logger.debug("从redis中删除 key [" + key + "]");
		try {



            V previous = get(key);

			SimplePrincipalCollection a=  (SimplePrincipalCollection) key;
			User u=(User) a.getPrimaryPrincipal();
            redisTemplate.delete(keyPrefix+u.getId());
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}


	public void clear() throws CacheException {
		logger.debug("从redis中删除所有元素");
		try {
			redisTemplate.delete(redisTemplate.keys(keyPrefix+"*"));
		} catch (Throwable t) {
            throw new CacheException(t);
        }
	}


	public int size() {
		try {
            return redisTemplate.keys(keyPrefix+"*").size();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@SuppressWarnings("unchecked")
	public Set<K> keys() {
		try {
            Set<String> keys = redisTemplate.keys(keyPrefix+"*");
            if (CollectionUtils.isEmpty(keys)) {
            	return Collections.emptySet();
            }else{
            	Set<K> newKeys = new HashSet<K>();
            	for(String key:keys){
            		newKeys.add((K)key);
            	}
            	return newKeys;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}


	public Collection<V> values() {
		try {
			 Set<String> keys = redisTemplate.keys(keyPrefix+"*");
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList<V>(keys.size());
                for (String key : keys) {
                    @SuppressWarnings("unchecked")
					V value = get((K)key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

}
