package com.freshlybuilt.enduserapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.freshlybuilt.enduserapp.ClickListeners;
import com.freshlybuilt.enduserapp.R;
import com.freshlybuilt.enduserapp.models.Posts;
import com.freshlybuilt.enduserapp.models.PostsResponse;

import java.util.ArrayList;
import java.util.logging.Logger;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {
    ArrayList<Posts> PostsResponsesList ;
   final ClickListeners onClickListener ;
    Context context;
          public PostsListAdapter(ArrayList<Posts> PostsResponsesList, Context context,ClickListeners onClickListener){
              this.PostsResponsesList=PostsResponsesList;
              this.context=context;
              this.onClickListener=onClickListener;

          }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PostViewHolder(inflater.inflate(R.layout.post_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
              final Posts post=PostsResponsesList.get(position);
              holder.mTitle.setText(post.getTitle());
              holder.mAuthor.setText(post.getAuthor().getName());
              String tempDetails=post.getExcerpt();
              String excerpt=tempDetails.substring(tempDetails.indexOf("<p>")+3, tempDetails.indexOf("<a"));
              Log.d("Tag",excerpt);
              holder.mDesc.setText(excerpt);
              holder.mPost.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                    onClickListener.OnPostClick(post);
                  }
              });
              Glide.with(context)
                .load(post.getThumbnailImages().getMedium().getUrl())
                .into(holder.mImage);


    }

    @Override
    public int getItemCount() {
        return PostsResponsesList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView mDesc;
        TextView mTitle;
        TextView mAuthor;
        ImageView mImage;
        LinearLayout mPost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mDesc = itemView.findViewById(R.id.post_desc);
            mTitle=itemView.findViewById(R.id.post_title);
            mAuthor=itemView.findViewById(R.id.post_author);
            mImage=itemView.findViewById(R.id.post_img);
            mPost=itemView.findViewById(R.id.post_card);
        }
    }
}
