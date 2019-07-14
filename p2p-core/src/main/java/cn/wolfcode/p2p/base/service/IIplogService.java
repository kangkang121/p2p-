package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.util.PageResult;

public interface IIplogService {
    /**
     * 对应的高级查询Iplog
     */
    PageResult querForList(QueryObject qo);

    /**
     * 保存登录的记录
     */
    void save(String username, int state, Integer userType);

}
