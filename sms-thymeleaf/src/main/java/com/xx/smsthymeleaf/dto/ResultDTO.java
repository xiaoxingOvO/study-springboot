package com.xx.smsthymeleaf.dto;

/**
* 数据传输对象
* 标准的javaBean格式:
* 1.所有数据私有化(如果是public则都能看到，应该设成私有然后提供getter和setter方法进行数据的读写)
* 2.无参构造器
* 3.getter和setter
*/
public class ResultDTO {

    private Integer code;

    private String msg;

    private Object data;

    public ResultDTO() {
    }

    public ResultDTO(String msg) {
        this.msg = msg;
    }

    public ResultDTO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultDTO(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
