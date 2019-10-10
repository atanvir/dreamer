package com.boushra.Adapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Activity.AddMoneyActivity;
import com.boushra.Activity.PaymentMethodActivity;
import com.boushra.Model.Data;
import com.boushra.Model.StorePoints;
import com.boushra.R;
import com.boushra.Utility.GlobalVariables;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {
    private Context context;
    private List<Data> storePointsList;


    public StoreAdapter(Context context,List<Data> storePointsList) {
        this.context=context;
        this.storePointsList=storePointsList;
    }


    @NonNull
    @Override
    public StoreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(context).inflate(R.layout.layout_store,viewGroup,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.pointtxt.setText(""+storePointsList.get(position).getPoints());
        myViewHolder.pushase_sr_txt.setText("Purchase "+storePointsList.get(position).getAmount()+"SAR");


    }

    @Override
    public int getItemCount() {
        return storePointsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView7) CardView cardView7;
        @BindView(R.id.pointtxt) TextView pointtxt;
        @BindView(R.id.coin_im) ImageView coin_im;
        @BindView(R.id.pushase_sr_txt) TextView pushase_sr_txt;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            cardView7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog=new Dialog(context,android.R.style.Theme_Black);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.activity_boushra_point_popup);
                    TextView continuetext = dialog.findViewById(R.id.continuetext);
                    LinearLayout closell=dialog.findViewById(R.id.closell);
                    TextView paymentmethodtxt=dialog.findViewById(R.id.paymentmethodtxt);
                    ImageView paymentmethodIm=dialog.findViewById(R.id.paymentmethodIm);
                    TextView pointtxt=dialog.findViewById(R.id.pointtxt);
                    TextView total_price_txt=dialog.findViewById(R.id.total_price_txt);

                    pointtxt.setText(storePointsList.get(getAdapterPosition()).getPoints());
                    total_price_txt.setText(""+storePointsList.get(getAdapterPosition()).getAmount()+"SAR");


                    paymentmethodIm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent=new Intent(context,PaymentMethodActivity.class);
                            context.startActivity(intent);
                        }
                    });

                    paymentmethodtxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent=new Intent(context, PaymentMethodActivity.class);
                            context.startActivity(intent);


                        }
                    });
                    closell.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });


                    continuetext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Intent intent = new Intent(context, AddMoneyActivity.class);
                                intent.putExtra("StoreActivity","yes");
                                intent.putExtra(GlobalVariables.storeId,storePointsList.get(getAdapterPosition()).getId());
                                intent.putExtra(GlobalVariables.points,storePointsList.get(getAdapterPosition()).getPoints());
                                intent.putExtra(GlobalVariables.totalPrice,""+storePointsList.get(getAdapterPosition()).getAmount());
                                Log.e("points", String.valueOf(storePointsList.get(getAdapterPosition()).getAmount()));
                                context.startActivity(intent);

                            }
                        });

                        dialog.show();


                }
            });

        }
    }

}
