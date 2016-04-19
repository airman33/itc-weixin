package com.itc.weixin.controller.msg;

import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.out.OutMsg;

/**
 * 接口，定义了需要实现的方法，主要用于业务层的AOP被拦截；
 * 需要后端需要响应的消息，都必须先在这里进行声明，然后在BaseMsgController中实现
 * 
 * @author airman33
 * create_date 2016年4月13日
 */
public interface IProcessInMsg
{
	public abstract OutMsg processInTextMsg(InTextMsg inTextMsg);
	
	public abstract OutMsg processInMenuEvent(InMenuEvent inMenuEvent);
}
