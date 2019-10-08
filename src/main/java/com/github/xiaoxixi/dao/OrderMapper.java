package com.github.xiaoxixi.dao;

import com.github.xiaoxixi.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OrderMapper {

    Order selectById(Long id);

    boolean deleteById(Long id);

    boolean insertSelective(Order order);

    boolean updateByIdSelective(Order order);
}
