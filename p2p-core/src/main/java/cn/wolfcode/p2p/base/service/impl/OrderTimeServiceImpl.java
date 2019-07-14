package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.OrderTime;
import cn.wolfcode.p2p.base.mapper.OrderTimeMapper;
import cn.wolfcode.p2p.base.service.IOrderTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderTimeServiceImpl implements IOrderTimeService {

    @Autowired
    private OrderTimeMapper orderTimeMapper;

    @Override
    public List<OrderTime> listAll() {
        return orderTimeMapper.selectAll();
    }
}
