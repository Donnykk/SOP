extern crate sdk;

use std::collections::HashMap;

use sdk::client::OpenClient;
use sdk::http::UploadFile;
use sdk::request::BaseRequest;
use sdk::request::memberinfoget::MemberInfoGetRequest;
use sdk::response::memberinfoget::MemberInfoGetResponse;
use std::env;

fn main() {
    // 创建请求客户端
    let client = OpenClient {
        // 应用ID
        app_id: "201904035630907729292csharp",
        // 应用私钥
        private_key: "MIIEowIBAAKCAQEA5+OvJxeSzf44NxQ/cl7Ii+BzPg2k6sRcvH4ffOtU5Dzq1/oEvg02nxIhmwOHBZmjbmuUu0aLsfglUTAwqfXftfAKZidshsgj9NNh0/kxk0avRZ1UoljWGz/FxVZA0ogbxxhohPZ9jWcD+eBQcIwF2DtHfAJqWWZrYFnCMeHD8mPzxo2kwXSvDzi0vf9I2tKiYvNG26a9FqeYtPOoi81sdS3+70HOMdxP8ejXtyfnKpKz7Dx506LCIRS5moWS3Q5eTLV3NGX/1CSJ8wpQA2DAQTjVhX5eVu7Yqz12t8W+sjWM/tHUR6cgwYYR10p7tSCeCPzkigjGxKm4cYXWtATQJQIDAQABAoIBAHFDsgrrJca+NKEan77ycwx3jnKx4WrWjOF4zVKL9AQjiSYDNgvKknJyPb3kpC/lEoHdxGERHSzJoxib7DkoIqRQYhPxj73pxj5QfYk3P7LLJNNg/LTrpXDb3nL8JV9wIflGf87qQvstZTDJEyFWE4jBs7Hr0BxovWvri8InnzkmERJ1cbGJgNHe1Y3Zo2tw0yaHxQCxLuajP+notRZhD9bEp7uKeI0w9AvlW6k8m/7y10F0BK/TlyW8rQiEC391yOiRYoMcUh4hd2Q9bMx3jngZgX8PXIvZZcup4/pvWlv1alwhB2tsnLdazP62r1MO80vLyLunzGO+7WwCjEYlVaECgYEA+lQRFmbhKaPuAuXMtY31Fbga8nedka5TjnEV7+/kX+yowE2OlNujF+ZG8UTddTxAGv56yVNi/mjRlgD74j8z0eOsgvOq9mwbCrgLhLo51H9O/wAxtb+hBKtC5l50pBr4gER6d8W6EQNTSGojnMIaLXTkAZ5Qf6Z8e2HFVdOn0X0CgYEA7SSrTokwzukt5KldNu5ukyyd+C3D1i6orbg6qD73EP9CfNMfGSBn7dDv9wMSJH01+Ty+RgTROgtjGRDbMJWnfbdt/61NePr9ar5sb6Nbsf7/I0w7cZF5dsaFYgzaOfQYquzXPbLQHkpMT64bqpv/Mwy4F2lFvaYWY5fA4pC2uckCgYEAg75Ym9ybJaoTqky8ttQ2Jy8UZ4VSVQhVC0My02sCWwWXLlXi8y7An+Rec73Ve0yxREOn5WrQT6pkmzh7V/ABWrYi5WxODpCIjtSbo0fLBa3Wqle00b0/hdCITetqIa/cFs1zUrOqICgK3bKWeXqiAkhhcwSZwwSgwOKM04Wn7ZUCgYBvhHX2mbdVJfyJ8kc+hMOE/E9RHRxiBVEXWHJlGi8PVCqNDq8qHr4g7Mdbzprig+s0yKblwHAvrpkseWvKHiZEjVTyDipHgShY4TGXEigVvUd37uppTrLi8xpYcJjS9gH/px7VCdiq1d+q/MJP6coJ1KphgATm2UrgDMYNBWaYWQKBgEHRxrmER7btUF60/YgcqPHFc8RpYQB2ZZE0kyKGDqk2Data1XYUY6vsPAU28yRLAaWr/D2H17iyLkxP80VLm6QhifxCadv90Q/Wl1DFfOJQMW6avyQ0so6G0wFq/LJxaFK4iLXQn1RJnmTp6BYiJMmK2BhFbRzw8ssMoF6ad2rr",
        // 请求地址
        url: "http://localhost:8081",
    };
    // 业务参数
    let mut biz_model = HashMap::new();
    biz_model.insert("name", "jim".to_string());
    biz_model.insert("address", "xx".to_string());
    biz_model.insert("age", "22".to_string());

    let mut files = vec![];

    // 添加上传文件
    /*let mod_dir = env::current_dir().unwrap().display().to_string();
    files = vec![
        UploadFile { name:"file1", path: format!("{}/{}", &mod_dir, "aa.txt")},
        UploadFile { name:"file2", path: format!("{}/{}", &mod_dir, "bb.txt") },
    ];*/


    // 创建请求，设置业务参数
    let request = MemberInfoGetRequest {
        base: BaseRequest { biz_model: biz_model, files: files }
    };

    // 发送请求
    let response:MemberInfoGetResponse = client.execute(request);

    // 成功
    if response.sub_code.len() == 0 {
        println!("resp:{:#?}", response)
    } else {
        println!("error:{:#?}", response)
    }
}

