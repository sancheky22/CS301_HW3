package com.example.cs301_hw3;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;


/**
 External Citation
 Date: 5 November 2019
 Problem: Wanted to use gridview to use swiping to move pieces
 Resource:
 https://developer.android.com/reference/android/widget/GridView
 Solution: Read documentation to learn how to implement swipe to move piece
 */


public class GestureDetect extends GridView {
    private GestureDetector detector;
    private boolean FlingConfirmed = false;
    private float TouchX;
    private float TouchY;

    private static final int swipeMinDist = 100;
    private static final int swipeMax = 100;
    private static final int swipeVelocity = 100;

    public GestureDetect(Context context) {
        super(context);
        init(context);
    }

    public GestureDetect(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureDetect(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

   @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GestureDetect(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     External Citation
     Date: 9 November 2019
     Problem: Unfamiliar with OnGesture listener
     Resource: https://developer.android.com/reference/android/view/GestureDetector.OnGestureListener
     Solution: I read the documentation to implement gestures
     */
    private void init(final Context context) {
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                final int position = GestureDetect.this.pointToPosition
                        (Math.round(e1.getX()), Math.round(e1.getY()));

                if (Math.abs(e1.getY() - e2.getY()) > swipeMax) {
                    if (Math.abs(e1.getX() - e2.getX()) > swipeMax
                            || Math.abs(velocityY) < swipeVelocity) {
                        return false;
                    }
                    if (e1.getY() - e2.getY() > swipeMinDist) {
                        MainActivity.moveTiles(context, MainActivity.up, position);
                    } else if (e2.getY() - e1.getY() > swipeMinDist) {
                        MainActivity.moveTiles(context, MainActivity.down, position);
                    }
                } else {
                    if (Math.abs(velocityX) < swipeVelocity) {
                        return false;
                    }
                    if (e1.getX() - e2.getX() > swipeMinDist) {
                        MainActivity.moveTiles(context, MainActivity.left, position);
                    } else if (e2.getX() - e1.getX() > swipeMinDist) {
                        MainActivity.moveTiles(context, MainActivity.right, position);
                    }
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }


    /**
     External Citation
     Date: 9 November 2019
     Problem: Needed to manage touch events in view group (gridview)
     Resource: https://developer.android.com/training/gestures/viewgroup
     Solution: I read the documentation to implement gestures
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        detector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            FlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            TouchX = ev.getX();
            TouchY = ev.getY();
        } else {
            if (FlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - TouchX));
            float dY = (Math.abs(ev.getY() - TouchY));
            if ((dX > swipeMinDist) || (dY > swipeMinDist)) {
                FlingConfirmed = true;
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return detector.onTouchEvent(ev);
    }
}
