package com.huanxin.oa.model.login;

public class AppInfoBean {

    /**
     * sAppVersion :
     * sAppApk :
     */

    private int iAppVersion;
    private String sAppApk;

    public int getSAppVersion() {
        return iAppVersion;
    }

    public void setSAppVersion(int sAppVersion) {
        this.iAppVersion = sAppVersion;
    }

    public String getSAppApk() {
        return sAppApk;
    }

    public void setSAppApk(String sAppApk) {
        this.sAppApk = sAppApk;
    }
}
