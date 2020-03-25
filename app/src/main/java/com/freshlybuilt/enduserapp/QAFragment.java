package com.freshlybuilt.enduserapp;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.freshlybuilt.enduserapp.adapters.questionAdapter;
import com.freshlybuilt.enduserapp.adapters.searchAdapter;
import com.freshlybuilt.enduserapp.api.ApiInstance;
import com.freshlybuilt.enduserapp.models.Post;
import com.freshlybuilt.enduserapp.models.PostsSearch;
import com.freshlybuilt.enduserapp.models.QuestionList;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QAFragment extends Fragment {
    RecyclerView mPostsListView;
    LinearLayout layout;
    boolean isScrolling,remove=false,removeSearchLoader=false;
    int currentItems,totalItems,scrollOutItems,page=1,pagesOfSearch=1;
    ArrayList<QuestionList> queslist=new ArrayList<>();
    ArrayList<Post> searches=new ArrayList<>();
    String previousKeyword;
    questionAdapter quesData;
    searchAdapter adapter;

    ImageView load;
    TextView fetch;
    private SwipeRefreshLayout refresh;
    public static boolean OnSearch=false;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qa,container,false);
        layout=view.findViewById(R.id.qa_layout);
        refresh=view.findViewById(R.id.refresh_list);

        load=view.findViewById(R.id.qna_load);
        fetch=view.findViewById(R.id.text_load);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               fetchLatestQues();
            }
        });
        mPostsListView = (RecyclerView)view.findViewById(R.id.posts_listview);
        final LinearLayoutManager llm= new LinearLayoutManager(getContext());
        mPostsListView.setLayoutManager(llm);
        mPostsListView.setItemAnimator(new DefaultItemAnimator());
        quesData=new questionAdapter(getContext(),queslist);
        adapter=new searchAdapter(getContext(),searches);
        mPostsListView.setAdapter(adapter);
        mPostsListView.setAdapter(quesData);




        fetchQues(page);
        if(!queslist.isEmpty()) {
            load.setVisibility(View.GONE);
            fetch.setVisibility(View.GONE);
        }


        mPostsListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;
                }
                if(mPostsListView.getChildLayoutPosition(mPostsListView.findViewById(R.id.post_card_qa))==1)
                {
                    isScrolling=false;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems=llm.getChildCount();
                totalItems=llm.getItemCount();
                scrollOutItems=llm.findFirstVisibleItemPosition();
                if(isScrolling&&(currentItems+scrollOutItems==totalItems)) {
                    isScrolling = false;
                    if (mPostsListView.getAdapter() instanceof questionAdapter)
                        fetchQues(++page);
                    if (mPostsListView.getAdapter() instanceof searchAdapter)
                        onSearch(previousKeyword,++pagesOfSearch,false);
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setQuesAdapter(){
       mPostsListView.setAdapter(null);
        mPostsListView.setAdapter(quesData);
    }




    private void fetchLatestQues() {

        Call<List<QuestionList>> list = ApiInstance.getInstance().getQuesApi().getLatestQues();

        list.enqueue(new Callback<List<QuestionList>>() {

            @Override
            public void onResponse(Call<List<QuestionList>> call, Response<List<QuestionList>> response) {
                if(response.isSuccessful()) {
                    if(!queslist.isEmpty())
                        queslist.clear();
                    queslist.addAll(response.body());
                    Log.d("TAGGING", queslist.size() + "");

                    mPostsListView.setAdapter(quesData);
                    quesData.notifyDataSetChanged();
                    quesData.addLoadingFooter();
                    refresh.setRefreshing(false);
                }
                else{
                    refresh.setRefreshing(false);
                    Snackbar.make(getActivity().findViewById(R.id.fragment_container),"No more questions",Snackbar.LENGTH_SHORT).show();
                }
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<QuestionList>> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onSearch(String keyword, int pagesOfSearch, final boolean firstSearch){

      final LoadingFragment progressbar=LoadingFragment.getInstance();

        if(pagesOfSearch==1&&progressbar!=null){
            searches.clear();
            progressbar.quotesGenerator();
            progressbar.show(getFragmentManager(),"Loading");
        }
        //This if block chckes that if the method is called for a new keyword then it initialises the var into initial state;
        if(firstSearch){
            pagesOfSearch=1;
            removeSearchLoader=false;
        }
        previousKeyword=keyword;
        Log.d("search",previousKeyword+" stage1");
        final int currentpage=pagesOfSearch;
        if(keyword.length()>2){

            OnSearch=true;
            Call<PostsSearch> call=ApiInstance.getInstance().getSearchResults().getSearchResults(keyword,pagesOfSearch);
            call.enqueue(new Callback<PostsSearch>() {
                @Override
                public void onResponse(Call<PostsSearch> call, Response<PostsSearch> response) {
                    PostsSearch p = response.body();



                        if (p.getCountTotal() > 0) {

                            searches.addAll(p.getPosts());

                            if(currentpage!=1){
                                adapter.removeSearchLoadingFooter();
                                isScrolling=false;
                            }

                            Log.d("search",searches.size()+" stage2");
                            //Progressbar visibility gone after searchResults are added into list
                            if(currentpage==1)
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressbar.dismiss();
                                }
                            }, 1000);
                            //Setting up adapter
                            if(firstSearch) {
                                mPostsListView.setAdapter(null);
                                mPostsListView.setAdapter(adapter);
                            }
                            else {
                                adapter.notifyDataSetChanged();
                            }
                            Log.d("search",searches.size()+" stage3");
                            viewGone();

                           if(searches.size()>=10)
                            adapter.addLoadingFooter();
                            Log.d("search",searches.size()+" stage4");
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else if(currentpage!=1&&(p.getCount() == 0)) {
                          noMoreResults(0);

                        }

                    else{
                        mPostsListView.setAdapter(null);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressbar.dismiss();
                            }
                        }, 1000);
                        load.setVisibility(View.VISIBLE);
                        fetch.setText("No Search Results Found");
                        fetch.setVisibility(View.VISIBLE);
                    }
                }
                @Override
                public void onFailure(Call<PostsSearch> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }




    private void fetchQues(int page) {


            Call<List<QuestionList>> call = ApiInstance.getInstance().getQuesApi().getQues(page);

           final int currentpage=page;

            call.enqueue(new Callback<List<QuestionList>>() {
                @Override
                public void onResponse(Call<List<QuestionList>> call, Response<List<QuestionList>> response) {
                        if(response.isSuccessful()) {
                            if(currentpage!=1){
                                quesData.removeLoadingFooter();
                                isScrolling=false;
                            }
                            Log.d("TAGGING", queslist.size() + "");

                            queslist.addAll(response.body());
                            viewGone();

                            quesData.notifyDataSetChanged();
                            Log.d("while","notify");
                             quesData.addLoadingFooter();
                        }
                        else{
                            noMoreResults(1);
                        }

                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<List<QuestionList>> call, Throwable t) {
                    Log.d("TAG", t.getMessage());
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

public void noMoreResults(int adapterId){
        switch(adapterId){
        case 0:  if(!removeSearchLoader) {
            adapter.removeSearchLoadingFooter();//this if block checks that if all results are loaded then remove the last item of list i.e loading item once
            removeSearchLoader=true;
        }
        break;
            case 1: if(!remove) {
             quesData.removeLoadingFooter();
                remove=true;
            }
                break;

    }
    final Snackbar snackbar=Snackbar.make(getActivity().findViewById(R.id.fragment_container),"No more questions",Snackbar.LENGTH_SHORT).setAction("Back To Top", new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mPostsListView.post(new Runnable() {
                @Override
                public void run() {
                    // Call smooth scroll
                    mPostsListView.smoothScrollToPosition(1);
                }
            });

        }
    });
    snackbar.show();

}
        public void viewGone(){
            if(!queslist.isEmpty()) {
                load.setVisibility(View.GONE);
                fetch.setVisibility(View.GONE);
            }
        }
    }

