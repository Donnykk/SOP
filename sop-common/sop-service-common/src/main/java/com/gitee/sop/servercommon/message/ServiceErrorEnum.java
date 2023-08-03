package com.gitee.sop.servercommon.message;

/**
 * @author tanghc
 */
public enum ServiceErrorEnum {
    /** 未知错误 */
    ISP_UNKNOWN_ERROR("isp.service-unknown-error"),
    /** 参数错误 */
    ISV_PARAM_ERROR("isv.invalid-parameter"),
    /** 通用错误 */
    ISV_COMMON_ERROR("isv.common-error"),
    ;
    private ServiceErrorMeta errorMeta;

    ServiceErrorEnum(String subCode) {
        this.errorMeta = new ServiceErrorMeta("isp.error_", subCode);
    }

    public ServiceErrorMeta getErrorMeta() {
        return errorMeta;
    }
}
