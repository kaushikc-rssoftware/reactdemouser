package com.example.merchantdemo.helperClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merchantdemo.R;
import com.example.merchantdemo.dataClass.ItemData;

import java.util.List;

public class AdapterViewInvoiceCustomerItems extends RecyclerView.Adapter<AdapterViewInvoiceCustomerItems.ViewHolder> {
    private List<ItemData> mData;

    public AdapterViewInvoiceCustomerItems(List<ItemData> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_invoice_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ItemData itemList = mData.get(position);
        holder.item_name.setText(itemList.getItemName());
        holder.item_quantity.setText("Unit x "+itemList.getItemQuantity());
        holder.item_price.setText("Unit price: $ "+itemList.getItemPrice());
        holder.item_amount.setText("$ "+itemList.getItemAmountPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name,item_quantity,item_price,item_amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_quantity = itemView.findViewById(R.id.item_quantity);
            item_price = itemView.findViewById(R.id.item_price);
            item_amount = itemView.findViewById(R.id.item_amount);
        }
    }
}
