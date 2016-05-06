package com.itc.weixin.controller;

import com.itc.weixin.aop.InMenuEventInterceptor;
import com.itc.weixin.aop.InTextMsgInterceptor;
import com.itc.weixin.controller.msg.InMsgController;
import com.jfinal.aop.Before;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.out.OutMsg;

/**
 * 处理消息的统一入口，提供一个拦截AOP的业务逻辑层
 * 
 * @author airman33
 * create_date 2016年4月13日
 */
public class BaseMsgController extends InMsgController
{
	public BaseMsgController()
	{
		super();
	}

	@Override
	@Before(InTextMsgInterceptor.class)
	public OutMsg processInTextMsg(InTextMsg inTextMsg)
	{
		return getService().processInTextMsg(inTextMsg);
	}

	@Override
	@Before(InMenuEventInterceptor.class)
	public OutMsg processInMenuEvent(InMenuEvent inMenuEvent)
	{
		return getService().processInMenuEvent(inMenuEvent);
	}

}
