package com.huanxin.oa.review.model;

public class ReviewSelect {
    String title;
    int id;
    int type;

    public ReviewSelect(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public ReviewSelect(String title, int id, int type) {
        this.title = title;
        this.id = id;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
