package com.airsharing.company.airsharing.model;

/**
 * Created by surin on 2017. 10. 16..
 */

public class MemberData {
    private String uuid;   //AirSharing 고유 id (+17.10.16 추가)
    private String name;   // 네이버에서 가져올 사용자 이름
    private String email;  // 네이버에서 가져올 사용자 이메일
    private String birthdate;  // 네이버에서 가져올 사용자 생년월일
    private String gender;   // 네이버에서 가져올 사용자 성별
    private String userid;   // 네이버에서 가져올 아이디
    private String pw;
    private String location;
    private String phone;
    private boolean alarm;

    public MemberData(){
        name = "";
        email = "";
        gender = "";
        birthdate = "";
        userid = "";
        pw = "";
        location = "";
        phone = "";
        email = "";
        alarm = false;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }
}
