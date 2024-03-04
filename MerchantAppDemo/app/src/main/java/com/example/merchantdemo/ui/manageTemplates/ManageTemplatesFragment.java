package com.example.merchantdemo.ui.manageTemplates;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.merchantdemo.R;
import com.example.merchantdemo.dataClass.ManageTemplatesData;
import com.example.merchantdemo.databinding.FragmentManageTemplatesBinding;
import com.example.merchantdemo.helperClass.AdapterManageTemplates;
import com.google.android.material.button.MaterialButton;

public class ManageTemplatesFragment extends Fragment {

    private FragmentManageTemplatesBinding binding;
    private NavController navController;
    AdapterManageTemplates adapter;
    ManageTemplatesViewModel manageTemplatesViewModel;

    MaterialButton addItemValueBtn;
    EditText itemName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize ViewModel
        manageTemplatesViewModel = new ViewModelProvider(requireActivity()).get(ManageTemplatesViewModel.class);
        Log.d("ManageTemplatesFragment", "ManageTemplatesViewModel initialized: " + (manageTemplatesViewModel != null));

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        manageTemplatesViewModel = new ViewModelProvider(this).get(ManageTemplatesViewModel.class);

        binding = FragmentManageTemplatesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_dashbord);


//        final TextView textView = binding.textManageTemplates;
//        manageTemplatesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_create_Template);
            }
        });


        binding.manageTemplatesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        if (adapter == null) {
        /*final AdapterManageTemplates*/
        adapter = new AdapterManageTemplates();
        adapter.setOnItemClickListener(new AdapterManageTemplates.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("importTemplate", String.valueOf(position));
                navController.navigate(R.id.nav_create_invoice,bundle);
            }

            @Override
            public void onEditClick(int position) {
                ManageTemplatesData selectedTemplate = adapter.getItem(position);
                openEditTemplateFragment(selectedTemplate, position);
            }

            @Override
            public void onDeleteClick(int position) {
                manageTemplatesViewModel.deleteTemplate(position);
            }
        });
        binding.manageTemplatesRecyclerView.setAdapter(adapter);
        Log.e("DEBUG", "adapter of HomeFragment");
//        }

        manageTemplatesViewModel.getTemplateList().observe(this, items -> {
            Log.e("DEBUG", "getTemplateList managetemplate" + items);
            if (items.isEmpty()){
                binding.addTemplateHintTv.setVisibility(View.VISIBLE);
                adapter.setItemList(items, getContext());
            }else {
                binding.addTemplateHintTv.setVisibility(View.GONE);
                adapter.setItemList(items, getContext());
            }

        });


        Log.e("DEBUG", "onCreateView of HomeFragment");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void openEditTemplateFragment(ManageTemplatesData template, int position) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("editTemplate", template);
        bundle.putInt("editTemplatePosition", position);
        navController.navigate(R.id.action_navManageTemplates_to_navCreateTemplate, bundle);//.navigate(R.id.action_navManageTemplates_to_navCreateTemplate, template);

    }


}

