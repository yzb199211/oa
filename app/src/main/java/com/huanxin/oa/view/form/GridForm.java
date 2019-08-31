package com.huanxin.oa.view.form;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;

import com.huanxin.oa.model.form.FormBean;
import com.huanxin.oa.model.form.FormModel;
import com.huanxin.oa.utils.StringUtil;

import java.util.List;

public class GridForm extends GridLayout {
    private final static String TAG = "GridForm";
    Context context;
    int maxRow;
    int maxColumn;
    int currentRow = 0;
    int currentColumn = 0;
    int columnSpan = 1;
    List<FormBean> formBeans;

    public GridForm(Context context) {
        this(context, null);
    }

    public GridForm(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setFormBeans(List<FormBean> formBeans) {
        this.formBeans = formBeans;
        try {
            maxColumn = formBeans.get(0).getFormTitles().size();
            maxRow = GridUtil.getRowSpan(formBeans.get(0).getFormModels());
            setRowCount(maxRow + 1);
            setRowCount(maxColumn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setView1(List<FormBean> formBeans) {
//        setFormTitle(formBeans.get(0).getFormTitles());
        initData(formBeans.get(0).getFormModels());
    }

    private void initData(List<FormModel> formModels) {
        for (int i = 0; i < formModels.size(); i++) {//遍历外层数组，根据text是否有数据：true 还有子类数据，继续遍历下一层；false表示该数据为List<<Object>>型数据，直接加载数据
            if (TextUtils.isEmpty(formModels.get(i).getText())) {//判断是否为List<List<Object>>型数据
                currentRow++;//换行
                List<FormModel> formChilds = formModels.get(i).getFormModels();//获取List<object>
                for (int j = 0; j < formChilds.size(); j++) {//遍历单行数据
                    addItem(currentRow, j + currentColumn, formChilds.get(j).getSpan(), 1, formChilds.get(j).getText());//添加单元格
                }
            } else {

            }
        }
    }

    /**
     * 加载数据
     *
     * @param formModels 数据列表
     * @param column     当前在第几列
     * @param str        上一级内容
     */
    private void setView(List<FormModel> formModels, int column, String str) {
        for (int i = 0; i < formModels.size(); i++) {
            int row = currentRow;
            if (StringUtil.isNotEmpty(formModels.get(i).getText())) {
                column++;
            } else {
                List<FormModel> itemList = formModels.get(i).getFormModels();
                for (int size = 0; size < itemList.size(); size++) {
                    addItem(currentRow, column + size, 1, 1, itemList.get(size).getText());
                    currentRow++;
                }
                /*设置上一级内容*/
                if (StringUtil.isNotEmpty(str)) {
                    addItem(row, column - 1, 1, itemList.size(), str);
                }
            }
        }
    }

//    public void setiew1(List<FormModel> formModels) {
//
//        for (int i = 0; i < formModels.size(); i++) {
//            if (StringUtil.isNotEmpty(formModels.get(i).getText())) {
//                Log.e(TAG, formModels.get(i).getFormModels().size() + "");
//                setiew1(formModels.get(i).getFormModels());
//            } else {
//                Log.e(TAG, formModels.get(i).getFormModels().size() + "");
//                count += formModels.get(i).getFormModels().size();
//                Log.e(TAG, "count:" + formModels.get(i).getFormModels().size() + "");
//            }
//        }
//        return count;
//    }

    private void getSpan(List<FormModel> formModels) {

    }


    private void addItem(int row, int col, int columnSpan, int rowSpan, String text) {
        TextView textView = new TextView(context);
        textView.setText(text);
        GridLayout.Spec rowSpec = GridLayout.spec(row,1.0f);     //设置它的行和列
        GridLayout.Spec columnSpec = GridLayout.spec(col,1.0f);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
        params.setGravity(Gravity.CENTER);
        addView(textView, params);
    }

}
