package com.ddyyyg.shop.utils;

import android.content.Context;

import com.ddyyyg.shop.Constants;
import com.ddyyyg.shop.R;
import com.ddyyyg.shop.model.PayModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by QuestZhang on 16/6/16.
 */
public final class OrderUtil {

    //**是 应用ID
    public static final String getAppid(Context context){
        return context.getResources().getString(R.string.APP_ID_WX);
    }

    //**是 商户号
    public static final String getMacid(Context context){
        return context.getResources().getString(R.string.MCH_ID_WX);
    }

    //**是 随机字符串
    public static final String getNonceStr(){
        return MD5.d(Math.random()).toUpperCase();
    }

    //**是 商户订单号
    public static final String getOutTradeNo(){
        return TimeUtil.getOutTradeNo();
    }

    //**是 终端IP
    public static final String getSpbillCreateIp(){
        return AppUtil.getHostAddress();
    }

    //**是 通知地址
    public static final String getNotifyUrl(){
        return Constants.HOST;
    }

    //**是 交易类型
    public static final String getTradeType(Context context){
        return context.getResources().getString(R.string.TRADE_TYPE_WX);
    }

    //**是 签名
    public static final String getSign(Context context,PayModel model){
        Map<String,String> m = M(model);
        Set<String> keySet = m.keySet();
        List<String> keys = new ArrayList<String>(keySet);
        Collections.sort(keys);//字典顺序
        return MD5.d(Splice(context,keys,m)).toUpperCase();
    }

    //分装参数集合
    private static final Map<String,String> M(PayModel model){
        Map<String,String> m = null;
        try {
            m = new HashMap<String, String>();
            Field[] fields = model.getClass().getDeclaredFields();
            System.out.println(fields.length);
            for (Field field: fields) {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                String fieldName = field.getName();
                String value = String.valueOf(field.get(model));
                field.setAccessible(flag);
                if ("null".equals(value) || value == null || value.trim().length() == 0){
                    continue;
                }
                m.put(fieldName,value);
            }
            return m;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return m;
    }

    private static String Splice(Context context,List<String> keys,Map<String,String> m){
        StringBuilder params = new StringBuilder();
        for (String key:keys) {
            params = params.append(key).append("=").append(m.get(key)).append("&");
        }
        params.append("key=").append(context.getResources().getString(R.string.API_SECRET_KEY));
        return params.toString();
    }
    //**是 时间戳(北京时间)
    public static String getTimeStamp(){
        return TimeUtil.Date2TimeStamp();
    }

//    <appid>wx6a6dabef860d5157</appid>
//    <attach></attach>
//    <body>测试商品</body>
//    <detail></detail>
//    <device__info></device__info>
//    <fee__type></fee__type>
//    <goods__tag></goods__tag>
//    <limit__pay></limit__pay>
//    <mch__id>1305176001</mch__id>
//    <nonce__str>0e11701a7d65ae2a06781d5678efdbba</nonce__str>
//    <notify__url>http://dingding.bdchenmei.cn</notify__url>
//    <out__trade__no>20150806125346</out__trade__no>
//    <sign>65AE2CC96C8CE75D21DF66C89CA666BE</sign>
//    <spbill__create__ip>192.168.10.101</spbill__create__ip>
//    <time__expire></time__expire>
//    <time__start></time__start>
//    <total__fee>0.01</total__fee>
//    <trade__type>APP</trade__type>

}
