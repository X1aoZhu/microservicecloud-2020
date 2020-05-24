package com.zhu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author Zhu
 * @Date 2020/4/13 20:20
 */
@RestController
@Slf4j
public class OrderZKController {

    private static final String INVOKE_URL = "http://cloud-provider-payment-zk";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/zk")
    public String paymentHelloZK() {
        return restTemplate.getForObject(INVOKE_URL + "/payment/zk", String.class);
    }

}
