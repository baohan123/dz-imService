package com.dz.dzim;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableRabbit
@EnableAsync
// 开启定时任务
@EnableScheduling
//@MapperScan("com.dz")
/**开启rabbitmq*/
public class DzImApplication {

    public static void main(String[] args) {
        SpringApplication.run(DzImApplication.class, args);
    }

    /**这里也需要设置消息转换类型
     * 和发送的消息类型一定要对应
     * 不然对象接受json启动主程序类时就会报错
     * */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
