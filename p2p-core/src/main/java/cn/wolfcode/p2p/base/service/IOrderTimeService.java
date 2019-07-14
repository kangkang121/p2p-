package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.OrderTime;

import java.util.List;

public interface IOrderTimeService {

    /**
     * 查询所有的时间段
     */
    List<OrderTime> listAll();

}
