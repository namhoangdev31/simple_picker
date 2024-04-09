package com.namhoang.dialogmenu.Logic;

import android.app.Activity;
import android.content.ClipData;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;



public class DragHandler {
    Activity mContext;

    public DragHandler(Activity context) {
        this.mContext = context;
    }

    // Assign the touch listener to your view which you want to move
    public void ondrag(View request, View receive) {
        request.setOnTouchListener(new MyTouchListener());
        receive.setOnDragListener(new MyDragListener());
    }

    public void ondrag(View view) {
        view.setOnTouchListener(new OnSwipeTouchListener(mContext,view));
        view.setOnDragListener(new MyDragListener());
    }

    // This defines your touch listener
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }

            //  return false;
        }
    }


    private static class MyDragListener implements View.OnDragListener {
        //  Drawable enterShape = mContext.getResources().getDrawable(R.drawable.shape_droptarget);
        //  Drawable normalShape = mContext.getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    Log.d("Http", "Drag started");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //  v.setBackgroundDrawable(enterShape);
                    Log.d("Http", "Drag entered");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //  v.setBackgroundDrawable(normalShape);
                    Log.d("Http", "Drag exited");
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                 /*   View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);*/
                    Log.d("Http", "Drag droped");
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //  v.setBackgroundDrawable(normalShape);
                    Log.d("Http", "Drag ended");
                default:
                    break;
            }
            return true;
        }
    }


}
