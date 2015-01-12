package com.coolweather.app.model;

/**
 * Created by yjh on 2015/1/12.
 */
public class County {
    private Integer id;
    private String name;
    private String code;
    private Integer city_id;

    public County() {
    }

    public County(Integer id, String name, String code,Integer city_id) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.city_id = city_id;
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

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }
}
