package com.namhoang.dialogmenu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namhoang.dialogmenu.Callbacks.OnItemClickListener;
import com.namhoang.dialogmenu.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<String> itemList;
    private LayoutInflater inflater;

    // Truyền sự kiện từ bên ngoài vào để xử lý sự kiện onClick của nút
    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.itemList = new ArrayList<>();
        // Thêm dữ liệu vào itemList nếu cần
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.button_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = itemList.get(position);
        holder.button.setText(item);

        // Xử lý sự kiện khi một nút được nhấn
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi sự kiện onClick từ bên ngoài vào
                //print log
                System.out.println("Button Clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setItemList(List<String> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.buttonItem);
        }
    }
}
