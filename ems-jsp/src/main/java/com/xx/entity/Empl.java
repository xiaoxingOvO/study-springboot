package com.xx.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * employee
 * @author 
 */
public class Empl{
    private Integer id;
    private String name;
    //springboot springmvc spring 默认处理日期格式:yyyy/MM/dd HH:mm:ss
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private Double salary;
    private Boolean gender;

    public Empl() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Empl(Integer id, String name, Date birthday, Double salary, Boolean gender) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.salary = salary;
        this.gender = gender;
    }
}