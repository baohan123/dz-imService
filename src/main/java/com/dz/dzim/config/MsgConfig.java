package com.dz.dzim.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author 常用报文信息
 * @className MsgConfig
 * @description TODO
 * @date 2021/2/18 14:52
 */
@Configuration
//@PropertySource("classpath:jdbc.properties") //指定多个配置文件，这样可以获取到其他的配置文件的配置信息
@PropertySource(value = "classpath:msg.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
@Component
public class MsgConfig {
    @Value("${0x20}")
    private String sysGo;

    @Value("${0x21}")
    private String sysBy;

    @Value("${0x23}")
    private String sysSmallGo;

    @Value("${0x30}")
    private String sysService;

    @Value("${0x31}")
    private String sysEnd;

    public String getSysGo() {
        return sysGo;
    }

    public String getSysBy() {
        return sysBy;
    }

    public String getSysSmallGo() {
        return sysSmallGo;
    }

    public String getSysService() {
        return sysService;
    }

    public String getSysEnd() {
        return sysEnd;
    }
}
