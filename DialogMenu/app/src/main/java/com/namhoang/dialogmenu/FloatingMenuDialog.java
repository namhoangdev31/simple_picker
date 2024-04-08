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

public class FloatingMenuDialog extends Dialog implements View.OnClickListener {
    OnMenuItemClickListener onPositiveOnClick, onNegativeOnClick, onNeutralOnClick, onExtraOnClick;

    TextView title, positiveButtonText, neutralButtonText, extraButtonText, cancelText;
    LinearLayout cancelButton, extraButton, neutralButton;
    private boolean dismissDialog, cancelable;

    private String titleText, positiveText, neutralText, extraText, cancellingText;
    private int titleColor, positiveTextColor, neutralTextColor, extraTextColor, cancelTextColor = 0;

    private Activity mContext;
    private String fontName = "";

    public FloatingMenuDialog(Activity context) {
        super(context);
        mContext = context;
        dismissDialog = true;
        cancelable = true;
        positiveText = null;
        neutralText = null;
        extraText = null;
    }

    private void initViews() {
        title = (TextView) findViewById(R.id.dg_Title_x);
        positiveButtonText = (TextView) findViewById(R.id.dg_PositiveButtonText_x);
        neutralButtonText = (TextView) findViewById(R.id.dg_NeutralButtonText_x);
        extraButtonText = (TextView) findViewById(R.id.dg_ExtraButtonText_x);
        cancelText = (TextView) findViewById(R.id.dg_cancelText_x);
        cancelButton = (LinearLayout) findViewById(R.id.dg_CancelButton_x);
        extraButton = (LinearLayout) findViewById(R.id.dg_ExtraButton_x);
        neutralButton = (LinearLayout) findViewById(R.id.dg_NeutralButton_x);

        positiveButtonText.setOnClickListener(this);
        neutralButton.setOnClickListener(this);
        extraButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        //////////////////////////////////////////////////////////////////////
        /// Hide Some of the Views, the POSITIVE, EXTRA, and NEUTRAL BUTTONS
        /////////////////////////////////////////////////////////////////////
        positiveButtonText.setVisibility(View.GONE);
        neutralButton.setVisibility(View.GONE);
        extraButton.setVisibility(View.GONE);


        setUpViewAttributes();
    }

