package com.ddyyyg.shop;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.ddyyyg.shop.model.PayModel;
import com.ddyyyg.shop.model.PayReqModel;
import com.ddyyyg.shop.model.PayReturnModel;
import com.ddyyyg.shop.utils.HttpUtil;
import com.ddyyyg.shop.utils.LogUtil;
import com.ddyyyg.shop.utils.OrderUtil;
import com.ddyyyg.shop.utils.ToastUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.thoughtworks.xstream.XStream;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


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

    @JavascriptInterface
    public void payAlipay(String json) {//javascript:payjavascript.payAlipay


    }

    @JavascriptInterface
    public void payWx(String json) {//javascript:payjavascript.payWX
        Log.d("javascript", json);
        if (!main.isPaySupported()){
            return;
        }
        if (main.isLock()) {
            //1、生成订单，返回订单号 2、使用微信支付订单
            main.setLock(true);
            wxPay(genStringEntity("{\"appid\"="+json+"}"));
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
        String xml = "";
        try {
            JSONObject json = new JSONObject(params);
            PayModel model = new PayModel();
            model.setAppid(OrderUtil.getAppid(mContext));//**是 应用ID
            model.setMch_id(OrderUtil.getMacid(mContext)/*"1356293102"*/);//**是 商户号
            //model.setDevice_info("");//否 设备号
            model.setNonce_str(OrderUtil.getNonceStr(mContext));//**是 随机字符串
            model.setBody("测试商品test");//**是 商品描述***************
            //model.setDetail(json.optString("detail"));//否 商品详情
            //model.setAttach(json.optString("attach"));//否 附加数据
            model.setOut_trade_no(OrderUtil.getOutTradeNo());//**是 商户订单号
            //model.setFee_type(json.optString("fee_type"));//否 货币类型
            model.setTotal_fee("1");//**是 总金额******************
            model.setSpbill_create_ip(OrderUtil.getSpbillCreateIp());//**是 终端IP
            //model.setTime_start(json.optString("time_start"));//否 交易起始时间
            //model.setTime_expire(json.optString("time_expire"));//否 交易结束时间
            //model.setGoods_tag(json.optString("goods_tag"));//否 商品标记
            model.setNotify_url(OrderUtil.getNotifyUrl());//**是 通知地址
            model.setTrade_type(OrderUtil.getTradeType(mContext));//**是 交易类型
            //model.setLimit_pay(json.optString("limit_pay"));//否 指定支付方式
            model.setSign(OrderUtil.getSign(mContext, model));//**是 签名
            XStream xStream = new XStream();
            xStream.autodetectAnnotations(true);
            xml = xStream.toXML(model).replace("&nbsp&","_");
            LogUtil.d("xml",xml);
            return xml;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return xml;
    }

    private void wxPay(String entity){
        ToastUtil.makeText(mContext, "获取订单中...");
        try{
            byte[] buf = HttpUtil.httpPost(Constants.GEN_URL, entity);
            if (buf != null && buf.length > 0) {
                String content = new String(buf);
                LogUtil.e("get server pay params:", content);
                XStream xStream = new XStream();
                xStream.autodetectAnnotations(true);
                xStream.alias("xml",PayReturnModel.class);
                PayReturnModel model = (PayReturnModel)xStream.fromXML(content);
                if(null != model){
                    if (Constants.ERR_CODE.equals(model.getReturn_code())){
                        ToastUtil.makeText(mContext,model.getReturn_msg());
                        return;
                    }
                    if (Constants.ERR_CODE.equals(model.getResult_code())){
                        ToastUtil.makeText(mContext,model.getErr_code_des());
                        return;
                    }
                    PayReq req = new PayReq();
                    req.appId			= model.getAppid();//应用ID
                    req.partnerId		= OrderUtil.getMacid(mContext);//商户号
                    req.prepayId		= model.getPrepay_id();//预支付交易会话ID
                    req.nonceStr		= OrderUtil.getNonceStr(mContext);//随机字符串
                    req.timeStamp		= OrderUtil.getTimeStamp();//时间戳(北京时间)
                    req.packageValue	= OrderUtil.getPackageValue(mContext);//扩展字段 暂填写固定值Sign=WXPay

                    PayReqModel payReq = new PayReqModel();
                    payReq.setAppid(req.appId);
                    payReq.setPartnerid(req.partnerId);
                    payReq.setPrepayid(req.prepayId);
                    payReq.setNoncestr(req.nonceStr);
                    payReq.setTimestamp(req.timeStamp);
                    payReq.setPackageValue(req.packageValue);
                    req.sign			= OrderUtil.getSign(mContext, payReq);//签名

                    req.extData			= "app data"; // optional
                    ToastUtil.makeText(mContext, "正在调起支付...");
                    LogUtil.d("req",payReq.toString());
                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                    main.getWxapi().sendReq(req);
                }else{
                    Log.d("PAY_POST", "返回错误" + model.getReturn_msg());
                   // ToastUtil.makeText(mContext, "返回错误" + model.getReturn_msg());
                }
            }else{
                Log.d("PAY_POST", "服务器请求错误");
                //ToastUtil.makeText(mContext, "服务器请求错误");
            }
        }catch(Exception e){
            Log.e("PAY_POST", "异常：" + e.getMessage());
            //ToastUtil.makeText(mContext, "异常：" + e.getMessage());
        }
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

    public String getErrorDes(String error_code){
        return mapError().get(error_code);
    }

    public Map<String,String> mapError(){
        final Map<String,String> m = new HashMap<String, String>();
        m.put("NOAUTH","商户无此接口权限");
        m.put("NOTENOUGH","余额不足");
        m.put("ORDERPAID","商户订单已支付");
        m.put("ORDERCLOSED","订单已关闭");
        m.put("SYSTEMERROR","系统错误");
        m.put("APPID_NOT_EXIST","APPID不存在");
        m.put("MCHID_NOT_EXIST","MCHID不存在");
        m.put("APPID_MCHID_NOT_MATCH","appid和mch_id不匹配");
        m.put("LACK_PARAMS","缺少参数");
        m.put("OUT_TRADE_NO_USED","商户订单号重复");
        m.put("SIGNERROR","签名错误");
        m.put("XML_FORMAT_ERROR","XML格式错误");
        m.put("REQUIRE_POST_METHOD","请使用post方法");
        m.put("POST_DATA_EMPTY","post数据为空");
        m.put("NOT_UTF8","编码格式错误");
        return m;
    }
///   NOAUTH 	商户无此接口权限 	商户未开通此接口权限 	请商户前往申请此接口权限
//    NOTENOUGH 	余额不足 	用户帐号余额不足 	用户帐号余额不足，请用户充值或更换支付卡后再支付
//    ORDERPAID 	商户订单已支付 	商户订单已支付，无需重复操作 	商户订单已支付，无需更多操作
//    ORDERCLOSED 	订单已关闭 	当前订单已关闭，无法支付 	当前订单已关闭，请重新下单
//    SYSTEMERROR 	系统错误 	系统超时 	系统异常，请用相同参数重新调用
//    APPID_NOT_EXIST 	APPID不存在 	参数中缺少APPID 	请检查APPID是否正确
//    MCHID_NOT_EXIST 	MCHID不存在 	参数中缺少MCHID 	请检查MCHID是否正确
//    APPID_MCHID_NOT_MATCH 	appid和mch_id不匹配 	appid和mch_id不匹配 	请确认appid和mch_id是否匹配
//    LACK_PARAMS 	缺少参数 	缺少必要的请求参数 	请检查参数是否齐全
//    OUT_TRADE_NO_USED 	商户订单号重复 	同一笔交易不能多次提交 	请核实商户订单号是否重复提交
//    SIGNERROR 	签名错误 	参数签名结果不正确 	请检查签名参数和方法是否都符合签名算法要求
//    XML_FORMAT_ERROR 	XML格式错误 	XML格式错误 	请检查XML参数格式是否正确
//    REQUIRE_POST_METHOD 	请使用post方法 	未使用post传递参数  	请检查请求参数是否通过post方法提交
//    POST_DATA_EMPTY 	post数据为空 	post数据不能为空 	请检查post数据是否为空
//    NOT_UTF8 	编码格式错误 	未使用指定编码格式 	请使用NOT_UTF8编码格式
}
