package cn.wolfcode.p2p.base.domain;

import cn.wolfcode.p2p.util.BitStatesUtils;

/**
 * 用户基本信息模型
 */
public class UserInfo extends BaseDomain {


    /**
     * 关联对应的RealAuth的Id  其中作用
     *
     *   实名认证对象的id ：
         如果这个id为null，没有实名在申请中
         如果这个id不为null，有实名申请：
         如果这个实名申请后台审核拒绝，把这个实名认证对象的id设置为null
         如果这个实名申请后台审核成功，不需要设置为null
         并且我可以通过这个id获取到通过实名认证的那一条审核记录信息
     */
    private Long realAuthId;

    /**
     * 视频认证id
     */
    private Long videoAuditId;


    /**
     * 版本号
     */
    private Long version = 0L;

    /**
     * 位状态值
     */
    private Long bitState = 0L;

    /**
     * 用户真实姓名  通过实名认证来完成
     */
    private String realName;

    /**
     * 用户身份证号码    通过实名认证来完成
     */
    private String idNumber;

    /**
     *  邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 收入
     */
    private SystemDictionaryItem incomeGrade;

    /**
     * 婚姻状态
     */
    private SystemDictionaryItem marriage;

    /**
     * 子女情况
     */
    private SystemDictionaryItem kidCount;

    /**
     * 学历
     */
    private SystemDictionaryItem educationBackground;

    /**
     * 住房条件
     */
    private SystemDictionaryItem houseCondition;

    /**
     * 用户是否完成基本信息认证
     */
    public boolean hasBasicInfo(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_BASIC_INFO);
    }

    /**
     * 用户是否完成实名认证
     */
    public boolean hasRealAuth(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_REAL_AUTH);
    }

    /**
     * 用户是否完成视频认证
     */
    public boolean hasVedioAuth(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_VEDIO_AUTH);
    }

    /**
     * 判断用户是否完成所有认证
     */
    public boolean hasAllCertification(){
        return hasBasicInfo() && hasRealAuth() && hasVedioAuth();
    }

    /**
     * 判断用户是否完成借款状态
     */
    public boolean hasBidRequest(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
    }

    /**
     *添加位状态
     */
    public void updateBitState(Long state){
        bitState = BitStatesUtils.addState(bitState,state);
    }

    /**
     * 删除位状态
     */
    public void removeBitState(Long state) {
        bitState = BitStatesUtils.removeState(bitState,state);
    }

    public Long getVideoAuditId() {
        return videoAuditId;
    }

    public void setVideoAuditId(Long videoAuditId) {
        this.videoAuditId = videoAuditId;
    }

    public Long getRealAuthId() {
        return realAuthId;
    }

    public void setRealAuthId(Long realAuthId) {
        this.realAuthId = realAuthId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getBitState() {
        return bitState;
    }

    public void setBitState(Long bitState) {
        this.bitState = bitState;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SystemDictionaryItem getIncomeGrade() {
        return incomeGrade;
    }

    public void setIncomeGrade(SystemDictionaryItem incomeGrade) {
        this.incomeGrade = incomeGrade;
    }

    public SystemDictionaryItem getMarriage() {
        return marriage;
    }

    public void setMarriage(SystemDictionaryItem marriage) {
        this.marriage = marriage;
    }

    public SystemDictionaryItem getKidCount() {
        return kidCount;
    }

    public void setKidCount(SystemDictionaryItem kidCount) {
        this.kidCount = kidCount;
    }

    public SystemDictionaryItem getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(SystemDictionaryItem educationBackground) {
        this.educationBackground = educationBackground;
    }

    public SystemDictionaryItem getHouseCondition() {
        return houseCondition;
    }

    public void setHouseCondition(SystemDictionaryItem houseCondition) {
        this.houseCondition = houseCondition;
    }


}