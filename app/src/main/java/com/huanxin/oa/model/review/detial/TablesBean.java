package com.huanxin.oa.model.review.detial;

import java.util.List;

public class TablesBean {
    private List<MainBean> Main;
    private List<List<DetailsBean>> Details;
    private List<String> Image;
    private List<OpinionBean> Opinion;

    public List<MainBean> getMain() {
        return Main;
    }

    public void setMain(List<MainBean> Main) {
        this.Main = Main;
    }

    public List<List<DetailsBean>> getDetails() {
        return Details;
    }

    public void setDetails(List<List<DetailsBean>> Details) {
        this.Details = Details;
    }

    public List<String> getImage() {
        return Image;
    }

    public void setImage(List<String> Image) {
        this.Image = Image;
    }

    public List<OpinionBean> getOpinion() {
        return Opinion;
    }

    public void setOpinion(List<OpinionBean> Opinion) {
        this.Opinion = Opinion;
    }

}