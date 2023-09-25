package com.example.gymapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        boolean isVisible = item.isClicked;
        holder.linearLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE); // Propagates this property to child
    }

    @Override
    public int getItemCount() {
        return setItems.size();
    }

    public class SetRVViewHolder extends RecyclerView.ViewHolder {

        TextView setValue;
        TextView weightValue;
        Button addSet;
        LinearLayout linearLayout;

        public SetRVViewHolder(@NonNull View itemView) {
            super(itemView);
            setValue = itemView.findViewById(R.id.setValueItemElement);
            weightValue = itemView.findViewById(R.id.weightValueItemElement);
            linearLayout = itemView.findViewById(R.id.linearLayoutForButtonItemElement);
            addSet = itemView.findViewById(R.id.addSetButtonItemElement);
            setValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetRVItem itemSelected = setItems.get(getAdapterPosition());
                    itemSelected.setClicked(!itemSelected.isClicked());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            addSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Add set clicked!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
