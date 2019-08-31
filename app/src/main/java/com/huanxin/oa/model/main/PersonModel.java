package com.huanxin.oa.model.main;

public class PersonModel {
    int id;
    String content = "";

    public PersonModel(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public PersonModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
