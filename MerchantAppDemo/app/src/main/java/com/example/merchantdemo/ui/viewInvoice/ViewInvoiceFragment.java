package com.example.merchantdemo.ui.viewInvoice;

import static com.example.merchantdemo.Dashbord.viewInvoiceDataSaved;
import static com.example.merchantdemo.ui.createInvoice.CreateInvoiceFragment.viewInvoiceData;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merchantdemo.Dashbord;
import com.example.merchantdemo.R;
import com.example.merchantdemo.dataClass.InstallmentData;
import com.example.merchantdemo.dataClass.ReportData;
import com.example.merchantdemo.dataClass.ViewInvoiceData;
import com.example.merchantdemo.databinding.FragmentViewInvoiceBinding;
import com.example.merchantdemo.helperClass.AdapterInstallment;
import com.example.merchantdemo.helperClass.AdapterViewInvoiceCustomerItems;
import com.example.merchantdemo.ui.createInvoice.CreateInvoiceFragment;
import com.example.merchantdemo.ui.manageTemplates.CreateTemplateFragment;
import com.example.merchantdemo.util.SharedPreferencesManager;
import com.example.merchantdemo.util.Utility;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.android.material.button.MaterialButton;

//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Properties;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
//import java.util.Base64;
import android.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class ViewInvoiceFragment extends Fragment {

    FragmentViewInvoiceBinding binding;
    AdapterViewInvoiceCustomerItems adapterViewInvoiceCustomerItems;
    private NavController navController;
    AdapterInstallment adapterInstallment;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_view_invoice, container, false);
        binding = FragmentViewInvoiceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


