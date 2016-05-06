# Itc Weixin 极速开发
Itc Weixin 是基于 JFinal Weixin 和 JFinal 的二次开发的微信公众号开发SDK，主要增加了根据Event事件为click的EventKey二级路由功能和用户会话管理功能，都是基于JFinal中业务级的AOP实现。

## 1、MyConfig配置
基本配置跟WeixinConfig的配置一样，另外多了一个EventMapping的配置，类似于Route配置，针对微信企业号应用中的不同菜单的EventKey进行了二次路由，即根据用户单击不同的菜单，拥有不同的Text文本等其他消息处理的场景。
``` java
public static void configEventMapping()
{
	/* 用于配置eventKey与msgService的映射关系 */
	EventMapping.me.add("demo", DemoMsgService.class);
}
```
`其他配置详情参考`：[JFinal weixin中的WeixinConfig配置](http://git.oschina.net/jfinal/jfinal-weixin/wikis/JFinal-weixin%E4%B8%AD%E7%9A%84WeixinConfig%E9%85%8D%E7%BD%AE)

## 2、MsgService
MsgService抽取了MsgController中处理各类消息的方法，并且所有方法都返回OutMsg对象。在打开微信应用后，没有点击任何菜单的情况下，用户所有的消息事件，将由DefaultMsgService进行处理（在a_little_config.txt中可以配置）。对于点击不同菜单后的消息处理服务，都必须继承MsgService。然后在MyConfig中，配置相应的映射关系。
``` java
public abstract class MsgService
{
	public abstract OutMsg processInTextMsg(InTextMsg inTextMsg);
	
	public OutMsg processInMenuEvent(InMenuEvent inMenuEvent)
	{
		return null;
	}
}
```

## 3、ItcMsgController
ItcMsgController继承了MsgControllerAdapter，主要是利用其中接收消息的功能，通过调用BaseMsgController中的方法来处理并返回消息。
``` java
public class ItcMsgController extends MsgControllerAdapter 
{

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
}
```

## 4、BaseMsgController
BaseMsgController是处理消息的统一入口，在ItcMsgController中被调用，主要是提供一个拦截AOP的业务逻辑层。它是采用了缺省适配器模式，由抽象类InMsgController和接口IProcessInMsg组成。InMenuEventInterceptor拦截器是实现EventKey路由的关键，它根据InMsg中的EventKey修改MsgService的子类，从而实现了路由的功能。InTextMsgInterceptor拦截器是实现了用户会话管理的功能，利用EhCache保存用户会话。
``` java
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
```

## 5、InMsgController
抽象类InMsgController用于保存基本信息，如用户消息InMsg和消息处理服务MsgService。InMsg主要是保存由JFinal Weixin解析后的信息，方便后续调用；MsgService主要是实际处理方法的对象，在接口方法的AOP拦截中，根据Mapping进行反射生成，默认值为DefaultMsgService。
``` java
public abstract class InMsgController implements IProcessInMsg
{
	static Log logger = Log.getLog(InMsgController.class);
	
	private InMsg inMsg = null;
	private MsgService service = null;
	private String userId = null;
	private String event = null;
	private static Class<?> clazz = null;

	public InMsgController()
	{
		try
		{
			if(null == clazz)
			{
				String serviceClass = PropKit.get("DefaultMsgService");
				if(StrKit.isBlank(serviceClass))
					throw new RuntimeException("请确认在配置文件中已配置DefaultMsgService！");
				clazz = Class.forName(serviceClass);
			}
			service = (MsgService) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			logger.error("请确认DefaultMsgService的配置是否正确！！", e);
		}
	}

	@Clear(DuplicateReqInterceptor.class)
	public void setInMsg(InMsg inMsg)
	{
		this.inMsg = inMsg;
		userId = inMsg.getFromUserName();
		if("event".equalsIgnoreCase(inMsg.getMsgType()))
			event = ((InMenuEvent) inMsg).getEvent();
	}
}
```

## 6、IProcessInMsg
接口IProcessInMsg定义了需要实现的方法，主要用于业务层的AOP被拦截；需要后端需要响应的消息，都必须先在这里进行声明函数，然后在BaseMsgController中实现。
``` java
public interface IProcessInMsg
{
	public abstract OutMsg processInTextMsg(InTextMsg inTextMsg);
	
	public abstract OutMsg processInMenuEvent(InMenuEvent inMenuEvent);
}
```

## 7、非Maven用户得到所有依赖 jar 包两种方法
- 将项目导入eclipse jee中，使用 export 功能导出 war包，其中的 WEB-INF/lib 下面会自动生成 jar 包
- 让使用 maven 的朋友使用 mvn package 打出 war包，其中的 WEB-INF/lib 下面会自动生成 jar 包
- 以上两种方法注意要先将pom.xml中的导出类型设置为 war，添加 <packaging>war</packaging> 内容进去即可
- 依赖jackson或fastjson

## 8、jar包依赖详细说明
`详见请见`：[JFinal weixin 1.6 Jar依赖](http://git.oschina.net/jfinal/jfinal-weixin/wikis/JFinal-weixin-1.6-Jar%E4%BE%9D%E8%B5%96)

## 9、更多支持
- JFinal 官方网站  [http://www.jfinal.com](http://www.jfinal.com/) 
- 关注官方微信号马上体验 demo 功能  
![JFinal Weixin SDK](http://static.oschina.net/uploads/space/2015/0211/181947_2431_201137.jpg) 

