package com.freshlybuilt.enduserapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freshlybuilt.enduserapp.adapters.PostsListAdapter;

public class ProfileFragment extends Fragment {

    RecyclerView mPostsLv;

    public Toolbar mAppBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment,container,false);
//        mPostsLv = view.findViewById(R.id.post_lv);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mPostsLv.setLayoutManager(new LinearLayoutManager(getContext()));
//        mPostsLv.setAdapter(new PostsListAdapter());

    }
}
