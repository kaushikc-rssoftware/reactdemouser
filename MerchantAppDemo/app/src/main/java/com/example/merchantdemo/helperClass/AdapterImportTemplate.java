package com.example.merchantdemo.helperClass;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.merchantdemo.R;
import com.example.merchantdemo.dataClass.ManageTemplatesData;
import com.example.merchantdemo.ui.createInvoice.CreateInvoiceFragment;

import java.util.List;

public class AdapterImportTemplate extends RecyclerView.Adapter<AdapterImportTemplate.ViewHolder> {
    private List<ManageTemplatesData> manageTemplatesData;
    private Context mContext;
    private AdapterImportTemplate.OnItemClickListener onItemClickListener;


    public void setItemList(List<ManageTemplatesData> manageTemplatesData, Context context) {
        this.manageTemplatesData = manageTemplatesData;
        this.mContext = context;
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(AdapterImportTemplate.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public AdapterImportTemplate.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.import_template_list_item, parent, false);
        AdapterImportTemplate.ViewHolder viewHolder = new AdapterImportTemplate.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterImportTemplate.ViewHolder holder, int position) {

        final ManageTemplatesData manageTemplatesData1 = manageTemplatesData.get(position);

        holder.importTemplateName.setText(manageTemplatesData1.getTemplateName());
        holder.importTemplateRl.setOnClickListener(view -> {
//            Toast.makeText(view.getContext(), holder.importTemplateName.getText(),Toast.LENGTH_LONG).show();
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return manageTemplatesData == null ? 0 : manageTemplatesData.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout importTemplateRl;
        public TextView importTemplateName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.importTemplateRl = itemView.findViewById(R.id.import_template_rl);
            this.importTemplateName = itemView.findViewById(R.id.import_template_name);
        }
    }
}