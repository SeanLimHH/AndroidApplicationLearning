package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private EditText editText;
    private Button button;

    private Button nav;

    private Button addToArrayList;

    private SharedViewModel sharedViewModel;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        SharedViewModel shared = new ViewModelProvider(this).get(SharedViewModel.class);
        shared.getSharedText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        editText = view.findViewById(R.id.firstFragmentText);
        nav = view.findViewById(R.id.navigateToSecond);
        // Obtain the SharedViewModel

        editText.setHint(sharedViewModel.getSharedText().getValue());
        // Observe the LiveData in the SharedViewModel
        sharedViewModel.getSharedText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String newTextToUpdate) {
                // Display a Toast message with the new text
                Toast.makeText(requireContext(), "Fragment 1: " + newTextToUpdate, Toast.LENGTH_SHORT).show();
            }
        });

        button = view.findViewById(R.id.firstFragmentButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the shared text when the button is clicked
                String text = editText.getText().toString();
                sharedViewModel.setSharedText(text);
            }
        });

        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the FirstFragment when the button is clicked
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new SecondFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        addToArrayList = view.findViewById(R.id.addToArrayList);
        addToArrayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                sharedViewModel.setSharedItem(text);
            }
        });

        sharedViewModel.getSharedItem().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> newArrayListToUpdate) {
                Toast.makeText(requireContext(), "Fragment 1: " + newArrayListToUpdate, Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}