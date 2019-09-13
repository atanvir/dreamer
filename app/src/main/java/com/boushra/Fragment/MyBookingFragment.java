package com.boushra.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Adapter.MyBookingsListAdapter;
import com.boushra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyBookingFragment extends Fragment {
    private  LinearLayoutManager manager;
    private MyBookingsListAdapter adapter;
    private ImageView backImageView;
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.myBookingRecView) RecyclerView myBookingRecView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mybooking,container,false);
        ButterKnife.bind(this,view);
        init();
        manager=new LinearLayoutManager(getActivity());
        adapter =new MyBookingsListAdapter(getActivity());
        myBookingRecView.setLayoutManager(manager);
        myBookingRecView.setAdapter(adapter);



        return view;
    }

    private void init() {
        backLL.setOnClickListener(this::onClick);
    }

    @OnClick()
    void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.backLL:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationMoreFragment()).commit();
                break;
        }
    }


}
