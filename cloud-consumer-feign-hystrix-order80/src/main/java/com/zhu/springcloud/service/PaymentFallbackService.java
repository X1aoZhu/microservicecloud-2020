package com.zhu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @Author Zhu
 * @Date 2020/4/16 19:16
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "----PaymentFallbackService fall back paymentInfo_OK,o(╥﹏╥)o";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "----PaymentFallbackService fall back paymentInfo_Timeout,o(╥﹏╥)o";
    }

}
