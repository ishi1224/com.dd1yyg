package com.ddyyyg.shop.ui.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by QuestZhang on 16/6/16.
 */
@XStreamAlias("xml")
public class PayReturnModel extends BaseModel implements Serializable {

    private String return_code; //**是 返回状态码 SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
    private String return_msg; //否  返回信息 签名失败,返回信息，如非空，为错误原因,签名失败,参数格式校验错误
    private String appid; 	//**是 应用APPID 调用接口提交的应用ID
    private String mch_id; 	//**是 商户号 调用接口提交的商户号
    private String device_info; 	//否 设备号 调用接口提交的终端设备号，
    private String nonce_str; 	//**是 随机字符串 微信返回的随机字符串
    private String sign; 	//**是 签名 微信返回的签名，详见签名算法
    private String result_code; 	//**是 业务结果 SUCCESS/FAIL
    private String trade_type; 	//**是 交易类型 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，详细说明见参数规定
    private String prepay_id; 	//**是 预支付交易会话标识 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
    private String err_code; 	//否 错误代码 详细参见第6节错误列表
    private String err_code_des; 	//否 错误代码描述	错误返回的信息描述

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

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
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

    public String getResult_code() {
        return result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public String getReturn_code() {
        return return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    @Override
    public String toString() {
        return "PayReturnModel{" +
                "return_code='" + return_code + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", device_info='" + device_info + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", result_code='" + result_code + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", prepay_id='" + prepay_id + '\'' +
                ", err_code='" + err_code + '\'' +
                ", err_code_des='" + err_code_des + '\'' +
                '}';
    }
}