#[cfg(test)]
mod tests {
    use sdk::sign::{HashType, SignUtil};
    use sdk::http::HttpTool;
    use std::collections::HashMap;

    #[test]
    fn it_works() {
        let content = "123";
        let private_key = "MIICXAIBAAKBgQCHJlAPN+1dCbgc3HiahQkT2W/skecGWOCkSX4CPvEc8oIk6544\nxihEwShHnfrapiQdF2fndv5agrhg4FyOHheST42L5MnCk+4Km+mWm5GDvmFS7Sa2\naZ5o3regY0MUoJ7D74dYjE3UYFuTujAXiXjGpAwa9qOcKotov5LCkSfUeQIDAQAB\nAoGAB1cyw8LYRQSHQCUO9Wiaq730jPNHSrJW4EGAIz/XMYjv/fCgx0lnDEX4CbzI\nUGoz/bME4R721YRyXoutJ0h14/cGrt/TEn/TMI0xnISzJHr8VSlyBkQEdfO/W3LO\nqjs/UYq2Bz4+kJROJHreM+7d5hiIWLzLBlyI8cSU92ySmHECQQDwju2SoRu88kQP\n1qr4seZyKQa8DHTVyCoa6LtPLXyJsdgWgY4KyqJHwMUumEC2Zhhu833CR0ZXbfta\nuQDmwAVJAkEAj9M225jrPasaD5vPO7thxtEqgV4F/ZyNKH0Z8bDH27KaKkQ+8GMt\nkxwKVckZXs2bMvg/6tCiDZkWAxawNrvFsQJBANmTrPWOmpQPW9gnhYRjA9gFm33C\nlno2DT9BeQloTtgL7zKMA3lnRdg4VyCJvR48waS4vupVpR228D1iT5pl22ECQF1M\nJUzkcM0rPheb+h2EW1QOgWU0Keyvbj4ykO7gv3T78dezN6TWoUzJpsapUiTWeXPh\n6AyZ1FW/1bChOiP3QLECQGAbObmsYlN0bjzPYChwWYeYjErXuv51a44GZCNWinFw\nGGiHU9ZAqF8RzmBVW4htwj0j/Yry/V1Sp0uoP0zu3uA=";
        let sign = SignUtil::rsa_sign(content, private_key, HashType::Sha256);
        println!("sign:{}", sign);
    }

    #[test]
    fn test_http() {
        let mut map = HashMap::new();
        map.insert("aaa", "bbb");
        let response = HttpTool::get("http://baidu.com", &map, &HashMap::new());
        println!("response:{:#?}", response);
    }

}