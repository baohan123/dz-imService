package com.dz.dzim.config;/**
 * @className WebMvcConfig
 * @description TODO
 * @author xxxyyy
 * @date 2021/1/27 17:58
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *@className WebMvcConfig
 *@description TODO
 *@author xxxyyy
 *@date 2021/1/27 17:58
 */

@Configuration
//@EnableWebMvc //这个注解会覆盖掉SpringBoot的默认的静态资源映射配置
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value("${filepath.imgdir}")
    private String imgDir;

    /*将浏览器访问地址和图片实际存放的附近做绑定，访问url地址就可以读取实际图片的内容，在前端展示*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + imgDir);
    }
}
