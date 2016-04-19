package com.itc.project.service;

import com.itc.weixin.service.MsgService;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.out.OutCustomMsg;
import com.jfinal.weixin.sdk.msg.out.OutMsg;

public class FaultMsgService extends MsgService
{

	@Override
	public OutMsg processInTextMsg(InTextMsg inTextMsg)
	{
		System.out.println("这里是FaultMsgService！！！");
		OutCustomMsg outCustomMsg = new OutCustomMsg(inTextMsg);
		return outCustomMsg;	
	}

}
