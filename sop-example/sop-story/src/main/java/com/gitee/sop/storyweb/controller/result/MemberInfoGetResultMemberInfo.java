package com.gitee.sop.storyweb.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class MemberInfoGetResultMemberInfo {
    @JsonProperty("is_vip")
    private Byte isVip;

    @JsonProperty("vip_endtime")
    private Date vipEndtime;

    public Byte getIsVip() {
        return isVip;
    }

    public void setIsVip(Byte isVip) {
        this.isVip = isVip;
    }

    public Date getVipEndtime() {
        return vipEndtime;
    }

    public void setVipEndtime(Date vipEndtime) {
        this.vipEndtime = vipEndtime;
    }
}