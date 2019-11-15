package com.huanxin.oa.form.merge;

import com.huanxin.oa.form.model.Form;
import com.huanxin.oa.form.model.FormBean;
import com.huanxin.oa.form.model.FormModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FormMergeUtil {

    public static List<List<FormModel>> initFormCol(JSONArray jsonArray, List<FormBean.ReportColumnsBean> titles) throws JSONException {
        List<List<FormModel>> columns = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            List<FormModel> column = new ArrayList<>();
            FormBean.ReportColumnsBean columnTitle = titles.get(i);
            for (int j = 0; j < jsonArray.length(); j++) {
                String field = jsonArray.getJSONObject(j).optString(columnTitle.getSFieldsName());
                if (j == 0) {
                    FormModel item = new FormModel();
                    item.setParent(columnTitle.getIMerge() == 1 ? true : false);
                    item.setRow(j);
                    item.setCol(i);
                    item.setTitle(field);
                    column.add(item);
                } else {
                    FormModel oldItem = column.get(column.size() - 1);
                    if (oldItem.getTitle().equals(field)) {
                        oldItem.addSpanHeight();
                    } else {
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

    public static List<FormModel> getFormColumns(JSONArray jsonArray, List<FormBean.ReportColumnsBean> titles) throws JSONException {
        List<FormModel> compare = new ArrayList<>();
        List<FormModel> items = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < jsonArray.length(); i++) {
            int pid = 0;
            List<FormModel> rowData = new ArrayList<>();
            for (int j = 0; j < titles.size(); j++) {
                String title = jsonArray.getJSONObject(i).getString(titles.get(j).getSFieldsName());
                boolean isParent = titles.get(j).getIMerge() == 1 ? true : false;
                if (isParent) {
                    if (i == 0) {
                        FormModel item = new FormModel(i, j, title, 1, isParent, pid, id);
                        compare.add(item);
                        items.add(item);
                        pid = id;
                        id = id + 1;
                    } else {
                        for (int k = 0; k < compare.size(); k++) {
                            FormModel compareItem = compare.get(k);
                            if (title.equals(compareItem.getTitle()) && pid == compareItem.getPid()) {
                                items.get(items.indexOf(compareItem)).addSpanHeight();
                                pid = compareItem.getId();
                            } else {
                                FormModel item;
                                item = new FormModel(i, j, title, 1, isParent, pid, id);
                                compare.remove(k);
                                compare.add(k, item);
                                items.add(item);
                                pid = id;
                                id += 1;
                            }
                        }
                    }
                } else {
                    FormModel item = new FormModel(i, j, title, 1, isParent);
                    rowData.add(item);
                }
            }
            if (items.get(items.size() - 1).getRowData() == null) {
                items.get(items.size() - 1).setRowData(new ArrayList<>());
            }
            items.get(items.size() - 1).getRowData().add(rowData);
        }
        return items;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public static List<FormModel> buildByRecursive(List<FormModel> treeNodes) {
        List<FormModel> trees = new ArrayList<>();
        for (FormModel treeNode : treeNodes) {
            if (0 == (treeNode.getPid())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public static FormModel findChildren(FormModel treeNode, List<FormModel> treeNodes) {
        for (FormModel it : treeNodes) {
            if (treeNode.getId() == (it.getPid())) {
                if (treeNode.getChild() == null) {
                    treeNode.setChild(new ArrayList<>());
                }
                treeNode.getChild().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

}
