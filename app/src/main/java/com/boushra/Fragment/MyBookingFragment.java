package com.boushra.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Adapter.MyBookingsListAdapter;
import com.boushra.Model.MyBooking.Data;
import com.boushra.Model.MyBooking.MyBooking;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.InternetCheck;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookingFragment extends Fragment {
    private  LinearLayoutManager manager;
    private MyBookingsListAdapter adapter;
    private ImageView backImageView;
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.myBookingRecView) RecyclerView myBookingRecView;
    List<Data> bookinglist;
    private ProgressDailogHelper dailogHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mybooking,container,false);
        ButterKnife.bind(this,view);
        init();
        getMyBookingApi();

        return view;
    }

    private void getMyBookingApi() {
        if(new InternetCheck(getActivity()).isConnect())
        {
            dailogHelper.showDailog();
            RetroInterface api_service= RetrofitInit.getConnect().createConnection();
            MyBooking booking=new MyBooking();
            booking.setUserId(SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables._id));
            booking.setLangCode("en");
            Call<MyBooking> call=api_service.getMyBooking(booking,SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables.jwtToken));
            call.enqueue(new Callback<MyBooking>() {
                @Override
                public void onResponse(Call<MyBooking> call, Response<MyBooking> response) {
                    if(response.isSuccessful())
                    {
                        dailogHelper.dismissDailog();
                        MyBooking server_response=response.body();
                        if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                        {
                            bookinglist=server_response.getData();
                            manager=new LinearLayoutManager(getActivity());
                            adapter =new MyBookingsListAdapter(getActivity(),bookinglist);
                            myBookingRecView.setLayoutManager(manager);
                            myBookingRecView.setAdapter(adapter);
                        }
                        else if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                        {
                            Toast toast=Toast.makeText(getActivity(),server_response.getResponseMessage(),Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();


                        }


                    }
                }

                @Override
                public void onFailure(Call<MyBooking> call, Throwable t) {
                    Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });


        }
        else
        {
            Toast toast=Toast.makeText(getActivity(),getString(R.string.check_internet),Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    private void init() {
        backLL.setOnClickListener(this::onClick);
        dailogHelper=new ProgressDailogHelper(getActivity(),"");
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
