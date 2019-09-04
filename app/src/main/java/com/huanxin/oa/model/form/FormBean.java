package com.huanxin.oa.model.form;

import com.huanxin.oa.form.model.FormTitle;

import java.util.List;

public class FormBean {
    List<FormTitle> formTitles;
    List<FormOne> formOnes;
    List<FormModel> formModels;

    public List<FormTitle> getFormTitles() {
        return formTitles;
    }

    public void setFormTitles(List<FormTitle> formTitles) {
        this.formTitles = formTitles;
    }

    public List<FormOne> getFormOnes() {
        return formOnes;
    }

    public void setFormOnes(List<FormOne> formOnes) {
        this.formOnes = formOnes;
    }

    public List<FormModel> getFormModels() {
        return formModels;
    }

    public void setFormModels(List<FormModel> formModels) {
        this.formModels = formModels;
    }
}
