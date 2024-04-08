package com.namhoang.dialogmenu.Logic;

import android.app.Activity;
import android.content.ClipData;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.namhoang.dialogmenu.R;


public class OnSwipeTouchListener implements View.OnTouchListener {
    Activity mContext;
    View view;

    private GestureDetector gestureDetector;

    public OnSwipeTouchListener(Activity c, View view) {
        this.mContext = c;
        this.view = view;
        gestureDetector = new GestureDetector(c, new GestureListener());
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        // Determines the fling velocity and then fires the appropriate swipe event accordingly
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeDown();
                        } else {
                            //  onSwipeUp();

                            //  if (e1.getAction() == MotionEvent.ACTION_DOWN) {
                            ClipData data = ClipData.newPlainText("", "");
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                            view.startDrag(data, shadowBuilder, view, 0);
                            //  view.setVisibility(View.INVISIBLE);
                            // return true;
                            //  } else {
                            //     return false;
                            // }
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {

        Toast.makeText(mContext, "Right", Toast.LENGTH_SHORT).show();
    }

    public void onSwipeLeft() {
        Toast.makeText(mContext, "Left", Toast.LENGTH_SHORT).show();
    }

    public void onSwipeUp() {
        try {
            mContext.getWindow().setWindowAnimations(R.style.AnimationDragUp);
        } catch (Exception e) {
        }
        mContext.finish();
        Toast.makeText(mContext, "up", Toast.LENGTH_SHORT).show();
    }

    public void onSwipeDown() {
        try {
            //      mContext.getWindow().setWindowAnimations(R.style.AnimationDragDown);
        } catch (Exception e) {

        }
        mContext.finish();
        Toast.makeText(mContext, "down", Toast.LENGTH_SHORT).show();
    }




}