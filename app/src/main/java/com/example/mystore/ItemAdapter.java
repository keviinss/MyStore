package com.example.mystore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<Item> listItem;
    private Context context;
    private ItemListener listener;

    public ItemAdapter(ArrayList<Item> listItem, Context context, ItemListener listener) {
        this.listItem = listItem;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final String name = listItem.get(position).getName();
        final String desc = listItem.get(position).getDesc();
        final int qty = listItem.get(position).getQty();

        holder.txtName.setText(name);
        holder.txtDesc.setText(desc);
        holder.txtQty.setText("Qty : " + String.valueOf(qty));
        holder.imgItemMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.imgItemMenu);
                popupMenu.inflate(R.menu.menu_item);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                Intent intent = new Intent(context, ItemEditActivity.class);
                                intent.putExtra("data", listItem.get(position));
                                context.startActivity(intent);
                                return true;
                            case R.id.action_delete:
                                listener.deleteItem(listItem.get(position));
                                /*Snackbar snackbar = Snackbar
                                        .make(holder.imgItemMenu, "Are you sure want to delete this item?", Snackbar.LENGTH_LONG)
                                        .setAction("Yes", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                listener.deleteItem(listItem.get(position));
                                            }
                                        });
                                snackbar.setActionTextColor(context.getResources().getColor(R.color.colorPrimary));
                                View snackbarView = snackbar.getView();
                                TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                textView.setTextColor(context.getResources().getColor(R.color.white));
                                snackbar.show();*/
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView txtName;
        private TextView txtDesc;
        private TextView txtQty;
        private ImageView imgItemMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.img_item);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDesc = itemView.findViewById(R.id.txt_desc);
            txtQty = itemView.findViewById(R.id.txt_qty);
            imgItemMenu = itemView.findViewById(R.id.img_item_menu);
        }
    }

    public interface ItemListener {
        void deleteItem(Item item);
    }
}
