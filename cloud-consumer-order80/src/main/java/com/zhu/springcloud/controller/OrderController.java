package com.zhu.springcloud.controller;

import com.zhu.springcloud.entities.CommonResult;
import com.zhu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Zhu
 * @Date 2020/4/12 20:04
 */
@RestController
@Slf4j
public class OrderController {

    //    private static final String PAYMENT_URL = "http://localhost:8001";

    private static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/payment/create", produces = {"application/json;charset=UTF-8"})
    public CommonResult create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/get/{id}", produces = {"application/json;charset=UTF-8"})
    public CommonResult getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }


    @GetMapping(value = "/consumer/payment/get2/{id}", produces = {"application/json;charset=UTF-8"})
    public CommonResult getPayment2(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        HttpStatus statusCode = entity.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult
                    (444, "com.zhu.springcloud.controller.OrderController getPayment2 error");
        }
    }

    @GetMapping(value = "/consumer/payment/create2", produces = {"application/json;charset=UTF-8"})
    public CommonResult craete2(Payment payment) {
        return restTemplate.
                postForEntity(PAYMENT_URL + "/payment/create", payment, CommonResult.class).getBody();
    }

}
