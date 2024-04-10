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
    OnMenuItemClickListener onTakePhotoClick, onTakeVideoClick, onChooseFromLibraryClick, onChooseFromPhotoRollClick;

    TextView title, takePhotoTextView, takeVideoTextView, chooseFromLibraryTextView, cancelTextView , chooseFromPhotoRollTextView;
    LinearLayout cancelButton, takePhotoButton , takeVideoButton , chooseFromLibraryButton , chooseFromPhotoRollButton;
    private boolean dismissDialog, cancelable;

    View takeVideoDivider , chooseFromLibraryDivider , choosePhotoRollDivider;

    private String titleText, takePhoto,takeVideo,chooseFromLibrary, chooseFromPhotoRoll, cancellingText;
    private int titleColor, takePhotoColor,takeVideoColor,chooseFromLibraryColor, chooseFromPhotoRollColor, cancelTextColor = 0;

    private final Activity mContext;

    private boolean isShowTakePhoto , isShowTakeVideo , isShowChooseFromLibrary , isShowChooseFromPhotoRoll;

    private String fontName = "";
    public FloatingMenuDialog(Activity context) {
        super(context);
        mContext = context;
        dismissDialog = true;
        cancelable = true;
        takePhoto = null;
        takeVideo= null;
        chooseFromLibrary= null;
        chooseFromPhotoRoll= null;
        isShowTakePhoto = true;
        isShowTakeVideo = true;
        isShowChooseFromLibrary = true;
        isShowChooseFromPhotoRoll = true;
    }

    private void initViews() {
        title = (TextView) findViewById(R.id.dg_Title_x);
        takePhotoTextView = (TextView) findViewById(R.id.dg_TakePhotoButtonText_x);
        takeVideoTextView = (TextView) findViewById(R.id.dg_TakeVideoButtonText_x);
        chooseFromLibraryTextView = (TextView) findViewById(R.id.dg_ChooseFromLibraryButtonText_x);
        chooseFromPhotoRollTextView = (TextView) findViewById(R.id.dg_ChooseFromPhotoRollText_x);
        cancelTextView = (TextView) findViewById(R.id.dg_cancelText_x);
        cancelButton = (LinearLayout) findViewById(R.id.dg_CancelButton_x);
        takePhotoButton = (LinearLayout) findViewById(R.id.dg_TakePhotoButton_x);
        takeVideoButton = (LinearLayout) findViewById(R.id.dg_TakeVideo_x);
        chooseFromLibraryButton= (LinearLayout) findViewById(R.id.dg_ChooseFromLibraryButtonButton_x);
        chooseFromPhotoRollButton =  (LinearLayout) findViewById(R.id.dg_ChooseFromPhotoRoll_x);
        takeVideoDivider = (View)  findViewById(R.id.v_TakeVideo_divider);
        chooseFromLibraryDivider = (View) findViewById(R.id.v_ChooseFromLibrary_view);
        choosePhotoRollDivider = (View) findViewById(R.id.v_ChooseFromPhotoRoll_divider);
        takePhotoButton.setOnClickListener(this);
        takeVideoButton.setOnClickListener(this);
        chooseFromLibraryButton.setOnClickListener(this);
        chooseFromPhotoRollButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        //////////////////////////////////////////////////////////////////////
        /// Hide Some of the Views, the POSITIVE, EXTRA, and NEUTRAL BUTTONS
        /////////////////////////////////////////////////////////////////////
        takePhotoButton.setVisibility(View.GONE);
        takeVideoButton.setVisibility(View.GONE);
        chooseFromLibraryButton.setVisibility(View.GONE);
        chooseFromPhotoRollButton.setVisibility(View.GONE);
        takeVideoDivider.setVisibility(View.GONE);
        chooseFromLibraryDivider.setVisibility(View.GONE);
        choosePhotoRollDivider.setVisibility(View.GONE);
        setUpViewAttributes();
    }

    private void setUpViewAttributes() {
        if (titleText != null && !TextUtils.isEmpty(titleText))
            title.setText(titleText);
        else
            title.setVisibility(View.GONE);

        if (cancellingText != null && !TextUtils.isEmpty(cancellingText))
            cancelTextView.setText(cancellingText);
        else
            cancelTextView.setText(getContext().getResources().getString(R.string.cancel));

        try {
            if (titleText != null && !TextUtils.isEmpty(titleText))
                this.title.setText(titleText);
            else
                this.title.setVisibility(View.GONE);


            this.setViewsText(takePhotoTextView, takePhoto);
            this.setViewsText(takeVideoTextView, takeVideo);
            this.setViewsText(chooseFromLibraryTextView, chooseFromLibrary);
            this.setViewsText(chooseFromPhotoRollTextView, chooseFromPhotoRoll);

            if (cancellingText != null && !TextUtils.isEmpty(cancellingText))
                this.setViewsText(cancelTextView, cancellingText);
            else
                this.setViewsText(cancelTextView, getContext().getResources().getString(R.string.cancel));


            //////////////////////////////////////////////////////////////////////
            /// Show the called Views, the POSITIVE, EXTRA, and NEUTRAL BUTTONS
            /////////////////////////////////////////////////////////////////////
            if (takePhoto != null && !TextUtils.isEmpty(takePhoto)) {
                takePhotoButton.setVisibility(View.VISIBLE);
                takeVideoDivider.setVisibility(View.VISIBLE);
            }

            if (takeVideo != null && !TextUtils.isEmpty(takeVideo)) {
                takeVideoButton.setVisibility(View.VISIBLE);
                chooseFromLibraryDivider.setVisibility(View.VISIBLE);
//                neutralButtonText.setVisibility(View.VISIBLE);
            }

            if (chooseFromLibrary != null && !TextUtils.isEmpty(chooseFromLibrary)) {
                chooseFromLibraryButton.setVisibility(View.VISIBLE);
                choosePhotoRollDivider.setVisibility(View.VISIBLE);
            }

            if (chooseFromPhotoRoll != null && !TextUtils.isEmpty(chooseFromPhotoRoll)) {
                chooseFromPhotoRollButton.setVisibility(View.VISIBLE);
            }

            try {
                if (titleColor != 0)
                    title.setTextColor(titleColor);

                if (takePhotoColor != 0)
                    takePhotoTextView.setTextColor(takePhotoColor);

                if (takeVideoColor != 0)
                    takeVideoTextView.setTextColor(takeVideoColor);

                if (chooseFromLibraryColor != 0)
                    chooseFromLibraryTextView.setTextColor(chooseFromLibraryColor);

                if (chooseFromPhotoRollColor != 0)
                    chooseFromPhotoRollTextView.setTextColor(chooseFromPhotoRollColor);

                if (cancelTextColor != 0)
                    cancelTextView.setTextColor(cancelTextColor);

            } catch (Exception e) {
                e.printStackTrace();
            }


            try{
                if (TextUtils.isEmpty(fontName) || fontName == null)
                    return;
                Typeface font = Typeface.createFromAsset(mContext.getAssets(), fontName);

                title.setTypeface(font);
                takePhotoTextView.setTypeface(font);
                takeVideoTextView.setTypeface(font);
                chooseFromLibraryTextView.setTypeface(font);
                chooseFromPhotoRollTextView.setTypeface(font);
                cancelTextView.setTypeface(font);

            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }

            if (cancelTextColor != 0)
                cancelTextView.setTextColor(cancelTextColor);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!TextUtils.isEmpty(fontName)) {
                Typeface font = Typeface.createFromAsset(mContext.getAssets(), fontName);
                title.setTypeface(font);
                cancelTextView.setTypeface(font);
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


    public FloatingMenuDialog setOnTakePhotoBtnClick(OnMenuItemClickListener onMenuItemClickListener) {
        this.onTakePhotoClick = onMenuItemClickListener;
        return this;
    }

    public FloatingMenuDialog setOnTakeVideoBtnClick(OnMenuItemClickListener onMenuItemClickListener) {
        this.onTakeVideoClick = onMenuItemClickListener;
        return this;
    }

    public FloatingMenuDialog setOnChooseFromLibraryBtnClick(OnMenuItemClickListener onMenuItemClickListener) {
        this.onChooseFromLibraryClick = onMenuItemClickListener;
        return this;
    }

    public FloatingMenuDialog setOnChooseFromPhotoRollClick(OnMenuItemClickListener onMenuItemClickListener) {
        this.onChooseFromPhotoRollClick = onMenuItemClickListener;
        return this;
    }


//    public FloatingMenuDialog setFontPath(String fontPath) {
//        this.fontName = fontPath;
//        return this;
//    }



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

//    public FloatingMenuDialog setCancelButtonText(CharSequence text) {
//        this.cancellingText = String.valueOf(text);
//        return this;
//    }


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


    public FloatingMenuDialog setTakePhotoButtonText(@Nullable CharSequence charSequence) {
        try {
            this.takePhoto = String.valueOf(charSequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public FloatingMenuDialog setTakePhotoButtonText(int textId) {
        try {
            this.takePhoto = getContext().getResources().getString(textId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public FloatingMenuDialog setTakeVideoButtonText(@Nullable CharSequence charSequence) {
        try {
            this.takeVideo = String.valueOf(charSequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public FloatingMenuDialog setTakeVideoButtonText(int textId) {
        try {
            this.takeVideo = getContext().getResources().getString(textId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

//    public FloatingMenuDialog setChooseFromLibraryButtonText(@Nullable CharSequence charSequence) {
//        try {
//            this.chooseFromLibrary = String.valueOf(charSequence);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return this;
//    }

    public FloatingMenuDialog setChooseFromLibraryButtonText(@Nullable CharSequence charSequence) {
        try {
            this.chooseFromLibrary = String.valueOf(charSequence);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public FloatingMenuDialog setChooseFromPhotoRoll(@Nullable CharSequence charSequence) {
        try {
            this.chooseFromPhotoRoll = String.valueOf(charSequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public FloatingMenuDialog setChooseFromPhotoRoll(int textId) {
        try {
            this.chooseFromPhotoRoll = getContext().getResources().getString(textId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
    public FloatingMenuDialog setCancelButtonText(@Nullable CharSequence charSequence) {
        try {
            this.cancellingText = String.valueOf(charSequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public FloatingMenuDialog setCancelButtonText(int textId) {
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
    public FloatingMenuDialog setTakePhotoColor(Object color ) {
        try {
            if(color instanceof Integer){
                this.takePhotoColor = mContext.getResources().getColor((int) color);
            }
           else if(color instanceof String) {
                this.takePhotoColor = Color.parseColor((String) color);
            } else {
               this.takePhotoColor = 0;
            }
        }catch (IllegalArgumentException e){
            this.takePhotoColor = 0;
        }
        return this;
    }

    public FloatingMenuDialog setTakeVideoColor(Object color) {
        try {
            if(color instanceof Integer){
                this.takeVideoColor = mContext.getResources().getColor((int) color);
            }
            else if(color instanceof String) {
                this.takeVideoColor = Color.parseColor((String) color);
            } else {
                this.takeVideoColor = 0;
            }
//            this.takeVideoColor = mContext.getResources().getColor(color) ;
        }catch (IllegalArgumentException e){
            this.takeVideoColor = 0;
        }
        return this;
    }

    public FloatingMenuDialog setChooseFromLibraryColor(Object color) {
        try {
            if(color instanceof Integer){
                this.chooseFromLibraryColor = mContext.getResources().getColor((int) color);
            }
            else if(color instanceof String) {
                this.chooseFromLibraryColor = Color.parseColor((String) color);
            } else {
                this.chooseFromLibraryColor = 0;
            }
        }catch (IllegalArgumentException e){
            this.chooseFromLibraryColor = 0;
        }
        return this;
    }
    public FloatingMenuDialog setChooseFromPhotoRollColor(Object color) {
        try {
            if(color instanceof Integer){
                this.chooseFromPhotoRollColor = mContext.getResources().getColor((int) color);
            }
            else if(color instanceof String) {
                this.chooseFromPhotoRollColor = Color.parseColor((String) color);
            } else {
                this.chooseFromPhotoRollColor = 0;
            }
        }catch (IllegalArgumentException e){
            this.chooseFromPhotoRollColor = 0;
        }
        return this;
    }
    public FloatingMenuDialog setCancelTextColor(int color) {
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



//    public FloatingMenuDialog setCancelTextColor(int color) {
//        this.cancelTextColor = color;
//        return this;
//    }

    public FloatingMenuDialog setFontPath(String fontPath) {
        this.fontName = fontPath;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dg_TakePhotoButton_x) {
            if (onTakePhotoClick != null)
                onTakePhotoClick.onClick();
        }

        if (v.getId() == R.id.dg_TakeVideo_x) {
            if (onTakeVideoClick != null)
                onTakeVideoClick.onClick();
        }

        if (v.getId() == R.id.dg_CancelButton_x) {
//            if ( != null)
//                onChooseFromLibraryClick.onClick();
            dismissDialog();
        }

        if (v.getId() == R.id.dg_ChooseFromLibraryButtonButton_x) {
            if (onChooseFromLibraryClick != null)
                onChooseFromLibraryClick.onClick();
        }

        if (v.getId() == R.id.dg_ChooseFromPhotoRoll_x) {
            if (onChooseFromPhotoRollClick != null)
                onChooseFromPhotoRollClick.onClick();
        }

        if (dismissDialog)
            dismissDialog();
    }

    private void dismissDialog() {
        dismiss();
        cancel();
    }
}
