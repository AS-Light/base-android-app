package jx.android.staff.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import jx.android.staff.R;

public class HalfCircleWithTextProgress extends View {

    Paint mCircleBackPaint;
    Paint mCircleFrontPaint;
    TextPaint mTextPaint;

    int mLineBackColor;
    int mLineFrontColor;
    int mLineWidth;
    int mTextColor;
    int mTextSize;

    float mFullProgress = 100f;
    float mWantProgress = 0f;
    float mCurrentProgress = 0f;

    String[] mStringArray = new String[]{"极低", "较低", "中等", "较高", "极高"};

    int mWidth;
    int mHeight;

    int mCenterX;
    int mCenterY;
    int mRadius;

    public HalfCircleWithTextProgress(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.HalfCircleWithTextProgress);
            mLineBackColor = typedArray.getColor(R.styleable.HalfCircleWithTextProgress_hcpBackColor, Color.parseColor("#66000000"));
            mLineFrontColor = typedArray.getColor(R.styleable.HalfCircleWithTextProgress_hcpFrontColor, Color.parseColor("#ff000000"));
            mTextColor = typedArray.getColor(R.styleable.HalfCircleWithTextProgress_hcpTextColor, Color.parseColor("#66000000"));
            ;
            mLineWidth = typedArray.getDimensionPixelOffset(R.styleable.HalfCircleWithTextProgress_hcpStrokeWidth, 4);
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.HalfCircleWithTextProgress_hcpTextSize, 10);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }

        mCircleBackPaint = new Paint();
        mCircleBackPaint.setAntiAlias(true);
        mCircleBackPaint.setStrokeWidth(mLineWidth);
        mCircleBackPaint.setStyle(Paint.Style.STROKE);
        mCircleBackPaint.setColor(mLineBackColor);

        mCircleFrontPaint = new Paint();
        mCircleFrontPaint.setAntiAlias(true);
        mCircleFrontPaint.setStrokeWidth(mLineWidth);
        mCircleFrontPaint.setStyle(Paint.Style.STROKE);
        mCircleFrontPaint.setColor(mLineFrontColor);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTypeface(Typeface.DEFAULT);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    public void setFullProgress(float progress) {
        mFullProgress = progress;
    }

    public void setWantProgress(float progress) {
        mWantProgress = progress;
    }

    public void runProgress() {
        mCurrentProgress = 0;
    }

    private Thread mProgressThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    if (mCurrentProgress < mWantProgress) {
                        mCurrentProgress++;
                        invalidate();
                        Thread.sleep(10);
                    } else {
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mCenterX = mWidth / 2;
        mCenterY = mHeight;
        mRadius = (mWidth >= 2 * mHeight - mLineWidth ? mHeight : mWidth / 2) - mLineWidth;

        mProgressThread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // todo: 绘制底圆
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mCircleBackPaint);
        // todo: 绘制进度
        RectF rectF = new RectF(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
        canvas.drawArc(rectF, -180, 180 * mCurrentProgress / mFullProgress, false, mCircleFrontPaint);

        canvas.drawText(mStringArray[0], mCenterX - mRadius + mLineWidth * 2, mCenterY - mTextSize / 4, mTextPaint);
        canvas.drawText(mStringArray[4], mCenterX + mRadius - mLineWidth * 2 - (mStringArray[2].length()) * mTextSize, mCenterY - mTextSize / 4, mTextPaint);

        canvas.drawText(mStringArray[1], (float) (mCenterX - Math.cos(Math.toRadians(30)) * mRadius + mLineWidth + mTextSize), (float) (mCenterY - Math.sin(Math.toRadians(30)) * mRadius), mTextPaint);
        canvas.drawText(mStringArray[3], (float) (mCenterX + Math.cos(Math.toRadians(30)) * mRadius - mLineWidth - (mStringArray[2].length() + 1) * mTextSize), (float) (mCenterY - Math.sin(Math.toRadians(30)) * mRadius), mTextPaint);

        canvas.drawText(mStringArray[2], mCenterX - (mStringArray[2].length() / 2) * mTextSize, mCenterY - mRadius + mTextSize + mLineWidth * 2, mTextPaint);
    }
}
