const BaseRequest = require('../common/BaseRequest');
const RequestType = require('../common/RequestType');

/**
 * 创建一个请求类，继承BaseRequest，重写三个函数
 */
module.exports = class StoryGetRequest extends BaseRequest {
    getMethod() {
        return 'story.get';
    }

    getVersion() {
        return '1.0';
    }

    getRequestType() {
        return RequestType.GET;
    }
};
