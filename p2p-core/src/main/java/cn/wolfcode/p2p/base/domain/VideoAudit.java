package cn.wolfcode.p2p.base.domain;

import com.alibaba.druid.sql.visitor.functions.If;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 视频审核模型
 */
public class VideoAudit extends BaseAuthDomain {

    /**
     * 预约的时间段
     */
    private OrderTime orderTime;

    /**
     *预约的日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    /**
     * json字符串用户数据的回显
     */
    public String getJsonString(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("username",getApplier().getUsername());
        map.put("orderTime",getCompleteTime());
        //转换成jsron字符串
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getCompleteTime(){
        if (orderDate!= null) {
            return DateFormat.getDateInstance().format(orderDate) + "  "
                + orderTime.getBegin() + "-"+orderTime.getEnd();
        }
        return null;
    }

    public OrderTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(OrderTime orderTime) {
        this.orderTime = orderTime;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
