package com.itc.weixin.controller.msg;

import com.itc.weixin.aop.DuplicateReqInterceptor;
import com.itc.weixin.service.MsgService;
import com.jfinal.aop.Clear;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.msg.in.InMsg;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;

/**
 * 缺省适配模式：抽象类+接口；
 * 包含了两个重要的成员，InMsg和MsgService；
 * InMsg主要是保存由JFinal Weixin解析后的信息，方便后续调用；
 * MsgService主要是实际处理方法的对象，在接口的方法中进行AOP拦截，根据Mapping进行反射生成；
 * userId和even是方便后续使用
 * clazz是根据用户配置文件中的配置确认默认消息处理服务的类
 * 
 * @author airman33
 * create_date 2016年4月8日
 */
public abstract class InMsgController implements IProcessInMsg
{
	static Log logger = Log.getLog(InMsgController.class);
	
	private InMsg inMsg = null;
	private MsgService service = null;
	private String userId = null;
	private String event = null;
	private static Class<?> clazz = null;

	public InMsgController()
	{
		try
		{
			if(null == clazz)
			{
				String serviceClass = PropKit.get("DefaultMsgService");
				if(StrKit.isBlank(serviceClass))
					throw new RuntimeException("请确认在配置文件中已配置DefaultMsgService！");
				clazz = Class.forName(serviceClass);
			}
			service = (MsgService) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			logger.error("请确认DefaultMsgService的配置是否正确！！", e);
		}
	}
	
	public MsgService getService()
	{
		return service;
	}

	public void setService(MsgService service)
	{
		this.service = service;
	}

	public InMsg getInMsg()
	{
		return inMsg;
	}

	public String getUserId()
	{
		return userId;
	}
	
	public String getEvent()
	{
		return event;
	}
	
	@Clear(DuplicateReqInterceptor.class)
	public void setInMsg(InMsg inMsg)
	{
		this.inMsg = inMsg;
		userId = inMsg.getFromUserName();
		if("event".equalsIgnoreCase(inMsg.getMsgType()))
			event = ((InMenuEvent) inMsg).getEvent();
	}
}
