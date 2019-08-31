package com.huanxin.oa.model.review;

import java.util.List;

public class TablesBean {
    /**
     * 未审核列表
     */
    private List<ReviewingBean> NoCheckList;

    public List<ReviewingBean> getNoCheckList() {
        return NoCheckList;
    }

    public void setNoCheckList(List<ReviewingBean> NoCheckList) {
        this.NoCheckList = NoCheckList;
    }

    /**
     * 已审核列表
     */
    private List<ReviewedBean> CheckList;

    public List<ReviewedBean> getCheckList() {
        return CheckList;
    }

    public void setCheckList(List<ReviewedBean> CheckList) {
        this.CheckList = CheckList;
    }

}