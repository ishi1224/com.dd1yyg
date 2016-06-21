package com.ddyyyg.shop;

public class Constants {

	public static final String HOST = "http://app.dd1yyg.com/";
	//统一下单
	//public static final String GEN_URL = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
	public static final String GEN_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	public static final String ERR_CODE = "FAIL";
	public static final String RGT_CODE = "SUCCESS";

    public static class ShowMsgActivity {
		public static final String STitle = "showmsg_title";
		public static final String SMessage = "showmsg_message";
		public static final String BAThumbData = "showmsg_thumb_data";
	}
}
