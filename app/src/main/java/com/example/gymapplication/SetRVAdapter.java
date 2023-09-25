package com.example.gymapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SetRVAdapter extends RecyclerView.Adapter<SetRVAdapter.SetRVViewHolder> {
    Context context;
    ArrayList<SetRVItem> setItems;

    public SetRVAdapter(Context context, ArrayList<SetRVItem> setItems) {
        this.context = context;
        this.setItems = setItems;
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
    }

    @Override
    public int getItemCount() {
        return setItems.size();
    }

    public class SetRVViewHolder extends RecyclerView.ViewHolder {

        TextView setValue;
        TextView weightValue;

        public SetRVViewHolder(@NonNull View itemView) {
            super(itemView);
            setValue = itemView.findViewById(R.id.setValueItemElement);
            weightValue = itemView.findViewById(R.id.weightValueItemElement);
        }

    }
}
