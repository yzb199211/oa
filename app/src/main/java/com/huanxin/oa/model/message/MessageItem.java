package com.huanxin.oa.model.message;

public  class MessageItem {
            /**
             * iRecNo : 10
             * sTitle : 你有25个订单未提交
             * sFilters : iOrderType=0 and (sUserID='master' or iStatus>1)
             * sAppMenuID : 
             * sImageUrl : 
             */

            private String iRecNo;
            private String sTitle;
            private String sFilters;
            private String sAppMenuID;
            private String sImageUrl;

            public String getIRecNo() {
                return iRecNo;
            }

            public void setIRecNo(String iRecNo) {
                this.iRecNo = iRecNo;
            }

            public String getSTitle() {
                return sTitle;
            }

            public void setSTitle(String sTitle) {
                this.sTitle = sTitle;
            }

            public String getSFilters() {
                return sFilters;
            }

            public void setSFilters(String sFilters) {
                this.sFilters = sFilters;
            }

            public String getSAppMenuID() {
                return sAppMenuID;
            }

            public void setSAppMenuID(String sAppMenuID) {
                this.sAppMenuID = sAppMenuID;
            }

            public String getSImageUrl() {
                return sImageUrl;
            }

            public void setSImageUrl(String sImageUrl) {
                this.sImageUrl = sImageUrl;
            }
        }