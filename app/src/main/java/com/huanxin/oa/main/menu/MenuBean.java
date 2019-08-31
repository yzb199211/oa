package com.huanxin.oa.main.menu;

import java.util.List;

public  class MenuBean {
        /**
         * iMenuID : 248
         * sMenuName : 销售报表
         * sIcon :
         * ChildMenus : [{"iMenuID":249,"sMenuName":"销售订单列表","iSerial":0,"iFormID":10000021,"sIcon":"","sAppStyle":"树形表格","iShowChart":1,"iIsUnion":0},{"iMenuID":286,"sMenuName":"销售分析","iSerial":1,"iFormID":10000023,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":287,"sMenuName":"销售订单汇总","iSerial":2,"iFormID":10000024,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":290,"sMenuName":"销售订单","iSerial":3,"iFormID":10000025,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":291,"sMenuName":"无","iSerial":4,"iFormID":10000026,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":292,"sMenuName":"销售订单","iSerial":5,"iFormID":10000027,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":293,"sMenuName":"销售订单","iSerial":6,"iFormID":10000028,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":294,"sMenuName":"销售订单","iSerial":7,"iFormID":10000029,"sIcon":"","sAppStyle":"自定义","iShowChart":0,"iIsUnion":0},{"iMenuID":295,"sMenuName":"销售订单","iSerial":8,"iFormID":10000030,"sIcon":"","sAppStyle":"","iShowChart":0,"iIsUnion":0},{"iMenuID":302,"sMenuName":"联合报表(表格)","iSerial":9,"iFormID":10000031,"sIcon":"","sAppStyle":"","iShowChart":0,"iIsUnion":1},{"iMenuID":306,"sMenuName":"联合报表(图表)","iSerial":10,"iFormID":10000034,"sIcon":"","sAppStyle":"","iShowChart":0,"iIsUnion":1},{"iMenuID":303,"sMenuName":"销售员月销量(柱状)","iSerial":11,"iFormID":10000032,"sIcon":"","sAppStyle":"普通表格","iShowChart":1,"iIsUnion":0},{"iMenuID":304,"sMenuName":"销售员年销量(饼状)","iSerial":12,"iFormID":10000033,"sIcon":"","sAppStyle":"普通表格","iShowChart":1,"iIsUnion":0}]
         */

        private String iMenuID;
        private String sMenuName;
        private String sIcon;
        private List<MenuChildBean> ChildMenus;

        public String getIMenuID() {
            return iMenuID;
        }

        public void setIMenuID(String iMenuID) {
            this.iMenuID = iMenuID;
        }

        public String getSMenuName() {
            return sMenuName;
        }

        public void setSMenuName(String sMenuName) {
            this.sMenuName = sMenuName;
        }

        public String getSIcon() {
            return sIcon;
        }

        public void setSIcon(String sIcon) {
            this.sIcon = sIcon;
        }

        public List<MenuChildBean> getChildMenus() {
            return ChildMenus;
        }

        public void setChildMenus(List<MenuChildBean> ChildMenus) {
            this.ChildMenus = ChildMenus;
        }

 
    }