package com.dz.dzim;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DzImApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

//    @Test
//    void contextLoads() {
//        for (int i = 0; i < 10000; i++) {
//            rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, "", "&&&&&&&&");
//        }
//    }

}
