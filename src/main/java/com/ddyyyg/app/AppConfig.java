package com.ddyyyg.app;

/**
 * Created by QuestZhang on 16/6/7.
 */

public class AppConfig {

	public static final int NETWORK_TYPE_WIFI = 1;
	public static final int NETWORK_TYPE_2G = 2;
	public static final int NETWORK_TYPE_3G = 3;
	public static String DIR_ROOT = "SoShare/5iwen/Log";
	/** 日志文件名后缀 */
	public static String FILE_NAME_EXTENSION_LOG = ".log";

	/** UI设计的基准宽度. */
	public static int uiWidth = 720;

	/** UI设计的基准高度. */
	public static int uiHeight = 1080;

	/** The Constant CONNECTEXCEPTION. */
	public static final String CONNECTEXCEPTION = "无法连接到网络";

	/** The Constant UNKNOWNHOSTEXCEPTION. */
	public static final String UNKNOWNHOSTEXCEPTION = "连接远程地址失败";

	/** The Constant SOCKETEXCEPTION. */
	public static final String SOCKETEXCEPTION = "网络连接出错，请重试";

	/** The Constant SOCKETTIMEOUTEXCEPTION. */
	public static final String SOCKETTIMEOUTEXCEPTION = "连接超时，请重试";

	/** The Constant NULLPOINTEREXCEPTION. */
	public static final String NULLPOINTEREXCEPTION = "抱歉，远程服务出错了";

	/** The Constant NULLMESSAGEEXCEPTION. */
	public static final String NULLMESSAGEEXCEPTION = "抱歉，程序出错了";

	/** The Constant CLIENTPROTOCOLEXCEPTION. */
	public static final String CLIENTPROTOCOLEXCEPTION = "Http请求参数错误";

	/** 参数个数不够. */
	public static final String MISSINGPARAMETERS = "参数没有包含足够的值";

	/** The Constant REMOTESERVICEEXCEPTION. */
	public static final String REMOTESERVICEEXCEPTION = "抱歉，远程服务出错了";
}
