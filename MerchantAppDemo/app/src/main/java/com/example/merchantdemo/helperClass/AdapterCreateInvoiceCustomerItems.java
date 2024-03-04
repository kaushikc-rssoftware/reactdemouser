package com.example.merchantdemo.helperClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.merchantdemo.R;
import com.example.merchantdemo.dataClass.ItemData;

import java.util.List;

public class AdapterCreateInvoiceCustomerItems extends RecyclerView.Adapter<AdapterCreateInvoiceCustomerItems.ViewHolder>{
    private List<ItemData> itemList;
    private Context mContext;
    private AdapterCreateInvoiceCustomerItems.OnItemClickListener onItemClickListener;

    // RecyclerView recyclerView;
//    public AdapterManageTemplates(List<ManageTemplatesData> manageTemplatesData) {
//        this.manageTemplatesData = manageTemplatesData;
//    }

    public void setItemList(List<ItemData> itemList,Context context) {
        this.itemList = itemList;
        this.mContext=context;
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }


    public void clear() {
        if (itemList!=null) {
            itemList.clear(); // Assuming dataset is the list/array you're using to populate the RecyclerView
            notifyDataSetChanged();
        }// Notify adapter of the changes
    }

    // Interface definition for item click handling
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onEditClick(int position);
    }

    // Method to set the click listener
    public void setOnItemClickListener(AdapterCreateInvoiceCustomerItems.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public AdapterCreateInvoiceCustomerItems.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.customer_item_list, parent, false);
        AdapterCreateInvoiceCustomerItems.ViewHolder viewHolder = new AdapterCreateInvoiceCustomerItems.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterCreateInvoiceCustomerItems.ViewHolder holder, int position) {
        final ItemData itemList1 = itemList.get(position);
        holder.itemName.setText(itemList1.getItemName());
        holder.itemQuantity.setText(itemList1.getItemQuantity());
        holder.itemPrice.setText(itemList1.getItemPrice());
        holder.itemPriceAmount.setText(itemList1.getItemAmountPrice());
        String itemNumber=String.valueOf(position+1);
        holder.textviewHeading.setText("Item "+itemNumber);

//        holder.merchantLogo.setImageResource(itemList1.getItemQuantity());
//        holder.editMerchantTemplate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"click on item: "+itemList1.getItemQuantity(),Toast.LENGTH_LONG).show();
//            }
//        });

        //get click control for whole item
//        holder.itemView.setOnClickListener(view -> {

        holder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onEditClick(position);
                    }
                }
            }
        });

        holder.deleteItems.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });

        holder.textviewHeading.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= holder.textviewHeading.getRight() - holder.textviewHeading.getTotalPaddingRight()){
                        // your action for drawable click event
//                        Toast.makeText(mContext,"clicked image",Toast.LENGTH_SHORT ).show();
                        if (holder.itemRl.getVisibility()==View.VISIBLE){
                           holder.itemRl.setVisibility(View.GONE);
                            holder.textviewHeading.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
                        }else {
                            holder.itemRl.setVisibility(View.VISIBLE);
                            holder.textviewHeading.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                        }
                        return true;
                    }
                }
                return true;
            }
        });

//        holder.editMerchantTemplate.setOnClickListener(view -> {
//            mContext.startActivity(new Intent(mContext, CreateTemplate.class));
//        });
    }


    @Override
    public int getItemCount() {
//        return manageTemplatesData.size();
        return itemList == null ? 0 : itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView deleteItems,editItem;
        public RelativeLayout itemRl;
        public TextView itemName,itemPrice,itemQuantity,itemPriceAmount,textviewHeading;
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemName = itemView.findViewById(R.id.item_value_tv_rv);
            this.itemPrice = itemView.findViewById(R.id.price_value_tv_rv);
            this.itemQuantity = itemView.findViewById(R.id.quantity_value_tv_rv);
            this.itemPriceAmount = itemView.findViewById(R.id.amount_value_tv_rv);
            this.deleteItems = itemView.findViewById(R.id.delete_item_iv);
            this.editItem = itemView.findViewById(R.id.edit_iv_rv);
            this.textviewHeading = itemView.findViewById(R.id.textview_heading);
            this.itemRl = itemView.findViewById(R.id.item_rl);
        }
    }
}