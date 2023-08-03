package com.gitee.sop.storyweb.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 接口返回结果
 */
public class MemberInfoGetResult {

    private Integer id;
    private String name;

    @JsonProperty("member_info")
    private MemberInfoGetResultMemberInfo memberInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MemberInfoGetResultMemberInfo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfoGetResultMemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }
}
