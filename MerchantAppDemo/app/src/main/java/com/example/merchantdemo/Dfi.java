package com.example.merchantdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.merchantdemo.databinding.ActivityDfiBinding;
import com.example.merchantdemo.helperClass.AdapterViewInvoiceCustomerItems;
import com.example.merchantdemo.ui.createInvoice.CreateInvoiceFragment;
import com.example.merchantdemo.util.SharedPreferencesManager;
import com.example.merchantdemo.util.Utility;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Dfi extends AppCompatActivity {

    ActivityDfiBinding binding;
    AdapterViewInvoiceCustomerItems adapterViewInvoiceCustomerItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dfi);
        binding=ActivityDfiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }

//        if (CreateInvoiceFragment.bundle != null) {
//            String data = CreateInvoiceFragment.bundle.getString("merchant_name");
//            // Update UI with the data
//            tv.setText(data);
//        }
        binding.back.setOnClickListener(view -> {finish();});

        setInvoiceData();
    }

    private void setInvoiceData(){
        if (CreateInvoiceFragment.bundle != null) {
            if (!CreateInvoiceFragment.bundle.getString("merchant_name").isEmpty()) {
                binding.merchantName.setText(CreateInvoiceFragment.bundle.getString("merchant_name"));
            } else {
                binding.merchantName.setVisibility(View.GONE);
            }

            if (!CreateInvoiceFragment.bundle.getString("merchant_header").isEmpty()) {
                binding.merchantHeader.setText(CreateInvoiceFragment.bundle.getString("merchant_header"));
            } else {
                binding.merchantHeader.setVisibility(View.GONE);
            }

            if (CreateInvoiceFragment.bundle.getString("merchant_logo") != null) {
                binding.merchantLogo.setImageURI(Uri.parse(CreateInvoiceFragment.bundle.getString("merchant_logo")));
            } else {
                binding.merchantLogo.setVisibility(View.GONE);
            }

            if (!CreateInvoiceFragment.bundle.getString("customer_name").isEmpty()) {
                binding.customerName.setText(CreateInvoiceFragment.bundle.getString("customer_name"));
            } else {
                binding.customerName.setVisibility(View.GONE);
            }

            if (!CreateInvoiceFragment.bundle.getString("customer_address").isEmpty()) {
                binding.customerAddress.setText(CreateInvoiceFragment.bundle.getString("customer_address"));
            } else {
                binding.customerAddress.setVisibility(View.GONE);
            }

            if (!CreateInvoiceFragment.bundle.getString("customer_contact_no").isEmpty()) {
                binding.customerContactNo.setText(CreateInvoiceFragment.bundle.getString("customer_contact_no"));
            } else {
                binding.customerContactNo.setVisibility(View.GONE);
            }

            if (!CreateInvoiceFragment.bundle.getString("customer_email").isEmpty()) {
                binding.customerEmail.setText(CreateInvoiceFragment.bundle.getString("customer_email"));
            } else {
                binding.customerEmail.setVisibility(View.GONE);
            }

            binding.invoiceNoValue.setText("INV-" + SharedPreferencesManager.getInvoiceCount(getApplicationContext()));
            binding.invoiceDateValue.setText(Utility.getCurrentDateFormatted());

            binding.invoiceItemRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapterViewInvoiceCustomerItems = new AdapterViewInvoiceCustomerItems(CreateInvoiceFragment.itemlistSaved);
            binding.invoiceItemRv.setAdapter(adapterViewInvoiceCustomerItems);

            if (CreateInvoiceFragment.bundle.getString("subtotal_amount") != null) {
                binding.subtotalValue.setText("$ " + CreateInvoiceFragment.bundle.getString("subtotal_amount"));
            } else {
                binding.subtotalTv.setVisibility(View.GONE);
                binding.subtotalValue.setVisibility(View.GONE);
            }

            if (!CreateInvoiceFragment.bundle.getString("total_dicount").trim().isEmpty()) {
                binding.discountTv.setText("Discount(" + CreateInvoiceFragment.bundle.getString("total_dicount") + "%)");
                binding.discountValue.setText("$ " + CreateInvoiceFragment.bundle.getString("total_dicount_value"));
            } else {
                binding.discountTv.setVisibility(View.GONE);
                binding.discountValue.setVisibility(View.GONE);
            }

            if (!CreateInvoiceFragment.bundle.getString("total_tax").trim().isEmpty()) {
                binding.taxTv.setText("Tax(" + CreateInvoiceFragment.bundle.getString("total_tax") + "%)");
                binding.taxValue.setText("$ " + CreateInvoiceFragment.bundle.getString("total_tax_value"));
            } else {
                binding.taxTv.setVisibility(View.GONE);
                binding.taxValue.setVisibility(View.GONE);
            }

            if (!CreateInvoiceFragment.bundle.getString("total_amount").trim().isEmpty()) {
                binding.totalValue.setText("$ " + CreateInvoiceFragment.bundle.getString("total_amount"));
            } else {
                binding.totalValue.setVisibility(View.GONE);
            }

            if (!CreateInvoiceFragment.bundle.getString("merchant_footer").trim().isEmpty()) {
                binding.footer.setText(CreateInvoiceFragment.bundle.getString("merchant_footer"));
            } else {
                binding.footer.setVisibility(View.GONE);
            }
        }
    }
}