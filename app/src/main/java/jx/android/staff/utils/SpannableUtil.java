package jx.android.staff.utils;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.ParcelableSpan;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;

import jx.android.staff.app.AppContext;

import java.util.List;

/**
 * Created by huanghe on 16/8/1.
 */
public class SpannableUtil {

    public static Spannable buildSpannable(List<StringWithStyle> strs) {
        StringBuilder builder = new StringBuilder();
        for (StringWithStyle str : strs) {
            str.start = builder.length();
            builder.append(str.mStr);
            str.end = builder.length();
        }

        Spannable spannable = new SpannableString(builder.toString());
        for (StringWithStyle tempLine : strs) {
            spannable.setSpan(
                    buildTextAppearanceSpannable(tempLine),
                    tempLine.start,
                    tempLine.end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (tempLine.showUnderLine) {
                spannable.setSpan(
                        new UnderlineSpan(),
                        tempLine.start,
                        tempLine.end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        return spannable;
    }

    public static ParcelableSpan buildTextAppearanceSpannable(StringWithStyle str) {
        ColorStateList csl = null;
        if (str.mColorId > 0) {
            csl = AppContext.resources().getColorStateList(str.mColorId);
        }

        return new TextAppearanceSpan("default", str.mTypeface, str.mSize, csl, null);
    }

    public static class StringWithStyle {
        public String mStr;
        public int mColorId = -1;
        public int mSize = -1;
        public int mTypeface = Typeface.NORMAL;
        public boolean showUnderLine = false;

        public StringWithStyle(String str) {
            mStr = str;
        }

        public StringWithStyle setColor(int colorId) {
            mColorId = colorId;
            return this;
        }

        public StringWithStyle setSize(int size) {
            mSize = size;
            return this;
        }

        public StringWithStyle setTypeface(int typeface) {
            mTypeface = typeface;
            return this;
        }

        public StringWithStyle showUnderLine(boolean show) {
            showUnderLine = show;
            return this;
        }

        public int start;
        public int end;

    }

    public static SpannableStringBuilder setNumColor(String str, int color) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (a >= '0' && a <= '9') {
                style.setSpan(new ForegroundColorSpan(color), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return style;
    }
}
