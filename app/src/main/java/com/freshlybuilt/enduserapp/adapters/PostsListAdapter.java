package com.freshlybuilt.enduserapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freshlybuilt.enduserapp.R;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PostViewHolder(inflater.inflate(R.layout.post_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.mDesc.setText("This is a sample description of project. And just keep it short. There’s nothing happy about any new year, so don’t feel blessed at all. Let the world go insane ......... ");
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView mDesc;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mDesc = itemView.findViewById(R.id.post_desc);
        }
    }
}
