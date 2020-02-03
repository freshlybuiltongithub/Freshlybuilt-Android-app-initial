package com.freshlybuilt.enduserapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freshlybuilt.enduserapp.R;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupCardViewHolder> {

    @NonNull
    @Override
    public GroupCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new GroupCardViewHolder(inflater.inflate(R.layout.group_card_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GroupCardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 9;
    }

    class GroupCardViewHolder extends RecyclerView.ViewHolder {
        public GroupCardViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
