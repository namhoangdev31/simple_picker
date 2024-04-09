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
        FloatingMenuDialog floatingMenuDialog = new FloatingMenuDialog(MainActivity.this);
        floatingMenuDialog.setCancelButtonText("Cancel").;
        floatingMenuDialog.show();
    }

    // Các phương thức khác của MainActivity
}
