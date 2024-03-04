package com.example.merchantdemo.ui.manageTemplates;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.merchantdemo.R;
import com.example.merchantdemo.dataClass.ManageTemplatesData;
import com.example.merchantdemo.databinding.FragmentCreateTemplateBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CreateTemplateFragment extends Fragment {

    private FragmentCreateTemplateBinding binding;
    CreateTemplateViewModel createTemplateViewModel;
    ManageTemplatesViewModel manageTemplatesViewModel;
    private static final int REQUEST_GALLERY = 1;
    String imagePathStore;
    private NavController navController;
    private int dynamicViewCount = 0;
    Boolean isEditTemplate = false;
    LinkedHashMap<String, String> customField=new LinkedHashMap<>();
    ManageTemplatesData template;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCreateTemplateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_dashbord);

        createTemplateViewModel = new ViewModelProvider(this).get(CreateTemplateViewModel.class);
        manageTemplatesViewModel = new ViewModelProvider(requireActivity()).get(ManageTemplatesViewModel.class);

        if (getArguments() != null) {
            /*ManageTemplatesData*/ template = getArguments().getParcelable("editTemplate");
            if (template != null) {
                isEditTemplate = true;
                if (!template.getCustomField().isEmpty()) {
                    addDynamicViews(true);
                }
                binding.saveTemplateBtn.setText("update");
                binding.templateNameEt.setText(template.getTemplateName());
                binding.merchantNameEt.setText(template.getMerchantName());
                binding.headerCreateTemplateEt.setText(template.getTemplateHeader());
                binding.footerCreateTemplateEt.setText(template.getTemplateFooter());
                if (template.getMerchantlogo()!=null && !template.getMerchantlogo().isEmpty()) {
                    binding.addLogoIv.setVisibility(View.VISIBLE);
                    binding.addLogoTv.setVisibility(View.INVISIBLE);
                    binding.addLogoIv.setImageURI(Uri.parse(template.getMerchantlogo()));
                }
            } else {
                isEditTemplate = false;
                binding.saveTemplateBtn.setText("save");
            }
        }

        binding.addItemIv.setOnClickListener(v -> {
            if (binding.customFieldsEt.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Add custom field name", Toast.LENGTH_SHORT).show();
            } else {
                addDynamicViews(false);
                binding.customFieldsEt.setText("");
            }
        });

        binding.addLogoTv.setOnClickListener(v -> {
            openGallery();
        });

        binding.addLogoIv.setOnClickListener(v -> {
            openGallery();
        });

        createTemplateViewModel.getImagePath().observe(this, imagePath -> {
            if (imagePath != null && !imagePath.isEmpty()) {
                // Load the thumbnail into the ImageView
                // You can use a library like Glide or Picasso for efficient image loading
                binding.addLogoIv.setVisibility(View.VISIBLE);
                binding.addLogoTv.setVisibility(View.INVISIBLE);
                binding.addLogoIv.setImageURI(Uri.parse(imagePath));
            }
        });

        binding.saveTemplateBtn.setOnClickListener(v -> {
            if (isEditTemplate) {
                saveEditData();
            } else {
                saveData();
//                getDynamicValue();
            }
        });
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
            imagePathStore = data.getDataString();

            // Set the image path in the ViewModel
            createTemplateViewModel.setImagePath(data.getDataString());
        }
    }

    private void addDynamicViews(boolean isAddDynamicView) {

//        // Customize views as needed
        if (isAddDynamicView){
            for (Map.Entry<String, String> entry : template.getCustomField().entrySet()) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dynamic_text_input_layout_and_delete, binding.customFieldLl, false);

                // Find views within the inflated layout
                TextInputLayout textInputLayout = view.findViewById(R.id.dynamic_create_template_tv);
                TextInputEditText textInputEditText = view.findViewById(R.id.dynamic_create_template_et);
                ImageView deleteImageView = view.findViewById(R.id.delete_field_iv);;

                // Set key-value pair to TextView and EditText
                textInputLayout.setHint(entry.getKey());
                textInputEditText.setText(entry.getValue());

                // Add TextInputLayout to the parent layout
                binding.customFieldLl.addView(view);

                deleteImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.customFieldLl.removeView((View) view.getParent());
                    }
                });

            }
        }else {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dynamic_text_input_layout_and_delete, binding.customFieldLl, false);

            // Find views within the inflated layout
            TextInputLayout textInputLayout = view.findViewById(R.id.dynamic_create_template_tv);
            ImageView deleteImageView = view.findViewById(R.id.delete_field_iv);

            textInputLayout.setHint(binding.customFieldsEt.getText().toString().trim() );
            // Add TextInputLayout to the parent layout
            binding.customFieldLl.addView(view);

            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.customFieldLl.removeView((View) view.getParent());

                }
            });

        }

    }

    private void saveEditData() {
        getDynamicValue();
        ManageTemplatesData newTemplate = new ManageTemplatesData(Objects.requireNonNull(binding.templateNameEt.getText()).toString().trim(), Objects.requireNonNull(binding.merchantNameEt.getText()).toString().trim(), imagePathStore, Objects.requireNonNull(binding.headerCreateTemplateEt.getText()).toString().trim(), Objects.requireNonNull(binding.footerCreateTemplateEt.getText()).toString().trim(),customField);
        manageTemplatesViewModel.updateTemplate(getArguments().getInt("editTemplatePosition"), newTemplate);

        // Navigate back
        navController.popBackStack(R.id.nav_manage_templates, false);
    }

    private void saveData() {
        getDynamicValue();
        ManageTemplatesData newTemplate = new ManageTemplatesData(Objects.requireNonNull(binding.templateNameEt.getText()).toString().trim(), Objects.requireNonNull(binding.merchantNameEt.getText()).toString().trim(), imagePathStore, Objects.requireNonNull(binding.headerCreateTemplateEt.getText()).toString().trim(), Objects.requireNonNull(binding.footerCreateTemplateEt.getText()).toString().trim(),customField);
        manageTemplatesViewModel.addTemplate(newTemplate);

        // Navigate back
//        navController.navigate(R.id.nav_manage_templates);
        navController.popBackStack(R.id.nav_manage_templates, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null; // Release the binding when the activity is destroyed
    }

    public void getDynamicValue() {
        for (int i = 0; i < binding.customFieldLl.getChildCount(); i++) {
            View childView = binding.customFieldLl.getChildAt(i);
            if (childView instanceof RelativeLayout) {
                TextInputLayout textview = childView.findViewById(R.id.dynamic_create_template_tv);
                TextInputEditText editText = childView.findViewById(R.id.dynamic_create_template_et);
                String textviewhint = textview.getHint().toString();
                String text = editText.getText().toString();
                customField.put(textviewhint,text);
                // Do something with the text (e.g., validate, store)
                // For demonstration, we just log it
                System.out.println("Text from TextInputEditText: " + textviewhint + " " + text);
            }
        }

    }
}