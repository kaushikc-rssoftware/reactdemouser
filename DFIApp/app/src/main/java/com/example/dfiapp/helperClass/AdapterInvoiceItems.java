package com.example.dfiapp.helperClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dfiapp.R;
import com.example.dfiapp.dataClass.InvoiceItemData;

import java.util.List;

public class AdapterInvoiceItems extends RecyclerView.Adapter<AdapterInvoiceItems.ViewHolder>{
    private List<InvoiceItemData> invoiceItemData;
    private Context mContext;

// RecyclerView recyclerView;
//    public AdapterManageTemplates(List<ManageTemplatesData> manageTemplatesData) {
//        this.manageTemplatesData = manageTemplatesData;
//    }

//    public AdapterReport(List<ReportData> searchResults) {
//        this.reportList = searchResults;
//    }

    public AdapterInvoiceItems(List<InvoiceItemData> invoiceItemData,Context context) {
        this.invoiceItemData = invoiceItemData;
        this.mContext=context;
//        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }


    @Override
    public AdapterInvoiceItems.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.invoice_item_list, parent, false);
        AdapterInvoiceItems.ViewHolder viewHolder = new AdapterInvoiceItems.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterInvoiceItems.ViewHolder holder, int position) {
        final InvoiceItemData invoiceItemData1 = invoiceItemData.get(position);
        holder.invoiceItemName.setText(invoiceItemData1.getInvoiceItems());
        holder.invoiceItemQuantity.setText(invoiceItemData1.getInvoiceQuantity());
        holder.invoiceItemPrice.setText(invoiceItemData1.getInvoicePrice());
        holder.invoiceItemTax.setText(invoiceItemData1.getInvoiceTax());
        holder.invoiceItemAmount.setText(invoiceItemData1.getInvoiceAmount());

    }


    @Override
    public int getItemCount() {
        return invoiceItemData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView invoiceItemName,invoiceItemQuantity,invoiceItemPrice,invoiceItemTax,invoiceItemAmount;
        public ViewHolder(View itemView) {
            super(itemView);
            this.invoiceItemName = itemView.findViewById(R.id.invoice_item_name);
            this.invoiceItemQuantity = itemView.findViewById(R.id.invoice_item_quantity);
            this.invoiceItemPrice = itemView.findViewById(R.id.invoice_item_price);
            this.invoiceItemTax = itemView.findViewById(R.id.invoice_item_tax);
            this.invoiceItemAmount = itemView.findViewById(R.id.invoice_item_amount);
        }
    }
}