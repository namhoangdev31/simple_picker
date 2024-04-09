package com.namhoang.dialogmenu;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.compose.ui.graphics.Color;

import com.namhoang.dialogmenu.Callbacks.OnMenuItemClickListener;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tạo một nút để mở popup FloatingMenuDialog
        Button openPopupButton = new Button(this);
        openPopupButton.setText("Open Popup");
        openPopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức để mở popup FloatingMenuDialog
                openFloatingMenuDialog();
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        openPopupButton.setLayoutParams(params);

        LinearLayout layout = new LinearLayout(this);
        layout.setGravity(Gravity.CENTER);
        layout.addView(openPopupButton);

        setContentView(layout);
    }

    // Phương thức để mở popup FloatingMenuDialog
    private void openFloatingMenuDialog() {
        // Tạo một instance của FloatingMenuDialog và hiển thị nó
        new FloatingMenuDialog(MainActivity.this)
//                 .setDialogTitle("Add Picture")
//                .setChooseFromLibraryButtonText("Choose From Library")
                .setChooseFromPhotoRoll("Choose From PhotoRoll") // Assuming a method for setting text for choosing from photo roll
//                .setTakePhotoButtonText("Take Photo")
//                .setDialogTitle("Simple Picker")
//                .setTakeVideoButtonText("Take Video")
                .setDismissDialogOnMenuOnClick(false) // Dismiss the dialog anytime a menu item is clicked
                .setDialogCancelable(true) // Set dialog cancellable
                .setOnTakePhotoBtnClick((OnMenuItemClickListener) () -> Toast.makeText(MainActivity.this, "Positive", Toast.LENGTH_SHORT).show())
                .setOnTakeVideoBtnClick((OnMenuItemClickListener) () -> Toast.makeText(MainActivity.this, "Negative", Toast.LENGTH_SHORT).show())
                .setOnChooseFromLibraryBtnClick((OnMenuItemClickListener) () -> Toast.makeText(MainActivity.this, "Neutral", Toast.LENGTH_SHORT).show())
                .setOnChooseFromPhotoRollClick((OnMenuItemClickListener) () -> Toast.makeText(MainActivity.this, "Extra", Toast.LENGTH_SHORT).show())
                .show();
    }

    // Các phương thức khác của MainActivity
}
