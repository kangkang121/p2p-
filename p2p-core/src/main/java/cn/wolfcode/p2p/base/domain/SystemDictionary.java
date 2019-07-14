package cn.wolfcode.p2p.base.domain;

/**
 * 数据字典目录模型
 */
public class SystemDictionary extends BaseDomain {
    //数据字典分类编码
    private String sn;

    //数据字典分类名称
    private String title;


    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

}