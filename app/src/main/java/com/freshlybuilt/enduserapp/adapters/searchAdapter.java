package com.freshlybuilt.enduserapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.freshlybuilt.enduserapp.R;
import com.freshlybuilt.enduserapp.adapters.questionAdapter;
import com.freshlybuilt.enduserapp.models.Post;
import com.freshlybuilt.enduserapp.models.PostsSearch;
import com.freshlybuilt.enduserapp.models.QuestionList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class searchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int SEARCH=0;
    private static final int LOADING=1;
    private boolean isLoadingAdded=false;
    private Context context;
    private ArrayList<Post> SearchResult=new ArrayList<Post>();
    public searchAdapter(Context c, ArrayList<Post> searches){
        this.context=c;
        this.SearchResult = searches;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder=null;
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        switch(viewType){
            case SEARCH:
                viewHolder=getViewHolder(parent,inflater);
                break;
            case LOADING:
                View v2=inflater.inflate(R.layout.loadfooter,parent,false);
                viewHolder=new Loader(v2);

        }
        return viewHolder;
    }
    public RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1=inflater.inflate(R.layout.qa_card,parent,false);
        viewHolder=new SearchViewholder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final Post post;
        post = SearchResult.get(position);


        switch (getItemViewType(position)) {
            case SEARCH:
                final SearchViewholder searcholder = (SearchViewholder) holder;
                try {

                // JSOUP is a library that could parse html code .

                    Document document = Jsoup.parse(post.getContent());
                    searcholder.content.setText(document.text());
                    searcholder.title.setText(post.getTitle());
                   // quesholder.replies.setText(ques.getLinks().getReplies().size());

                    searcholder.Date.setText(formatDate(post.getDate()));
                    searcholder.SeeMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = post.getUrl();
                            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                            CustomTabsIntent customTabsIntent = builder.build();
                            customTabsIntent.launchUrl(context, Uri.parse(url));
                        }
                    });
                }
                catch(NullPointerException e){
                    getItemViewType(position++);
                }

        break;


        case LOADING:
        break;

    }
    }
    private String formatDate(String date ) {
        CharSequence ago="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            long time = sdf.parse(date).getTime();
            long now = System.currentTimeMillis();
            ago =  DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
        } catch (ParseException e) {
            Log.d("DATE",e.getMessage());
        }
        return ago.toString();
    }

    public int getItemViewType(int position){
        return (position==SearchResult.size()-1&&isLoadingAdded)?LOADING:SEARCH;
    }

    @Override
    public int getItemCount() {
        return SearchResult.size();
    }

    public class SearchViewholder extends RecyclerView.ViewHolder{
        ImageView person;
        TextView title,content,SeeMore,Date;
        TextView likes,replies;
        LinearLayout tagsLayout;
        public SearchViewholder(@NonNull View itemView) {
            super(itemView);
            replies= itemView.findViewById(R.id.replies);
            title=itemView.findViewById(R.id.post_title);
            content= itemView.findViewById(R.id.post_desc);
            SeeMore= itemView.findViewById(R.id.loadmore);
            Date=itemView.findViewById(R.id.date);
//            tagsLayout=(LinearLayout)itemView.findViewById(R.id.tagsLayout);
        }
    }
    public void add(Post p){
        SearchResult.add(p);

        notifyItemInserted(SearchResult.size()-1);
    }

    public void addLoadingFooter(){
        isLoadingAdded=true;
        add(new Post());
    }

    public void removeSearchLoadingFooter(){
        isLoadingAdded=false;
        int pos=SearchResult.size()-1;
       Post p=SearchResult.get(pos);
        if(p!=null){
            SearchResult.remove(pos);
            notifyItemRemoved(pos);
        }
    }
    public class Loader extends RecyclerView.ViewHolder{
        public Loader(@NonNull View itemView) {
            super(itemView);
        }
    }

}
