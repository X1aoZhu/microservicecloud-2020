package com.zhu.springcloud.controller;

import com.zhu.springcloud.entities.CommonResult;
import com.zhu.springcloud.entities.Payment;
import com.zhu.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Zhu
 * @Date 2020/4/13 23:17
 */
@RestController
@Slf4j
public class OrderOpenFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;


    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping("/consumer/payment/feign/timeout")
    String paymentFeignTimeout() {
        return paymentFeignService.paymentFeignTimeout();
    }

}