//        setInvoiceData();

        if (getArguments().getString("viewInvoice").matches("withoutPayment")) {
//            Toast.makeText(getContext(), "withoutPayment from viewInvoice", Toast.LENGTH_LONG).show();
            setInvoiceData();
        } else {
            setSavedInvoiceData(getArguments().getInt("position"));
//            Toast.makeText(getContext(), "withPayment from report", Toast.LENGTH_LONG).show();
        }

        binding.sendInvoiceFinal.setOnClickListener(view -> {
//            sendEmail(recipient, subject, body);
            sendInvoiceDialog();
//            SharedPreferencesManager.incrementInvoiceCount(getContext());
        });

        return root;
    }

    private void setSavedInvoiceData(int position) {
        binding.merchantName.setText(viewInvoiceDataSaved.get(position).getMerchantName());
        binding.merchantHeader.setText(viewInvoiceDataSaved.get(position).getMerchantHeader());
        binding.merchantLogo.setImageURI(Uri.parse(viewInvoiceDataSaved.get(position).getMerchantLogo()));
        binding.customerName.setText(viewInvoiceDataSaved.get(position).getCustomerName());
        binding.customerAddress.setText(viewInvoiceDataSaved.get(position).getCustomerAddress());
        binding.customerContactNo.setText(viewInvoiceDataSaved.get(position).getCustomerContactNo());
        binding.customerEmail.setText(viewInvoiceDataSaved.get(position).getCustomerEmail());
        binding.invoiceDateValue.setText(viewInvoiceDataSaved.get(position).getInvoiceDate());

        binding.invoiceItemRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterViewInvoiceCustomerItems = new AdapterViewInvoiceCustomerItems(viewInvoiceDataSaved.get(position).getItemData());
        binding.invoiceItemRv.setAdapter(adapterViewInvoiceCustomerItems);

        if (viewInvoiceDataSaved.get(position).getSubtotal() == null || viewInvoiceDataSaved.get(position).getSubtotal().matches("0")) {
            binding.subtotalTv.setVisibility(View.GONE);
            binding.subtotalValue.setVisibility(View.GONE);
        } else {
            binding.subtotalValue.setText("$ " + viewInvoiceDataSaved.get(position).getSubtotal());

        }

        if (viewInvoiceDataSaved.get(position).getDiscountValue() == null || viewInvoiceDataSaved.get(position).getDiscountValue().matches("0")) {
            binding.discountTv.setVisibility(View.GONE);
            binding.discountValue.setVisibility(View.GONE);
        } else {
            binding.discountTv.setText("Discount(" + viewInvoiceDataSaved.get(position).getDiscount() + "%)");
            binding.discountValue.setText("$ " + viewInvoiceDataSaved.get(position).getDiscountValue());
        }

        if (viewInvoiceDataSaved.get(position).getTaxValue() == null || viewInvoiceDataSaved.get(position).getTaxValue().matches("0")) {
            binding.taxTv.setVisibility(View.GONE);
            binding.taxValue.setVisibility(View.GONE);
        } else {
            binding.taxTv.setText("Tax(" + viewInvoiceDataSaved.get(position).getTax() + "%)");
            binding.taxValue.setText("$ " + viewInvoiceDataSaved.get(position).getTaxValue());
        }

        binding.totalValue.setText("$ " + viewInvoiceDataSaved.get(position).getTotalValue());
        binding.footer.setText(viewInvoiceDataSaved.get(position).getFooter());

        if (viewInvoiceDataSaved.get(position).getCustomField() != null && !viewInvoiceDataSaved.get(position).getCustomField().isEmpty()) {
            binding.customFieldLl.removeAllViews();
            for (Map.Entry<String, String> entry : viewInvoiceDataSaved.get(position).getCustomField().entrySet()) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dynamic_text_layout, binding.customFieldLl, false);

                // Find views within the inflated layout
                TextView textTitle = view.findViewById(R.id.dynamic_title_tv);
                TextView textTitleValue = view.findViewById(R.id.dynamic_title_value_tv);

                // Set key-value pair to TextView and EditText
                textTitle.setText(entry.getKey()+": ");
                textTitleValue.setText(entry.getValue());

                // Add TextInputLayout to the parent layout
                binding.customFieldLl.addView(view);

            }
        }

        binding.invoiceNoValue.setText(viewInvoiceDataSaved.get(position).getInvoiceNo());
        binding.sendInvoiceFinal.setVisibility(View.INVISIBLE);
        binding.paidCheckRl.setVisibility(View.VISIBLE);

        float instalmentValue = Float.parseFloat(viewInvoiceDataSaved.get(position).getTotalValue())/3;

        List<InstallmentData> installmentData = new ArrayList<>();
        installmentData.add(new InstallmentData("RS"+ UUID.randomUUID().toString(), String.valueOf(instalmentValue), "Credit card", Utility.getCurrentDateFormatted(), "11 am"));
        installmentData.add(new InstallmentData("RS"+ UUID.randomUUID().toString(), String.valueOf(instalmentValue), "Credit card", Utility.getCurrentDateFormatted(), "02:15 pm"));
        installmentData.add(new InstallmentData("RS"+ UUID.randomUUID().toString(), String.valueOf(instalmentValue), "Credit card", Utility.getCurrentDateFormatted(), "04 pm"));

        binding.installmentRv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterInstallment = new AdapterInstallment();

        adapterInstallment.setItemList(installmentData, getContext());
        binding.installmentRv.setAdapter(adapterInstallment);


    }


    private void setInvoiceData() {
        binding.merchantName.setText(viewInvoiceData.getMerchantName());
        binding.merchantHeader.setText(viewInvoiceData.getMerchantHeader());
        binding.merchantLogo.setImageURI(Uri.parse(viewInvoiceData.getMerchantLogo()));
        Log.e("TAG", "setInvoiceData MerchantLogo: "+viewInvoiceData.getMerchantLogo() );
        binding.customerName.setText(viewInvoiceData.getCustomerName());
        binding.customerAddress.setText(viewInvoiceData.getCustomerAddress());
        binding.customerContactNo.setText(viewInvoiceData.getCustomerContactNo());
        binding.customerEmail.setText(viewInvoiceData.getCustomerEmail());
        binding.invoiceDateValue.setText(Utility.getCurrentDateFormatted());

        binding.invoiceItemRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterViewInvoiceCustomerItems = new AdapterViewInvoiceCustomerItems(viewInvoiceData.getItemData());
        binding.invoiceItemRv.setAdapter(adapterViewInvoiceCustomerItems);

        if (viewInvoiceData.getSubtotal() == null || viewInvoiceData.getSubtotal().matches("0")) {
            binding.subtotalTv.setVisibility(View.GONE);
            binding.subtotalValue.setVisibility(View.GONE);
        } else {
            binding.subtotalValue.setText("$ " + viewInvoiceData.getSubtotal());

        }

        if (viewInvoiceData.getDiscountValue() == null || viewInvoiceData.getDiscountValue().matches("0")) {
            binding.discountTv.setVisibility(View.GONE);
            binding.discountValue.setVisibility(View.GONE);
        } else {
            binding.discountTv.setText("Discount(" + viewInvoiceData.getDiscount() + "%)");
            binding.discountValue.setText("$ " + viewInvoiceData.getDiscountValue());
        }

        if (viewInvoiceData.getTaxValue() == null || viewInvoiceData.getTaxValue().matches("0")) {
            binding.taxTv.setVisibility(View.GONE);
            binding.taxValue.setVisibility(View.GONE);
        } else {
            binding.taxTv.setText("Tax(" + viewInvoiceData.getTax() + "%)");
            binding.taxValue.setText("$ " + viewInvoiceData.getTaxValue());
        }

            binding.totalValue.setText("$ " + viewInvoiceData.getTotalValue());
            binding.footer.setText(viewInvoiceData.getFooter());

        if (viewInvoiceData.getCustomField() != null && !viewInvoiceData.getCustomField().isEmpty()) {
            binding.customFieldLl.removeAllViews();
            for (Map.Entry<String, String> entry : viewInvoiceData.getCustomField().entrySet()) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dynamic_text_layout, binding.customFieldLl, false);

                // Find views within the inflated layout
                TextView textTitle = view.findViewById(R.id.dynamic_title_tv);
                TextView textTitleValue = view.findViewById(R.id.dynamic_title_value_tv);

                // Set key-value pair to TextView and EditText
                textTitle.setText(entry.getKey()+": ");
                textTitleValue.setText(entry.getValue());

                // Add TextInputLayout to the parent layout
                binding.customFieldLl.addView(view);

            }
        }

