package com.example.merchantdemo.ui.createInvoice;


import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merchantdemo.Dashbord;
import com.example.merchantdemo.R;
import com.example.merchantdemo.dataClass.ItemData;
import com.example.merchantdemo.dataClass.ManageTemplatesData;
import com.example.merchantdemo.dataClass.ViewInvoiceData;
import com.example.merchantdemo.databinding.FragmentCreateInvoiceBinding;
import com.example.merchantdemo.helperClass.AdapterCreateInvoiceCustomerItems;
import com.example.merchantdemo.helperClass.AdapterImportTemplate;
import com.example.merchantdemo.helperClass.AdapterManageTemplates;
import com.example.merchantdemo.util.SharedPreferencesManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CreateInvoiceFragment extends Fragment {

    private FragmentCreateInvoiceBinding binding;
    private NavController navController;
    MaterialButton addItemValueBtn, cancelItemValueBtn;
    TextView amount;
    EditText quantity, price, itemName;
    private AdapterCreateInvoiceCustomerItems adapterCreateInvoiceCustomerItems;
    CreateInvoiceViewModel createInvoiceViewModel;
    public static List<ItemData> itemlistSaved = new ArrayList<>();
    List<ItemData> itemsEditValue = new ArrayList<>();
    String discount, tax;
    public static Bundle bundle = new Bundle();

//    public static void setData(Bundle bundle) {
//        bundle = bundle;
//    }

    private static final int REQUEST_GALLERY = 1;
    Uri imagepathstatic;
    boolean isImportTemplate=false;
    int importTemplatePosition;
    public static ViewInvoiceData viewInvoiceData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createInvoiceViewModel = new ViewModelProvider(this).get(CreateInvoiceViewModel.class);

        binding = FragmentCreateInvoiceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewInvoiceData = new ViewInvoiceData();

//        final TextView textView = binding.textCreateInvoice;
//        createInvoiceViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        if (isImportTemplate){
            importTemplate(importTemplatePosition);
        }else if ((getArguments() != null ? getArguments().getString("importTemplate") : null) !=null){
            importTemplate(Integer.parseInt(getArguments().getString("importTemplate")));
        }

