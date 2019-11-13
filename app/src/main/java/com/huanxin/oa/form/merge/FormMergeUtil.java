package com.huanxin.oa.form.merge;

import com.huanxin.oa.form.model.FormBean;
import com.huanxin.oa.form.model.FormModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FormMergeUtil {
    public static List<List<FormModel>> initFormData(JSONArray jsonArray, List<FormBean.ReportColumnsBean> titles) throws JSONException {
        List<List<FormModel>> columns = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            List<FormModel> column = new ArrayList<>();
            FormBean.ReportColumnsBean columnTitle = titles.get(i);
            for (int j = 0; j < jsonArray.length(); j++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(j);
                String field = jsonArray.getJSONObject(j).optString(columnTitle.getSFieldsName());
                if (j == 0) {
                    FormModel item = new FormModel();
                    item.setParent(columnTitle.getIMerge() == 1 ? true : false);
                    item.setRow(j);
                    item.setCol(i);
                    item.setTitle(field);
                    column.add(item);
                } else {
                    FormModel oldItem = column.get(column.size()-1);
                    if (oldItem.getTitle().equals(field)) {
                        oldItem.addSpanHeight();
                    }else {
                        FormModel item = new FormModel();
                        item.setParent(columnTitle.getIMerge() == 1 ? true : false);
                        item.setRow(j);
                        item.setCol(i);
                        item.setTitle(field);
                        column.add(item);
                    }
                }
            }
            columns.add(column);
        }
        return columns;
    }

}
