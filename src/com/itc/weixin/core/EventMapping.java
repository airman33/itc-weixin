package com.itc.weixin.core;

import java.util.HashMap;
import java.util.Map;

import com.itc.weixin.service.MsgService;
import com.jfinal.log.Log;

/**
 * 用于保存eventKey与msgService的映射关系
 * 
 * @author airman33
 * create_date 2016年4月15日
 */
public class EventMapping
{
	private static final Log log = Log.getLog(EventMapping.class);
	
	private final Map<String, Class<? extends MsgService>> serviceRoutes = new HashMap<String, Class<? extends MsgService>>();
	
	public static final EventMapping me = new EventMapping();

	/**
	 * 添加 eventKey到msgService的映射关系
	 * @author airman33
	 * modify_date 2016年4月15日
	 * @param eventKey
	 * @param serviceName
	 */
	public void add(String eventKey, Class<? extends MsgService> serviceName)
	{
		if(null != serviceRoutes.put(eventKey, serviceName))
			throw new RuntimeException("eventKey为【"+eventKey+"】的映射关系以存在，请确保eventKey的唯一性！");
	}
	
	/**
	 * 在映射关系中，获取相应eventKey的msgService，并创建相应的实例
	 * @author airman33
	 * modify_date 2016年4月15日
	 * @param eventKey
	 * @return
	 */
	public MsgService get(String eventKey)
	{
		Class<? extends MsgService> msgServiceClass = serviceRoutes.get(eventKey);
		if(null == msgServiceClass)
		{
			log.warn("找不到eventKey为【"+eventKey+"】的MsgService，请确保已配置映射关系！");
			return null;
		}
		MsgService msgService = null;
		try
		{
			msgService = msgServiceClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e)
		{
			log.error("创建【"+msgServiceClass+"】类的实例时出错！",e);
		}
		return msgService;
	}
}
