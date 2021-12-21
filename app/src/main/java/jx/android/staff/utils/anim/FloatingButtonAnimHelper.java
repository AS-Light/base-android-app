package jx.android.staff.utils.anim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import jx.android.staff.R;

public class FloatingButtonAnimHelper {

    private final static int STATE_SHOW = 1;
    private final static int STATE_HIDE = 2;
    private final static int STATE_SHOWING = 3;
    private final static int STATE_HIDING = 4;

    public final static int ACTION_SHOW = 1;
    public final static int ACTION_HIDE = 2;

    private Context mContext;
    private View mView;

    private TranslateAnimation mShowAnimation;
    private TranslateAnimation mHideAnimation;

    private boolean working = true;
    private int mState = STATE_SHOW;
    private int mNextAction = STATE_SHOW;

    public FloatingButtonAnimHelper(Context context, View view) {
        mContext = context;
        mView = view;

        mHideAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(mContext, R.anim.translate_out_to_right);
        mShowAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(mContext, R.anim.translate_in_from_right);

        mHideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mState = STATE_HIDING;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mState = STATE_HIDE;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                mState = STATE_HIDING;
            }
        });

        mShowAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mState = STATE_SHOWING;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mState = STATE_SHOW;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                mState = STATE_SHOWING;
            }
        });

        working = true;
        // todo: waiting
        Thread watchdog = new Thread(() -> {
            try {
                while (working) {
                    switch (mState) {
                        case STATE_SHOWING:
                        case STATE_HIDING:
                            // todo: waiting
                            break;
                        case STATE_HIDE:
                            if (mNextAction == ACTION_SHOW) {
                                show(true);
                            }
                            break;
                        case STATE_SHOW:
                            if (mNextAction == ACTION_HIDE) {
                                show(false);
                            }
                            break;
                        default:
                            break;
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        watchdog.start();
    }

    public void release() {
        working = false;
    }

    public void postAction(int action) {
        mNextAction = action;
    }

    private void show(boolean show) {
        Message message = new Message();
        message.what = 1;
        message.obj = show;
        mHandler.sendMessage(message);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                boolean show = (boolean) msg.obj;
                mView.clearAnimation();
                mView.setAnimation(show ? mShowAnimation : mHideAnimation);
                mView.getAnimation().start();
            }
        }
    };

}