//        if (getArguments().getString("viewInvoice").matches("withoutPayment")) {
//            Toast.makeText(getContext(), "withoutPayment from viewInvoice", Toast.LENGTH_LONG).show();
            binding.invoiceNoValue.setText("INV-" + SharedPreferencesManager.getInvoiceCount(getContext()));
            binding.sendInvoiceFinal.setVisibility(View.VISIBLE);
            binding.paidCheckRl.setVisibility(View.INVISIBLE);
//        } else {
//            Toast.makeText(getContext(), "withPayment from report", Toast.LENGTH_LONG).show();
//            binding.invoiceNoValue.setText(viewInvoiceData.getInvoiceNo());
//            binding.sendInvoiceFinal.setVisibility(View.INVISIBLE);
//            binding.paidCheckRl.setVisibility(View.VISIBLE);
//
//            float instalmentValue = Float.parseFloat(viewInvoiceData.getTotalValue())/3;
//
//            List<InstallmentData> installmentData = new ArrayList<>();
//            installmentData.add(new InstallmentData("RS"+ UUID.randomUUID().toString(), String.valueOf(instalmentValue), "Credit card", Utility.getCurrentDateFormatted(), "11 am"));
//            installmentData.add(new InstallmentData("RS"+ UUID.randomUUID().toString(), String.valueOf(instalmentValue), "Credit card", Utility.getCurrentDateFormatted(), "02:15 pm"));
//            installmentData.add(new InstallmentData("RS"+ UUID.randomUUID().toString(), String.valueOf(instalmentValue), "Credit card", Utility.getCurrentDateFormatted(), "04 pm"));
//
//                    binding.installmentRv.setLayoutManager(new LinearLayoutManager(getContext()));
//
//            adapterInstallment = new AdapterInstallment();
//
//            adapterInstallment.setItemList(installmentData, getContext());
//            binding.installmentRv.setAdapter(adapterInstallment);
//        }


    }

    private void setInvoiceData1() {
        if (!getArguments().getString("merchant_name").isEmpty()) {
            binding.merchantName.setText(getArguments().getString("merchant_name"));
        } else {
            binding.merchantName.setVisibility(View.GONE);
        }

        if (!getArguments().getString("merchant_header").isEmpty()) {
            binding.merchantHeader.setText(getArguments().getString("merchant_header"));
        } else {
            binding.merchantHeader.setVisibility(View.GONE);
        }

        if (getArguments().getString("merchant_logo") != null) {
            binding.merchantLogo.setImageURI(Uri.parse(getArguments().getString("merchant_logo")));
        } else {
            binding.merchantLogo.setVisibility(View.GONE);
        }

        if (!getArguments().getString("customer_name").isEmpty()) {
            binding.customerName.setText(getArguments().getString("customer_name"));
        } else {
            binding.customerName.setVisibility(View.GONE);
        }

        if (!getArguments().getString("customer_address").isEmpty()) {
            binding.customerAddress.setText(getArguments().getString("customer_address"));
        } else {
            binding.customerAddress.setVisibility(View.GONE);
        }

        if (!getArguments().getString("customer_contact_no").isEmpty()) {
            binding.customerContactNo.setText(getArguments().getString("customer_contact_no"));
        } else {
            binding.customerContactNo.setVisibility(View.GONE);
        }

        if (!getArguments().getString("customer_email").isEmpty()) {
            binding.customerEmail.setText(getArguments().getString("customer_email"));
        } else {
            binding.customerEmail.setVisibility(View.GONE);
        }

        binding.invoiceNoValue.setText("INV-" + SharedPreferencesManager.getInvoiceCount(getContext()));
        binding.invoiceDateValue.setText(Utility.getCurrentDateFormatted());

        binding.invoiceItemRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterViewInvoiceCustomerItems = new AdapterViewInvoiceCustomerItems(CreateInvoiceFragment.itemlistSaved);
        binding.invoiceItemRv.setAdapter(adapterViewInvoiceCustomerItems);

        if (getArguments().getString("subtotal_amount") != null) {
            binding.subtotalValue.setText("$ " + getArguments().getString("subtotal_amount"));
        } else {
            binding.subtotalTv.setVisibility(View.GONE);
            binding.subtotalValue.setVisibility(View.GONE);
        }

        if (!getArguments().getString("total_dicount").trim().isEmpty()) {
            binding.discountTv.setText("Discount(" + getArguments().getString("total_dicount") + "%)");
            binding.discountValue.setText("$ " + getArguments().getString("total_dicount_value"));
        } else {
            binding.discountTv.setVisibility(View.GONE);
            binding.discountValue.setVisibility(View.GONE);
        }

        if (!getArguments().getString("total_tax").trim().isEmpty()) {
            binding.taxTv.setText("Tax(" + getArguments().getString("total_tax") + "%)");
            binding.taxValue.setText("$ " + getArguments().getString("total_tax_value"));
        } else {
            binding.taxTv.setVisibility(View.GONE);
            binding.taxValue.setVisibility(View.GONE);
        }

        if (!getArguments().getString("total_amount").trim().isEmpty()) {
            binding.totalValue.setText("$ " + getArguments().getString("total_amount"));
        } else {
            binding.totalValue.setVisibility(View.GONE);
        }

        if (!getArguments().getString("merchant_footer").trim().isEmpty()) {
            binding.footer.setText(getArguments().getString("merchant_footer"));
        } else {
            binding.footer.setVisibility(View.GONE);
        }
    }

    private void sendEmail() {
        try {
            // Defining sender's email and password
//            final String senderEmail = "rsdevelopmenttest@gmail.com";
//            final String password = "simanjitnayak";
//            final String password = "jxpxpvaeclshxtvr";

            final String senderEmail = "enterpriseglobal92@gmail.com";
            final String password = "hnmxxkiqasvxveyk";

            // Defining receiver's email
//            final String receiverEmail = "rsdevelopmenttest@gmail.com";
//            final String receiverEmail = "simanjitn@rssoftware.co.in";
            final String receiverEmail = viewInvoiceData.getCustomerEmail();
//            final String[] receiverEmail = {"rsdevelopmenttest@gmail.com", "simanjitn@rssoftware.co.in"};

            // Defining the SMTP server host
            final String stringHost = "smtp.gmail.com";

            // Setting up email properties
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            // Creating a session with authentication
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, password);
                }
            });

            // Creating a MimeMessage
            MimeMessage mimeMessage = new MimeMessage(session);

            // Adding the recipient's email address
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));

