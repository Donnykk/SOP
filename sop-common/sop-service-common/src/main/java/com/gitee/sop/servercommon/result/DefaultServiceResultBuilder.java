package com.gitee.sop.servercommon.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.gitee.sop.servercommon.exception.ServiceException;
import com.gitee.sop.servercommon.message.ServiceError;
import com.gitee.sop.servercommon.message.ServiceErrorEnum;
import com.gitee.sop.servercommon.message.ServiceErrorMeta;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理业务返回结果
 *
 * @author tanghc
 */
@Slf4j
public class DefaultServiceResultBuilder implements ServiceResultBuilder {

    @Override
    public Object buildError(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
        ServiceError error;
        if (throwable instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) throwable;
            error = serviceException.getError();
        } else {
            ServiceErrorMeta errorMeta = ServiceErrorEnum.ISP_UNKNOWN_ERROR.getErrorMeta();
            error = errorMeta.getError();
        }
        return this.buildError(error.getSub_code(), error.getSub_msg());
    }

    @Override
    public Object buildError(String subCode, String subMsg) {
        AlipayResult result = new AlipayResult();
        result.setSub_code(subCode);
        result.setSub_msg(subMsg);
        return result;
    }

    @Data
    public static class AlipayResult {
        @JSONField(ordinal = 1)
        private String sub_code;
        @JSONField(ordinal = 2)
        private String sub_msg;
    }
}
