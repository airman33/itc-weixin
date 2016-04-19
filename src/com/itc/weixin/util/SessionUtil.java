package com.itc.weixin.util;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * 用户会话UserSession的工具类，保存用户的会话信息
 * 
 * @author airman33
 * create_date 2016年4月14日
 */
public class SessionUtil
{
	/**
	 * 缓存的名称，需要跟encache.xml中配置的一致
	 */
	private static final String cache = "userSession";
	
	/**
	 * 添加或者更新Cache中的Session信息，如果为空则添加，如果不为空则先删除，然后添加
	 * @author airman33
	 * modify_date 2016年4月14日
	 * @param userId
	 * @param eventKey
	 */
	public static void put(String userId, String eventKey)
	{
		if(StrKit.notBlank((String) CacheKit.get(cache, userId)))
			CacheKit.remove(cache, userId);
		CacheKit.put(cache, userId, eventKey);
	}
	
	/**
	 * 根据userId从Cache中获取Session的信息
	 * @author airman33
	 * modify_date 2016年4月14日
	 * @param userId
	 * @return
	 */
	public static String get(String userId)
	{
		return CacheKit.get(cache, userId);
	}
	
	/**
	 * 清除userId对应的Session信息
	 * @author airman33
	 * modify_date 2016年4月15日
	 * @param userId
	 */
	public static void remove(String userId)
	{
		CacheKit.remove(cache, userId);
	}
}
