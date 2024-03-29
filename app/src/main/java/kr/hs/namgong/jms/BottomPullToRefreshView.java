/*
 * @(#)PullToRefreshView.java $version 2012. 3. 27.
 *
 * Copyright 2007 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package kr.hs.namgong.jms;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;


/**
 * @author cranix
 */
public class BottomPullToRefreshView extends LinearLayout {
    private PropertyAnimation headerLayoutHeightAni = null;
    private LinearLayout headerLayout = null;
    private ScrollView scrollView = null;
    private LinearLayout scrollMainLayout = null;
    private ImageView imageView = null;

    private LinearLayout beforeLoadingLayout = null;
    private ProgressBar loadingProgressBar = null;

    private float startPosY = 0;

    private boolean bottom = false;

    public static enum MODE {
        NORMAL, PULL, READY_TO_REFRESH, REFRESH
    }

    public static interface Listener {
        public void onChangeMode(MODE mode);
    }

    private Listener listener = null;

    private MODE mode = MODE.NORMAL;

    /**
     * @param context
     */
    public BottomPullToRefreshView(Context context) {
        super(context);
    }

    public BottomPullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * @see View#onAttachedToWindow()
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            init();
        } catch (Exception e) {

        }
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;

    }

    public MODE getMode() {
        return mode;
    }

    public void completeRefresh() {
        chageMode(MODE.NORMAL);
        if (headerLayout.getVisibility() == View.GONE) {
            return;
        }
        headerLayoutHeightAni.setStart(headerLayout.getHeight());
        headerLayoutHeightAni.setEnd(0);
        headerLayout.startAnimation(headerLayoutHeightAni);
    }

    private void chageMode(MODE mode) {
        if (this.mode == mode) {
            return;
        }
        this.mode = mode;
        switch (mode) {
            case NORMAL:
                beforeLoadingLayout.setVisibility(View.VISIBLE);
                loadingProgressBar.setVisibility(View.GONE);
                break;
            case PULL:
                imageView.startAnimation(flipAni);
                break;
            case READY_TO_REFRESH:
                imageView.startAnimation(reverseFlipAni);
                break;
            case REFRESH:
                beforeLoadingLayout.setVisibility(View.GONE);
                loadingProgressBar.setVisibility(View.VISIBLE);
                break;
        }
        if (this.listener != null) {
            this.listener.onChangeMode(mode);
        }
    }

    public boolean isBottom() {
        return bottom;
    }

    RotateAnimation flipAni = null;
    RotateAnimation reverseFlipAni = null;

    private void init() {

        // layout
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerLayout = (LinearLayout) inflater.inflate(R.layout.pull_to_refresh_layout, null);

        scrollView = (ScrollView) headerLayout.findViewById(R.id.scrollView);
        scrollMainLayout = (LinearLayout) scrollView.findViewById(R.id.linearLayout_scrollMain);
        headerLayout.setVisibility(View.GONE);

        imageView = (ImageView) headerLayout.findViewById(R.id.imageView);
        beforeLoadingLayout = (LinearLayout) headerLayout.findViewById(R.id.beforeLoading);
        loadingProgressBar = (ProgressBar) headerLayout.findViewById(R.id.progressBar);
        addView(headerLayout);

        // animation
        headerLayoutHeightAni = new PropertyAnimation(0, 0, new PropertyAnimation.PropertyListener() {
            @Override
            public void onPropertyChanged(PropertyAnimation animation, float now) {
                headerLayout.getLayoutParams().height = (int) now;
                headerLayout.requestLayout();
            }
        });
        headerLayoutHeightAni.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mode == MODE.PULL) {
                    headerLayout.setVisibility(View.GONE);
                    chageMode(MODE.NORMAL);
                }
                headerLayout.clearAnimation();
                isStart = false;
            }
        });
        headerLayoutHeightAni.setDuration(300);

        flipAni = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        flipAni.setInterpolator(new LinearInterpolator());
        flipAni.setDuration(250);
        flipAni.setFillAfter(true);
        reverseFlipAni = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseFlipAni.setInterpolator(new LinearInterpolator());
        reverseFlipAni.setDuration(250);
        reverseFlipAni.setFillAfter(true);
    }

    private boolean isStart = false;

    public boolean touchDelegate(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                if (!isStart) {
                    break;
                }
                if (headerLayout.getVisibility() == View.GONE) {
                    break;
                }
                if (headerLayoutHeightAni.hasStarted() && !headerLayoutHeightAni.hasEnded()) {
                    break;
                }
                headerLayoutHeightAni.setStart(headerLayout.getHeight());
                if (mode == MODE.READY_TO_REFRESH) {
                    headerLayoutHeightAni.setEnd(scrollMainLayout.getHeight());
                    chageMode(MODE.REFRESH);
                } else {
                    headerLayoutHeightAni.setEnd(0);
                }
                headerLayout.startAnimation(headerLayoutHeightAni);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!isStart) {
                    break;
                }
                if (headerLayoutHeightAni.hasStarted() && !headerLayoutHeightAni.hasEnded()) {
                    break;
                }
                float gap = startPosY - event.getRawY();
                if (gap <= 0) {
                    break;
                }
                if (gap > scrollMainLayout.getHeight()) {
                    chageMode(MODE.READY_TO_REFRESH);

                } else {
                    chageMode(MODE.PULL);
                }

                headerLayout.getLayoutParams().height = (int) gap;
                headerLayout.requestLayout();

                if (headerLayout.getVisibility() == View.GONE) {
                    headerLayout.setVisibility(View.VISIBLE);
                }
                return true;
            case MotionEvent.ACTION_DOWN:
                if (!bottom) {
                    break;
                }
                if (mode != MODE.NORMAL && mode != MODE.READY_TO_REFRESH) {
                    break;
                }
                if (headerLayoutHeightAni.hasStarted() && !headerLayoutHeightAni.hasEnded()) {
                    break;
                }
                isStart = true;
                startPosY = event.getRawY() - headerLayout.getHeight();

                return true;
        }
        return false;
    }
}
