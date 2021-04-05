package com.zhu.springcloud.controller;

import com.zhu.springcloud.entities.CommonResult;
import com.zhu.springcloud.entities.Payment;
import com.zhu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author Zhu
 * @Date 2020/4/12 18:40
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/payment/lb", produces = {"application/json;charset=UTF-8"})
    public String getPaymentLb() {
        return serverPort;
    }

    @PostMapping(value = "/payment/create", produces = {"application/json;charset=UTF-8"})
    public CommonResult<Object> create(@RequestBody Payment payment) {
        Integer result = paymentService.create(payment);
        log.info("*****插入结果：" + result);
        if (result > 0) {
            return new CommonResult<Object>(200, "插入数据库成功,ServerPort:" + serverPort, result);
        } else {
            return new CommonResult<>(444, "插入数据库失败");
        }
    }


    @GetMapping(value = "/payment/get/{id}", produces = {"application/json;charset=UTF-8"})
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("*******查询的结果为：" + payment);
        if (payment == null) {
            return new CommonResult<>(444, "查询数据库失败");
        } else {
            return new CommonResult<>(200, "查询数据库成功,ServerPort：" + serverPort, payment);
        }
    }


}
