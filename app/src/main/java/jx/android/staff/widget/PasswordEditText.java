package jx.android.staff.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jx.android.staff.R;

import jx.android.staff.utils.Dip2Px;

/**
 * Created by ywl on 2016/7/10.
 */
public class PasswordEditText extends RelativeLayout {

    private EditText editText; //文本编辑框
    private Context context;

    private LinearLayout linearLayout; //文本密码的文本
    private TextView[] textViews; //文本数组

    private int mBackgroundResId;
    private int mTextCount;
    private int mHideTextCount;
    private int mTextColor;
    private int mStrokeColor;
    private float mTextSize;
    private float mStrokeWidth;

    private OnTextFinishListener onTextFinishListener;


    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
            mBackgroundResId = typedArray.getResourceId(R.styleable.PasswordEditText_petBackground, R.color.transparent);
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.PasswordEditText_petTextSize, Dip2Px.dip2px(12));
            mTextColor = typedArray.getColor(R.styleable.PasswordEditText_petTextColor, Color.parseColor("#66000000"));
            mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.PasswordEditText_petStrokeWidth, 1);
            mStrokeColor = typedArray.getColor(R.styleable.PasswordEditText_petStrokeColor, Color.parseColor("#66000000"));
            mTextCount = typedArray.getInteger(R.styleable.PasswordEditText_petTextCount, 6);
            mHideTextCount = typedArray.getInteger(R.styleable.PasswordEditText_petHideTextCount, 6);

            initEdit(mBackgroundResId, mTextCount);
            initShowInput(mBackgroundResId, mTextCount, mHideTextCount, mStrokeWidth, mStrokeColor, mTextColor, mTextSize);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
    }

    private void initEdit(int backgroundResId, int textCount) {
        editText = new EditText(context);
        editText.setBackgroundResource(backgroundResId);
        editText.setCursorVisible(false);
        editText.setTextSize(0);
        editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(textCount)});
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Editable etext = editText.getText();
                Selection.setSelection(etext, etext.length());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setTextData(s);
                if (s.length() == textCount) {
                    if (onTextFinishListener != null) {
                        onTextFinishListener.onFinish(s.toString().trim());
                    }
                }
            }
        });
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        addView(editText, lp);

    }

    /**
     * @param backgroundResId 背景drawable
     * @param textCount       密码长度
     * @param strokeWidth     分割线宽度
     * @param strokeColor     分割线颜色
     * @param textColor       密码字体颜色
     * @param textSize        密码字体大小
     */
    public void initShowInput(int backgroundResId, int textCount, int hideTextCount, float strokeWidth, int strokeColor, int textColor, float textSize) {
        //添加密码框父布局
        linearLayout = new LinearLayout(context);
        linearLayout.setBackgroundResource(backgroundResId);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(linearLayout);

        //添加密码框
        textViews = new TextView[textCount];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        params.weight = 1;
        params.gravity = Gravity.CENTER;

        int showTextCount = textCount - hideTextCount;
        int hideTextCountStart = showTextCount / 2;
        int hideTextCountEnd = hideTextCountStart + hideTextCount;
        LinearLayout.LayoutParams lineLayoutParam = new LinearLayout.LayoutParams((int) strokeWidth, LayoutParams.MATCH_PARENT);
        for (int i = 0; i < textViews.length; i++) {
            final int index = i;
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textViews[i] = textView;
            textViews[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            textViews[i].setTextColor(textColor);
            if (i >= hideTextCountStart && i <= hideTextCountEnd) {
                textViews[i].setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);
            } else {
                textViews[i].setInputType(InputType.TYPE_CLASS_NUMBER);
            }
            linearLayout.addView(textView, params);

            if (i < textViews.length - 1) {
                View view = new View(context);
                view.setBackgroundColor(strokeColor);
                linearLayout.addView(view, lineLayoutParam);
            }
        }
    }

    /**
     * 是否显示明文
     *
     * @param showPwd
     */
    public void setShowPwd(boolean showPwd) {
        int length = textViews.length;
        for (int i = 0; i < length; i++) {
            if (showPwd) {
                textViews[i].setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                textViews[i].setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        }
    }

    /**
     * 设置显示类型
     *
     * @param type
     */
    public void setInputType(int type) {
        int length = textViews.length;
        for (int i = 0; i < length; i++) {
            textViews[i].setInputType(type);
        }
    }

    /**
     * 清除文本框
     */
    public void clearText() {
        editText.setText("");
        for (int i = 0; i < mTextCount; i++) {
            textViews[i].setText("");
        }
    }

    public void setOnTextFinishListener(OnTextFinishListener onTextFinishListener) {
        this.onTextFinishListener = onTextFinishListener;
    }

    /**
     * 根据输入字符，显示密码个数
     *
     * @param s
     */
    public void setTextData(Editable s) {
        if (s.length() > 0) {
            int length = s.length();
            for (int i = 0; i < mTextCount; i++) {
                if (i < length) {
                    for (int j = 0; j < length; j++) {
                        char ch = s.charAt(j);
                        textViews[j].setText(String.valueOf(ch));
                    }
                } else {
                    textViews[i].setText("");
                }
            }
        } else {
            for (int i = 0; i < mTextCount; i++) {
                textViews[i].setText("");
            }
        }
    }

    public String getPwdText() {
        if (editText != null)
            return editText.getText().toString().trim();
        return "";
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public interface OnTextFinishListener {
        void onFinish(String str);
    }

    public void setFocus() {
        editText.requestFocus();
        editText.setFocusable(true);
        showKeyBord(editText);
    }

    /**
     * 显示键盘
     *
     * @param view
     */
    public void showKeyBord(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);

    }

}
