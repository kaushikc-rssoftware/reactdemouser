package com.example.merchantdemo.helperClass;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.merchantdemo.R;
import com.example.merchantdemo.dataClass.ManageTemplatesData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AdapterManageTemplates extends RecyclerView.Adapter<AdapterManageTemplates.ViewHolder> {
    public static List<ManageTemplatesData> manageTemplatesData;
    private Context mContext;
    private AdapterManageTemplates.OnItemClickListener onItemClickListener;


    public void setItemList(List<ManageTemplatesData> manageTemplatesData, Context context) {
        this.manageTemplatesData = manageTemplatesData;
        this.mContext = context;
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }


    // Interface definition for item click handling
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onEditClick(int position);

        void onDeleteClick(int position);
    }

    // Method to set the click listener
    public void setOnItemClickListener(AdapterManageTemplates.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.manage_templates_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ManageTemplatesData manageTemplatesData1 = manageTemplatesData.get(position);

        holder.templateName.setText(manageTemplatesData1.getTemplateName());
        holder.merchantName.setText(manageTemplatesData1.getMerchantName());
        if (manageTemplatesData1.getMerchantlogo()!=null) {
            holder.merchantLogo.setImageURI(Uri.parse(manageTemplatesData1.getMerchantlogo()));
        }

        holder.editMerchantTemplate.setOnClickListener(view -> {
            if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                if (onItemClickListener != null) {
                    onItemClickListener.onEditClick(holder.getAdapterPosition());
                }
            }

        });

        holder.deleteMerchantTemplate.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onDeleteClick(position);
            }
        });
        holder.arrowMerchantTemplate.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });

    }

    public ManageTemplatesData getItem(int position) {
        return manageTemplatesData.get(position);
    }

    @Override
    public int getItemCount() {
        return manageTemplatesData == null ? 0 : manageTemplatesData.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView merchantLogo, editMerchantTemplate, deleteMerchantTemplate, arrowMerchantTemplate;
        public TextView templateName, merchantName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.merchantLogo = itemView.findViewById(R.id.merchant_logo);
            this.templateName = itemView.findViewById(R.id.template_name);
            this.merchantName = itemView.findViewById(R.id.merchant_name);
            this.editMerchantTemplate = itemView.findViewById(R.id.edit_merchant_template);
            this.deleteMerchantTemplate = itemView.findViewById(R.id.delete_merchant_template);
            this.arrowMerchantTemplate = itemView.findViewById(R.id.arrow_merchant_template);
        }
    }
}