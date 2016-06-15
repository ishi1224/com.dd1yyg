package com.dd1yyg.shop.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by QuestZhang on 16/6/12.
 */
@XStreamAlias("xml")
public class PayModel extends BaseModel {

    private String appid;//是 应用ID
    private String mch_id;//**是 商户号
    private String device_info;//否 设备号
    private String nonce_str;//**是 随机字符串
    private String sign;//**是 签名
    private String body;//**是 商品描述
    private String detail;//否 商品详情
    private String attach;//否 附加数据
    private String out_trade_no;//**是 商户订单号
    private String fee_type;//否 货币类型
    private String total_fee;//**是 总金额
    private String spbill_create_ip;//**是 终端IP
    private String time_start;//否 交易起始时间
    private String time_expire;//否 交易结束时间
    private String goods_tag;//否 商品标记
    private String notify_url;//**是 通知地址
    private String trade_type;//**是 交易类型
    private String limit_pay;//否 指定支付方式


    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getAppid() {
        return appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public String getBody() {
        return body;
    }

    public String getDetail() {
        return detail;
    }

    public String getAttach() {
        return attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public String getFee_type() {
        return fee_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    @Override
    public String toString() {
        return "PayModel{" +
                "appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", device_info='" + device_info + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", body='" + body + '\'' +
                ", detail='" + detail + '\'' +
                ", attach='" + attach + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", fee_type='" + fee_type + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", spbill_create_ip='" + spbill_create_ip + '\'' +
                ", time_start='" + time_start + '\'' +
                ", time_expire='" + time_expire + '\'' +
                ", goods_tag='" + goods_tag + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", limit_pay='" + limit_pay + '\'' +
                '}';
    }
}
