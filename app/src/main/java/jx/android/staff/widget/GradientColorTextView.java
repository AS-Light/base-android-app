package jx.android.staff.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import jx.android.staff.R;

import jx.android.staff.utils.Dip2Px;

@SuppressLint("AppCompatCustomView")
public class GradientColorTextView extends TextView {

    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private Rect mTextBound = new Rect();

    private int mWidth = 0;
    private int mHeight = 0;

    private int mStartColor = 0;
    private int mEndColor = 0;

    public GradientColorTextView (Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientColorTextView);
            mStartColor = typedArray.getColor(R.styleable.GradientColorTextView_startColor, 0);
            mEndColor = typedArray.getColor(R.styleable.GradientColorTextView_endColor, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        mLinearGradient = new LinearGradient(0, 0, mWidth, mHeight,
                new int[]{mStartColor, mEndColor},
                null,
                Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        canvas.drawText(mTipText, mWidth / 2 - mTextBound.width() / 2, mHeight / 2 + mPaint.getTextSize() / 2 - Dip2Px.dip2px(1), mPaint);
    }
}