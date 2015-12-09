package com.cnfol.emoj.bean;

import java.io.Serializable;

/**
 * @date 2015年11月5日 下午2:27:26
 * @autor LXM
 * @detail
 **/
public class LiveColorBean implements Serializable {

    private String data;//变颜色的内容
    private String color;//要显示的颜色
    private String code;//是否添加点击事件的代码

    public LiveColorBean(String data, String color, String code) {
        super();
        this.data = data;
        this.color = color;
        this.code = code;
    }

    public LiveColorBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "LiveColorBean{" +
                "data='" + data + '\'' +
                ", color='" + color + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
