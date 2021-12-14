package com.xx.smsthymeleaf.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
public class Student {
    private Integer stuId;

    private String stuNum;

    private String stuName;

    private String stuPwd;

    private Byte stuGender;

    private Byte stuMajor;

    private String stuTime;

    private String stuRemark;

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum == null ? null : stuNum.trim();
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName == null ? null : stuName.trim();
    }

    public String getStuPwd() {
        return stuPwd;
    }

    public void setStuPwd(String stuPwd) {
        this.stuPwd = stuPwd == null ? null : stuPwd.trim();
    }

    public Byte getStuGender() {
        return stuGender;
    }

    public void setStuGender(Byte stuGender) {
        this.stuGender = stuGender;
    }

    public Byte getStuMajor() {
        return stuMajor;
    }

    public void setStuMajor(Byte stuMajor) {
        this.stuMajor = stuMajor;
    }

    public String getStuTime() {
        return stuTime;
    }

    public void setStuTime(String stuTime) {
        this.stuTime = stuTime;
    }

    public String getStuRemark() {
        return stuRemark;
    }

    public void setStuRemark(String stuRemark) {
        this.stuRemark = stuRemark == null ? null : stuRemark.trim();
    }
}