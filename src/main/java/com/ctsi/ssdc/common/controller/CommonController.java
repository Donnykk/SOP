package com.ctsi.ssdc.common.controller;

import com.ctsi.ssdc.common.oos.OosConfig;
import com.ctsi.ssdc.common.oos.OosUtils;
import com.ctsi.ssdc.common.utils.DateUtils;
import com.ctsi.ssdc.common.utils.FileUtils;
import com.ctsi.ssdc.model.AjaxResult;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;

/**
 * 通用请求处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/common")
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    private static final String FILE_DELIMETER = ",";

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            String filename= FileUtils.getFileNameShort(file.getOriginalFilename());
            String suffix = "shuren/" + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, new Date());
            OosUtils.putObject(transferToFile(file), filename, OosConfig.OOS_BUCKET_NAME + "/" + suffix);
            return AjaxResult.success()
                    .put("tmp_url", OosUtils.generatePresignedUrl(suffix + "/" +filename, OosConfig.OOS_BUCKET_NAME, 30));
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @GetMapping("/getFile")
    public AjaxResult getFile(@RequestParam String key) throws Exception
    {
        try
        {
            return AjaxResult.success().put("tmp_url", OosUtils.generatePresignedUrl(key, OosConfig.OOS_BUCKET_NAME, 30));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }


    @GetMapping("/getIp")
    public AjaxResult getIp(HttpServletRequest request)
    {
        String ip = getIpAddr(request);
        return AjaxResult.success(ip);
    }

    public static String getIpAddr(HttpServletRequest request)
    {
        if (request == null)
        {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : getMultistageReverseProxyIp(ip);
    }

    public static String getMultistageReverseProxyIp(String ip)
    {
        // 多级反向代理检测
        if (ip != null && ip.indexOf(",") > 0)
        {
            final String[] ips = ip.trim().split(",");
            for (String subIp : ips)
            {
                if (false == isUnknown(subIp))
                {
                    ip = subIp;
                    break;
                }
            }
        }
        return substring(ip, 0, 255);
    }
    public static String substring(final String str, int start, int end)
    {
        if (str == null)
        {
            return "";
        }

        if (end < 0)
        {
            end = str.length() + end;
        }
        if (start < 0)
        {
            start = str.length() + start;
        }

        if (end > str.length())
        {
            end = str.length();
        }

        if (start > end)
        {
            return "";
        }

        if (start < 0)
        {
            start = 0;
        }
        if (end < 0)
        {
            end = 0;
        }

        return str.substring(start, end);
    }
    /**
     * 检测给定字符串是否为未知，多用于检测HTTP请求相关
     *
     * @param checkString 被检测的字符串
     * @return 是否未知
     */
    public static boolean isUnknown(String checkString)
    {
        return StringUtils.isEmpty(checkString) || "unknown".equalsIgnoreCase(checkString);
    }
    private File transferToFile(MultipartFile multipartFile) {
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若须要防止生成的临时文件重复,能够在文件名后添加随机码
        try {
            File file = File.createTempFile(fileName, prefix);
            org.apache.commons.io.FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
