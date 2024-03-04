package com.example.merchantdemo.helperClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.merchantdemo.R;
import com.example.merchantdemo.dataClass.InstallmentData;
import com.example.merchantdemo.dataClass.ItemData;

import java.util.List;

public class AdapterInstallment extends RecyclerView.Adapter<AdapterInstallment.ViewHolder>{
    private List<InstallmentData> installmentData;
    private Context mContext;

    public void setItemList(List<InstallmentData> installmentData,Context context) {
        this.installmentData = installmentData;
        this.mContext=context;
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }

    @Override
    public AdapterInstallment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.installment_item_list, parent, false);
        AdapterInstallment.ViewHolder viewHolder = new AdapterInstallment.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterInstallment.ViewHolder holder, int position) {
        final InstallmentData installmentData1 = installmentData.get(position);
        holder.transactionId.setText(installmentData1.getTransactionId());
        holder.transactionAmount.setText("$ "+installmentData1.getTransactionAmount());
        holder.paymentMethod.setText(installmentData1.getPaymentMethod());
        holder.paymentDate.setText(installmentData1.getPaymentDate());
        holder.paymentTime.setText(installmentData1.getPaymentTime());
        String itemNumber=String.valueOf(position+1);
        holder.textviewHeading.setText("Installment "+itemNumber);

        if (itemNumber.matches("1")){
            holder.itemLl.setVisibility(View.VISIBLE);
            holder.textviewHeading.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
        }


        holder.textviewHeading.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= holder.textviewHeading.getRight() - holder.textviewHeading.getTotalPaddingRight()){
                        // your action for drawable click event
//                        Toast.makeText(mContext,"clicked image",Toast.LENGTH_SHORT ).show();
                        if (holder.itemLl.getVisibility()==View.VISIBLE){
                            holder.itemLl.setVisibility(View.GONE);
                            holder.textviewHeading.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
                        }else {
                            holder.itemLl.setVisibility(View.VISIBLE);
                            holder.textviewHeading.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                        }
                        return true;
                    }
                }
                return true;
            }
        });

    }


    @Override
    public int getItemCount() {
//        return manageTemplatesData.size();
        return installmentData == null ? 0 : installmentData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout itemLl;
        public TextView transactionId,transactionAmount,paymentMethod,textviewHeading,paymentDate,paymentTime;
        public ViewHolder(View itemView) {
            super(itemView);
            this.transactionId = itemView.findViewById(R.id.transaction_id);
            this.transactionAmount = itemView.findViewById(R.id.transaction_amount);
            this.paymentMethod = itemView.findViewById(R.id.payment_method);
            this.textviewHeading = itemView.findViewById(R.id.textview_heading);
            this.paymentDate = itemView.findViewById(R.id.payment_date);
            this.paymentTime = itemView.findViewById(R.id.payment_time);
            this.itemLl = itemView.findViewById(R.id.item_ll);
        }
    }
}