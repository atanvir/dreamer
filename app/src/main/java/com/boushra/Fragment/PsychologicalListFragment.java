package com.boushra.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.boushra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PsychologicalListFragment extends Fragment {
    @BindView(R.id.notification_im) ImageView notification_im;
    @BindView(R.id.self_development_cv) CardView self_development_cv;
    @BindView(R.id.family_cv) CardView family_cv;
    @BindView(R.id.parenting_cv) CardView parenting_cv;
    @BindView(R.id.psychological_cv) CardView psychological_cv;
    @BindView(R.id.backLL) LinearLayout backLL;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_psychological_list,container,false);
        ButterKnife.bind(this,view);
        init();



        return view;
    }

    private void init() {
        notification_im.setOnClickListener(this::onClick);
        self_development_cv.setOnClickListener(this::onClick);
        family_cv.setOnClickListener(this::onClick);
        parenting_cv.setOnClickListener(this::onClick);
        psychological_cv.setOnClickListener(this::onClick);
        backLL.setOnClickListener(this::onClick);

    }


    @OnClick()
void onClick(View view)
{
    switch(view.getId())
    {
        case R.id.notification_im:
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NotificationFragment()).commit();
            break;

        case R.id.self_development_cv:
           getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace,new ForecasterListFragment()).addToBackStack(ForecasterListFragment.class.getName()).addToBackStack(NavigationHomeFragment.class.getName()).commit();
           break;

        case R.id.family_cv:
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace,new ForecasterListFragment()).addToBackStack(ForecasterListFragment.class.getName()).addToBackStack(NavigationHomeFragment.class.getName()).commit();
            break;

        case R.id.parenting_cv:
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace,new ForecasterListFragment()).addToBackStack(ForecasterListFragment.class.getName()).addToBackStack(NavigationHomeFragment.class.getName()).commit();
            break;

        case R.id.psychological_cv:
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace,new ForecasterListFragment()).addToBackStack(ForecasterListFragment.class.getName()).commit();
            break;

        case R.id.backLL:
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace,new NavigationHomeFragment()).commit();
            break;

    }
}
}

