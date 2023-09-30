package com.example.gymapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Settings extends Fragment {

    private ArrayList<SetRVItem> setItems;
    private RecyclerView recyclerView;
    private SetRVAdapter setRVAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        setItems = new ArrayList<>();

        recyclerView = view.findViewById(R.id.setRVContainer);

        setItems.add(new SetRVItem(100,"999","9999"));
        // Set up the RecyclerView and adapter
        setRVAdapter = new SetRVAdapter(requireContext(), setItems, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(setRVAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);




        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        // Refresh the data when returning from the stopped state
        setItems.addAll(loadSetsFromDatabase());
        setRVAdapter.notifyDataSetChanged();
    }
    private ArrayList<SetRVItem> loadSetsFromDatabase() {
        ArrayList<SetRVItem> sets = new ArrayList<>();

        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Query all sets
        Cursor cursor = db.query("sets", null, null, null, null, null, null);
        int columnIndexId = cursor.getColumnIndex("id");
        int columnIndexSetValue = cursor.getColumnIndex("setValue");
        int columnIndexWeightValue = cursor.getColumnIndex("weightValue");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int setID = cursor.getInt(columnIndexId);
                String setValue = cursor.getString(columnIndexSetValue);
                String setWeight = cursor.getString(columnIndexWeightValue);
                SetRVItem set = new SetRVItem(setID, setValue, setWeight);
                sets.add(set);
            }
            cursor.close();
        }

        db.close();
        return sets;
    }
    @Override
    public void onPause() {
        super.onPause();

        replaceItemsWithDatabase(setItems);
        setRVAdapter.notifyDataSetChanged();
    }

    private void replaceItemsWithDatabase(ArrayList<SetRVItem> currentSetItems) {
        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        try {
            // Assuming that the 'id' is the unique identifier in your database
            // 1. Delete existing data
            db.delete("sets", null, null);
            for (SetRVItem currentItem : currentSetItems) {
                ContentValues values = new ContentValues();
                values.put("id", currentItem.getId());
                values.put("setValue", currentItem.getSetValue().toString());
                values.put("weightValue", currentItem.getWeightValue().toString());



                // 2. Insert new data
                db.insert("sets", null, values);


            }

            // Additional logic for handling deletions or insertions, if needed
            // For example, compare currentSetItems with the original setItems
            // and insert or delete items as needed

        } finally {
            db.close();
        }
    }

    private void performCrudOperations(List<SetRVItem> currentSetItems) {
        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try {
            // Assuming that the 'id' is the unique identifier in your database
            for (SetRVItem currentItem : currentSetItems) {
                ContentValues values = new ContentValues();
                values.put("id", currentItem.getId()+1);
                values.put("setValue", currentItem.getSetValue().toString());
                values.put("weightValue", currentItem.getWeightValue().toString());

                // Update the existing set in the database
                db.insert("sets", null, values);
            }

            // Additional logic for handling deletions or insertions, if needed
            // For example, compare currentSetItems with the original setItems
            // and insert or delete items as needed

        } finally {
            db.close();
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(setItems, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // Handles left and right. Drag and dropping top and bottom is implemented in onMove
        }
    };
}