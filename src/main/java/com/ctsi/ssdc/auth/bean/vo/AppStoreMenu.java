package com.ctsi.ssdc.auth.bean.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.ctsi.ssdc.admin.domain.dto.CscpMenusDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 应用 菜单
 */
@Data
@Accessors(chain = true)
@TableName("app_store_menu")
@NoArgsConstructor
public class AppStoreMenu {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableLogic
    private Integer del;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String appStoreId;

    private String menuId;

    private String menuParentId;

    /** 显示顺序 */
    private Integer orderNum;

    /** 菜单名称 */
    private String menuName;

    /** 权限标识 */
    private String perms;

    /** 路由地址 */
    private String path;

    /** 组件路径 */
    private String component;

    /** 路由参数 */
    private String query;

    /** 是否为外链（0是 1否） */
    private Integer isFrame;

    /** 是否缓存（0缓存 1不缓存） */
    private Integer isCache;

    /** 菜单类型（M目录 C菜单 F按钮） */
    private String menuType;

    /** 菜单状态（0显示 1隐藏） */
    private String visible;

    /** 菜单状态（0正常 1停用） */
    private String status;

    /** 菜单图标 */
    private String icon;


    /** 子菜单 */
    @TableField(exist = false)
    private List<AppStoreMenu> children = new ArrayList<AppStoreMenu>();

    public AppStoreMenu(CscpMenusDTO menu)
    {
        menuId = String.valueOf(menu.getId());
        menuName = menu.getTitle();
        menuParentId = String.valueOf(menu.getParentId());
        orderNum = menu.getOrderby();
        path = menu.getUrl();
        component = menu.getComponent();
//        query = menu.get
        isFrame = 0;
        isCache = 0;
        switch (menu.getType()){
            case "menu1":
            case "menu2":
            case "menu3":
                menuType = "M";
                break;
            case "button":
                menuType = "F";
                break;
            case "non-menu":
                menuType = "C";
                break;
            default:
                break;
        }
        visible = "0";
        status = "0";
        perms = menu.getPermissionCode();
        icon = menu.getIcon();
    }
}
