package com.itc.project.service;

import com.itc.weixin.service.MsgService;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.out.OutCustomMsg;
import com.jfinal.weixin.sdk.msg.out.OutMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;

/**
 * 默认处理所有消息的方法
 * 
 * @author airman33
 * create_date 2016年4月14日
 */
public class DefaultMsgService extends MsgService
{

	@Override
	public OutMsg processInTextMsg(InTextMsg inTextMsg)
	{
		System.out.println("这是默认处理Text的方法");
		OutCustomMsg outCustomMsg = new OutCustomMsg(inTextMsg);
		return outCustomMsg;
	}
	
	public OutMsg processInMenuEvent(InMenuEvent inMenuEvent){
		System.out.println("这是默认处理Menu的方法");
		OutTextMsg outMsg = new OutTextMsg(inMenuEvent);
		outMsg.setContent("菜单事件内容是：" + inMenuEvent.getEventKey());
		return outMsg;
	} 

}
