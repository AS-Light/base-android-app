package jx.android.staff.binding.vm.base;

import android.graphics.drawable.Drawable;

import androidx.databinding.BaseObservable;

public class BaseViewModel extends BaseObservable {

    public class TextViewInfo {
        public Drawable background;
        public String text;
        public int visible;
        public boolean enable = true;
        public int textColor;

        public TextViewInfo setText(String text) {
            this.text = text;
            return this;
        }
    }
}
