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
            int width = titles.get(i).getIWidth();
            for (int j = 0; j < jsonArray.length(); j++) {
                String field = jsonArray.getJSONObject(j).optString(columnTitle.getSFieldsName());
                if (j == 0) {
                    FormModel item = new FormModel();
                    item.setParent(columnTitle.getIMerge() == 1 ? true : false);
                    item.setRow(j);
                    item.setCol(i);
                    item.setTitle(field);
                    item.setColumnWidth(width);
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
                        item.setColumnWidth(width);
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
        int widthTotal = getTotalWidth(titles);
        int widthUsed = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            int pid = 0;
            int marginLeft = 0;
            List<FormModel> rowData = new ArrayList<>();
            for (int j = 0; j < titles.size(); j++) {
                if (!titles.get(j).isChild()) {
                    String title = jsonArray.getJSONObject(i).getString(titles.get(j).getSFieldsName());
                    boolean isParent = titles.get(j).getIMerge() == 1 ? true : false;
                    if (isParent) {
                        if (i == 0) {
                            FormModel item = new FormModel(row, j, title, 1, isParent, pid, id);
                            widthUsed = titles.get(j).getIWidth();
                            item.setTotalWidth(widthTotal - widthUsed);
                            item.setColumnWidth(widthUsed);
                            compare.add(item);
                            items.add(item);
                            pid = id;
                            id = id + 1;
                            marginLeft = titles.get(j).getIWidth();
                        } else {
                            FormModel compareItem = compare.get(j);
                            if (title.equals(compareItem.getTitle()) && pid == compareItem.getPid()) {
                                items.get(items.indexOf(compareItem)).addSpanHeight();
                                pid = compareItem.getId();
                            } else {
                                row = row + 1;
                                FormModel item;
                                item = new FormModel(row, j, title, 1, isParent, pid, id);
                                widthUsed = j == 0 ? titles.get(j).getIWidth() : widthUsed + titles.get(j).getIWidth();
                                item.setTotalWidth(widthTotal - widthUsed);
                                item.setColumnWidth(titles.get(j).getIWidth());
                                compare.remove(j);
                                compare.add(j, item);
                                items.add(item);
                                pid = id;
                                id += 1;
                                marginLeft = titles.get(j).getIWidth();
                            }
                        }
                    } else {
                        FormModel item = new FormModel(row, j, title, 1, isParent);
                        item.setColumnWidth(titles.get(j).getIWidth());
                        item.setMarginLeft(marginLeft);
                        marginLeft = marginLeft + titles.get(j).getIWidth();
                        rowData.add(item);
                    }
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

    private static int getTotalWidth(List<FormBean.ReportColumnsBean> titles) {
        int width = 0;
        for (FormBean.ReportColumnsBean item : titles) {
            width = width + item.getIWidth();
        }
        return width;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public static List<FormModel> buildByRecursive(List<FormModel> treeNodes, int pos) {
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
            treeNode.sum(pos);
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
