package com.boushra.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.boushra.Model.Data;
import com.boushra.Model.StaticContent;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.SharedPreferenceWriter;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsandConditionsActivity extends AppCompatActivity {
    @BindView(R.id.term_cl) ConstraintLayout term_cl;
    @BindView(R.id.term_condition_desc_txt) TextView term_condition_desc_txt;
    @BindView(R.id.term_condition_txt) TextView term_condition_txt;
    @BindView(R.id.backLL) LinearLayout backLL;

    long clickcount=0;
    long clickcount2=0;
    String langCode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsand_conditions);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();
        getStaticContent();

    }

    private void init() {

        term_cl.setOnClickListener(this::OnClick);
        term_condition_desc_txt.setOnClickListener(this::OnClick);
        backLL.setOnClickListener(this::OnClick);
        langCode=SharedPreferenceWriter.getInstance(this).getString(GlobalVariables.langCode);

    }

    private void getStaticContent() {
       RetroInterface api_serice= RetrofitInit.getConnect().createConnection();
       StaticContent staticContent=new StaticContent();
       staticContent.setLangCode(SharedPreferenceWriter.getInstance(this).getString(GlobalVariables.langCode));
       Call<StaticContent> call=api_serice.getStaticContent();
       call.enqueue(new Callback<StaticContent>() {
           @Override
           public void onResponse(Call<StaticContent> call, Response<StaticContent> response) {

                   StaticContent server_response=response.body();
                   List<Data> alldata=server_response.getData();
                   for(int i=0;i<alldata.size();i++)
                   {
                       if(alldata.get(i).getType()!=null) {

                           if (alldata.get(i).getType().equalsIgnoreCase("TermCondition")) {
                               if (langCode.equalsIgnoreCase("ar"))
                               {
                                   term_condition_txt.setText(alldata.get(i).getTitleArabic());
                                   term_condition_desc_txt.setText(alldata.get(i).getDescriptionArabic());
                               }
                               else
                               {
                                   term_condition_txt.setText(alldata.get(i).getTitle());
                                   term_condition_desc_txt.setText(alldata.get(i).getDescription());
                               }


                           }
                       }

                   }


           }

           @Override
           public void onFailure(Call<StaticContent> call, Throwable t) {

           }
       });



    }


    @OnClick()
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.backLL:

                super.onBackPressed();
                break;



            case R.id.term_cl:
                if(clickcount2 % 2==0)
                {
                    clickcount2=clickcount2+1;
                    term_condition_desc_txt.setVisibility(View.VISIBLE);

                }
                else
                {
                    clickcount2=clickcount2+1;
                    term_condition_desc_txt.setVisibility(View.GONE);

                }


                break;


        }

    }
}


