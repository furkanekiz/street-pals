package com.furkanekiz.streetpals.fact.model;

public class FactCard {
    public int id;
    private String type;
    private String info;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
