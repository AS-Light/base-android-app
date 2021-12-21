package jx.android.staff.utils;

import jx.android.staff.R;

import java.lang.reflect.Field;

public class ResUtils {
    public static int getDrawableIdWithName(String name) {
        Class mipmap = R.drawable.class;
        try {
            Field field = mipmap.getField(name);
            int resId = field.getInt(name);
            return resId;
        } catch (NoSuchFieldException e) {//如果没有在"drawable"下找到imageName,将会返回0
            return 0;
        } catch (IllegalAccessException e) {
            return 0;
        }

    }
}
