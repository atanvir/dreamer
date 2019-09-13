package com.boushra.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.boushra.Activity.CategorySelectionActivity;
import com.boushra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationFragment extends Fragment {
    @BindView(R.id.backLL) LinearLayout backLL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_notification,container,false);
        ButterKnife.bind(this,view);
        init();

        return view;
    }

    private void init() {
        backLL.setOnClickListener(this::OnClick);
    }

    @OnClick()
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.backLL:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace, new NavigationHomeFragment()).commit();
                break;
        }
    }
}
