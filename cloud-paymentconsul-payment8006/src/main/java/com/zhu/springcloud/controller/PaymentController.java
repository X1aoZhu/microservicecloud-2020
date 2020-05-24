package com.zhu.springcloud.controller;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Zhu
 * @Date 2020/4/13 19:53
 */
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/payment/consul")
    public String paymentZK() {
        return "springCloud with consul, serverPort: " +
                serverPort + ", " + UUID.randomUUID().toString();
    }
}
