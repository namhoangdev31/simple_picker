package com.namhoang.dialogmenu;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.namhoang.dialogmenu.Callbacks.OnMenuItemClickListener;
import static android.view.Gravity.BOTTOM;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class FloatingMenuDialog extends Dialog implements View.OnClickListener {
    OnMenuItemClickListener onCancelClickListener , onButtonClick;
    TextView title, cancelText;
    LinearLayout cancelButton;
    private boolean dismissDialog = true;
    private String titleText, cancellingText;
    private int titleColor, cancelTextColor = 0;
    private Activity mContext;

    private String fontName = "";
    public FloatingMenuDialog(Activity context) {
        super(context);
        mContext = context;
    }

    private void initViews() {
        title = findViewById(R.id.dg_Title_x);
        cancelText = findViewById(R.id.dg_cancelText_x);
        cancelButton = findViewById(R.id.dg_CancelButton_x);
        setUpViewAttributes();
    }

    private void setUpViewAttributes() {
        if (titleText != null && !TextUtils.isEmpty(titleText))
            title.setText(titleText);
        else
            title.setVisibility(View.GONE);

        if (cancellingText != null && !TextUtils.isEmpty(cancellingText))
            cancelText.setText(cancellingText);
        else
            cancelText.setText(getContext().getResources().getString(R.string.cancel));

        try {
            if (titleColor != 0)
                title.setTextColor(titleColor);

            if (cancelTextColor != 0)
                cancelText.setTextColor(cancelTextColor);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!TextUtils.isEmpty(fontName)) {
                Typeface font = Typeface.createFromAsset(mContext.getAssets(), fontName);
                title.setTypeface(font);
                cancelText.setTypeface(font);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_menu_layout);
        initViews();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        final View view = this.getWindow().getDecorView();
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.gravity = BOTTOM;
        this.getWindow().setAttributes(params);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(getContext().getResources().getDrawable(R.drawable.dialog_inset_bg));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            getWindow().setWindowAnimations(R.style.DialogDragDown);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getWindow().getWindowManager().updateViewLayout(view, params);
    }

    public FloatingMenuDialog setOnCancelClickListener(OnMenuItemClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
        return this;
    }
    //onButtonClick
    public FloatingMenuDialog setOnButtonClick(OnMenuItemClickListener onButtonClick) {
        this.onButtonClick = onButtonClick;
        return this;
    }
    @Override
    public void setTitle(int titleId) {
        try {
            this.titleText = getContext().getResources().getString(titleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
        try {
            this.titleText = String.valueOf(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FloatingMenuDialog setCancelButtonText(CharSequence text) {
        this.cancellingText = String.valueOf(text);
        return this;
    }

    public FloatingMenuDialog setTitleTextColor(int color) {
        this.titleColor = color;
        return this;
    }



    public FloatingMenuDialog setCancelTextColor(int color) {
        this.cancelTextColor = color;
        return this;
    }

    public FloatingMenuDialog setFontPath(String fontPath) {
        this.fontName = fontPath;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dg_CancelButton_x) {
            if (onCancelClickListener != null)
                onCancelClickListener.onClick();
            dismissDialog();
        }

        if (v.getId() == R.id.dg_RecyclerView) {
            if (onButtonClick != null)
                onButtonClick.onClick();
            dismissDialog();
        }

        if (dismissDialog)
            dismissDialog();
    }

    private void dismissDialog() {
        dismiss();
        cancel();
    }
}
