package com.gitee.sop.storyweb.controller;

import com.gitee.sop.servercommon.annotation.Open;
import com.gitee.sop.storyweb.controller.param.StoryParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 模拟超时设置
 *
 * @author tanghc
 */
@RestController
public class Example1009_TimeoutController {

    @Open("goods.timeout")
    @RequestMapping("timeoutTest")
    public Object timeout(StoryParam param) {
        // 模拟耗时操作，耗时10秒
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return param;
    }
}
