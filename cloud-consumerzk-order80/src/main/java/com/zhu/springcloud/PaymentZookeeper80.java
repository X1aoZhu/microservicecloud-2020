package com.zhu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author Zhu
 * @Date 2020/4/13 20:19
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentZookeeper80 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentZookeeper80.class, args);
    }
}
