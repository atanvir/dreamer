package com.boushra.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Activity.MyWalletActivity;
import com.boushra.Adapter.PaymentListAdapter;
import com.boushra.Model.Menus;
import com.boushra.R;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.SharedPreferenceWriter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class NavigationMoreFragment extends Fragment implements View.OnClickListener{
    private LinearLayoutManager manager;
    PaymentListAdapter adapter;
    private RecyclerView recyclerView;
    List<Menus> menusList;
    ImageView notification_im;
    TextView pointtxt;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_navigation, container, false);
        SettingMenusValues();
        recyclerView=view.findViewById(R.id.paymentRecView);
        notification_im=view.findViewById(R.id.notification_im);
        pointtxt=view.findViewById(R.id.pointtxt);
        pointtxt.setOnClickListener(this);
        pointtxt.setText(""+SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables.totalPoints));

        notification_im.setOnClickListener((View.OnClickListener) this);
        manager=new LinearLayoutManager(getActivity());
        adapter =new PaymentListAdapter(getActivity(),menusList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);




        return view;

    }

    private void SettingMenusValues() {
        menusList=new ArrayList<>();
        menusList.add(new Menus("My Booking"));
        menusList.add(new Menus("My Payments"));
        menusList.add(new Menus("My Wallet"));
        menusList.add(new Menus("Terms & Conditions"));
        menusList.add(new Menus("Settings"));
        menusList.add(new Menus("Help"));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.notification_im:

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NotificationFragment()).commit();

            break;
            case R.id.pointtxt:
                Intent intent=new Intent(getActivity(), MyWalletActivity.class);
                startActivity(intent);
                break;
        }
    }
}