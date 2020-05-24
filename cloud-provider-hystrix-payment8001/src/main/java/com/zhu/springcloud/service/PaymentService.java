package com.zhu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @Author Zhu
 * @Date 2020/4/16 16:33
 */
@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id) {
        return "线程池： " + Thread.currentThread().getName()
                + "   paymentInfo_OK,id:" + id + " 正常访问！";
    }


    /**
     * 配置hystrix，设置超时时间3秒，当前方法超时后调用 fallbackMethod 服务降级
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")})
    public String paymentInfo_Timeout(Integer id) {
        int timeNumber = 3;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "线程池： " + Thread.currentThread().getName()
                + "   paymentInfo_TimeOut,id:" + id + " 耗时(秒):" + timeNumber;
    }

    private String paymentInfo_TimeoutHandler(Integer id) {
        return "线程池： " + Thread.currentThread().getName()
                + "   paymentInfo_TimeOut,id:" + id + " 系统繁忙or报错，超时报错，请稍后再试！";
    }


    // 服务熔断

    /**
     * circuitBreaker.enabled:是否开启断路器
     * circuitBreaker.requestVolumeThreshold:请求数达到后才计算
     * circuitBreaker.sleepWindowInMilliseconds:休眠时间窗
     * circuitBreaker.errorThresholdPercentage:错误率达到多少跳闸
     * 当前配置 10s内请求10次，并且60%错误率则触发熔断
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("****id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能为负数,请稍后再试， o(╥﹏╥)o id: " + id;
    }

}