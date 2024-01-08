package com.geek.libusers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.WeakHashMap;

public class UserSpUtils {

    public static final String NATION = "UserSpUtils";
    private static final Object mContent = new Object();

    private SharedPreferences mSp;
    private WeakHashMap<OnSpChangeListener, Object> mListeners = new WeakHashMap<>();

    private static UserSpUtils instance;

    public UserSpUtils(Context ctx) {
        this(ctx, NATION);
    }

    public UserSpUtils(Context ctx, String file) {
        mSp = ctx.getSharedPreferences(file, Context.MODE_PRIVATE);
    }

    public static UserSpUtils get(Context ctx){
        if (instance==null) {
            instance = new UserSpUtils(ctx);
        }
        return instance;
    }

    /**
     * get
     *
     * @param key
     * @param defValue
     * @return
     */
    public Object get(String key, Object defValue) {
        if (defValue instanceof Integer) {
            return mSp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return mSp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return mSp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return mSp.getLong(key, (Long) defValue);
        } else if (defValue instanceof Set<?>) {
            return mSp.getStringSet(key, (Set<String>) defValue);
        }
        return mSp.getString(key, (String) defValue);
    }

//    /**
//     * @param key
//     * @param defValue
//     * @param <T>
//     * @return
//     */
//    public <T> T get(String key, T defValue) {
//        if (defValue instanceof Integer) {
//            return (T) (Object) mSp.getInt(key, (Integer) defValue);
//        } else if (defValue instanceof Boolean) {
//            return (T) (Object) mSp.getBoolean(key, (Boolean) defValue);
//        } else if (defValue instanceof Float) {
//            return (T) (Object) mSp.getFloat(key, (Float) defValue);
//        } else if (defValue instanceof Long) {
//            return (T) (Object) mSp.getLong(key, (Long) defValue);
//        } else if (defValue instanceof Set<?>) {
//            return (T) (Object) mSp.getStringSet(key, (Set<String>) defValue);
//        }
//        return (T) (Object) mSp.getString(key, (String) defValue);
//    }

    /**
     * put key->value and apply
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        SharedPreferences.Editor editor = mSp.edit();
        putWithoutApply(editor, key, value);
        apply(editor);
    }

    /**
     * put object
     *
     * @param obj
     */
    public void put(Object obj) {
        SharedPreferences.Editor editor = mSp.edit();
        for (Class<?> klass = obj.getClass(); !(Object.class.equals(klass));
             klass = klass.getSuperclass()) {
            Field[] fields = klass.getDeclaredFields();
            for (Field item : fields) {
                try {
                    putWithoutApply(editor, item.getName(), item.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        apply(editor);
    }

    public void putWithoutApply(SharedPreferences.Editor editor, String key, Object value) {
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Set<?>) {
            editor.putStringSet(key, (Set<String>) value);
        } else {
            editor.putString(key, (String) value);
        }
    }

    public void clear() {
        clear(false);
    }

    public void clear(boolean invokeListener) {
        apply(mSp.edit().clear());
        if (!invokeListener) { return;}
        invokeListener();
    }

    private void invokeListener() {
        Set<OnSpChangeListener> keys = mListeners.keySet();
        for (OnSpChangeListener li : keys) {
            if (li != null) { li.onChange();}
        }
    }

    public void remove(String key) {
        apply(mSp.edit().remove(key));
    }

    public void apply(SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT >= 9) {
            editor.apply();
        } else {
            editor.commit();
        }

        invokeListener();
    }


    public void registerListener(OnSpChangeListener li) {
        mListeners.put(li, mContent);
    }

    public void unregisterListener(OnSpChangeListener li) {
        mListeners.remove(li);
    }

    public static class SpExt {
        private UserSpUtils mSpUtils;
        private SharedPreferences.Editor mEditor;

        public SpExt(UserSpUtils spUtils) {
            mSpUtils = spUtils;
            mEditor = mSpUtils.mSp.edit();
        }

        /**
         * put key->value and do not apply
         * @param key
         * @param value
         */
        public SpExt add(String key, Object value) {
            mSpUtils.putWithoutApply(mEditor, key, value);
            return this;
        }

        public void apply() {
            mSpUtils.apply(mEditor);
            release();
        }

        public void release() {
            mEditor = null;
            mSpUtils = null;
        }
    }

    public interface OnSpChangeListener {
        void onChange();
    }
}
