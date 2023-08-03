package com.ctsi.ssdc.project.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cscp_user")
public class UserInfo {

    private String id;

    private String userName;

    private String password;
}
