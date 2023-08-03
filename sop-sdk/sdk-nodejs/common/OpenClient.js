const axios = require('axios');
const moment = require('moment');
const qs = require('qs');

const RequestType = require('./RequestType');
const SignUtil = require('./SignUtil');
const BaseRequest = require('./BaseRequest');

const IS_RUN_IN_BROWSER = typeof window !== 'undefined' && this === window;

const HEADERS = {'Accept-Encoding': 'identity'};

const getHeaders = (headers = {}) => {
    return Object.assign({}, headers, HEADERS);
};

const parseResponse = (error, response, request) => {
    if (!error && response.status === 200) {
        return request.parseResponse(response.data);
    } else {
        return { // 重新封装请求异常回调，以防中断
            msg: '请求异常',
            code: '502',
            sub_msg: `${error}`,
            sub_code: 'isv.invalid-server'
        };
    }
};

const buildParams = (instance, request, token) => {
    const {appId, privateKey} = instance;
    const allParams = {
        'app_id': appId,
        'method': request.getMethod(),
        'charset': 'UTF-8',
        'sign_type': 'RSA2',
        'timestamp': moment().format('YYYY-MM-DD HH:mm:ss'),
        'version': request.getVersion(),
        'biz_content': JSON.stringify(request.bizModel)
    };

    if (token) {
        allParams['app_auth_token'] = token;
    }
    // 创建签名
    allParams.sign = SignUtil.createSign(allParams, privateKey, 'RSA2');
    return allParams;
};

const executeRequest = async (instance = {}, request, token, callback, customOptions = {}) => {
    const params = buildParams(instance, request, token);
    const {url} = instance;
    let {headers} = customOptions;
    const config = {
        url,
        method: 'POST',
        params: undefined,
        data: undefined
    };
    headers = getHeaders(headers);
    const requestType = request.getRealRequestType();
    switch (requestType) {
        case RequestType.GET: {
            config.method = 'GET';
            config.params = params;
        }
            break;
        case RequestType.POST_FORM: {
            headers = Object.assign(headers, {
                'Content-Type': 'application/x-www-form-urlencoded'
            });
            config.data = qs.stringify(params);
        }
            break;
        case RequestType.POST_JSON: {
            config.data = params;
        }
            break;
        case RequestType.POST_FILE: {
            let formData;
            if (IS_RUN_IN_BROWSER) {
                formData = new window.FormData();
                (request.files || []).forEach(({name, path}) => {
                    formData.append(name, path, {
                        contentType: 'application/octet-stream'
                    });
                });
            } else {
                const fs = require('fs');
                const fd = require('form-data');
                formData = new fd();
                (request.files || []).forEach(({name, path}) => {
                    formData.append(name, fs.createReadStream(path), {
                        contentType: 'application/octet-stream'
                    });
                });
                headers = Object.assign(headers, formData.getHeaders());
            }
            Object.keys(params).forEach(key => {
                const value = params[key];
                if (!(typeof key === 'undefined' || typeof value === 'undefined')) {
                    formData.append(key, params[key]);
                }
            });
            config.data = formData;
        }
            break;
        default: {
            callback(parseResponse(new Error('request.getRequestType()类型不正确'), undefined, request));
            return;
        }
    }
    try {
        config['headers'] = headers;
        const response = await axios.request(config);
        callback(parseResponse(undefined, response, request));
    } catch (error) {
        callback(parseResponse(error, undefined, request));
    }
};

module.exports = class OpenClient {
    /**
     * 初始化客户端
     * @param appId 应用ID
     * @param privateKey 应用私钥，2048位，PKCS8
     * @param url 请求url
     */
    constructor(appId, privateKey, url) {
        this.appId = appId || '';
        this.privateKey = privateKey || '';
        this.url = url || '';
    }

    setAppId(appId) {
        this.appId = appId || '';
        return this;
    }

    setPrivateKey(privateKey) {
        this.privateKey = privateKey || '';
        return this;
    }

    setUrl(url) {
        this.url = url || '';
        return this;
    }

    /**
     * 发送请求
     * @param request 请求类
     * @param callback 回调函数，参数json（undefined则使用executeSync）
     * @param options 自定义参数，如headers
     */
    execute(request, callback, options) {
        if (typeof callback == 'function') {
            return this.executeToken(request, null, callback, options);
        } else {
            return this.executeSync(request, options);
        }
    }

    /**
     * 发送同步请求
     * @param request 请求类
     * @param options 自定义参数，如headers
     * */
    executeSync(request, options) {
        return new Promise(async (resolve) => {
            await this.execute(request, res => {
                resolve(res);
            }, options);
        });
    }

    /**
     * 发送请求
     * @param request 请求类
     * @param token token
     * @param callback 回调函数，参数json（undefined则使用executeTokenSync）
     * @param options 自定义参数，如headers
     */
    async executeToken(request, token, callback, options) {
        if (!(request instanceof BaseRequest)) {
            throw 'request类未继承BaseRequest';
        }
        if (typeof callback == 'function') {
            const files = request.files;
            if (files && files.length > 0) {
                request.setForceRequestType(RequestType.POST_FILE);
            }
            return await executeRequest(this, request, token, callback, options);
        } else {
            return this.executeTokenSync(request, token);
        }
    }

    /**
     * 发送同步请求
     * @param request 请求类
     * @param token token
     * @param options 自定义参数，如headers
     */
    executeTokenSync(request, token, options) {
        return new Promise(async (resolve) => {
            await this.executeToken(request, token, res => {
                resolve(res);
            }, options);
        });
    }

};
