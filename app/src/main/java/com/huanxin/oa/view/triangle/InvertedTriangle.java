package com.huanxin.oa.view.triangle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.huanxin.oa.R;

public class InvertedTriangle extends View {
    Context context;
    Paint p;
    int paintColor;
    int striangleSrc;
    float srcScale;

    public InvertedTriangle(Context context) {
        this(context, null);
    }

    public InvertedTriangle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StriangleView);
        paintColor = typedArray.getColor(R.styleable.StriangleView_paintColor, Color.BLACK);
        striangleSrc = typedArray.getResourceId(R.styleable.StriangleView_striangleSrc, 0);
        srcScale = typedArray.getFloat(R.styleable.StriangleView_srcScale, 0);
        typedArray.recycle();
        p = new Paint();
        p.setColor(paintColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //实例化路径
        Path path = new Path();
        path.moveTo(0, 0);// 此点为多边形的起点
        path.lineTo(getWidth() / 2, getHeight());
        path.lineTo(getWidth(), 0);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);
        canvas.save();
    }
}
