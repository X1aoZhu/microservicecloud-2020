package com.zhu.springcloud.controller;

import com.zhu.springcloud.entities.CommonResult;
import com.zhu.springcloud.entities.Payment;
import com.zhu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/payment/lb", produces = {"application/json;charset=UTF-8"})
    public String getPaymentLb() {
        return serverPort;
    }


    @PostMapping(value = "/payment/create", produces = {"application/json;charset=UTF-8"})
    public CommonResult<Object> create(@RequestBody Payment payment) {
        Integer result = paymentService.create(payment);
        log.info("*****插入结果：" + result);
        if (result > 0) {
            return new CommonResult<>(200, "插入数据库成功,ServerPort:" + serverPort, result);
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

    /**
     * 返回当前服务的基本信息
     *
     * @return
     */
    @GetMapping(value = "/payment/discovery", produces = {"application/json;charset=UTF-8"})
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("*****element:" + service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() +
                    "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }


    /**
     * 查看openfeign的超时情况
     *
     * @return
     */
    @GetMapping(value = "/payment/feign/timeout", produces = {"application/json;charset=UTF-8"})
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return serverPort;
        }
    }

}