//        Log.e("TAG", "importtemplate5646 "+getArguments().getString("importTemplate") );

        binding.addItemBtn.setOnClickListener(view -> {
            addItemDialog("new");
        });


        binding.addedItemRv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterCreateInvoiceCustomerItems = new AdapterCreateInvoiceCustomerItems();
        binding.addedItemRv.setAdapter(adapterCreateInvoiceCustomerItems);

        adapterCreateInvoiceCustomerItems.setOnItemClickListener(new AdapterCreateInvoiceCustomerItems.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                List<ItemData> items = createInvoiceViewModel.getItemList().getValue();
                if (items != null && position >= 0 && position < items.size()) {
                    items.remove(position);
                    if (items.isEmpty()) {
                        binding.taxAndTotalRl.setVisibility(View.GONE);
                        binding.customerItemDiscountEt.setText("");
                        binding.customerItemTaxEt.setText("");
                    }
                    if ((discount != null && !discount.isEmpty()) && (tax != null && !tax.isEmpty())) {
                        createInvoiceViewModel.calculateTax(items, new BigDecimal(discount), new BigDecimal(tax));
                    } else if (discount != null && !discount.isEmpty()) {
                        createInvoiceViewModel.calculateTax(items, new BigDecimal(discount), BigDecimal.ZERO);
                    } else if (tax != null && !tax.isEmpty()) {
                        createInvoiceViewModel.calculateTax(items, BigDecimal.ZERO, new BigDecimal(tax));
                    } else {
                        createInvoiceViewModel.calculateTax(items, BigDecimal.ZERO, BigDecimal.ZERO);
                    }
                    adapterCreateInvoiceCustomerItems.notifyDataSetChanged(); // Notify adapter about the change
                }
            }

            @Override
            public void onEditClick(int position) {
//                ItemData item = itemList.get(position);

                itemsEditValue.add(createInvoiceViewModel.getItemList().getValue().get(position));
                Log.e("TAG", "onEditClickhdjxe: " + itemsEditValue);
                addItemDialog(String.valueOf(position));


            }
        });


        createInvoiceViewModel.getItemList().observe(this, items -> {
            adapterCreateInvoiceCustomerItems.setItemList(items, getContext());
//            createInvoiceViewModel.calculateTax(items.get(0));
            if ((discount != null && !discount.isEmpty()) && (tax != null && !tax.isEmpty())) {
                createInvoiceViewModel.calculateTax(items, new BigDecimal(discount), new BigDecimal(tax));
            } else if (discount != null && !discount.isEmpty()) {
                createInvoiceViewModel.calculateTax(items, new BigDecimal(discount), BigDecimal.ZERO);
            } else if (tax != null && !tax.isEmpty()) {
                createInvoiceViewModel.calculateTax(items, BigDecimal.ZERO, new BigDecimal(tax));
            } else {
                createInvoiceViewModel.calculateTax(items, BigDecimal.ZERO, BigDecimal.ZERO);
            }
            itemlistSaved = items;
            Log.e("TAG", "checkItemListAdapter: " + itemlistSaved);
        });

        createInvoiceViewModel.getTotalAmount().observe(this, totalAmount -> {
            if (totalAmount != null) {
                binding.amountTotalValueTv.setText(totalAmount.toString());
            }
        });

        binding.customerItemDiscountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called when the text is changed
                // Perform your action here
                if (TextUtils.isEmpty(s)) {
                    discount = "";
                    if (tax != null && !tax.isEmpty()) {
                        createInvoiceViewModel.calculateTax(itemlistSaved, BigDecimal.ZERO, new BigDecimal(tax));
                    } else {
                        createInvoiceViewModel.calculateTax(itemlistSaved, BigDecimal.ZERO, BigDecimal.ZERO);
                    }
                } else {
                    discount = binding.customerItemDiscountEt.getText().toString().trim();
                    if (tax != null && !tax.isEmpty()) {
                        createInvoiceViewModel.calculateTax(itemlistSaved, new BigDecimal(discount), new BigDecimal(tax));
                    } else {
                        createInvoiceViewModel.calculateTax(itemlistSaved, new BigDecimal(discount), BigDecimal.ZERO);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This method is called after the text is changed
            }
        });

        binding.customerItemTaxEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called when the text is changed
                // Perform your action here
                if (TextUtils.isEmpty(s)) {
                    tax = "";
                    if (discount != null && !discount.isEmpty()) {
                        createInvoiceViewModel.calculateTax(itemlistSaved, new BigDecimal(discount), BigDecimal.ZERO);
                    } else {
                        createInvoiceViewModel.calculateTax(itemlistSaved, BigDecimal.ZERO, BigDecimal.ZERO);
                    }
                } else {
                    tax = binding.customerItemTaxEt.getText().toString().trim();
                    if (discount != null && !discount.isEmpty()) {
                        createInvoiceViewModel.calculateTax(itemlistSaved, new BigDecimal(discount), new BigDecimal(tax));
                    } else {
                        createInvoiceViewModel.calculateTax(itemlistSaved, BigDecimal.ZERO, new BigDecimal(tax));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This method is called after the text is changed
            }
        });

        binding.addLogoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        binding.addLogoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        createInvoiceViewModel.getImagePath().observe(this, imagePath -> {
            if (imagePath != null && !imagePath.isEmpty()) {
                // Load the thumbnail into the ImageView
                // You can use a library like Glide or Picasso for efficient image loading
                binding.addLogoIv.setVisibility(View.VISIBLE);
                binding.addLogoTv.setVisibility(View.INVISIBLE);
                binding.addLogoIv.setImageURI(Uri.parse(imagePath));
                imagepathstatic = Uri.parse(imagePath);
                Log.e("DEBUGPDF", "imagepathstatic :- " + imagepathstatic);
            }
        });

        binding.saveInvoice.setOnClickListener(view -> {
//            captureAndSaveAsPdf();
//            createPDF(getContext(), "itextsample1");
            SharedPreferencesManager.incrementInvoiceCount(getContext());
            binding.merchantNameEt.setText("INV-" + /*String. valueOf(*/SharedPreferencesManager.getInvoiceCount(getContext())/*)*/);
        });

        binding.viewInvoice.setOnClickListener(view -> {
//            createPDF(getContext(), "itextsample1");
//            openNewFragment("DEMO Data");
            openNewFragmentSaveData();
        });

        binding.resetInvoice.setOnClickListener(view -> {
//            createPDF(getContext(), "itextsample1");
            clear();
        });

