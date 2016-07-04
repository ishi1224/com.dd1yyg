package com.ddyyyg.shop;

public class Constants {

	public static final String HOST = "http://app.dd1yyg.com";
	//统一下单
	//public static final String GEN_URL = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
	public static final String GEN_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	public static final String NOTIFY_URL = "http://app.dd1yyg.com/payok.php";

	public static final String ERR_CODE = "FAIL";
	public static final String RGT_CODE = "SUCCESS";

    public static class ShowMsgActivity {
		public static final String STitle = "showmsg_title";
		public static final String SMessage = "showmsg_message";
		public static final String BAThumbData = "showmsg_thumb_data";
	}

	public static class WxAuth {
		public static final String SCOPE_WX = "snsapi_userinfo";
		public static final String STATE_WX = "ding_ding_yun_gou";
		public static final String GRANT_TYPE_WX = "authorization_code";
	}
}
