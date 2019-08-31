package com.huanxin.oa.main.menu;

import java.util.List;

public class MenuData {

    /**
     * success : true
     * message :
     * menus : [{"iMenuID":"248","sMenuName":"销售报表","sIcon":"","ChildMenus":[{"iMenuID":249,"sMenuName":"销售订单列表","iSerial":0,"iFormID":10000021,"sIcon":"","sAppStyle":"树形表格","iShowChart":1,"iIsUnion":0},{"iMenuID":286,"sMenuName":"销售分析","iSerial":1,"iFormID":10000023,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":287,"sMenuName":"销售订单汇总","iSerial":2,"iFormID":10000024,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":290,"sMenuName":"销售订单","iSerial":3,"iFormID":10000025,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":291,"sMenuName":"无","iSerial":4,"iFormID":10000026,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":292,"sMenuName":"销售订单","iSerial":5,"iFormID":10000027,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":293,"sMenuName":"销售订单","iSerial":6,"iFormID":10000028,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":294,"sMenuName":"销售订单","iSerial":7,"iFormID":10000029,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":295,"sMenuName":"销售订单","iSerial":8,"iFormID":10000030,"sIcon":"","sAppStyle":"","iShowChart":0,"iIsUnion":0},{"iMenuID":302,"sMenuName":"联合报表(表格)","iSerial":9,"iFormID":10000031,"sIcon":"","sAppStyle":"","iShowChart":0,"iIsUnion":1},{"iMenuID":306,"sMenuName":"联合报表(图表)","iSerial":10,"iFormID":10000034,"sIcon":"","sAppStyle":"","iShowChart":0,"iIsUnion":1},{"iMenuID":303,"sMenuName":"销售员月销量(柱状)","iSerial":11,"iFormID":10000032,"sIcon":"","sAppStyle":"普通表格","iShowChart":1,"iIsUnion":0},{"iMenuID":304,"sMenuName":"销售员年销量(饼状)","iSerial":12,"iFormID":10000033,"sIcon":"","sAppStyle":"普通表格","iShowChart":1,"iIsUnion":0}]},{"iMenuID":"274","sMenuName":"年计划报表","sIcon":"","ChildMenus":[{"iMenuID":281,"sMenuName":"年计划执行情况","iSerial":1,"iFormID":27401,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":282,"sMenuName":"年计划人员执行","iSerial":2,"iFormID":27402,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0}]},{"iMenuID":"275","sMenuName":"周计划报表","sIcon":"","ChildMenus":[{"iMenuID":283,"sMenuName":"周计划列表","iSerial":1,"iFormID":1000041,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":284,"sMenuName":"周计划完成情况","iSerial":2,"iFormID":1000042,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0}]},{"iMenuID":"276","sMenuName":"客户分析","sIcon":"","ChildMenus":[{"iMenuID":285,"sMenuName":"客户分析","iSerial":1,"iFormID":27601,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0}]},{"iMenuID":"277","sMenuName":"拜访分析","sIcon":"","ChildMenus":[{"iMenuID":278,"sMenuName":"拜访分析","iSerial":1,"iFormID":27701,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0}]}]
     */

    private boolean success;
    private String message;
    private List<MenuBean> menus;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MenuBean> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuBean> menus) {
        this.menus = menus;
    }


}
