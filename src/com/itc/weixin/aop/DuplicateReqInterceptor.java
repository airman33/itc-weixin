package com.itc.weixin.aop;

import com.itc.weixin.controller.msg.InMsgController;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.weixin.sdk.msg.in.InMsg;

/**
 * 微信服务器在五秒内收不到响应会断掉连接，并且重新发起请求，总共重试三次；
 * 用于拦截微信服务器重复发起的请求
 * 
 * @author airman33
 * create_date 2016年4月15日
 */
public class DuplicateReqInterceptor implements Interceptor
{

	@Override
	public void intercept(Invocation inv)
	{
		/* 在开发阶段可以忽略这个拦截器的存在 */
		if (!PropKit.getBoolean("encryptMessage",false)) 
		{
			inv.invoke();
		}
		InMsgController controller = inv.getTarget();
		InMsg msg = controller.getInMsg();
		/* 关于重试的消息排重，推荐使用FromUserName + CreateTime 排重 */
		StringBuilder sb = new StringBuilder(msg.getFromUserName()).append(msg.getCreateTime());
		if(null == CacheKit.get("userRequest", sb.toString()))
		{
			inv.invoke();
			/* 处理过的用户请求，在Cache缓存中增加标识，6秒内不再进行处理相同的请求 */
			CacheKit.put("userRequest", sb.toString(), true);
		}	
	}

}
