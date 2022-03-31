package com.geek.libbase.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

// 左边图标+换行textview
//            SpannableString spanString = new SpannableString("icon");
//                    Drawable tvDrawable = ContextCompat.getDrawable(this, R.drawable.icon_zhibo1);
//                    tvDrawable.setBounds(0, 0, tvDrawable.getMinimumWidth(), tvDrawable.getMinimumHeight());
////        MyImageSpan span = new MyImageSpan(tvDrawable, ImageSpan.ALIGN_BASELINE);
//                    CenteredImageSpan span = new CenteredImageSpan(this, R.drawable.icon_zhibo1);
//                    spanString.setSpan(span, 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
public class CenteredImageSpan extends ImageSpan {

    public CenteredImageSpan(Context context, final int drawableRes) {
        super(context, drawableRes);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, Paint paint) {
        // image to draw
        Drawable b = getDrawable();
        // font metrics of text to be replaced
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int transY = (y + fm.descent + y + fm.ascent) / 2
                - b.getBounds().bottom / 2;

        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }
}