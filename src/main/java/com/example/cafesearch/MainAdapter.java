package com.example.cafesearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    ArrayList<String> s;
    public MainAdapter(ArrayList<String> temp) {
        s=temp;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(s.get(position));

    }

    @Override
    public int getItemCount() {
        return s.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name;

        public ViewHolder(View itemView)
        {
            super(itemView);
            name= itemView.findViewById(R.id.my_name);
        }

    }


}
