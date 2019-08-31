package com.huanxin.oa.view.form;

import android.util.Log;
import android.view.Gravity;
import android.widget.GridLayout;

import com.huanxin.oa.model.form.FormModel;
import com.huanxin.oa.model.form.FormOne;
import com.huanxin.oa.model.form.FormTwo;
import com.huanxin.oa.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class GridUtil {
    private final static String TAG = "GridUtil";
    private static int count = 0;

    public static GridLayout.LayoutParams getParams(int row, int rowSpan, int column, int columnSpan) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(row, rowSpan, GridLayout.FILL);
        params.columnSpec = GridLayout.spec(column, columnSpan, GridLayout.FILL);
        params.setGravity(Gravity.CENTER);
        return params;
    }

    /**
     * 获取行数
     *
     * @param formOnes
     * @return
     */
    public static int getRowCount(List<FormOne> formOnes) {
        int maxRow = 0;
        if (formOnes.size() > 0) {
            maxRow++;
            for (int i = 0; i < formOnes.size(); i++) {
                maxRow++;
                Log.e(TAG, "getRowCount: " + "i" + i);
                List<FormTwo> formTwos = formOnes.get(i).getFormTwos();
                if (formTwos != null & formTwos.size() > 0) {
                    for (int j = 0; j < formTwos.size(); j++) {
                        Log.e(TAG, "getRowCount: " + "j" + j);
                        maxRow++;
                        if (formTwos.get(j).getFormThrees() != null)
                            maxRow = maxRow + formTwos.get(j).getFormThrees().size();
                    }
                }
            }
        }
        return maxRow;
    }

    public static int getRowSpan(List<FormModel> formModels) {
//        Log.e(TAG, formModels.size() + "");
//        int count = 0;
        for (int i = 0; i < formModels.size(); i++) {
            if (StringUtil.isNotEmpty(formModels.get(i).getText())) {
                Log.e(TAG, formModels.get(i).getFormModels().size() + "");
                getRowSpan(formModels.get(i).getFormModels());
            } else {
                Log.e(TAG, formModels.get(i).getFormModels().size() + "");
                count += formModels.get(i).getFormModels().size();
                Log.e(TAG, "count:" + formModels.get(i).getFormModels().size() + "");
            }
        }
        return count;
    }

    public static List<FormModel> getFromModel() {
        List<FormModel> formModels = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            FormModel formModel = new FormModel();
            formModel.setText(i + "");
            List<FormModel> formModels1 = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                FormModel formModel11 = new FormModel();

                formModels1.add(formModel11);
            }
            formModel.setFormModels(formModels1);
            formModels.add(formModel);
        }
        return formModels;
    }

    public static List<FormOne> getFrom() {
        List<FormOne> formOnes = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<FormTwo> formTwos = new ArrayList<>();
            FormOne formOne = new FormOne();
            formOne.setText(i + "," + 0);
            for (int j = 0; j < 2; j++) {
                FormTwo formTwo = new FormTwo();
                formTwo.setText(j + "," + 1);
                formTwos.add(formTwo);
            }
            formOne.setFormTwos(formTwos);
            formOnes.add(formOne);
        }
        return formOnes;
    }
}
