package com.coolweather.app.model;

/**
 * Created by yjh on 2015/1/12.
 */
public class Province {
    private Integer id;
    private String name;
    private String code;

    public Province() {
    }

    public Province(Integer id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
