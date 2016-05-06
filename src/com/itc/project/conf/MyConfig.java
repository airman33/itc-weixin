package com.itc.project.conf;

import com.itc.project.service.FaultMsgService;
import com.itc.weixin.core.EventMapping;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;

public class MyConfig
{

	/**
	 * 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
	 * @param pro 生产环境配置文件
	 * @param dev 开发环境配置文件
	 */
	public static void loadProp(String pro, String dev) {
		try {
			PropKit.use(pro);
		}
		catch (Exception e) {
			PropKit.use(dev);
		}
	}
	
	public static void configConstant(Constants me)
	{
		loadProp("a_little_config_pro.txt", "a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
	}

	public static void configRoute(Routes me)
	{
		
	}

	public static void configPlugin(Plugins me)
	{
		// C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		// me.add(c3p0Plugin);
	}

	public static void configInterceptor(Interceptors me)
	{

	}

	public static void configHandler(Handlers me)
	{
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		JFinal.start("webapp", 80, "/", 5);
	}

	public static void configEventMapping()
	{
		/* 用于配置eventKey与msgService的映射关系 */
		EventMapping.me.add("Fault", FaultMsgService.class);
	}
}
