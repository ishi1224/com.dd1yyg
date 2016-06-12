package com.dd1yyg.shop;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.dd1yyg.shop.utils.Util;
import com.tencent.mm.sdk.modelpay.PayReq;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by QuestZhang on 16/6/5.
 */
public class PayJavaScript {

    private Context mContext;
    private Handler mHandler;
    private MainActivity main;

    public PayJavaScript(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.mHandler = handler;
        this.main = (MainActivity) context;
    }

    /*
    '{
    "appId":"wx0952813220b9ef2b",
    "timeStamp":"1465137465",
    "nonceStr":"120flpvpu9rndbrku3hjz6hayb8gu6yb",
    "package":"prepay_id=",
    "signType":"MD5",
    "paySign":"A482B0C0FE1E8B3DC9D8779329EBD815"
    }'
    * */
    @JavascriptInterface
    public void payWx(String json) {//javascript:payjavascript.payWX
        Log.d("javascript",json);
        if (!main.isPaySupported()){
            return;
        }
        if (main.isLock()) {
            //1、生成订单，返回订单号 2、使用微信支付订单
            main.setLock(true);
            dealWxOrder(genUrl(json));
        }
    }

    private String genUrl(String params){
        StringBuilder url = new StringBuilder(Constants.GEN_URL);
        try {
            JSONObject json = new JSONObject(params);
            url = url.append("?").append("appid=").append(json.optString(Constants.APP_ID));//是 应用ID
            url = url.append("&").append("mch_id=").append(json.optString("mch_id"));//是 商户号
            url = url.append("&").append("device_info=").append(json.optString("device_info"));//否 设备号
            url = url.append("&").append("nonce_str=").append(json.optString("nonce_str"));//是 随机字符串
            url = url.append("&").append("sign=").append(json.optString("sign"));//是 签名
            url = url.append("&").append("body=").append(json.optString("body"));//是 商品描述
            url = url.append("&").append("detail=").append(json.optString("detail"));//否 商品详情
            url = url.append("&").append("attach=").append(json.optString("attach"));//否 附加数据
            url = url.append("&").append("out_trade_no=").append(json.optString("out_trade_no"));//是 商户订单号
            url = url.append("&").append("fee_type=").append(json.optString("fee_type"));//否 货币类型
            url = url.append("&").append("total_fee=").append(json.optString("total_fee"));//是 总金额
            url = url.append("&").append("spbill_create_ip=").append(json.optString("spbill_create_ip"));//是 终端IP
            url = url.append("&").append("time_start=").append(json.optString("time_start"));//否 交易起始时间
            url = url.append("&").append("time_expire=").append(json.optString("time_expire"));//否 交易结束时间
            url = url.append("&").append("goods_tag=").append(json.optString("goods_tag"));//否 商品标记
            url = url.append("&").append("notify_url=").append(json.optString("notify_url"));//是 通知地址
            url = url.append("&").append("trade_type=").append(json.optString("trade_type"));//是 交易类型
            url = url.append("&").append("limit_pay=").append(json.optString("limit_pay"));//否 指定支付方式
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url.toString();
    }

    private void dealWxOrder(String genUrl){
        Toast.makeText(mContext, "获取订单中...", Toast.LENGTH_SHORT).show();
        try{
            byte[] buf = Util.httpGet(genUrl);
            if (buf != null && buf.length > 0) {
                String content = new String(buf);
                Log.e("get server pay params:",content);
                JSONObject json = new JSONObject(content);
                if(null != json && !json.has("retcode") ){
                    PayReq req = new PayReq();
                    //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                    req.appId			= json.getString("appid");//应用ID
                    req.partnerId		= json.getString("partnerid");//商户号
                    req.prepayId		= json.getString("prepayid");//预支付交易会话ID
                    req.nonceStr		= json.getString("noncestr");//随机字符串
                    req.timeStamp		= json.getString("timestamp");//时间戳(北京时间)
                    req.packageValue	= json.getString("package");//扩展字段 Sign=WXPay
                    req.sign			= json.getString("sign");//签名
                    //req.extData			= "app data"; // optional
                    Toast.makeText(mContext, "正常调起支付", Toast.LENGTH_SHORT).show();
                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                    main.getApi().sendReq(req);
                }else{
                    Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
                    Toast.makeText(mContext, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
                }
            }else{
                Log.d("PAY_GET", "服务器请求错误");
                Toast.makeText(mContext, "服务器请求错误", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Log.e("PAY_GET", "异常："+e.getMessage());
            Toast.makeText(mContext, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @JavascriptInterface
    public void payAlipay(String json) {//javascript:payjavascript.payAlipay


    }
}
