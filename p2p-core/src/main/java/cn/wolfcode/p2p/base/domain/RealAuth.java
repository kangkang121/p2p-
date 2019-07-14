package cn.wolfcode.p2p.base.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 用户实名认证模型
 */
public class RealAuth extends BaseAuthDomain {


    /**
     * 男
     */
    public static final int SEC_MEAL = 0;
    /**
     * 女
     */
    public static final int SEX_FEMAL = 1;

    /**
     * 真实名字
     */
    private String  realName;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 身份证号
     */
    private String  idNumber;
    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date    bornDate;
    /**
     * 身份证地址
     */
    private String  address;
    /**
     * 身份证正面
     */
    private String  image1;
    /**
     * 身份证反面
     */
    private String  image2;


    /**
     * 显示性别
     */
    public String getSexDisplay(){
        return sex == SEC_MEAL ? "男":"女";
    }



    /**
     * json字符串用户数据的回显
     */
    public String getJsonString(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("username",getApplier().getUsername());
        map.put("realname",realName);
        map.put("sex",getSexDisplay());
        map.put("idNumber",idNumber);
        map.put("bornDate", DateFormat.getDateInstance().format(bornDate));
        map.put("address",address);
        map.put("image1",image1);
        map.put("image2",image2);
        //转换成jsron字符串
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }


}
