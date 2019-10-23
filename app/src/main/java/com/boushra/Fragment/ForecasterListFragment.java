package com.boushra.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.boushra.Activity.CategorySelectionActivity;
import com.boushra.Activity.LoginActivity;
import com.boushra.Adapter.ForcasterListAdapter;
import com.boushra.Model.Data;
import com.boushra.Model.ForcasterList;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecasterListFragment extends Fragment implements Toolbar.OnMenuItemClickListener {
    @BindView(R.id.ForcasterRecyclerView) RecyclerView ForcasterRecyclerView;
    @BindView(R.id.filter_iv) ImageView filter_iv;
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private ForcasterListAdapter listAdapter;
    List<Data> List;
    private boolean price=false,rating=false;
    LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_forecaster_list,container,false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.filter_menu);
        toolbar.setOverflowIcon(getActivity().getDrawable(R.drawable.filter));
        toolbar.setOnMenuItemClickListener(this);

        init();
        getForcasterListApi(price,rating);
        return view;
 }


    private void getForcasterListApi(boolean price,boolean rating) {
        ProgressDailogHelper dailog=new ProgressDailogHelper(getActivity(),"Getting forecaster list");
        dailog.showDailog();
        RetroInterface api_service= RetrofitInit.getConnect().createConnection();
        ForcasterList forcasterList=new ForcasterList();
        forcasterList.setUserId(SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables._id));
        forcasterList.setLangCode(SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables.langCode));
        forcasterList.setPrice(price);
        forcasterList.setRating(rating);
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
                        if (server_response.getResponseMessage().equalsIgnoreCase(GlobalVariables.invalidoken)) {
                            Toast.makeText(getActivity(), getString(R.string.other_device_logged_in), Toast.LENGTH_LONG).show();
                            getActivity().finish();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            SharedPreferenceWriter.getInstance(getActivity()).clearPreferenceValues();
                            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        // Log.w(TAG, "getInstanceId failed", task.getException());
                                        return;
                                    }

                                    String auth_token = task.getResult().getToken();
                                    Log.w("firebaese", "token: " + auth_token);
                                    SharedPreferenceWriter.getInstance(getActivity()).writeStringValue(GlobalVariables.firebase_token, auth_token);
                                }
                            });
                        }
                        else {
                            Toast.makeText(getActivity(), server_response.getResponseMessage(), Toast.LENGTH_LONG).show();
                        }
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
        filter_iv.setOnClickListener(this::OnClick);
    }


    @OnClick()
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.backLL:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationHomeFragment()).commit();
                break;


            case R.id.filter_iv:



                break;
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {



        switch (item.getItemId())
        {
            case R.id.price_menu:

            price=true;
            rating=false;
            getForcasterListApi(price,rating);
            item.setChecked(true);

            break;

            case R.id.rating_menu:
            rating=true;
            price=false;
            getForcasterListApi(price,rating);
            item.setChecked(true);
            break;

        }

        return false;
    }
}
