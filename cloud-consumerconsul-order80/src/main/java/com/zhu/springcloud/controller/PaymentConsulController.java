package com.zhu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author Zhu
 * @Date 2020/4/13 20:04
 */
@RestController
@Slf4j
public class PaymentConsulController {

    private static final String ORDER_CONSUL = "http://consul-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/consumer/payment/consul")
    public String paymentConsulInfo() {
        return restTemplate.getForObject(ORDER_CONSUL + "/payment/consul", String.class);
    }

}
