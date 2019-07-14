package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.query.RealAuthQuery;
import cn.wolfcode.p2p.util.PageResult;

public interface IRealAuthService {

    /**
     * 保存对应的realAuthor
     */
    void save(RealAuth realAuth);

    /**
     * 根据id获取到realAuth对象
     */
    RealAuth getById(Long realAuthId);

    /**
     * 后台分页查询
     */
    PageResult queryForList(RealAuthQuery qo);

    /**
     * 审核对应的状态值
     * @param id  当前用户的id
     * @param state 当前用户的状态
     * @param remark 当前用户的标记
     */
    void updateState(Long id, Integer state, String remark);
}