//            for (String recipient : receiverEmail) {
//                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
//            }

            // Setting the subject and message content
            // You can Specify yours
            mimeMessage.setSubject("New invoice from "+viewInvoiceData.getMerchantName());
//            mimeMessage.setText(message+"\n"+ "new line testing");

            // Convert image to byte array
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_icon);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String imageData = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                imageData = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
            }

            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.email_logo_header);
            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos1);
            byte[] imageBytes1 = baos1.toByteArray();
            String imageData1 = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                imageData1 = Base64.encodeToString(imageBytes1, Base64.NO_WRAP);
            }

            // HTML content with dynamic image and button design
//            String htmlContent = "<html><body>"
//                    + "<p>Hello,</p>"
//                    + "<p>Thank you for your interest in our products. To proceed with your payment, please click the button below:</p>"
//                    + "<p><a href=\"https://www.example.com/payment\" style=\"background-color:#FF5722; color:#FFFFFF; padding:10px 20px; text-decoration:none; border-radius:4px;\">Pay Now</a></p>"
//                    + "<p>If you have any questions, feel free to contact us.</p>"
//                    + "<p>Best regards,<br/>Your Company</p>"
//                    + "<img src=\"data:image/png;base64," + imageData + "\" alt=\"Image\">"
//                    + "</body></html>";


            String htmlContentHead = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<style>" +
                    ".container {" +
                    "    display: flex;" +
                    "    align-items: center; /* Vertically center the items */" +
                    "    flex-direction: column; /* Arrange items in a column */" +
                    "}" +
                    ".image-container {" +
                    "    display: flex;" +
                    "    align-items: center; /* Vertically center the items */" +
                    "    margin-bottom: 25px; /* Add space between image and text */" +

                    "}" +
                    ".image-container1 {" +
                    "    display: flex;" +
                    "    align-items: center; /* Vertically center the items */" +
                    "    margin-bottom: 10px; /* Add space between image and text */" +
                    "}" +
                    ".text {" +
                    "    font-size: 18px; /* Increase font size */" +
                    "    text-align: center; /* Center text horizontally */" +
                    "    margin-left: 10px; /* Add space between image and text */" +
                    "    font-weight: bold; /* Make text bold */" +
                    "}" +
                    ".text1 {" +
                    "    font-size: 18px; /* Increase font size */" +
                    "    text-align: center; /* Center text horizontally */" +
                    "    font-weight: bold; /* Make text bold */" +
                    "}" +
                    ".textAmount {" +
                    "    font-size: 15px; /* Increase font size */" +
                    "    margin-top: 30px; " +
                    "    color: #44b2ff; /* Set color for Title or Description for Image 2 */" +
                    "}" +
                    ".textAmountValue {" +
                    "    font-size: 35px; /* Increase font size */" +
                    "    font-weight: bold; /* Make text bold */" +
                    "}" +
                    ".button {" +
