package com.zhu.springcloud.service.impl;

import com.zhu.springcloud.dao.PaymentDao;
import com.zhu.springcloud.entities.Payment;
import com.zhu.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Zhu
 * @Date 2020/4/12 18:38
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
