package com.ctsi.ssdc.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
/**
 * 字符验证码配置
 * @author ctsi
 *
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public Producer kaptchaProducer() {
        Properties kaptchaProperties = new Properties();

        //图片边框
        kaptchaProperties.put("kaptcha.border", "no");
        //图片边框颜色
		//kaptchaProperties.put("kaptcha.border.color", "105,179,90");
        //图片高
        kaptchaProperties.put("kaptcha.image.height","50");
        //图片宽
        kaptchaProperties.put("kaptcha.image.width","150");
        /* 图片样式
         * 水纹com.google.code.kaptcha.impl.WaterRipple
		 * 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
		 * 阴影com.google.code.kaptcha.impl.ShadowGimpy 
         */
        kaptchaProperties.put("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");
        //验证码长度
        kaptchaProperties.put("kaptcha.textproducer.char.length","4");
        //字体颜色
        kaptchaProperties.put("kaptcha.textproducer.font.color","black");
        //字体大小
        kaptchaProperties.put("kaptcha.textproducer.font.size","40");
        //字体
        kaptchaProperties.put("kaptcha.textproducer.font.names","DejaVuSerif");
        //文字间隔
        //kaptchaProperties.put("kaptcha.textproducer.char.space","3");
        //干扰实现类
        kaptchaProperties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
        //kaptchaProperties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.DefaultNoise");
        //干扰颜色
        //kaptchaProperties.put("kaptcha.noise.color","105,179,90");
        //文本集合
        kaptchaProperties.put("kaptcha.textproducer.char.string","acdefhkmnprtwxy345678");
        //背景颜色渐变，开始颜色
		//kaptchaProperties.put("kaptcha.background.clear.from","185,56,213");
		//背景颜色渐变，结束颜色
		//kaptchaProperties.put("kaptcha.background.clear.to","white");
               
        Config config = new Config(kaptchaProperties);
        return config.getProducerImpl();
    }
}