//                    "    background-color: #44b2ff;" +
//                    "    color: #FFFFFF; /* Button text color */" +
                    "    padding: 10px 40px;" +
                    "    text-decoration: none;" +
                    "    border-radius: 8px;" +
                    "    margin-top: 40px; " +
                    "}" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"container\">" +
                    "    <div class=\"image-container\">" +
                    "        <img src=\"data:image/png;base64," + imageData + "\" alt=\"Image 1\" style=\"width: 28px; height: 28px;\">" + // Replace with actual image URL
                    "        <div class=\"text\">" +
                    "            Central Infrastructure" +
                    "        </div>" +
                    "    </div>" +
                    "    <div class=\"image-container1\">" +
                    "        <img src=\"data:image/png;base64," + imageData1 + "\" alt=\"Image 2\" style=\"width: 300px; height: auto;\">" + // Replace with actual image URL
                    "    </div>" +
                    "    <div class=\"text1\">" +
                    "        New invoice from" +
                    "        <br>"+viewInvoiceData.getMerchantName() +
                    "    </div>" +
                    "    <div class=\"textAmount\">" +
                    "        Total Amount" +
                    "    </div>" +
                    "    <div class=\"textAmountValue\">" +
                          "$ "+viewInvoiceData.getTotalValue() +
                    "    </div>" +
                    "<a href=\"#\" class=\"button\" style=\"background-color: #44b2ff; color: #FFFFFF; \">Pay Now</a>" +
                    "</div>" +
                    "</body>" +
                    "</html>";






