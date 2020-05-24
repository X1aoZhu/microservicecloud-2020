package com.zhu.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zhu.springcloud.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Zhu
 * @Date 2020/4/16 17:49
 */
@RestController
@Slf4j
@DefaultProperties(defaultFallback = "paymentInfo_Global_FallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentInfo_OK(id);
    }

    /**
     * 客户端降级处，超时时间设为1.5s，超时或出错即降级处理
     *
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "6000")
    })
    public String paymentInfo_Timeout(@PathVariable("id") Integer id) {
        int num = 10 / 0;
        return paymentHystrixService.paymentInfo_Timeout(id);
    }

    /**
     * 特定降级方法
     *
     * @param id
     * @return
     */
    private String paymentInfo_TimeoutHandler(Integer id) {
        return "/(ToT)/我是消费者80，调用8001支付系统繁忙，请10秒钟后重新尝试、\t";
    }

    /**
     * 下面是全局fallback方法
     *
     * @return
     */
    public String paymentInfo_Global_FallbackMethod() {
        return "Global异常处理信息，请稍后再试， /(ToT)/";
    }

}
