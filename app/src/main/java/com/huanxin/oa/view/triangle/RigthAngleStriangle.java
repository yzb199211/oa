package com.huanxin.oa.view.triangle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.huanxin.oa.R;

public class RigthAngleStriangle extends View {
    Paint p;
    int paintColor;
    int striangleSrc;
    float srcScale;
    Bitmap striangleBitmap;
    Context context;


    public RigthAngleStriangle(Context context) {
        super(context);
    }

    public RigthAngleStriangle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }


    public RigthAngleStriangle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StriangleView);
        paintColor = typedArray.getColor(R.styleable.StriangleView_paintColor, Color.BLACK);
        striangleSrc = typedArray.getResourceId(R.styleable.StriangleView_striangleSrc, 0);
        srcScale = typedArray.getFloat(R.styleable.StriangleView_srcScale, 0);
        typedArray.recycle();
        p = new Paint();
        p.setColor(paintColor);
        if (striangleSrc != 0) {
            striangleBitmap = BitmapFactory.decodeResource(context.getResources(), striangleSrc);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //实例化路径
        Path path = new Path();
        path.moveTo(getWidth(), 0);// 此点为多边形的起点
        path.lineTo(0, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);
        canvas.save();
        Matrix matrix = new Matrix();
        matrix.setTranslate(getWidth() * 1 / 2, getHeight() * 1 / 2);
        matrix.preScale(srcScale, srcScale);
        canvas.drawBitmap(striangleBitmap, matrix, null);
        canvas.restore();
    }

    /**
     * 设置paint颜色
     *
     * @param paintColor
     */
    public void setAngleBackgroundColor(int paintColor) {
        this.paintColor = paintColor;
        p.setColor(paintColor);
    }

    public void setAngleSrc(int striangleSrc) {
        this.striangleSrc = striangleSrc;
        if (striangleSrc != 0) {
            striangleBitmap = BitmapFactory.decodeResource(context.getResources(), striangleSrc);
        }
    }
}
