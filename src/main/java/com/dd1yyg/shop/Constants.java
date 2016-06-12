package com.dd1yyg.shop;

public class Constants {

	public static final String HOST = "http://dingding.bdchenmei.cn";
	// APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wxd930ea5d5a258f4f";
	//统一下单
	//public static final String GEN_URL = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
	public static final String GEN_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static class ShowMsgActivity {
		public static final String STitle = "showmsg_title";
		public static final String SMessage = "showmsg_message";
		public static final String BAThumbData = "showmsg_thumb_data";
	}
}
