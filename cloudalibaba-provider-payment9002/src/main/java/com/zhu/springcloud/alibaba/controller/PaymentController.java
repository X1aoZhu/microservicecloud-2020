package com.zhu.springcloud.alibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Zhu
 * @Date 2020/5/20 20:26
 */
@RestController
@RefreshScope
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @Value("${config.info}")
    private String configInfo;

    @GetMapping(value = "/payment/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id) {
        return "Hello Nacos Discovery: " + serverPort + "\t id: " + id + ", ConfigInfo : " + configInfo;
//        return "Hello Nacos Discovery: " + serverPort + "\t id: " + id;
    }
}
