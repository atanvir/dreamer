package com.boushra.Adapter;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Activity.ForcasterForPriceActivity;
import com.boushra.Activity.ForecasterDetailsActivity;
import com.boushra.Activity.StoreActivity;
import com.boushra.Model.Data;
import com.boushra.R;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.SharedPreferenceWriter;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ForcasterListAdapter extends RecyclerView.Adapter<ForcasterListAdapter.MyViewHolder> {
    private Context context;
    private List<Data> lists;


    public ForcasterListAdapter(Context context, List<Data> lists) {
        this.context = context;
        this.lists = lists;

    }


    @NonNull
    @Override
    public ForcasterListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_forcaster_list, parent, false);

        return new ForcasterListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForcasterListAdapter.MyViewHolder holder, int position) {
       holder.forcasterNameTxt.setText(lists.get(position).getName());
       Glide.with(context).load(lists.get(position).getProfilePic()).into(holder.profile_im);
       holder.pendingQueue_txt.setText(""+lists.get(position).getPendingQueue());
       holder.responseTime_txt.setText(""+lists.get(position).getResponseTime());
       if(lists.get(position).getOnlineStatus())
       {
           holder.onlineStatus_iv.setVisibility(View.VISIBLE);
       }
       else
       {
           holder.onlineStatus_iv.setVisibility(View.GONE);
       }
       holder.ratingBar.setRating(lists.get(position).getAvgRating());
       holder.pricePerQues_txt.setText(lists.get(position).getPricePerQues());



    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.forcasterNameTxt) TextView forcasterNameTxt;
        @BindView(R.id.cardview_main) CardView cardview_main;
        @BindView(R.id.info_im) ImageView info_im;
        @BindView(R.id.responseTime_txt) TextView responseTime_txt;
        @BindView(R.id.pendingQueue_txt) TextView pendingQueue_txt;
        @BindView(R.id.profile_im) CircleImageView profile_im;
        @BindView(R.id.ratingBar) RatingBar ratingBar;
        @BindView(R.id.onlineStatus_iv) ImageView onlineStatus_iv;
        @BindView(R.id.pricePerQues_txt) TextView pricePerQues_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardview_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("points", String.valueOf(SharedPreferenceWriter.getInstance(context).getString(GlobalVariables.totalPoints)));
                            ForcasterforPricePopUp(getAdapterPosition());
                }
            });

            info_im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ForecasterDetailsActivity.class);

                    intent.putExtra(GlobalVariables.forecasterId,lists.get(getAdapterPosition()).getId());
                    intent.putExtra(GlobalVariables.categoryName,lists.get(getAdapterPosition()).getCategoryName());
                    intent.putExtra(GlobalVariables.userId,SharedPreferenceWriter.getInstance(context).getString(GlobalVariables._id));
                    intent.putExtra(GlobalVariables.points,lists.get(getAdapterPosition()).getPricePerQues());
                    context.startActivity(intent);


                }
            });
        }
    }

    private void ForcasterforPricePopUp(int position) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Black);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.forcaster_for_price);
        TextView yesTxt = dialog.findViewById(R.id.yesText);
        TextView noText = dialog.findViewById(R.id.noText);
        LinearLayout closell = dialog.findViewById(R.id.closell);
        CircleImageView forecaster_img=dialog.findViewById(R.id.forecaster_img);
        dialog.setCancelable(false);

        Glide.with(context).load(lists.get(position).getProfilePic()).into(forecaster_img);


        closell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yesTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lists.get(position).getPricePerQues() != null) {
                    dialog.dismiss();
                    if (Float.parseFloat(SharedPreferenceWriter.getInstance(context).getString(GlobalVariables.totalPoints)) < Float.parseFloat(lists.get(position).getPricePerQues())) {
                        Intent intent = new Intent(context, StoreActivity.class);
                        intent.putExtra("StoreActivity","yes");
                        context.startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(context, ForcasterForPriceActivity.class);
                        intent.putExtra(GlobalVariables.forecasterId,lists.get(position).getId());
                        intent.putExtra(GlobalVariables.points,lists.get(position).getPricePerQues());
                        intent.putExtra(GlobalVariables.categoryName,lists.get(position).getCategoryName());
                        context.startActivity(intent);

                    }
                }
                else
                {
                    Intent intent = new Intent(context, ForcasterForPriceActivity.class);
                    intent.putExtra(GlobalVariables.forecasterId,lists.get(position).getId());
                    intent.putExtra(GlobalVariables.points,lists.get(position).getPricePerQues());
                    intent.putExtra(GlobalVariables.categoryName,lists.get(position).getCategoryName());
                    context.startActivity(intent);

                }



            }
        });
        noText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
