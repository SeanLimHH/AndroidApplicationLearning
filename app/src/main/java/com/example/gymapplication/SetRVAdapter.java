package com.example.gymapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SetRVAdapter extends RecyclerView.Adapter<SetRVAdapter.SetRVViewHolder> {
    Context context;
    ArrayList<SetRVItem> setItems;
    private RecyclerView recyclerView;

    public SetRVAdapter(Context context, ArrayList<SetRVItem> setItems, RecyclerView recyclerView) {
        this.context = context;
        this.setItems = setItems;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public SetRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setitem, parent, false);
        return new SetRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SetRVViewHolder holder, int position) {
        SetRVItem item = setItems.get(position);
        holder.setValue.setText(item.getSetValue());
        holder.weightValue.setText(item.getWeightValue());
        boolean isVisible = item.isClicked;
        holder.linearLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE); // Propagates this property to child
        holder.addSet.setVisibility(item.isClicked() ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();

                if (adapterPosition != RecyclerView.NO_POSITION) {
                    item.setClicked(!item.isClicked());
                    notifyItemChanged(adapterPosition);
                    recyclerView.smoothScrollToPosition(adapterPosition);

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return setItems.size();
    }

    public class SetRVViewHolder extends RecyclerView.ViewHolder {

        EditText setValue;
        EditText weightValue;
        Button addSet;
        Button editSet;
        Button deleteSet;
        LinearLayout linearLayout;
        DatabaseHelper db = new DatabaseHelper(context);

        public SetRVViewHolder(@NonNull View itemView) {
            super(itemView);
            setValue = itemView.findViewById(R.id.setValueItemElement);
            weightValue = itemView.findViewById(R.id.weightValueItemElement);
            linearLayout = itemView.findViewById(R.id.linearLayoutForButtonItemElement);
            addSet = itemView.findViewById(R.id.addSetButtonItemElement);
            deleteSet = itemView.findViewById(R.id.deleteSetButtonItemElement);
            editSet = itemView.findViewById(R.id.editSetButtonItemElement);

            addSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        SetRVItem item = setItems.get(position);
                        item.setClicked(!item.isClicked());
                        notifyItemChanged(position);

                        SetRVItem newItem = new SetRVItem(db.getNextId(), String.valueOf(db.getNextId()), String.valueOf(db.getNextId()));
                        setItems.add(position+1, newItem);
                        notifyItemInserted(position + 1);
                    }
                }
            });

            deleteSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        notifyItemChanged(position);

                        setItems.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });
        }

    }
}
