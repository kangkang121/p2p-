package cn.wolfcode.p2p.base.domain;

/**
 * 数据字典明细模型
 */
public class SystemDictionaryItem extends BaseDomain {
    //对应的数据字典的目录
    private Long parentId;

    //数据字典明细显示名称
    private String title;

    //数据字典明细可选值
    private String tvalue;

    //数据字典明细在该分类中的排序
    private Integer sequence = 0;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTvalue() {
        return tvalue;
    }

    public void setTvalue(String tvalue) {
        this.tvalue = tvalue;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}