    private void setUpViewAttributes() {
        try {
            if (titleText != null && !TextUtils.isEmpty(titleText))
                this.title.setText(titleText);
            else
                this.title.setVisibility(View.GONE);


            this.setViewsText(positiveButtonText, positiveText);
            this.setViewsText(neutralButtonText, neutralText);
            this.setViewsText(extraButtonText, extraText);

            if (cancellingText != null && !TextUtils.isEmpty(cancellingText))
                this.setViewsText(cancelText, cancellingText);
            else
                this.setViewsText(cancelText, getContext().getResources().getString(R.string.cancel));


            //////////////////////////////////////////////////////////////////////
            /// Show the called Views, the POSITIVE, EXTRA, and NEUTRAL BUTTONS
            /////////////////////////////////////////////////////////////////////
            if (positiveText != null && !TextUtils.isEmpty(positiveText)) {
                positiveButtonText.setVisibility(View.VISIBLE);
            }

            if (neutralText != null && !TextUtils.isEmpty(neutralText)) {
                neutralButton.setVisibility(View.VISIBLE);
                neutralButtonText.setVisibility(View.VISIBLE);
            }

            if (extraText != null && !TextUtils.isEmpty(extraText)) {
                extraButton.setVisibility(View.VISIBLE);
            }


            try {
                if (titleColor != 0)
                    title.setTextColor(titleColor);

                if (positiveTextColor != 0)
                    positiveButtonText.setTextColor(positiveTextColor);

                if (neutralTextColor != 0)
                    neutralButtonText.setTextColor(neutralTextColor);

                if (extraTextColor != 0)
                    extraButtonText.setTextColor(extraTextColor);

                if (cancelTextColor != 0)
                    cancelText.setTextColor(cancelTextColor);

            } catch (Exception e) {
                e.printStackTrace();
            }


            try{
                if (TextUtils.isEmpty(fontName) || fontName == null)
                    return;
                Typeface font = Typeface.createFromAsset(mContext.getAssets(), fontName);

                title.setTypeface(font);
                positiveButtonText.setTypeface(font);
                neutralButtonText.setTypeface(font);
                extraButtonText.setTypeface(font);
                cancelText.setTypeface(font);

            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }


        } catch (Exception e) {
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
            // this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); //Clear background Dim
            // this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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


    public FloatingMenuDialog setOnPositiveButtonOnClick(OnMenuItemClickListener onMenuItemClickListener) {
        this.onPositiveOnClick = onMenuItemClickListener;
        return this;
    }

    public FloatingMenuDialog setOnExtraButtonOnClick(OnMenuItemClickListener onMenuItemClickListener) {
        this.onExtraOnClick = onMenuItemClickListener;
        return this;
    }

    public FloatingMenuDialog setOnNeutralButtonOnClick(OnMenuItemClickListener onMenuItemClickListener) {
        this.onNeutralOnClick = onMenuItemClickListener;
        return this;
    }

    public FloatingMenuDialog setOnNegativeButtonOnClick(OnMenuItemClickListener onMenuItemClickListener) {
        this.onNegativeOnClick = onMenuItemClickListener;
        return this;
    }


    public FloatingMenuDialog setFontPath(String fontPath) {
        this.fontName = fontPath;
        return this;
    }



    ///////////////////////////////////////////////////////////
    ////////  SET-UP DIALOG VIEW ATTRIBUTES
    //////////////////////////////////////////////////////////


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

    public FloatingMenuDialog setDialogTitle(int titleId) {
        this.setTitle(titleId);
        return this;
    }


    public FloatingMenuDialog setDialogTitle(@Nullable CharSequence title) {
        this.setTitle(title);
        return this;
    }


    /**
     * Default metthod to be called
     **/
    @Deprecated
    private void setDefaultText(TextView textView, CharSequence titleText, int titleId) {
        try {
            if (titleText != null && TextUtils.isEmpty(titleText))
                textView.setText(titleText);
            else if (titleId != -1)
                textView.setText(titleId);
            else textView.setText(R.string.default_text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Default method to be called
     **/
    private void setViewsText(TextView textView, String text) {
        try {
            if (text != null && !TextUtils.isEmpty(text))
                textView.setText(text);
            else textView.setText(R.string.default_text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public FloatingMenuDialog setPositveButtonText(@Nullable CharSequence charSequence) {
        try {
            this.positiveText = String.valueOf(charSequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public FloatingMenuDialog setPositveButtonText(int textId) {
        try {
            this.positiveText = getContext().getResources().getString(textId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    public FloatingMenuDialog setNeutralButtonText(@Nullable CharSequence charSequence) {
        try {
            this.neutralText = String.valueOf(charSequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public FloatingMenuDialog setNeutralButtonText(int textId) {
        try {
            this.neutralText = getContext().getResources().getString(textId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public FloatingMenuDialog setExtraButtonText(@Nullable CharSequence charSequence) {
        try {
            this.extraText = String.valueOf(charSequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public FloatingMenuDialog setExtraButtonText(int textId) {
        try {
            this.extraText = getContext().getResources().getString(textId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public FloatingMenuDialog setNegativeButtonText(@Nullable CharSequence charSequence) {
        try {
            this.cancellingText = String.valueOf(charSequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public FloatingMenuDialog setNegativeButtonText(int textId) {
        try {
            //   this.setDefaultText(cancelText, null, textId);
            this.cancellingText = getContext().getResources().getString(textId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    public FloatingMenuDialog setDismissDialogOnMenuOnClick(boolean dismissDialog) {
        this.dismissDialog = dismissDialog;
        return this;
    }

    public FloatingMenuDialog setDialogCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        setCancelable(this.cancelable);
        return this;
    }


    ///////////======= SET THE TEXT COLORS ========/////////////
    public FloatingMenuDialog setPositiveTextColor(int color) {
        try {
            this.positiveTextColor = mContext.getResources().getColor(color) ;
        }catch (IllegalArgumentException e){
            this.positiveTextColor = 0;
        }
        return this;
    }

    public FloatingMenuDialog setExtraTextColor(int color) {
        try {
            this.extraTextColor = mContext.getResources().getColor(color) ;
        }catch (IllegalArgumentException e){
            this.extraTextColor = 0;
        }
        return this;
    }

    public FloatingMenuDialog setNeutralTextColor(int color) {
        try {
            this.neutralTextColor = mContext.getResources().getColor(color) ;
        }catch (IllegalArgumentException e){
            this.neutralTextColor = 0;
        }
        return this;
    }

    public FloatingMenuDialog setNegativeTextColor(int color) {
        try {
            this.cancelTextColor = mContext.getResources().getColor(color) ;
        }catch (IllegalArgumentException e){
            this.cancelTextColor = 0;
        }
        return this;
    }


    public FloatingMenuDialog setTitleTextColor(int color) {
        try {
            this.titleColor = mContext.getResources().getColor(color) ;
        }catch (IllegalArgumentException e){
            this.titleColor = 0;
        }
        return this;
    }

    // Using the Hexadecimal Colors

    public FloatingMenuDialog setPositiveTextColor(String color) {
        try {
            this.positiveTextColor = Color.parseColor(color);
        }catch (IllegalArgumentException e){
            this.positiveTextColor = 0;
        }
        return this;
    }

    public FloatingMenuDialog setExtraTextColor(String color) {
        try {
            this.extraTextColor = Color.parseColor(color);
        }catch (IllegalArgumentException e){
            this.extraTextColor = 0;
        }
        return this;
    }

    public FloatingMenuDialog setNeutralTextColor(String color) {
        try {
            this.neutralTextColor = Color.parseColor(color);
        }catch (IllegalArgumentException e){
            this.neutralTextColor = 0;
        }
        return this;
    }

    public FloatingMenuDialog setNegativeTextColor(String color) {
        try {
            this.cancelTextColor = Color.parseColor(color);
        }catch (IllegalArgumentException e){
            this.cancelTextColor = 0;
        }
        return this;
    }


    public FloatingMenuDialog setTitleTextColor(String color) {
        try {
            this.titleColor = Color.parseColor(color);
        }catch (IllegalArgumentException e){
            this.titleColor = 0;
        }
        return this;
    }




    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dg_PositiveButtonText_x) {
            if (onPositiveOnClick != null)
                onPositiveOnClick.onClick();
        }

        if (v.getId() == R.id.dg_NeutralButton_x) {
            if (onNeutralOnClick != null)
                onNeutralOnClick.onClick();
        }

        if (v.getId() == R.id.dg_CancelButton_x) {
            if (onNegativeOnClick != null)
                onNegativeOnClick.onClick();
            dismissDialog();
        }

        if (v.getId() == R.id.dg_ExtraButton_x) {
            if (onExtraOnClick != null)
                onExtraOnClick.onClick();
        }

        if (dismissDialog)
            dismissDialog();

    }


    private void dismissDialog() {
        this.dismiss();
        this.cancel();
    }


}