//        binding.merchantNameEt.setText("INV-" +/*String. valueOf(*/SharedPreferencesManager.getInvoiceCount(getContext())/*)*/);
        if (!itemlistSaved.isEmpty()) {
            binding.taxAndTotalRl.setVisibility(View.VISIBLE);
        }

        binding.importTemplteBtn.setOnClickListener(view -> {
            if (AdapterManageTemplates.manageTemplatesData != null && !AdapterManageTemplates.manageTemplatesData.isEmpty()) {
                importTemplateDialog();
            }else {
                Toast.makeText(getContext(), "Please add template to import", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            // Get the selected image URI
            String imagePath = data.getDataString();

            // Set the image path in the ViewModel
            createInvoiceViewModel.setImagePath(imagePath);
        }
    }


    public void addItemDialog(String itemType) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_item_dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        addItemValueBtn = dialog.findViewById(R.id.add_item_value_btn);
        cancelItemValueBtn = dialog.findViewById(R.id.cancel_item_value_btn);
        itemName = dialog.findViewById(R.id.item_et);
        quantity = dialog.findViewById(R.id.quantity_et);
        price = dialog.findViewById(R.id.price_et);
        amount = dialog.findViewById(R.id.amount_value_tv);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount.setText("");
                try {
                    if (quantity.getText().toString().trim().compareTo("0") > 0 && price.getText().toString().trim().compareTo("0.1") >= 0.1) {
                        amount.setText(new BigDecimal(quantity.getText().toString()).multiply(new BigDecimal(price.getText().toString())).toString());
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
//                    Log.e("userName", user.getUserName());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        quantity.addTextChangedListener(textWatcher);
        price.addTextChangedListener(textWatcher);

        addItemValueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Add all details of item", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
//                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    if (itemType.matches("new")) {
                        addItemToRecyclerView();
                    } else {
                        updateItemToRecyclerView(Integer.parseInt(itemType));
                        itemsEditValue.clear();
//                       adapterCreateInvoiceCustomerItems.notifyItemChanged(Integer.parseInt(itemType));
//                       adapterCreateInvoiceCustomerItems.notifyDataSetChanged();
                    }
                }
//                Toast.makeText(getContext(), "okay clicked", Toast.LENGTH_SHORT).show();
            }
        });

        cancelItemValueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        if (!itemType.matches("new")) {
            itemName.setText(itemsEditValue.get(0).getItemName());
            quantity.setText(itemsEditValue.get(0).getItemQuantity());
            price.setText(itemsEditValue.get(0).getItemPrice());
            amount.setText(itemsEditValue.get(0).getItemAmountPrice());
        }

        dialog.show();

    }

    public void importTemplateDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.import_template_dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        RecyclerView recyclerView = dialog.findViewById(R.id.import_template_rv);
        cancelItemValueBtn = dialog.findViewById(R.id.cancel_item_value_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        AdapterImportTemplate adapter = new AdapterImportTemplate();
        recyclerView.setAdapter(adapter);
        adapter.setItemList(AdapterManageTemplates.manageTemplatesData, getContext());
        adapter.setOnItemClickListener(new AdapterImportTemplate.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                isImportTemplate = true;
                importTemplatePosition=position;
                importTemplate(position);
                dialog.dismiss();
            }
        });


        cancelItemValueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();

    }

    private void addItemToRecyclerView() {
        if (!itemName.getText().toString().isEmpty() && !quantity.getText().toString().isEmpty() && !price.getText().toString().isEmpty()) {
            ItemData newItem = new ItemData(itemName.getText().toString(), quantity.getText().toString(), price.getText().toString(), amount.getText().toString());
            createInvoiceViewModel.addItem(newItem);
            binding.taxAndTotalRl.setVisibility(View.VISIBLE);
        }

    }

    private void updateItemToRecyclerView(int itemPosition) {
        if (!itemName.getText().toString().isEmpty() && !quantity.getText().toString().isEmpty() && !price.getText().toString().isEmpty()) {
            ItemData newItem = new ItemData(itemName.getText().toString(), quantity.getText().toString(), price.getText().toString(), amount.getText().toString());
            createInvoiceViewModel.updateItem(itemPosition, newItem);
        }

    }


    private void createPDF(Context context, String pdfFilename) {

        try {
            int count = 1;
            Document document = new Document(PageSize.A4);
            File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString(), pdfFilename + ".pdf");

            if (pdfFile != null) {
                PdfWriter.getInstance(document, new FileOutputStream(pdfFile));


                PdfPTable table = createHeaderTable(context);

                PdfPTable irdTable = new PdfPTable(2);
                irdTable.addCell(getIRDCell("Invoice No"));
                irdTable.addCell(getIRDCell("Invoice Date"));
                irdTable.addCell(getIRDCell("XE1234")); // pass invoice number
                irdTable.addCell(getIRDCell("13-Mar-2016")); // pass invoice date

                PdfPTable irhTable = new PdfPTable(3);
                irhTable.setWidthPercentage(100);

                irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
                irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
                irhTable.addCell(getIRHCell("Invoice", PdfPCell.ALIGN_RIGHT));
                irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
                irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
                PdfPCell invoiceTable = new PdfPCell(irdTable);
                invoiceTable.setBorder(0);
                irhTable.addCell(invoiceTable);

                FontSelector fs = new FontSelector();
                Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.BOLD);
                fs.addFont(font);
                Phrase bill = fs.process("Bill To"); // customer information
                Paragraph name = new Paragraph("Mr.Venkateswara Rao");
                name.setIndentationLeft(20);
                Paragraph contact = new Paragraph("9652886877");
                contact.setIndentationLeft(20);
                Paragraph address = new Paragraph("Kuchipudi,Movva");
                address.setIndentationLeft(20);

                PdfPTable billTable = new PdfPTable(6); //one page contains 15 records
                billTable.setWidthPercentage(100);
                billTable.setWidths(new float[]{1, 2, 5, 2, 1, 2});
                billTable.setSpacingBefore(30.0f);
                billTable.addCell(getBillHeaderCell("Index"));
                billTable.addCell(getBillHeaderCell("Item"));
                billTable.addCell(getBillHeaderCell("Description"));
                billTable.addCell(getBillHeaderCell("Unit Price"));
                billTable.addCell(getBillHeaderCell("Qty"));
                billTable.addCell(getBillHeaderCell("Amount"));

                for (int i = 0; i < itemlistSaved.size(); i++) {
                    billTable.addCell(getBillRowCell(String.valueOf(count++)));
                    billTable.addCell(getBillRowCell(itemlistSaved.get(i).getItemName()));
                    billTable.addCell(getBillRowCell(" Nokia Lumia 610 \n\n IMI:WQ361989213 \n\n Serial:UR8531 \n\n warrenty number : tew5427627 \n\n "));
                    billTable.addCell(getBillRowCell(itemlistSaved.get(i).getItemPrice()));
                    billTable.addCell(getBillRowCell(itemlistSaved.get(i).getItemQuantity()));
                    billTable.addCell(getBillRowCell(itemlistSaved.get(i).getItemAmountPrice()));
                    Log.e("TAG", "checkItemListAdapter1: loop " + itemlistSaved.get(i).getItemName() + "   " + itemlistSaved.size());
                }

                PdfPTable validity = new PdfPTable(1);
                validity.setWidthPercentage(100);
                validity.addCell(getValidityCell(" "));
                validity.addCell(getValidityCell("Warranty"));
                validity.addCell(getValidityCell(" * Products purchased comes with 1 year national warranty \n   (if applicable)"));
                validity.addCell(getValidityCell(" * Warranty should be claimed only from the respective manufactures"));
                PdfPCell summaryL = new PdfPCell(validity);
                summaryL.setColspan(3);
                summaryL.setPadding(1.0f);
                billTable.addCell(summaryL);

                PdfPTable accounts = new PdfPTable(2);
                accounts.setWidthPercentage(100);
                accounts.addCell(getAccountsCell("Subtotal"));
                accounts.addCell(getAccountsCellR("12620.00"));
                accounts.addCell(getAccountsCell("Discount (10%)"));
                accounts.addCell(getAccountsCellR("1262.00"));
                accounts.addCell(getAccountsCell("Tax(2.5%)"));
                accounts.addCell(getAccountsCellR("315.55"));
                accounts.addCell(getAccountsCell("Total"));
                accounts.addCell(getAccountsCellR("11673.55"));
                PdfPCell summaryR = new PdfPCell(accounts);
                summaryR.setColspan(3);
                billTable.addCell(summaryR);

                PdfPTable describer = new PdfPTable(1);
                describer.setWidthPercentage(100);
                describer.addCell(getdescCell(" "));
                describer.addCell(getdescCell("Goods once sold will not be taken back or exchanged || Subject to product justification || Product damage no one responsible || "
                        + " Service only at concarned authorized service centers"));

                document.open();//PDF document opened........

//                addFullPageBorder(writer,20);
                document.add(table);
//                document.add(image);
                document.add(irhTable);
                document.add(bill);
                document.add(name);
                document.add(contact);
                document.add(address);
                document.add(billTable);
                document.add(describer);

                document.close();
//                writer.close();
            }

