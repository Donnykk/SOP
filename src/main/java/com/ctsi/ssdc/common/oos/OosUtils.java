package com.ctsi.ssdc.common.oos;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.CtyunBucketDataLocation;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import liquibase.pro.packaged.S;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class OosUtils {

    public static AmazonS3 getAmazonS3(){
        ClientConfiguration clientConfig = new ClientConfiguration();
        //设置连接的超时时间，单位毫秒
        clientConfig.setConnectionTimeout(10*1000);
        //设置 socket 超时时间，单位毫秒
        clientConfig.setSocketTimeout(10*1000) ;
        clientConfig.setProtocol(Protocol.HTTP); //设置 http 设置 V4 签名算法中负载是否参与签名，关于签名部分请参看《OOS 开发者文档》
        S3ClientOptions options = new S3ClientOptions();
        options.setPayloadSigningEnabled(true);
        // 创建 client
        AmazonS3 oosClient = new AmazonS3Client(
                new PropertiesCredentials(OosConfig.OOS_ACCESS_ID,
                        OosConfig.OOS_ACCESS_KEY),clientConfig);
        // 设置 endpoint
        oosClient.setEndpoint(OosConfig.OOS_ENDPOINT);
        //设置选项
        oosClient.setS3ClientOptions(options);
        return oosClient;
    }


    public static void putObject(File file, String objectInBucketKey,String bucketName){
        AmazonS3 oosClient=getAmazonS3();
        PutObjectRequest request = new PutObjectRequest(bucketName,objectInBucketKey,file);
        ObjectMetadata metadata = new ObjectMetadata();
        CtyunBucketDataLocation dataLocation = new CtyunBucketDataLocation();
        dataLocation.setType(CtyunBucketDataLocation.CtyunBucketDataType.Specified);
        List<String> dataRegions = new ArrayList<String>();
        dataRegions.add("yzdx");
        dataLocation.setDataRegions(dataRegions);
        metadata.setDataLocation(dataLocation);
        request.setMetadata(metadata);
        oosClient.putObject(request);
    }

    public static void putObject(InputStream inputStream, String objectInBucketKey, String bucketName){
        AmazonS3 oosClient=getAmazonS3();
        PutObjectRequest request = new PutObjectRequest(bucketName,objectInBucketKey,inputStream,null);
        ObjectMetadata metadata = new ObjectMetadata();
        CtyunBucketDataLocation dataLocation = new CtyunBucketDataLocation();
        dataLocation.setType(CtyunBucketDataLocation.CtyunBucketDataType.Specified);
        List<String> dataRegions = new ArrayList<String>();
        dataRegions.add("yzdx");
        dataLocation.setDataRegions(dataRegions);
        metadata.setDataLocation(dataLocation);
        request.setMetadata(metadata);
        oosClient.putObject(request);
    }

    public static String generatePresignedUrl(String key, String bucketName, Integer minute) {
        AmazonS3 oosClient = getAmazonS3();
        GeneratePresignedUrlRequest request = new
                GeneratePresignedUrlRequest(bucketName, key);
        //超时时间 10min
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, minute);
        request.setExpiration(instance.getTime());
        URL url = oosClient.generatePresignedUrl(request);
        return url.toString();
    }

    public static String getKey(String url){
        String replace = url.replace(OosConfig.DOWNLOAD_REPLACE_URL_PRE, "");

        if(replace.contains("?")) {
            String[] split = replace.split("\\?");
            return split[0];
        }
        return replace;
    }

    /**
     * 转换 oos 图片地址
     * @param baseUrl
     * @return
     */
    public static String changeUrl(String baseUrl)
    {
        if(StringUtils.isEmpty(baseUrl))
        {
            return baseUrl;
        }

        List<String> resUrls =new ArrayList<>();

        List<String> urlList = Arrays.asList(baseUrl.split(","));

        for(String url:urlList)
        {
            String key = getKey(url);
            String resUrl = generatePresignedUrl(key,OosConfig.OOS_BUCKET_NAME,30);
            resUrls.add(resUrl);
        }

        String finalUrl = String.join(",",resUrls);
        return finalUrl;
    }

    /**
     * 批量将原有的转换为私有
     * @param target
     * @return
     */
    public static String buildSelfUrl(String target, String character){
        if(StringUtils.isEmpty(target)) return target;

        String[] split = target.split(character);
        StringBuilder builder = new StringBuilder();

        for (String s : split) {
            builder.append( OosUtils.generatePresignedUrl(s, OosConfig.OOS_BUCKET_NAME, 30) ).append(character);
        }
        return builder.toString().substring(0, builder.length() - 1);
    }
}
