package com.boushra.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.boushra.Activity.CategorySelectionActivity;
import com.boushra.Adapter.ForcasterListAdapter;
import com.boushra.Model.Data;
import com.boushra.Model.ForcasterList;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecasterListFragment extends Fragment {
    @BindView(R.id.ForcasterRecyclerView) RecyclerView ForcasterRecyclerView;
    @BindView(R.id.backLL) LinearLayout backLL;
    private ForcasterListAdapter listAdapter;
    List<Data> List;
    LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_forecaster_list,container,false);
        ButterKnife.bind(this,view);
        init();
        getForcasterListApi();
        return view;
 }





    private void getForcasterListApi() {
        ProgressDailogHelper dailog=new ProgressDailogHelper(getActivity(),"Getting forecaster list");
        dailog.showDailog();
        RetroInterface api_service= RetrofitInit.getConnect().createConnection();
        ForcasterList forcasterList=new ForcasterList();
        forcasterList.setUserId(SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables._id));
        forcasterList.setLangCode("en");
        forcasterList.setType(GlobalVariables.getType());
        Call<ForcasterList> call=api_service.getForecasterList(forcasterList,SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables.jwtToken));
        call.enqueue(new Callback<ForcasterList>() {
            @Override
            public void onResponse(Call<ForcasterList> call, Response<ForcasterList> response) {
                if(response.isSuccessful())
                {
                    dailog.dismissDailog();
                    ForcasterList server_response=response.body();
                    if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                    {

                        List=server_response.getData();
                        settingAdapter();
                    }else if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                    {
                        Toast.makeText(getActivity(),server_response.getResponseMessage(),Toast.LENGTH_LONG).show();

                    }



                }
            }

            @Override
            public void onFailure(Call<ForcasterList> call, Throwable t) {
                Toast.makeText(getActivity(),""+t.getStackTrace(),Toast.LENGTH_LONG).show();
                Log.e("Failure", String.valueOf(t.getStackTrace()));
                t.printStackTrace();

            }
        });

    }

    private void settingAdapter() {
        ForcasterRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter=new ForcasterListAdapter(getActivity(),List);
        ForcasterRecyclerView.setAdapter(listAdapter);
    }


    private void init() {
        backLL.setOnClickListener(this::OnClick);
        List=new ArrayList<>();
    }


    @OnClick()
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.backLL:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationHomeFragment()).commit();
                break;
        }
    }



}
