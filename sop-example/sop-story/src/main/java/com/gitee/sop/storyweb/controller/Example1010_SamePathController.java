package com.gitee.sop.storyweb.controller;

import com.gitee.sop.servercommon.annotation.Open;
import com.gitee.sop.storyweb.controller.param.StoryParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟超时设置
 *
 * @author tanghc
 */
@RestController
public class Example1010_SamePathController {

    @Open("story.path.same")
    @RequestMapping("iam_same_path")
    public Object iam_same_path(StoryParam param) {
        param.setName(param.getName() + " story..");
        return param;
    }
}
