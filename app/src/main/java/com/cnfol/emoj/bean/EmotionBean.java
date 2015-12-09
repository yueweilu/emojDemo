package com.cnfol.emoj.bean;

import java.io.Serializable;

public class EmotionBean implements Serializable {
    private int id;//图片id
    private String name;//表情名字--->>[/hehe]

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return "EmotionBean [id=" + id + ", name=" + name + "]";
    }


}
