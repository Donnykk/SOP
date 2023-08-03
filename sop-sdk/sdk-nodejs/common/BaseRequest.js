const isArray = require('isarray');
/**
 * 请求类父类
 */
module.exports = class BaseRequest {
    constructor() {
        this.bizModel = {};

        this.files = undefined;

        // 用于文件上传时强制转换成POST_FILE请求
        this.__forceRequestType__ = undefined;

        this.checkOverride();
    }

    /**
     * 校验子类是否已重写相关方法
     * */
    checkOverride() {
        try {
            this.getMethod();
            this.getVersion();
            this.getRequestType();
        } catch (error) {
            throw error;
        }
    }

    setBizModel(biz = {}) {
        this.bizModel = biz;
        return this;
    }

    setFiles(files) {
        this.files = files;
        return this;
    }

    addFile(name, path) {
        if (name && path) {
            if (!isArray(this.files)) {
                this.files = [];
            }
            this.files.push({name, path});
        }
        return this;
    }

    /**
     * 返回接口名称
     */
    getMethod() {
        throw `未实现BaseRequest类getMethod()方法`;
    }

    /**
     * 返回版本号
     */
    getVersion() {
        throw '未实现BaseRequest类getVersion()方法';
    }

    /**
     * 返回请求类型，使用RequestType.js
     */
    getRequestType() {
        throw '未实现BaseRequest类getRequestType()方法';
    }

    setForceRequestType(type) {
        this.__forceRequestType__ = type;
        return this;
    }

    getRealRequestType() {
        return this.__forceRequestType__ || this.getRequestType();
    }

    /**
     * 解析返回结果，子类可以覆盖实现
     * @param responseData 服务器返回内容
     * @returns 返回结果
     */
    parseResponse(responseData) {
        let data = responseData['error_response'];
        if (!data) {
            const dataNodeName = this.getMethod().replace(/\./g, '_') + '_response';
            data = responseData[dataNodeName];
        }
        return data;
    }
};
