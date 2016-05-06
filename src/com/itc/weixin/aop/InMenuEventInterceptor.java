package com.itc.weixin.aop;

import com.itc.weixin.controller.msg.InMsgController;
import com.itc.weixin.util.SessionUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;

/**
 * Menu事件的拦截器，具体包含的类型可参考JFinal Weixin中的InMenuEvent
 * 
 * @author airman33
 * create_date 2016年4月14日
 */
public class InMenuEventInterceptor implements Interceptor
{

	@Override
	public void intercept(Invocation inv)
	{
		InMsgController controller = inv.getTarget();
		/* 如果为Click事件，则在Cache中添加新的用户会话信息 */
		if(InMenuEvent.EVENT_INMENU_CLICK.equalsIgnoreCase(controller.getEvent()))
		{
			String userId = controller.getUserId();
			String eventKey = ((InMenuEvent) controller.getInMsg()).getEventKey();
			SessionUtil.put(userId, eventKey);
		}
		
		inv.invoke();
	}

}
