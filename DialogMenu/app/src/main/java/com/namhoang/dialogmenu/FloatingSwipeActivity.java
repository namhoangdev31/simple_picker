package com.namhoang.dialogmenu;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.namhoang.dialogmenu.Logic.DragHandler;

public class FloatingSwipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        final View view = getWindow().getDecorView();
        final WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //  params.setMargins(5, 20, 5, 20);

        lp.gravity = Gravity.CENTER;

        lp.width = params.width;
        lp.height = params.height;
        // view.setLayoutParams(params);
        new DragHandler(this).ondrag(view);
        getWindowManager().updateViewLayout(view, lp);
    }

}
