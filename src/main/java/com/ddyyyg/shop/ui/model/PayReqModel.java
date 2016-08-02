package com.ddyyyg.shop.ui.model;

/**
 * Created by QuestZhang on 16/6/27.
 */
public class PayReqModel extends BaseModel{

    private String appid;
    private String partnerid;	//商户号
    private String prepayid;//预支付交易会话ID
    private String timestamp;	//时间戳(北京时间)
    private String packageValue; //扩展字段
    private String noncestr;	//随机字符串

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getAppid() {
        return appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public String getNoncestr() {
        return noncestr;
    }

    @Override
    public String toString() {
        return "PayReqModel{" +
                "appid='" + appid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", packageValue='" + packageValue + '\'' +
                ", noncestr='" + noncestr + '\'' +
                '}';
    }
}
