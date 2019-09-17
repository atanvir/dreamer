package com.boushra.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.boushra.Fragment.MyBookingFragment;
import com.boushra.Model.Data;
import com.boushra.Model.Rating;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.InternetCheck;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookingsListAdapter extends RecyclerView.Adapter<MyBookingsListAdapter.MyViewHolder> {
    private Context context;
    private List<Data> bookinglist;


    public MyBookingsListAdapter(Context context,List<Data> bookinglist) {
        this.context=context;
        this.bookinglist=bookinglist;
    }


    @NonNull
    @Override
    public MyBookingsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(context).inflate(R.layout.adapter_mybooking,viewGroup,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingsListAdapter.MyViewHolder myViewHolder, int i) {
        Glide.with(context).load(bookinglist.get(i).getForecasterData().getProfilePic()).into(myViewHolder.profilepic_iv);
        myViewHolder.name_txt.setText(bookinglist.get(i).getForecasterData().getName());
        myViewHolder.price_txt.setText("Price "+bookinglist.get(i).getForecasterData().getPricePerQues());

        String getDate = bookinglist.get(i).getCreatedAt();
        String server_format = getDate;    //server comes format ?
        String server_format1 = "2019-04-04T13:27:36.591Z";    //server comes format ?
        String myFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            Date date = sdf.parse(server_format);
            System.out.println(date);
            String your_format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
            System.out.println(your_format);
            String[] splitted = your_format.split(" ");
            System.out.println(splitted[1]);    //The second part of the splitted string, i.e time
            // Now you can set the TextView here
            myViewHolder.calenderdatetxt.setText(String.valueOf(splitted[0]));
        } catch (Exception e) {
            System.out.println(e.toString()); //date format error
        }
        if(bookinglist.get(i).getForecasterData().getTotalRating()>0)
        {
            myViewHolder.calenderdatetxt.setVisibility(View.GONE);
            myViewHolder.calenderImgVw.setVisibility(View.GONE);
            myViewHolder.ratingBar3.setVisibility(View.VISIBLE);
            myViewHolder.ratingText.setVisibility(View.GONE);
            myViewHolder.ratingBar3.setRating(bookinglist.get(i).getForecasterData().getAvgRating());

        }
        else
        {

            myViewHolder.calenderdatetxt.setVisibility(View.VISIBLE);
            myViewHolder.calenderImgVw.setVisibility(View.VISIBLE);
            myViewHolder.ratingBar3.setVisibility(View.GONE);
            myViewHolder.ratingText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return bookinglist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ratingText) TextView ratingText;
        @BindView(R.id.calenderImgVw) ImageView calenderImgVw;
        @BindView(R.id.calenderdatetxt) TextView calenderdatetxt;
        @BindView(R.id.ratingBar3) RatingBar ratingBar3;
        @BindView(R.id.profilepic_iv) CircleImageView profilepic_iv;
        @BindView(R.id.name_txt) TextView name_txt;
        @BindView(R.id.price_txt) TextView price_txt;


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
                    EditText rating_cmt_ed=dialog.findViewById(R.id.rating_cmt_ed);
                    TextView please_rate_txt=dialog.findViewById(R.id.please_rate_txt);


                    ratingTxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!rating_cmt_ed.getText().toString().isEmpty() && ratingBar2.getRating()!=0)
                            {

                                if(new InternetCheck(context).isConnect())
                                {
                                    ProgressDailogHelper dailogHelper=new ProgressDailogHelper(context,"Rating..");
                                    dailogHelper.showDailog();
                                    RetroInterface api_service=RetrofitInit.getConnect().createConnection();
                                    Rating rating=new Rating();
                                    rating.setUserId(SharedPreferenceWriter.getInstance(context).getString(GlobalVariables._id));
                                    rating.setForecasterId(bookinglist.get(getAdapterPosition()).getForecasterId());
                                    rating.setBookingId(bookinglist.get(getAdapterPosition()).getId());
                                    rating.setRating((int) ratingBar2.getRating());
                                    rating.setRatingMessage(rating_cmt_ed.getText().toString());

                                    Call<Rating> call=api_service.rating(rating,SharedPreferenceWriter.getInstance(context).getString(GlobalVariables.jwtToken));
                                    call.enqueue(new Callback<Rating>() {
                                        @Override
                                        public void onResponse(Call<Rating> call, Response<Rating> response) {
                                            if(response.isSuccessful())
                                            {
                                             dailogHelper.dismissDailog();
                                             dialog.dismiss();
                                             Rating server_response=response.body();
                                             if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                                             {


                                                Toast toast= Toast.makeText(context,server_response.getResponseMessage(),Toast.LENGTH_LONG);
                                                 toast.setGravity(Gravity.CENTER,0,0);
                                                 toast.show();

                                                 ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.replace,new MyBookingFragment()).commit();

                                             }
                                             else if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                                             {
                                                 Toast toast= Toast.makeText(context,server_response.getResponseMessage(),Toast.LENGTH_LONG);
                                                 toast.setGravity(Gravity.CENTER,0,0);
                                                 toast.show();
                                             }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Rating> call, Throwable t) {

                                        }
                                    });




                                }
                                else
                                {
                                    Toast toast=Toast.makeText(context,context.getString(R.string.check_internet),Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER,0,0);
                                    toast.show();
                                }


                            }
                            else
                            {
                                rating_cmt_ed.setError("Please write your comment");
                                rating_cmt_ed.setFocusable(true);
                                rating_cmt_ed.requestFocus();
                                please_rate_txt.setError("");



                            }

                            ratingBar2.getRating();




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
