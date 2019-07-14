package cn.wolfcode.p2p.base.service.impl;
import java.util.Date;

import cn.wolfcode.p2p.base.domain.Iplog;
import cn.wolfcode.p2p.base.mapper.IplogMapper;
import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.base.service.IIplogService;
import cn.wolfcode.p2p.util.PageResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IplogServiceImpl implements IIplogService {

    @Autowired
    private IplogMapper iplogMapper;

    @Override
    public PageResult querForList(QueryObject qo) {
        Integer count = iplogMapper.selectForCount(qo);
        if(count == null){
            return PageResult.empty(qo.getPageSize());
        }
        List<Iplog> list =  iplogMapper.selectForList(qo);
        return new PageResult(list,count, qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public void save(String username, int state, Integer userType) {
        Iplog iplog = new Iplog();
        iplog.setUsername(username);
        iplog.setIp(UserContext.getIp());
        iplog.setLoginTime(new Date());
        iplog.setState(state);
        iplog.setUserType(userType);

        iplogMapper.insert(iplog);
    }
}
