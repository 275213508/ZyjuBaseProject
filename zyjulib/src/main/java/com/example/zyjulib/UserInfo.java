package com.example.zyjulib;

/**
 * Created by zyju on 2019/6/3.
 */


import com.example.zyjulib.utile.CommonUtils;

/**
 * 用户信息
 */
public class UserInfo {

    private String id="";
    private String uuid="";
    private String name="";
    private String nickName="";
    private String icon="";
    private String phone="";
    private String idCode="";
    private int sex;//(0:男,1:女)
    private String level="";
    private String address="";
    private String xxaddress="";
    private String personalizedSign="";
    private String synopsis="";
    private String mail="";
    private String token="";
    private String bgImage="";

    private String isFollow = "";
    /**
     * followCount : 7
     * mail : null
     * level : null
     * fansCount : 3
     * bgImage : null
     * isFollow : 0
     * integral : 65
     * personalizedSign : null
     * name : null
     * id : 219
     */

    private int followCount;
    private int fansCount;
    private int integral;


    public String getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    public UserInfo() {
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getXxaddress() {
        return xxaddress;
    }

    public void setXxaddress(String xxaddress) {
        this.xxaddress = xxaddress;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonalizedSign() {
        return personalizedSign;
    }

    public void setPersonalizedSign(String personalizedSign) {
        this.personalizedSign = personalizedSign;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        if(id.isEmpty()){
            return CommonUtils.getAndroidId();
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ",\"uuid\":\"" + uuid + '\"' +
                ",\"name\":\"" + name + '\"' +
                ",\"nickName\":\"" + nickName + '\"' +
                ",\"bgImage\":\"" + bgImage + '\"' +
                ",\"icon\":\"" + icon + '\"' +
                ",\"phone\":\"" + phone + '\"' +
                ",\"idCode\":\"" + idCode + '\"' +
                ",\"sex\":\"" + sex + '\"' +
                ",\"level\":\"" + level + '\"' +
                ",\"address\":\"" + address + '\"' +
                ",\"xxaddress\":\"" + xxaddress + '\"' +
                ",\"personalizedSign\":\"" + personalizedSign + '\"' +
                ",\"synopsis\":\"" + synopsis + '\"' +
                ",\"mail\":\"" + mail + '\"' +
                ",\"followCount\":\"" + followCount + '\"' +
                ",\"fansCount\":\"" + fansCount + '\"' +
                ",\"integral\":\"" + integral + '\"' +
                ",\"token\":\"" + token + '\"' +
                '}';
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }


    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }
}
