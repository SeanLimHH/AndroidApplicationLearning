package com.example.gymapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class Settings extends Fragment {

    private ArrayList<SetRVItem> setItems;
    private RecyclerView recyclerView;
    private SetRVAdapter setRVAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.setRVContainer);

        // Create dummy data
        setItems = new ArrayList<>();
        setItems.add(new SetRVItem("10 Reps", "20kg"));
        setItems.add(new SetRVItem("15 Reps", "30kg"));
        setItems.add(new SetRVItem("12 Reps", "25kg"));

        // Set up the RecyclerView and adapter
        setRVAdapter = new SetRVAdapter(requireContext(), setItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(setRVAdapter);

        return view;
    }

}