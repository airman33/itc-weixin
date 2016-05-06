package com.itc.weixin.conf;

import com.itc.project.conf.MyConfig;
import com.itc.weixin.aop.DuplicateReqInterceptor;
import com.itc.weixin.controller.ItcMsgController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.weixin.sdk.api.ApiConfigKit;

public class CoreConfig extends JFinalConfig
{

	/**
	 * 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
	 * @param pro 生产环境配置文件
	 * @param dev 开发环境配置文件
	 */
	public void loadProp(String pro, String dev) {
		try {
			PropKit.use(pro);
		}
		catch (Exception e) {
			PropKit.use(dev);
		}
	}
	
	@Override
	public void configConstant(Constants me)
	{
		MyConfig.configConstant(me);
		/*loadProp("a_little_config_pro.txt", "a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));*/
		
		// ApiConfigKit 设为开发模式可以在开发阶段输出请求交互的 xml 与 json 数据
		ApiConfigKit.setDevMode(me.getDevMode());
	}

	@Override
	public void configRoute(Routes me)
	{
		me.add("/msg", ItcMsgController.class);
		MyConfig.configRoute(me);
	}

	@Override
	public void configPlugin(Plugins me)
	{
		MyConfig.configPlugin(me);
		// C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		// me.add(c3p0Plugin);
		
		EhCachePlugin ecp = new EhCachePlugin();
		me.add(ecp);
	}

	@Override
	public void configInterceptor(Interceptors me)
	{
		me.addGlobalServiceInterceptor(new DuplicateReqInterceptor());
		MyConfig.configInterceptor(me);
	}

	@Override
	public void configHandler(Handlers me)
	{
		MyConfig.configHandler(me);
	}
	
	public static void main(String[] args) {
		JFinal.start("webapp", 80, "/", 5);
	}

	@Override
	public void afterJFinalStart()
	{
		MyConfig.configEventMapping();
	}
}
