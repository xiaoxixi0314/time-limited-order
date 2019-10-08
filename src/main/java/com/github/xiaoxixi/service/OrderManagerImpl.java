package com.github.xiaoxixi.service;

import com.github.xiaoxixi.dao.OrderMapper;
import com.github.xiaoxixi.domain.Order;
import com.github.xiaoxixi.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OrderManagerImpl implements OrderManager {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Long initOrder() {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.INIT);
        boolean result = orderMapper.insertSelective(order);
        if (result) {
            return order.getId();
        }
        return -1L;

    }

    @Override
    public boolean checkOrderPaid(Long id) {
        Order order = orderMapper.selectById(id);
        if (Objects.isNull(order)) {
            return false;
        }
        return order.getOrderStatus() == OrderStatus.PAID;
    }

    @Override
    public boolean closeOrder(Long id) {
        Order order = orderMapper.selectById(id);
        if (Objects.isNull(order)) {
            return false;
        }
        if (order.getOrderStatus() == OrderStatus.PAID) {
            return  false;
        }
        Order closeOrder = new Order();
        closeOrder.setId(order.getId());
        closeOrder.setOrderStatus(OrderStatus.CLOSED);
        return orderMapper.updateByIdSelective(closeOrder);
    }
}
