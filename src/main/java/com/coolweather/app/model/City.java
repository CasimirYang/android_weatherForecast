package com.coolweather.app.model;

/**
 * Created by yjh on 2015/1/12.
 */
public class City {
    private Integer id;
    private String name;
    private String code;
    private Integer province_id;

    public City() {
    }

    public City(Integer id, String name, String code,Integer province_id) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.province_id = province_id;
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

    public Integer getProvince_id() {
        return province_id;
    }

    public void setProvince_id(Integer province_id) {
        this.province_id = province_id;
    }
}
