package com.boushra.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Activity.CategorySelectionActivity;
import com.boushra.Activity.ProvideRatingActivity;
import com.boushra.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBookingsListAdapter extends RecyclerView.Adapter<MyBookingsListAdapter.MyViewHolder> {
    private Context context;


    public MyBookingsListAdapter(Context context) {
        this.context=context;
    }


    @NonNull
    @Override
    public MyBookingsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(context).inflate(R.layout.adapter_mybooking,viewGroup,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingsListAdapter.MyViewHolder myViewHolder, int i) {
        if(i>1)
        {
            myViewHolder.calenderdatetxt.setVisibility(View.GONE);
            myViewHolder.calenderImgVw.setVisibility(View.GONE);
            myViewHolder.ratingBar3.setVisibility(View.VISIBLE);
            myViewHolder.ratingText.setVisibility(View.GONE);

        }
        else {


            myViewHolder.calenderdatetxt.setVisibility(View.VISIBLE);
            myViewHolder.calenderImgVw.setVisibility(View.VISIBLE);
            myViewHolder.ratingBar3.setVisibility(View.GONE);
            myViewHolder.ratingText.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ratingText)
        TextView ratingText;

        @BindView(R.id.calenderImgVw)
        ImageView calenderImgVw;

        @BindView(R.id.calenderdatetxt)
        TextView calenderdatetxt;

        @BindView(R.id.ratingBar3)
        RatingBar ratingBar3;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            ratingText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog=new Dialog(context,android.R.style.Theme_Black);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.activity_provide_rating);

                    TextView ratingTxt=dialog.findViewById(R.id.ratingTxt);
                    LinearLayout closell=dialog.findViewById(R.id.closell);
                    RatingBar ratingBar2=dialog.findViewById(R.id.ratingBar2);


                    ratingTxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
//                            Intent intent=new Intent(context, CategorySelectionActivity.class);
//                            context.startActivity(intent);

                        }
                    });
                    closell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });
                    dialog.show();

                }
            });
        }
    }
}
