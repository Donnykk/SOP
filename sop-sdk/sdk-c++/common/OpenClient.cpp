#include <json/json.h>
#include "OpenClient.h"
#include "httplib.h"
#include "tool.h"
#include "sign.h"

httplib::Headers headers = {
        {"Accept-Encoding", "identity"}
};

const string ERROR_NODE = "error_response";

OpenClient::OpenClient(const string &appId, const string &privateKeyFilePath, const string &url) {
    this->appId = appId;
    this->url = url;
    this->privateKeyFilePath = privateKeyFilePath;
}

Json::Value OpenClient::execute(BaseRequest *request) {
    return this->execute(request, "");
}

Json::Value OpenClient::execute(BaseRequest *request,  const string &token) {
    string method = request->getMethod();
    string version = request->getVersion();
    RequestType requestType = request->getRequestType();
    map<string, string> bizModel = request->bizModel;
    // 创建HTTP请求客户端
//    httplib::Client cli(this->hostInfo.host, this->hostInfo.port);
    httplib::Client cli(this->url.c_str());
    const char *url = this->url.c_str();
    // 构建请求参数
    map<string, string> allParams = this->buildParams(request, token);
    string responseBody;
    // 如果有文件上传
    if (!request->getFiles().empty()) {
        httplib::MultipartFormDataItems items = OpenClient::getMultipartFormDataItems(
                allParams, request->getFiles());
        responseBody = cli.Post(url, headers, items)->body;
    } else {
        switch (requestType) {
            case GET: {
                string fullPath = this->url + "?" + OpenClient::getQuery(allParams);
                responseBody = cli.Get(fullPath.c_str())->body;
                break;
            }
            case POST_FORM: {
                responseBody = cli.Post(url, headers, OpenClient::getParams(allParams))->body;
                break;
            }
            case POST_JSON: {
                string json = tool::mapToJson(allParams);
                responseBody = cli.Post(url, json, "application/json")->body;
                break;
            }
            case POST_FILE: {
                httplib::MultipartFormDataItems items = OpenClient::getMultipartFormDataItems(
                        allParams, request->getFiles());
                responseBody = cli.Post(url, headers, items)->body;
            }
        }
    }
    return OpenClient::parseResponse(responseBody, request);
}


httplib::Params OpenClient::getParams(map<string, string> allParams) {
    httplib::Params params;
    map<string, string>::iterator it;
    for (it = allParams.begin(); it != allParams.end(); ++it) {
        params.emplace(it->first, it->second);
    }
    return params;
}

string OpenClient::getQuery(map<string, string> allParams) {
    string query;
    map<string, string>::iterator it;
    int i = 0;
    for (it = allParams.begin(); it != allParams.end(); ++it) {
        if (i++ > 0) {
            query.append("&");
        }
        query.append(it->first).append("=").append(tool::url_encode(it->second));
    }
    return query;
}

map<string, string> OpenClient::buildParams(BaseRequest *request, const string &token) {
    map<string, string> allParams;
    allParams["app_id"] = this->appId;
    allParams["method"] = request->getMethod();
    allParams["charset"] = "UTF-8";
    allParams["sign_type"] = "RSA2";
    allParams["timestamp"] = tool::getTime();
    allParams["version"] = request->getVersion();
    allParams["biz_content"] = tool::mapToJson(request->bizModel);

    if (!token.empty()) {
        allParams["app_auth_token"] = token;
    }

    // 生成签名
    string sign = signutil::createSign(allParams, this->privateKeyFilePath, "RSA2");
    allParams["sign"] = sign;
    return allParams;
}

httplib::MultipartFormDataItems
OpenClient::getMultipartFormDataItems(map<string, string> allParams, vector<FileInfo> fileInfoList) {
    httplib::MultipartFormDataItems items = {};
    map<string, string>::iterator it;
    for (it = allParams.begin(); it != allParams.end(); ++it) {
        items.push_back({it->first, it->second, "", ""});
    }
    // 添加上传文件
    vector<FileInfo>::iterator vit;
    for (vit = fileInfoList.begin(); vit != fileInfoList.end(); vit++) {
        string content = tool::getFileContent(vit->filepath);
        items.push_back({vit->name, content, tool::getFilename(vit->filepath), "application/octet-stream"});
    }
    return items;
}

Json::Value OpenClient::parseResponse(const string& responseBody, BaseRequest *request) {
    Json::Value root;
    Json::Reader reader;
    reader.parse(responseBody, root, false);

    Json::Value data = root[ERROR_NODE];
    if (!data) {
        string method = request->getMethod();
        string nodeName = tool::replace(method.c_str(),".","_") + "_response";
        data = root[nodeName];
    }
    return data;
}