//            file.close();

            Toast.makeText(context, "Pdf saved successfully..", Toast.LENGTH_SHORT).show();
            System.out.println("Pdf created successfully.." + pdfFile.getPath());
            openNewFragment(pdfFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private PdfPTable createHeaderTable(Context context) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);


        // Add details to the left side
        PdfPCell detailsCell = new PdfPCell();

        detailsCell.addElement(new Phrase("Your Company Name\n", getBoldFont()));
        detailsCell.addElement(new Phrase("Address Line 1\n"));
        detailsCell.addElement(new Phrase("Address Line 2\n"));
        detailsCell.addElement(new Phrase("City, Country\n"));
        detailsCell.addElement(new Phrase("Phone: xxx-xxx-xxxx\n"));
        detailsCell.addElement(new Phrase("Email: info@example.com\n"));
        detailsCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(detailsCell);

        // Add logo to the right side
        Image image = Image.getInstance(getBytesFromImageUri(imagepathstatic, context));
        image.scaleAbsolute(72f, 72f);
//        Image logoImage = Image.getInstance("path/to/your/logo.png");
        PdfPCell logoCell = new PdfPCell(image, false);
        logoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        logoCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(logoCell);

        return table;
    }

    private static Font getBoldFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        font.setColor(BaseColor.BLACK);
        return font;
    }


    public static PdfPCell getIRHCell(String text, int alignment) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 16);
        /*	font.setColor(BaseColor.GRAY);*/
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public static PdfPCell getIRDCell(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        return cell;
    }

    public static PdfPCell getBillHeaderCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 11);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        return cell;
    }

    public static PdfPCell getBillRowCell(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthTop(0);
        return cell;
    }

    public static PdfPCell getBillFooterCell(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthTop(0);
        return cell;
    }

    public static PdfPCell getValidityCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorder(0);
        return cell;
    }

    public static PdfPCell getAccountsCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setPadding(5.0f);
        return cell;
    }

    public static PdfPCell getAccountsCellR(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthTop(0);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPadding(5.0f);
        cell.setPaddingRight(20.0f);
        return cell;
    }

    public static PdfPCell getdescCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(0);
        return cell;
    }

    private static byte[] getBytesFromImageUri(Uri imageUri, Context context) throws IOException {
        ContentResolver contentResolver = context.getContentResolver();
        InputStream inputStream = contentResolver.openInputStream(imageUri);
        return getBytes(inputStream);
    }

    private static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    private void clear() {
        binding.merchantNameEt.setText("");
        binding.addLogoTv.setVisibility(View.VISIBLE);
        binding.addLogoIv.setVisibility(View.INVISIBLE);
        binding.headerCreateInvoiceEt.setText("");
        binding.customerNameEt.setText("");
        binding.customerAddressEt.setText("");
        binding.customerContactNoEt.setText("");
        binding.customerEmailEt.setText("");
        binding.customerItemDiscountEt.setText("");
        binding.customerItemTaxEt.setText("");
        binding.taxAndTotalRl.setVisibility(View.GONE);
        binding.footerEt.setText("");
        adapterCreateInvoiceCustomerItems.clear();
        binding.customFieldLl.removeAllViews();
        viewInvoiceData.clearData();
    }

    private void openNewFragmentSaveData() {
        viewInvoiceData.setMerchantName(binding.merchantNameEt.getText().toString().trim());
        viewInvoiceData.setMerchantLogo(String.valueOf(imagepathstatic));
        viewInvoiceData.setMerchantHeader(binding.headerCreateInvoiceEt.getText().toString().trim());
        viewInvoiceData.setCustomerName(binding.customerNameEt.getText().toString().trim());
        viewInvoiceData.setCustomerAddress(binding.customerAddressEt.getText().toString().trim());
        viewInvoiceData.setCustomerContactNo(binding.customerContactNoEt.getText().toString().trim());
        viewInvoiceData.setCustomerEmail(binding.customerEmailEt.getText().toString().trim());
        createInvoiceViewModel.getSubTotalBeforeDiscount().observe(this, subtotalValue -> {
            if (subtotalValue != null) {
                viewInvoiceData.setSubtotal(subtotalValue.toString().trim());
            }
        });
        viewInvoiceData.setDiscount(binding.customerItemDiscountEt.getText().toString().trim());
        createInvoiceViewModel.getDiscountResult().observe(this, discountValue -> {
            if (discountValue != null) {
                viewInvoiceData.setDiscountValue(discountValue.toString().trim());
            }
        });
        viewInvoiceData.setTax(binding.customerItemTaxEt.getText().toString().trim());
        createInvoiceViewModel.getTaxResult().observe(this, taxValue -> {
            if (taxValue != null) {
                viewInvoiceData.setTaxValue(taxValue.toString().trim());
            }
        });
        viewInvoiceData.setTotalValue(binding.amountTotalValueTv.getText().toString().trim());
        viewInvoiceData.setFooter(binding.footerEt.getText().toString().trim());
        viewInvoiceData.setItemData(itemlistSaved);



//        navController.navigate(R.id.action_navCreateInvoice_to_navViewInvoice);

        Bundle bundle = new Bundle();
        bundle.putString("viewInvoice", "withoutPayment");
        navController.navigate(R.id.action_navCreateInvoice_to_navViewInvoice, bundle);



        Log.e("TAG", "openNewFragmentSaveData: "+viewInvoiceData );
    }

    private void openNewFragment(String fileUri) {

        // Navigate to FragmentB and pass data
//        Bundle bundle = new Bundle();
//        bundle.putString("dataPath", fileUri);


        bundle.putString("merchant_name", binding.merchantNameEt.getText().toString().trim());

        if (imagepathstatic != null && !imagepathstatic.equals(Uri.EMPTY)) {
            bundle.putString("merchant_logo", String.valueOf(imagepathstatic));
        }


        bundle.putString("merchant_header", binding.headerCreateInvoiceEt.getText().toString().trim());


        bundle.putString("customer_name", binding.customerNameEt.getText().toString().trim());


        bundle.putString("customer_address", binding.customerAddressEt.getText().toString().trim());


        bundle.putString("customer_contact_no", binding.customerContactNoEt.getText().toString().trim());


        bundle.putString("customer_email", binding.customerEmailEt.getText().toString().trim());


        createInvoiceViewModel.getSubTotalBeforeDiscount().observe(this, subtotalValue -> {
            if (subtotalValue != null) {
                bundle.putString("subtotal_amount", subtotalValue.toString().trim());
            }
        });

        bundle.putString("total_dicount", binding.customerItemDiscountEt.getText().toString().trim());

        createInvoiceViewModel.getDiscountResult().observe(this, discountValue -> {
            if (discountValue != null) {
                bundle.putString("total_dicount_value", discountValue.toString().trim());
            }
        });
        bundle.putString("total_tax", binding.customerItemTaxEt.getText().toString().trim());

        createInvoiceViewModel.getTaxResult().observe(this, taxValue -> {
            if (taxValue != null) {
                bundle.putString("total_tax_value", taxValue.toString().trim());
            }
        });
        bundle.putString("total_amount", binding.amountTotalValueTv.getText().toString().trim());


        bundle.putString("merchant_footer", binding.footerEt.getText().toString().trim());

//        setData(bundle);

        navController.navigate(R.id.action_navCreateInvoice_to_navViewInvoice, bundle);
    }

    public void importTemplate(int position){
//        Toast.makeText(getContext(), "importTemplate called", Toast.LENGTH_SHORT).show();
        Log.e("TAG", "importTemplateloadvalue: "+AdapterManageTemplates.manageTemplatesData.get(position) );
        final ManageTemplatesData manageTemplatesData = AdapterManageTemplates.manageTemplatesData.get(position);
        viewInvoiceData.setCustomField((LinkedHashMap<String, String>) manageTemplatesData.getCustomField());
        binding.merchantNameEt.setText(manageTemplatesData.getMerchantName());
        binding.headerCreateInvoiceEt.setText(manageTemplatesData.getTemplateHeader());
        binding.addLogoIv.setVisibility(View.VISIBLE);
        binding.addLogoTv.setVisibility(View.INVISIBLE);
        binding.addLogoIv.setImageURI(Uri.parse(manageTemplatesData.getMerchantlogo()));
        imagepathstatic = Uri.parse(manageTemplatesData.getMerchantlogo());
        binding.footerEt.setText(manageTemplatesData.getTemplateFooter());
        int editTextIdCounter = 1000;
        if (!manageTemplatesData.getCustomField().isEmpty()) {
            binding.customFieldLl.removeAllViews();
            for (Map.Entry<String, String> entry : manageTemplatesData.getCustomField().entrySet()) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dynamic_text_input_layout, binding.customFieldLl, false);

                // Find views within the inflated layout
                TextInputLayout textInputLayout = view.findViewById(R.id.dynamic_create_invoice_tv);
                TextInputEditText textInputEditText = view.findViewById(R.id.dynamic_create_invoice_et);
//                ImageView deleteImageView = view.findViewById(R.id.delete_field_iv);
                Log.e("TAG", "importTemplateloadvalueOne: "+entry.getKey()+"="+entry.getValue() );
                // Set key-value pair to TextView and EditText
                textInputLayout.setHint(entry.getKey());
                textInputEditText.setText(entry.getValue());
                textInputEditText.setId(editTextIdCounter++);
                Log.e("TAG", "importTemplateloadvalueTwo: "+entry.getKey()+"="+entry.getValue()  );

                // Add TextInputLayout to the parent layout
                binding.customFieldLl.addView(view);

//                deleteImageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        binding.customFieldLl.removeView((View) view.getParent());
//                    }
//                });

            }
        }
    }

}