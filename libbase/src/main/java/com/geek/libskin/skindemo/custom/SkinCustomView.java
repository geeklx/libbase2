package com.geek.libskin.skindemo.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.geek.libbase.R;


/**
 * @ClassName: SkinCustomView
 * @Author: 史大拿
 * @CreateDate: 1/4/23$ 4:53 PM$
 * TODO
 */
public class SkinCustomView extends View {

    private final Paint mPaint;
    private int mBackground;
    private int mTextColor;
    String mText;
    private float mFontSize;

    public SkinCustomView(Context context) {
        this(context, null);
    }

    public SkinCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinCustomView);

        // 读取自定义属性
        mBackground = typedArray.getColor(R.styleable.SkinCustomView_skin_background, Color.RED);
        mTextColor = typedArray.getColor(R.styleable.SkinCustomView_skin_font_color, Color.BLACK);
        mFontSize = typedArray.getDimension(R.styleable.SkinCustomView_skin_font_size, getResources().getDisplayMetrics().density * 18);
        mText = typedArray.getString(R.styleable.SkinCustomView_skin_text);

        mPaint.setTextSize(mFontSize);
        mPaint.setColor(mTextColor);
        typedArray.recycle();
    }

    public void setText(String text) {
        mText = text;
        invalidate();
    }

    public void setBackground(int background) {
        mBackground = background;
        invalidate();
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        if (mPaint != null) {
            mPaint.setColor(mTextColor);
        }
        invalidate();
    }

    public void setTextSize(float textSize) {
        mFontSize = textSize;
        if (mPaint != null) {
            mPaint.setTextSize(mFontSize);
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = (int) (getResources().getDisplayMetrics().density * 100);

        int w = resolveSize(width, widthMeasureSpec);
        int h = resolveSize(height, heightMeasureSpec);
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBackground);

        // 文字宽度
        float textWidth = mPaint.measureText(mText);
        // 文字高度
        float textHeight = mPaint.descent() + mPaint.ascent();

        float x = getWidth() / 2f - textWidth / 2f;
        // 最底部绘制
        float y = getHeight() / 2f - textHeight / 2f;

        canvas.drawText(mText, x, y, mPaint);
    }
}
