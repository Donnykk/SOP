#ifndef SDK_CXX_OPENCLIENT_H
#define SDK_CXX_OPENCLIENT_H

#include <string>
#include <json/json.h>
#include "httplib.h"
#include "../request/BaseRequest.h"

using namespace std;

/**
 * 请求客户端
 */
class OpenClient {
private:
    /** 应用id */
    string appId;
    string url;
    /** 私钥文件路径 */
    string privateKeyFilePath;

public:
    /**
     * 创建客户端对象
     * @param appId 应用ID
     * @param privateKeyFilePath  应用私钥路径
     * @param url 请求URL
     */
    OpenClient(const string &appId, const string &privateKeyFilePath, const string &url);

    /**
     * 发送请求
     * @param request 请求对象，BaseRequest的子类
     * @param token token
     * @return 返回响应结果
     */
    Json::Value execute(BaseRequest *request, const string& token);

    /**
     * 发送请求
     * @param request 请求对象，BaseRequest的子类
     * @return 返回响应结果
     */
    Json::Value execute(BaseRequest *request);

private:

    map<string, string> buildParams(BaseRequest *request, const string& token);

    static httplib::MultipartFormDataItems
    getMultipartFormDataItems(map<string, string> allParams, vector<FileInfo> fileInfoList);

    static httplib::Params getParams(map<string, string> params);

    static string getQuery(map<string, string> params);

    static Json::Value parseResponse(const string& responseBody,BaseRequest *request);
};


#endif //SDK_CXX_OPENCLIENT_H
