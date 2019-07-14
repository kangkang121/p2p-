package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;

import java.util.List;

/**
 * 数据字典对应的service
 */
public interface ISystemDictionaryItem {

    /**
     * 根据字典的sn查询对应的明细
     */
    List<SystemDictionaryItem> listItemByDirSn(String sn);

}
