package com.geek.libskin.skinbase;

import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Method;

/**
 * @ClassName: SkinReplace
 * @Author: 史大拿
 * @CreateDate: 1/3/23$ 10:56 AM$
 * TODO
 */
public enum SkinReplace {
    ANDROID_SRC("src") {
        @Override
        void loadResource(View view, SkinAttr attr) {
            SkinLog.i("szjANDROID_SRC:", view + "\tattr:" + attr);
            if (view instanceof ImageView) {
                /// 如果当前是隐藏状态，那么就返回
                if (view.getVisibility() == View.GONE || view.getVisibility() == View.INVISIBLE) {
                    return;
                }
                try {
                    // 有可能是drawable 或者 bitmap
                    if ("drawable".equals(attr.getType())) {
                        ((ImageView) view).setImageDrawable(SkinManager.getInstance().getDrawable(attr.getValue()));
                    } else if ("color".equals(attr.getType())) {
                        view.setBackgroundColor(SkinManager.getInstance().getColor(attr.getValue()));
                    } else {
                        ((ImageView) view).setImageResource(SkinManager.getInstance().getIdentifier(attr.getValue(), attr.getType()));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    SkinLog.e("换肤失败(src)" + SkinConfig.SKIN_ERROR_9 + ":name:" + view.getClass().getName() + "\tattr:" + attr);
                }
//                ((ImageView) view).setImageDrawable(SkinManager.getInstance().getDrawable(attr.getValue()));
            }
        }
    },
    ANDROID_TEXT("text") {
        @Override
        void loadResource(View view, SkinAttr attr) {
            SkinLog.i("szjANDROID_TEXT:", view);
            if (view instanceof TextView) {
                try {
                    ((TextView) view).setText(SkinManager.getInstance().getString(attr.getValue()));
                } catch (Exception e) {
                    e.printStackTrace();
                    SkinLog.e("换肤失败(text)" + SkinConfig.SKIN_ERROR_9 + ":name:" + view.getClass().getName() + "\tattr:" + attr);
                }
            }
        }
    },
    ANDROID_TEXT_COLOR("textColor") {
        @Override
        void loadResource(View view, SkinAttr attr) {
            SkinLog.i("szjANDROID_TEXT_COLOR:", view + "\tattr:" + attr);
            if (view instanceof TextView) {
                try {
                    if ("color".equals(attr.getType())) {
                        ((TextView) view).setTextColor(SkinManager.getInstance().getColor(attr.getValue()));
                    } else if ("drawable".equals(attr.getType())) {
                        ((TextView) view).setTextColor(SkinManager.getInstance().getIdentifier(attr.getValue(), attr.getType()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    SkinLog.e("换肤失败(Color)" + SkinConfig.SKIN_ERROR_9 + ":name:" + view.getClass().getName() + "\tattr:" + attr);
                }
            }
        }
    },
    ANDROID_TEXT_SIZE("textSize") {
        @Override
        void loadResource(View view, SkinAttr attr) {
            SkinLog.i("szjANDROID_TEXT_SIZE:", view + "\tattr:" + attr);
            if (view instanceof TextView) {
                try {
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, SkinManager.getInstance().getFontSize(attr.getValue()));
                } catch (Exception e) {
                    e.printStackTrace();
                    SkinLog.e("换肤失败(background)" + SkinConfig.SKIN_ERROR_9 + ":name:" + view.getClass().getName() + "\tattr:" + attr);
                }

            }
        }
    },
    ANDROID_BACKGROUND("background") {
        @Override
        void loadResource(View view, SkinAttr attr) {
            SkinLog.i("szjANDROID_BACKGROUND:", view + "\tattr:" + attr);
            try {
                if ("color".equals(attr.getType())) {
                    int color = SkinManager.getInstance().getColor(attr.getValue());
                    view.setBackgroundColor(color);
                } else if ("drawable".equals(attr.getType())) {
                    /// 如果没有获取到color,那么就说明background是drawable，那么就尝试获取drawable
                    Drawable drawable = SkinManager.getInstance().getDrawable(attr.getValue());
                    view.setBackground(drawable);
                }
            } catch (Exception e) {
                e.printStackTrace();
                SkinLog.e("换肤失败(background)" + SkinConfig.SKIN_ERROR_9 + ":name:" + view.getClass().getName() + "\tattr:" + attr);
            }
        }
    },
    ANDROID_FONT_FAMILY("fontFamily") {
        @Override
        void loadResource(View view, SkinAttr attr) {
            SkinLog.i("szjANDROID_FONT_FAMILY:", view + "\tattr:" + attr);
            try {
                if (view instanceof TextView) {
                    TextView tmpView = (TextView) view;
                    tmpView.setTypeface(SkinManager.getInstance().getFont(attr.getValue()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    },
    ANDROID_DRAWABLE_LEFT("drawableLeft") {
        @Override
        void loadResource(View view, SkinAttr attr) {
            if (view instanceof TextView) {
                drawableView((TextView) view, attr);
            }
        }
    },
    ANDROID_DRAWABLE_RIGHT("drawableRight") {
        @Override
        void loadResource(View view, SkinAttr attr) {
            if (view instanceof TextView) {
                drawableView((TextView) view, attr);
            }
        }
    },
    ANDROID_DRAWABLE_Top("drawableTop") {
        @Override
        void loadResource(View view, SkinAttr attr) {
            if (view instanceof TextView) {
                drawableView((TextView) view, attr);
            }
        }
    },
    ANDROID_DRAWABLE_Bottom("drawableBottom") {
        @Override
        void loadResource(View view, SkinAttr attr) {
            if (view instanceof TextView) {
                drawableView((TextView) view, attr);
            }
        }
    },
    CUSTOM_SKIN_VIEW_BACKGROUND("skin_background") {
        @Override
        void loadResource(View view, SkinAttr attr) {
            int color = SkinManager.getInstance().getColor(attr.getValue());
            setCustomAttr(view, "setBackground", new SkinReflectionMethod(int.class, color));
        }
    },
    CUSTOM_SKIN_VIEW_FONT_COLOR("skin_font_color") {
        @Override
        void loadResource(View view, SkinAttr attr) {
            int color = SkinManager.getInstance().getColor(attr.getValue());
            setCustomAttr(view, "setTextColor", new SkinReflectionMethod(int.class, color));
        }
    },
    CUSTOM_SKIN_VIEW_FONT_SIZE("skin_font_size") {
        @Override
        void loadResource(View view, SkinAttr attr) {
            float fontSize = SkinManager.getInstance().getFontSize(attr.getValue());
            setCustomAttr(view, "setTextSize", new SkinReflectionMethod(float.class, fontSize));
        }
    },
    CUSTOM_SKIN_VIEW_FONT_TEXT("skin_text") {
        @Override
        void loadResource(View view, SkinAttr attr) {
            String text = SkinManager.getInstance().getString(attr.getValue());
            setCustomAttr(view, "setText", new SkinReflectionMethod(String.class, text));
        }
    };

    public void drawableView(TextView view, SkinAttr attr) {

//        SkinLog.i("szjDrawableView", attr.toString());
        String key = attr.getKey();
        Drawable drawable = SkinManager.getInstance().getDrawable(attr.getValue());

        if (drawable == null) {
            return;
        }
        Drawable[] drawables = view.getCompoundDrawables();
        if ("drawableLeft".equals(key)) {
            view.setCompoundDrawablesWithIntrinsicBounds(drawable, drawables[1], drawables[2], drawables[3]);
        } else if ("drawableRight".equals(key)) {
            view.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawable, drawables[3]);
        } else if ("drawableTop".equals(key)) {
            view.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawable, drawables[2], drawables[3]);
        } else if ("drawableBottom".equals(key)) {
            view.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawable);
        }
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/4/23 8:07 PM
     * TODO 自定义反射,反射具体方法属性
     * @param view: 需要反射的对象
     * @param methodName: 反射的方法名字
     * @param SkinReflectionMethod: 反射具体数据 [类型和参数]
     */
    public void setCustomAttr(View view, String methodName, SkinReflectionMethod... data) {
        try {
            Class<?>[] cls = new Class<?>[data.length];
            Object[] objects = new Object[data.length];
            for (int i = 0; i < data.length; i++) {
                cls[i] = data[i].getCls();
                objects[i] = data[i].getObj();
            }
            Method method = view.getClass().getDeclaredMethod(methodName, cls);
            method.setAccessible(true);
            method.invoke(view, objects);
        } catch (Exception e) {
            e.printStackTrace();
            SkinLog.e("反射失败;" + e.getMessage() + "\t" + SkinConfig.SKIN_ERROR_7);
        }
    }


    private final String mName;

    SkinReplace(String value) {
        mName = value;
    }

    public String getName() {
        return mName;
    }

    /*
     * 作者:史大拿
     * 创建时间: 1/3/23 2:30 PM
     * TODO
     * @value: 要加载的参数
     */
    abstract void loadResource(View view, SkinAttr value);
}
