package cn.wolfcode.p2p.base.query;

/**
 * 借款申请查询对象
 */
public class BidRequestQuery extends QueryObject {

    private Integer state = -1;

    private Integer[] states;

    public Integer[] getStates() {
        return states;
    }

    public void setStates(Integer[] states) {
        this.states = states;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
