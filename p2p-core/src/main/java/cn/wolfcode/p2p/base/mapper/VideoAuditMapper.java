package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.VideoAudit;
import cn.wolfcode.p2p.base.query.VideoAuditQuery;
import java.util.List;

public interface VideoAuditMapper {

    int insert(VideoAudit record);

    VideoAudit selectByPrimaryKey(Long id);

    int updateByPrimaryKey(VideoAudit record);

    Integer selectForCount(VideoAuditQuery qo);

    List<VideoAudit> selectForList(VideoAuditQuery qo);

    void delectById(Long videoAuditId);
}