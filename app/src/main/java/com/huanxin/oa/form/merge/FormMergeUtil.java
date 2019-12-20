package com.huanxin.oa.form.merge;

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
        int row = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            int pid = 0;
            List<FormModel> rowData = new ArrayList<>();
            for (int j = 0; j < titles.size(); j++) {
                String title = jsonArray.getJSONObject(i).getString(titles.get(j).getSFieldsName());
                boolean isParent = titles.get(j).getIMerge() == 1 ? true : false;
                if (isParent) {
                    if (i == 0) {
                        FormModel item = new FormModel(row, j, title, 1, isParent, pid, id);
                        compare.add(item);
                        items.add(item);
                        pid = id;
                        id = id + 1;
                    } else {
                        FormModel compareItem = compare.get(j);
                        if (title.equals(compareItem.getTitle()) && pid == compareItem.getPid()) {
                            items.get(items.indexOf(compareItem)).addSpanHeight();
                            pid = compareItem.getId();
                        } else {
                            row = row + 1;
                            FormModel item;
                            item = new FormModel(row, j, title, 1, isParent, pid, id);
                            compare.remove(j);
                            compare.add(j, item);
                            items.add(item);
                            pid = id;
                            id += 1;
                        }

                    }
                } else {
                    FormModel item = new FormModel(row, j, title, 1, isParent);
                    rowData.add(item);
                }
            }
            row = row + 1;
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
            if (treeNode.getPid() == 0) {
                trees.add(treeNode);
            }
        }
        for (FormModel treeNode : treeNodes) {
            toTreeChild(trees, treeNode);
        }
        for (FormModel treeNode : trees) {
            treeNode.getSpanHeightTotal();
        }
        return trees;
    }

    private static void toTreeChild(List<FormModel> trees, FormModel treeNode) {
        for (FormModel node : trees) {
            if (treeNode.getPid() == node.getId()) {
                if (node.getChild() == null) {
                    node.setChild(new ArrayList<>());
                }
                if (!node.getChild().contains(treeNode))
                    node.getChild().add(treeNode);
            }
            if (node.getChild() != null && !node.getChild().contains(treeNode)) {
                toTreeChild(node.getChild(), treeNode);
            }
        }
    }

}
