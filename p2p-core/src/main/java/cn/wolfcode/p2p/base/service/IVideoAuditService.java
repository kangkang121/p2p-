package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.VideoAudit;
import cn.wolfcode.p2p.base.query.VideoAuditQuery;
import cn.wolfcode.p2p.util.PageResult;

/**
 * 视频认证
 */
public interface IVideoAuditService {
    /**
     * 视频认证的方法
     */
    void audit(VideoAudit videoAudit);

    /**
     *  根据id查询对应的videaudit对象
     */
    VideoAudit getById(Long videoAuditId);

    /**
     * 后台高级查询
     */
    PageResult queryForList(VideoAuditQuery qo);

    /**
     * 后台审核认证
     */
    void attest(Long id, Integer state, String remark);

    void update(VideoAudit videoAudit);

    /**
     * 取消视频申请
     */
    void cancleVideoAudit();

}
