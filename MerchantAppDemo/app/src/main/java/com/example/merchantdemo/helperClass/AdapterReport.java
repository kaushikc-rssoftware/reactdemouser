package com.example.merchantdemo.helperClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.merchantdemo.R;
import com.example.merchantdemo.dataClass.ReportData;

import java.util.List;

public class AdapterReport extends RecyclerView.Adapter<AdapterReport.ViewHolder> {
    private List<ReportData> reportList;
    private Context mContext;
    private AdapterReport.OnItemClickListener onItemClickListener;

    public void setItemList(List<ReportData> reportList, Context context) {
        this.reportList = reportList;
        this.mContext = context;
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(AdapterReport.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public AdapterReport.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.report_item_list, parent, false);
        AdapterReport.ViewHolder viewHolder = new AdapterReport.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterReport.ViewHolder holder, int position) {
        final ReportData reportList1 = reportList.get(position);
//        Log.e("TAG", "onItemClick8355052453: "+viewInvoiceData.getCustomerName() );
//        if (position==1 && viewInvoiceData.getCustomerName() != null && !viewInvoiceData.getCustomerName().trim().isEmpty()) {
//            holder.invoiceNumber.setText(viewInvoiceData.getInvoiceNo());
//            holder.customerName.setText(viewInvoiceData.getCustomerName());
//            holder.amount.setText("$ "+viewInvoiceData.getTotalValue());
//            holder.status.setText(reportList1.getStatus());
//
//            holder.reportItemRl.setOnClickListener(view -> {
//                if (onItemClickListener != null) {
//                    onItemClickListener.onItemClick(position);
//                }
//            });
//        }else {
        holder.invoiceNumber.setText(reportList1.getInvoiceNumber());
        holder.customerName.setText(reportList1.getCustomerName());
        holder.amount.setText("$ " + reportList1.getAmount());
        holder.status.setText(reportList1.getStatus());

        if (reportList1.getStatus() == R.string.report_status_done/*.matches(String.valueOf(R.string.report_status_done))*/) {
            holder.reportItemRl.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }

//        }

    }


    @Override
    public int getItemCount() {
        return reportList == null ? 0 : reportList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView invoiceNumber, customerName, amount, status;
        public RelativeLayout reportItemRl;

        public ViewHolder(View itemView) {
            super(itemView);
            this.invoiceNumber = itemView.findViewById(R.id.report_invoice_number);
            this.customerName = itemView.findViewById(R.id.report_customer_name);
            this.amount = itemView.findViewById(R.id.report_amount);
            this.status = itemView.findViewById(R.id.report_status);
            this.reportItemRl = itemView.findViewById(R.id.report_item_rl);
        }
    }
}