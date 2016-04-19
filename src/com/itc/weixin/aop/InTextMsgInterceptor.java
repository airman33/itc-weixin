package com.itc.weixin.aop;

import com.itc.weixin.controller.msg.InMsgController;
import com.itc.weixin.core.EventMapping;
import com.itc.weixin.util.SessionUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;

/**
 * Text类型消息处理的拦截器
 * 
 * @author airman33
 * create_date 2016年4月15日
 */
public class InTextMsgInterceptor implements Interceptor
{

	@Override
	public void intercept(Invocation inv)
	{
		InMsgController controller = inv.getTarget();
		String userId = controller.getUserId();
		String eventKey = SessionUtil.get(userId);
		/* 根据eventKey查询相应的Service */
		if(StrKit.notBlank(eventKey))
		{
			/* 通过设置Controller中的Service，实现消息的二级分发处理 */
			controller.setService(EventMapping.me.get(eventKey));
			/* 处理完用户请求后，清除Session信息 */
			SessionUtil.remove(userId);
		}
		inv.invoke();		
	}

}
