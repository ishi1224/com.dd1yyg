package com.dd1yyg.shop;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.dd1yyg.shop.model.PayModel;
import com.dd1yyg.shop.utils.AppUtil;
import com.dd1yyg.shop.utils.HttpUtil;
import com.dd1yyg.shop.utils.LogUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.thoughtworks.xstream.XStream;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by QuestZhang on 16/6/5.
 */
public class PayJavaScript {

    private Context mContext;
    private Handler mHandler;
    private MainActivity main;

    private static final String JSON = "{\"appid\":\"wxb4ba3c02aa476ea1\",\"partnerid\":\"1305176001\",\"package\":\"Sign=WXPay\",\"noncestr\":\"0ebd343acfb2dca5c65f42cd9b138e44\",\"timestamp\":1465873643,\"prepayid\":\"wx2016061411072322c5c14c4b0505475724\",\"sign\":\"5B1C6D891DFC6C1C90EA6B2DCC5274F7\"}";

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
            dealWxOrder(genStringEntity(JSON));
            //testWxOrder();
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                main.setLock(false);
            }
        },10*1000);

    }

    //拼接统一下单的ValueNamePair
    private String genStringEntity(String params){
        //Gson gson = null;
        try {
            JSONObject json = new JSONObject(params);
            //gson = new Gson();
            PayModel model = new PayModel();
            model.setAppid(mContext.getResources().getString(R.string.APP_ID_WX));//**是 应用ID
            model.setMch_id("1305176001");//**是 商户号
            model.setDevice_info(AppUtil.getDeviceInfo()[0]);//否 设备号
            model.setNonce_str("0ebd343acfb2dca5c65f42cd9b138e44");//**是 随机字符串
            model.setSign(AppUtil.getSign(mContext));//**是 签名
            model.setBody("测试商品");//**是 商品描述
            model.setDetail(json.optString("detail"));//否 商品详情
            model.setAttach(json.optString("attach"));//否 附加数据
            model.setOut_trade_no("20150806125346");//**是 商户订单号
            model.setFee_type(json.optString("fee_type"));//否 货币类型
            model.setTotal_fee("0.01");//**是 总金额
            model.setSpbill_create_ip(AppUtil.getHostAddress());//**是 终端IP
            model.setTime_start(json.optString("time_start"));//否 交易起始时间
            model.setTime_expire(json.optString("time_expire"));//否 交易结束时间
            model.setGoods_tag(json.optString("goods_tag"));//否 商品标记
            model.setNotify_url(Constants.HOST);//**是 通知地址
            model.setTrade_type("APP");//**是 交易类型
            model.setLimit_pay(json.optString("limit_pay"));//否 指定支付方式
            //LogUtil.d("json", gson.toJson(model));
            XStream xStream = new XStream();
            xStream.autodetectAnnotations(true);
            String xml = xStream.toXML(model);
            LogUtil.d("xml", xml);
            return xml;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void dealWxOrder(String entity){
        Toast.makeText(mContext, "获取订单中...", Toast.LENGTH_SHORT).show();
        try{
            byte[] buf = HttpUtil.httpPost(Constants.GEN_URL,entity);
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
                    main.getWxapi().sendReq(req);
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

    public void testWxOrder(){
        //统一下单
        String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
        Toast.makeText(mContext, "获取订单中...", Toast.LENGTH_SHORT).show();
        try{
            byte[] buf = HttpUtil.httpGet(url);
            if (buf != null && buf.length > 0) {
                String content = new String(buf);
                Log.e("get server pay params:",content);
                JSONObject json = new JSONObject(content);
                if(null != json && !json.has("retcode") ){
                    PayReq req = new PayReq();
                    req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                    //req.appId			= json.getString("appid");//应用ID
                    req.partnerId		= json.getString("partnerid");//商户号
                    req.prepayId		= json.getString("prepayid");//预支付交易会话ID
                    req.nonceStr		= json.getString("noncestr");//随机字符串
                    req.timeStamp		= json.getString("timestamp");//时间戳(北京时间)
                    req.packageValue	= json.getString("package");//扩展字段 Sign=WXPay
                    req.sign			= json.getString("sign");//签名
                    req.extData			= "app data"; // optional
                    Toast.makeText(mContext, "正常调起支付", Toast.LENGTH_SHORT).show();
                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                    main.getWxapi().sendReq(req);
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
}
