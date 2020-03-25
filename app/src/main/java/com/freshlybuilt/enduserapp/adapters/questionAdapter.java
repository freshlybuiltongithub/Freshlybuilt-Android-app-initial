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
import com.freshlybuilt.enduserapp.models.Post;
import com.freshlybuilt.enduserapp.models.QuestionList;
import com.freshlybuilt.enduserapp.models.Tag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;



public class questionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    public String s;
    private static final int ITEM=0;
    private static final int LOADING=1;
private boolean isLoadingAdded=false;
    private ArrayList<QuestionList> item=new ArrayList<>();
    public questionAdapter(Context context,ArrayList<QuestionList> item) {
        this.context=context;
        this.item = item;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder=null;
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        switch(viewType){
            case ITEM:
                viewHolder=getViewHolder(parent,inflater);
                break;
            case LOADING:
                View v2=inflater.inflate(R.layout.loadfooter,parent,false);
                viewHolder=new Loader(v2);
        }
        Log.d("while","oncreate");
        return viewHolder;
    }

    public RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1=inflater.inflate(R.layout.qa_card,parent,false);
        viewHolder=new QuesViewholder(v1);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

   final QuestionList ques;
         ques=item.get(position);


     switch(getItemViewType(position)) {
         case ITEM:
         final QuesViewholder quesholder=(QuesViewholder) holder;
         quesholder.title.setText(ques.getTitle().getRendered());
         // JSOUP is a library that could parse html code .
             Document document= Jsoup.parse(ques.getContent().getRendered());
             quesholder.content.setText(document.text());

             if(ques.getQuestionTag().size()>0) {
                 int numOfViews=ques.getQuestionTag().size(),pos=0;
                 int count=0,max=10;
                 Log.d("while","inwhile"+ques.getTitle().getRendered());
                 while(numOfViews>0) {

                     count++;
                     Log.d("while","inwhile"+ques.getTitle().getRendered());
                     numOfViews--;
                     TextView textView1 = new TextView(context);
                     LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                             ViewGroup.LayoutParams.WRAP_CONTENT);
                     params.setMargins(15,10,0,0);
                     textView1.setBackgroundResource(R.drawable.custom);
                     textView1.setTextColor(ContextCompat.getColor(textView1.getContext(), R.color.black));
                     textView1.setLayoutParams(params);
                     textView1.setPadding(2,2,2,2);
                     textView1.setText(ques.getQuestionTag().get(pos)+""+position);
                     quesholder.tagsLayout.addView(textView1,pos);
                     quesholder.replies.setText(ques.getLinks().getReplies().size()+"");

                     //quesholder.Tag.setText(String.valueOf(ques.getQuestionTag().get(pos)));
                     pos++;
                 }
                 Log.d("while",count+"at end");
             }
        if(ques.getModified()!=null){
            quesholder.Date.setText("Edited "+formatDate(ques.getDate()));
        }
        else {
            quesholder.Date.setText(formatDate(ques.getDate()));
        }
         quesholder.SeeMore.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String url=ques.getLink();
                 CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                 CustomTabsIntent customTabsIntent = builder.build();
                 customTabsIntent.launchUrl(context, Uri.parse(url));
             }
         });
         break;

         case LOADING:
             break;
     }
    }

    private String formatDate(String date ) {
        CharSequence ago="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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

    @Override
    public int getItemCount() {
        return item.size();
    }

    public int getItemViewType(int position){


       return (position==item.size()-1&&isLoadingAdded)?LOADING:ITEM;
    }

    public void add(QuestionList ques){
        item.add(ques);

        notifyItemInserted(item.size()-1);
    }

    public void addLoadingFooter(){
        isLoadingAdded=true;
        add(new QuestionList());
    }

    public void removeLoadingFooter(){
        isLoadingAdded=false;
        int pos=item.size()-1;
        QuestionList ques=item.get(pos);
        if(ques!=null){
            item.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    public class QuesViewholder extends RecyclerView.ViewHolder{

TextView title,content,SeeMore,Date;
TextView replies;
LinearLayout tagsLayout;
        public QuesViewholder(@NonNull View itemView) {
            super(itemView);
           replies= itemView.findViewById(R.id.replies);
           title= itemView.findViewById(R.id.post_title);
           content= itemView.findViewById(R.id.post_desc);
           SeeMore= itemView.findViewById(R.id.loadmore);
           Date=itemView.findViewById(R.id.date);
           tagsLayout=itemView.findViewById(R.id.tagsLayout);
        }

    }

    public class Loader extends RecyclerView.ViewHolder{
        public Loader(@NonNull View itemView) {
            super(itemView);
        }
    }

}
