/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.itc.weixin.controller;

import com.itc.weixin.controller.msg.IProcessInMsg;
import com.itc.weixin.controller.msg.InMsgController;
import com.jfinal.aop.Before;
import com.jfinal.aop.Enhancer;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.jfinal.MsgControllerAdapter;
import com.jfinal.weixin.sdk.jfinal.MsgInterceptor;
import com.jfinal.weixin.sdk.msg.in.InMsg;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.event.InFollowEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.out.OutMsg;

/**
 * 将此 DemoController 在YourJFinalConfig 中注册路由，
 * 并设置好weixin开发者中心的 URL 与 token ，使 URL 指向该
 * DemoController 继承自父类 WeixinController 的 index
 * 方法即可直接运行看效果，在此基础之上修改相关的方法即可进行实际项目开发
 */
public class ItcMsgController extends MsgControllerAdapter {

	static Log logger = Log.getLog(ItcMsgController.class);

	/**
	 * 如果要支持多公众账号，只需要在此返回各个公众号对应的  ApiConfig 对象即可
	 * 可以通过在请求 url 中挂参数来动态从数据库中获取 ApiConfig 属性值
	 */
	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();
		
		// 配置微信 API 相关常量
		ac.setToken(PropKit.get("token"));
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));
		
		/**
		 *  是否对消息进行加密，对应于微信平台的消息加解密方式：
		 *  1：true进行加密且必须配置 encodingAesKey
		 *  2：false采用明文模式，同时也支持混合模式
		 */
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
		
		return ac;
	}
	
	/**
	 * 重写了MsgController的Index方法
	 */
	@Before(MsgInterceptor.class)
	public void index() {
		if (ApiConfigKit.isDevMode()) {
			System.out.println("接收消息:");
			System.out.println(getInMsgXml());
		}
		
		InMsg msg = getInMsg();
		/* 利用Enhancer对对象进行AOP增强。PS:没有办法对带参的构造函数进行增强  */
		IProcessInMsg serviceController = Enhancer.enhance(new BaseMsgController());
		/* 并且将JFinal Weixin解析后的InMsg保存到二级Controller中  */
		((InMsgController) serviceController).setInMsg(msg);
		
		OutMsg outMsg = processInMsg(serviceController, msg);
		if(null == outMsg)
			renderNull();
		else
			render(outMsg);
	}
	
	/**
	 * 用于分发处理各类InMsg消息
	 * @author airman33
	 * modify_date 2016年4月15日
	 * @param serviceController
	 * @param msg
	 * @return
	 */
	private OutMsg processInMsg(IProcessInMsg serviceController, InMsg msg)
	{
		OutMsg outMsg;
		if (msg instanceof InMenuEvent)
			outMsg = serviceController.processInMenuEvent((InMenuEvent) msg);
		else if (msg instanceof InTextMsg)
			outMsg = serviceController.processInTextMsg((InTextMsg) msg);
		else
			outMsg = null;
		return outMsg;
	}

	/* 以下部分为JFinal Weixin MsgController需要实现的方法，可以忽略 */
	@Override
	protected void processInFollowEvent(InFollowEvent inFollowEvent)
	{
		
	}

	@Override
	protected void processInTextMsg(InTextMsg inTextMsg)
	{
		
	}

	@Override
	protected void processInMenuEvent(InMenuEvent inMenuEvent)
	{
		
	}
}






