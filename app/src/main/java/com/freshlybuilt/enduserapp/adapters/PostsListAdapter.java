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
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.freshlybuilt.enduserapp.ClickListeners;
import com.freshlybuilt.enduserapp.PostsDatabase;
import com.freshlybuilt.enduserapp.R;
import com.freshlybuilt.enduserapp.models.Posts;
import com.freshlybuilt.enduserapp.models.PostsOffline;
import com.freshlybuilt.enduserapp.models.PostsResponse;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {
    ArrayList<Posts> PostsResponsesList ;
   final ClickListeners onClickListener ;


   private   Context context;

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
             final   Posts post=PostsResponsesList.get(position);
              holder.mTitle.setText(post.getTitle());
              holder.mAuthor.setText(post.getAuthor().getName());
              String tempDetails=post.getExcerpt();
              final String excerpt=tempDetails.substring(tempDetails.indexOf("p>")+2, tempDetails.indexOf("<a"));
              Log.d("Tag",excerpt);
              holder.mDesc.setText(excerpt);
              final String   imageUrl = post.getThumbnailImages().getMedium().getUrl();
              Glide.with(context)
                   .load(imageUrl)
                   .into(holder.mImage);
              holder.mPost.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                    onClickListener.OnPostClick(post);
                  }
              });
              holder.mbkmbtn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                   String name=   post.getAuthor().getName();
                   String title=   post.getTitle();
                   String url=   post.getUrl();

                   PostsOffline post=new PostsOffline(name,title,excerpt,url,imageUrl);
                   onClickListener.OnbkmbtnClick(post);
                  }
              });

//              if(post.getThumbnailImages().getMedium()!=null) {
               //   imageUrl = post.getThumbnailImages().getMedium().getUrl();
//              }
//              else if(post.getThumbnailImages().getMedium()){
//                  imageUrl=post.getThumbnailImages().getThumbnail().getUrl();
//              }




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
        ImageView mbkmbtn;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mDesc = itemView.findViewById(R.id.post_desc);
            mTitle=itemView.findViewById(R.id.post_title);
            mAuthor=itemView.findViewById(R.id.post_author);
            mImage=itemView.findViewById(R.id.post_img);
            mPost=itemView.findViewById(R.id.post_card);
            mbkmbtn=itemView.findViewById(R.id.bookmark_post);
        }
    }
}