//            String htmlContentforoutlookandgmail  = "<html><head>" +
//                    "<!--[if mso]>" +
//                    "<xml>" +
//                    "<o:OfficeDocumentSettings>" +
//                    "<o:AllowPNG/>" +
//                    "<o:PixelsPerInch>96</o:PixelsPerInch>" +
//                    "</o:OfficeDocumentSettings>" +
//                    "</xml>" +
//                    "<style>" +
//                    ".button { mso-padding-alt:10px 20px; border-radius:4px; background-color:#FF5722; display:inline-block; font-family:Arial, sans-serif; font-size:14px; color:#ffffff; text-decoration:none; }" +
//                    "</style>" +
//                    "<![endif]-->" +
//                    "</head>" +
//                    "<body>" +
//
//                     "<p>Thank you for your interest in our products. To proceed with your payment, please click the button below:</p>"
//                    +
//
//
//                    "<!--[if mso]>" +
//                    "<v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"https://www.example.com\" style=\"height:40px;v-text-anchor:middle;width:100px;\" arcsize=\"10%\" strokecolor=\"#FF5722\" fillcolor=\"#FF5722\">" +
//                    "<w:anchorlock/>" +
//                    "<center style=\"color:#ffffff;font-family:Arial, sans-serif;font-size:14px;\">Pay Now</center>" +
//                    "</v:roundrect>" +
//                    "<![endif]-->" +
//                    "<!--[if !mso]><!-- -->" +
//                    "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">" +
//                    "<tr>" +
//                    "<td align=\"center\">" +
//                    "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" +
//                    "<tr>" +
//                    "<td bgcolor=\"#FF5722\" style=\"border-radius:4px;\">" +
//                    "<a href=\"https://www.example.com\" style=\"background-color:#FF5722;color:#FFFFFF;padding:10px 20px;text-decoration:none;border-radius:4px;display:inline-block;font-size:14px;font-family:Arial, sans-serif;\">Pay Now</a>" +
//                    "</td>" +
//                    "</tr>" +
//                    "</table>" +
//                    "</td>" +
//                    "</tr>" +
//                    "</table>" +
//                    "<!--<![endif]-->" +
//
//                    "<p>If you have any questions, feel free to contact us.</p>"
//                    + "<p>Best regards,<br/>Your Company</p>"+
//
//                    "</body>" +
//                    "</html>";




            mimeMessage.setContent(htmlContentHead, "text/html");


            // Creating a separate thread to send the email
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
//                        Thread.currentThread().setContextClassLoader( getClass().getClassLoader() );
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        // Handling messaging exception
                        // Note: You cannot directly use Toast in a non-activity class
                        // Consider using Log or a callback mechanism to handle errors in a Java class
                        e.printStackTrace();
                        Log.e("TAG", "sendEmail RunMessagingException: "+e );
                    }
                }
            });
            t.start();
        } catch (AddressException e) {
            // Handling address exception
            e.printStackTrace();
            Log.e("TAG", "sendEmail AddressException: "+e );
        } catch (MessagingException e) {
            // Handling messaging exception (e.g. network error)
            e.printStackTrace();
            Log.e("TAG", "sendEmail MessagingException: "+e );
        }

        // Displaying a toast message indicating that the email was sent successfully
        // Note: You cannot directly use Toast in a non-activity class
        // Consider using Log or a callback mechanism to indicate success in a Java class
    }

    public void sendInvoiceDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.send_invoice_dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        CheckBox sms = dialog.findViewById(R.id.sms);
        CheckBox whatsapp = dialog.findViewById(R.id.whatsapp);
        CheckBox email = dialog.findViewById(R.id.email);
        MaterialButton sendInvoiceBtn = dialog.findViewById(R.id.send_invoice_btn);
        MaterialButton cancelSendInvoiceBtn = dialog.findViewById(R.id.cancel_send_invoice_btn);

        sendInvoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (/*sms.isChecked() || whatsapp.isChecked() ||*/ email.isChecked() && viewInvoiceData.getCustomerEmail()!=null && !viewInvoiceData.getCustomerEmail().isEmpty()) {
                    viewInvoiceData.setInvoiceNo(binding.invoiceNoValue.getText().toString());
                    viewInvoiceData.setInvoiceDate(binding.invoiceDateValue.getText().toString());
                    SharedPreferencesManager.incrementInvoiceCount(getContext());
                    dialog.dismiss();
//                    ReportData reportData = new ReportData();
                    Log.e("tag","34534edhb2eb8778555"+ viewInvoiceDataSaved);

                        Log.e("tag","34534edhb2eb877855500 "+ "yes printing");
                    sendEmail();
                        viewInvoiceDataSaved.add(viewInvoiceData);
                        viewInvoiceData=null;



                    Log.e("tag","34534edhb2eb877855522"+ viewInvoiceDataSaved);
                    Toast.makeText(getContext(), "Invoice sent successfully", Toast.LENGTH_SHORT).show();
                    navController.popBackStack(R.id.nav_home, false);
                } else {
                    if (viewInvoiceData.getCustomerEmail()==null || viewInvoiceData.getCustomerEmail().isEmpty()){
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Please provide email to send invoice", Toast.LENGTH_SHORT).show();
                    }else {
                        sms.setChecked(false);
                        whatsapp.setChecked(false);
                        Toast.makeText(getContext(), "Please select email to send invoice", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), "Please select a medium to send invoice", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        cancelSendInvoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();

    }


//    public static void sendEmail(String recipientEmail, String subject, String messageBody) throws MessagingException {
//
//        // Set up mail server properties
//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", "your_smtp_server");
//        properties.put("mail.smtp.port", "your_smtp_port");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true"); // if required
//
//        // Create session with authentication
//        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("your_email", "your_password");
//            }
//        });
//
//        try {
//            // Create a default MimeMessage object
//            MimeMessage message = new MimeMessage(session);
//
//            // Set From: header field
//            message.setFrom(new InternetAddress("your_email"));
//
//            // Set To: header field
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
//
//            // Set Subject: header field
//            message.setSubject(subject);
//
//            // Set message body
//            message.setText(messageBody);
//
//            // Send message
//            Transport.send(message);
//
//            System.out.println("Email sent successfully.");
//
//        } catch (MessagingException e) {
//            throw new RuntimeException("Failed to send email", e);
//        }
//    }


    private void displayPDF(String fileUri) {
        File pdfFile = new File(fileUri);
        binding.pdfView.fromFile(pdfFile)
                .defaultPage(0)
                .enableSwipe(true)
                .enableDoubletap(true)
                .swipeHorizontal(false)
                .onLoad(nbPages -> {
                })
                .onPageChange((page, pageCount) -> {
                })
                .scrollHandle(new DefaultScrollHandle(getContext()))
                .enableAnnotationRendering(true)
                .password(null)
                .pageFitPolicy(FitPolicy.WIDTH)
                .load();
    }

    public static String getImagePathFromUri(Context context, Uri uri) {
        String filePath = "";
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    filePath = cursor.getString(columnIndex);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        return filePath;

    }

    private byte[] readImageDataFromUri(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        return outputStream.toByteArray();
    }
}