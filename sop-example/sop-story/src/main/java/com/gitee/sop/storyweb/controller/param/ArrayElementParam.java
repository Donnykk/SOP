package com.gitee.sop.storyweb.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tanghc
 */
@Data
public class ArrayElementParam {

    @ApiModelProperty(value = "名字", required = true, example = "白雪公主", position = 1)
    private String name;

    @ApiModelProperty(value = "数组", required = true, dataType = "List", example = "白雪公主", position = 2)
    private List<StoryParam> list;

}
