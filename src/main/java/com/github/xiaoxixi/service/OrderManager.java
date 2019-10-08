package com.github.xiaoxixi.service;

public interface OrderManager {

    Long initOrder();

    boolean checkOrderPaid(Long id);

    boolean closeOrder(Long id);
}
