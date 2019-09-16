package com.boushra.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Activity.MyWalletActivity;
import com.boushra.Activity.SettingsActivity;
import com.boushra.Activity.TermsandConditionsActivity;
import com.boushra.Activity.WebviewActivity;
import com.boushra.Fragment.MyBookingFragment;
import com.boushra.Fragment.NavigationMoreFragment;
import com.boushra.Model.Menus;
import com.boushra.Model.UserSetting;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.SharedPreferenceWriter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.MyViewHolder> {
    private Context context;
    private List<Menus> menusList;


    public PaymentListAdapter(Context context, List<Menus> menusList) {
        this.context=context;
        this.menusList=menusList;
    }


    @NonNull
    @Override
    public PaymentListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(context).inflate(R.layout.adapter_payment,viewGroup,false);

        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull PaymentListAdapter.MyViewHolder myViewHolder, int positon) {
        if(positon==2)
        {
            myViewHolder.walletpointtxtView.setVisibility(View.VISIBLE);
            myViewHolder.walletpointtxtView.setText(""+SharedPreferenceWriter.getInstance(context).getInt(GlobalVariables.totalPoints));
        }
        else
        {
            myViewHolder.walletpointtxtView.setVisibility(View.GONE);
        }

        myViewHolder.menu_text.setText(menusList.get(positon).getMenu_name());

    }

    @Override
    public int getItemCount() {
        return menusList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.menu_text)
        TextView menu_text;
        @BindView(R.id.Cv_Main)
        CardView Cv_Main;
        @BindView(R.id.point_txt)
        TextView walletpointtxtView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            walletpointtxtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,MyWalletActivity.class);
                    context.startActivity(intent);
                }
            });


            Cv_Main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getAdapterPosition()==0)
                    {
                        Log.e("NavigationMore",NavigationMoreFragment.class.getSimpleName());
                        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.replace,new MyBookingFragment()).addToBackStack(NavigationMoreFragment.class.getSimpleName()).commit();

                    }
                    if(getAdapterPosition()==2)
                    {
                        Intent intent=new Intent(context, MyWalletActivity.class);
                        context.startActivity(intent);

                    }
                    if(getAdapterPosition()==3)
                    {
                        Intent intent=new Intent(context, TermsandConditionsActivity.class);
                        context.startActivity(intent);

                    }

                    if(getAdapterPosition()==4) {
                        RetroInterface api_service= RetrofitInit.getConnect().createConnection();
                        UserSetting setting=new UserSetting();
                        setting.setUserId(SharedPreferenceWriter.getInstance(context).getString(GlobalVariables._id));
                        setting.setLangCode("en");
                        Call<UserSetting> call=api_service.getUserSettings(setting,SharedPreferenceWriter.getInstance(context).getString(GlobalVariables.jwtToken));
                        call.enqueue(new Callback<UserSetting>() {
                            @Override
                            public void onResponse(Call<UserSetting> call, Response<UserSetting> response) {
                                if(response.isSuccessful())
                                {
                                    UserSetting server_resposne=response.body();
                                    if(server_resposne.getStatus().equalsIgnoreCase("SUCCESS"))
                                    {
                                        setPreferences(server_resposne);
                                        Intent intent = new Intent(context, SettingsActivity.class);
                                        context.startActivity(intent);
                                        setPreferences(server_resposne);
                                        Toast.makeText(context,""+server_resposne.getResponseMessage(),Toast.LENGTH_LONG).show();


                                    }
                                    else if(server_resposne.getStatus().equalsIgnoreCase("FAILURE"))
                                    {
                                        Toast.makeText(context,""+server_resposne.getResponseMessage(),Toast.LENGTH_LONG).show();


                                    }



                                }
                            }

                            private void setPreferences(UserSetting server_resposne) {
                                SharedPreferenceWriter.getInstance(context).writeBooleanValue(GlobalVariables.notificationStatus,server_resposne.getData().getNotificationStatus());
                                SharedPreferenceWriter.getInstance(context).writeStringValue(GlobalVariables.language,server_resposne.getData().getLanguage());
                                SharedPreferenceWriter.getInstance(context).writeStringValue(GlobalVariables._id,server_resposne.getData().getId());
                            }

                            @Override
                            public void onFailure(Call<UserSetting> call, Throwable t) {

                            }
                        });



                    }
                    if(getAdapterPosition()==5)
                    {
                        Intent intent=new Intent(context, WebviewActivity.class);
                        context.startActivity(intent);

                    }

                }
            });

        }
    }
}
