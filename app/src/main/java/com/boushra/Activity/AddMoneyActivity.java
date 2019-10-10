package com.boushra.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.databinding.adapters.ToolbarBindingAdapter;

import com.boushra.Model.BankDetail;
import com.boushra.Model.Common;
import com.boushra.Model.ApplyPromocode;
import com.boushra.Model.Payment;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.InternetCheck;
import com.boushra.Utility.PaymentServiceBody;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;
import com.boushra.Utility.TakeImage;
import com.boushra.Utility.Validation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AddMoneyActivity extends AppCompatActivity {
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.submittxt) TextView submittxt;
    @BindView(R.id.netTotal_txt) TextView netTotal_txt;
    @BindView(R.id.discount_txt) TextView discount_txt;
    @BindView(R.id.total_price_txt) TextView total_price_txt;
    @BindView(R.id.admin_accountno_txt) TextView admin_accountno_txt;
    @BindView(R.id.admin_accountholder_txt) TextView admin_accountholder_txt;
    @BindView(R.id.admin_bankname_txt) TextView admin_bankname_txt;
    @BindView(R.id.apply_txt) TextView apply_txt;
    @BindView(R.id.promocode_ed) EditText promocode_ed;
    @BindView(R.id.banktransfer_txt) TextView banktransfer_txt;
    @BindView(R.id.scrollView) ScrollView scrollView;
    @BindView(R.id.bankaccountholder_ed) EditText bankaccountholder_ed;
    @BindView(R.id.selectbank_spn) Spinner selectbank_spn;
    @BindView(R.id.selectbank_txt) TextView selectbank_txt;
    @BindView(R.id.accountno_ed) EditText accountno_ed;
    @BindView(R.id.attach_photo_iv) ImageView attach_photo_iv;
    @BindView(R.id.attach_photo_txt) TextView attach_photo_txt;
    @BindView(R.id.banktransfer_cv) CardView banktransfer_cv;
    @BindView(R.id.paymentgateway_txt) TextView paymentgateway_txt;
    List<String> bankList;
    private final int CAMERA_PIC_REQUEST = 11, REQ_CODE_PICK_IMAGE = 1;
    private int START_VERIFICATION = 1001;
    final int PERMISSION_REQUEST_CODE = 200;
    private ProgressDailogHelper dailogHelper;
    private String imagePath = null;
    private File attach_photo;
    private boolean promocode=false;
    String paymentType="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();
        getAdminBankDeatilApi();
        spinnerClick();
    }

    private void spinnerClick() {
        selectbank_spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectbank_txt.setError(null);
                selectbank_txt.setText(bankList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getAdminBankDeatilApi() {
        if(new InternetCheck(this).isConnect())
        {
            dailogHelper.showDailog();
            RetroInterface api_service= RetrofitInit.getConnect().createConnection();
            Call<BankDetail> call=api_service.getAdminBankDetail();
            call.enqueue(new Callback<BankDetail>() {
                @Override
                public void onResponse(Call<BankDetail> call, Response<BankDetail> response) {
                    if(response.isSuccessful())
                    {
                       dailogHelper.dismissDailog();
                        BankDetail server_response=response.body();
                       if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                       {
                           admin_bankname_txt.setText(server_response.getData().getBankName());
                           admin_accountholder_txt.setText(server_response.getData().getAccountHolderName());
                           admin_accountno_txt.setText(server_response.getData().getAccountNumber());


                       }else if(server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                       {
                           Toast.makeText(AddMoneyActivity.this, server_response.getResponseMessage(), Toast.LENGTH_SHORT).show();

                       }

                    }
                }

                @Override
                public void onFailure(Call<BankDetail> call, Throwable t) {
                    dailogHelper.dismissDailog();
                    Toast.makeText(AddMoneyActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        }else
        {
            Toast toast=Toast.makeText(this, getString(R.string.check_internet), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(AddMoneyActivity.this,StoreActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("StoreActivity","yes");
        startActivity(intent);
        finish();

    }

    private void init() {
        backLL.setOnClickListener(this::OnClick);
        submittxt.setOnClickListener(this::OnClick);
        netTotal_txt.setText(getIntent().getStringExtra(GlobalVariables.totalPrice)+"SAR");
        discount_txt.setText("-0SAR");
        total_price_txt.setText(getIntent().getStringExtra(GlobalVariables.totalPrice)+"SAR");
        dailogHelper=new ProgressDailogHelper(this,"");
        apply_txt.setOnClickListener(this::OnClick);
        banktransfer_txt.setOnClickListener(this::OnClick);
        selectbank_txt.setOnClickListener(this::OnClick);
        attach_photo_iv.setOnClickListener(this::OnClick);
        paymentgateway_txt.setOnClickListener(this::OnClick);

    }

    @OnClick()
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.submittxt:
                if(checkValidation())
                Banktransferpopup();
                break;

            case R.id.backLL:
                Intent intent=new Intent(AddMoneyActivity.this,StoreActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("StoreActivity","yes");
                startActivity(intent);
                finish();
                break;

            case R.id.apply_txt:
                if(!promocode_ed.getText().toString().isEmpty())
                {
                    promocode=true;
                    applyPromocodeApi();
                }

                break;

            case R.id.banktransfer_txt:
                banktransfer_cv.setVisibility(View.VISIBLE);
                attach_photo_iv.setVisibility(View.VISIBLE);
                attach_photo_txt.setVisibility(View.VISIBLE);
                scrollView.fullScroll(View.FOCUS_DOWN);
                paymentType="Bank";

                break;

            case R.id.selectbank_txt:
                BankSpinner();
                break;

            case R.id.attach_photo_iv:
                if (checkingPermission()) {
                    profileBottomLayout();
                }
                break;
            case R.id.paymentgateway_txt:
                paymentType="Gateway";
                banktransfer_cv.setVisibility(View.GONE);
                attach_photo_iv.setVisibility(View.GONE);
                attach_photo_txt.setVisibility(View.GONE);
                break;
        }

    }

    private boolean checkingPermission() {
        boolean ret=true;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                ret=false;


                requestPermissions(new String[]{CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);




            } else {

                ret=true;
            }
        }
        return ret;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            switch (requestCode)
            {
                case PERMISSION_REQUEST_CODE:
                    if(grantResults.length>0) {
                        boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        boolean writeAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                        boolean readAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                        if (cameraAccepted && writeAccepted && readAccepted) {
                            //Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
                               profileBottomLayout();

                    }

                    break;

            }

        }
    }

    private void profileBottomLayout() {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.update_pic_layout, null);
        ImageView camera = (ImageView) popupView.findViewById(R.id.camera);
        ImageView gallery = (ImageView) popupView.findViewById(R.id.gallery);

        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        alertDialog.setTitle("Upload photo");
        alertDialog.setView(popupView);
        final AlertDialog dialog = alertDialog.show();
        alertDialog.setCancelable(true);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    dialog.dismiss();
                    Intent intent = new Intent(AddMoneyActivity.this, TakeImage.class);
                    intent.putExtra("from", "camera");
                    startActivityForResult(intent, CAMERA_PIC_REQUEST);
                } catch (Exception ex) {
                    Log.d("exp_result:", ex.getMessage().toString());
                }
            }


        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddMoneyActivity.this, TakeImage.class);
                intent.putExtra("from", "gallery");
                startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
                dialog.dismiss();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == START_VERIFICATION) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        } else if (resultCode == RESULT_OK) {
            if (data.getStringExtra("filePath") != null) {
                imagePath = data.getStringExtra("filePath");
                attach_photo = new File(data.getStringExtra("filePath"));

                if (attach_photo.exists() && attach_photo != null) {
                    attach_photo_iv.setImageURI(Uri.fromFile(attach_photo));
                    attach_photo_txt.setText(attach_photo.getName());
                }
            }
        } else if (requestCode == 1 && resultCode == RESULT_CANCELED) {
//            finish();


        }



        super.onActivityResult(requestCode, resultCode, data);


    }

    private boolean checkValidation() {
        boolean ret=true;

        if(selectbank_txt.getText().toString().equalsIgnoreCase(getString(R.string.select_bank))
        || !Validation.hasText(bankaccountholder_ed,getString(R.string.please_enter_account_holder_name))
        || !Validation.hasText(accountno_ed,getString(R.string.please_enter_account_number))
        || accountno_ed.getText().toString().length()!=16
        || imagePath==null
        )
        {
            if(selectbank_txt.getText().toString().equalsIgnoreCase(getString(R.string.select_bank)))
            {
                ret=false;
                selectbank_txt.setError("");
                selectbank_txt.setText(getString(R.string.select_bank));
                selectbank_txt.requestFocus();
            }
            else if(!Validation.hasText(bankaccountholder_ed,getString(R.string.please_enter_account_holder_name)))
            {
                ret=false;
                bankaccountholder_ed.setError(getString(R.string.please_enter_account_holder_name));
                bankaccountholder_ed.requestFocus();
            }
            else if(!Validation.hasText(accountno_ed,getString(R.string.please_enter_account_number)))
            {
                ret=false;
                accountno_ed.setError(getString(R.string.please_enter_account_number));
                accountno_ed.requestFocus();


            }
           else if(accountno_ed.getText().toString().length()!=16)
            {
                ret=false;
                accountno_ed.setError(getString(R.string.please_enter_valid_account_no));
                accountno_ed.requestFocus();
            }
           else if(imagePath==null)
            {
                ret=false;
                Toast.makeText(this, getString(R.string.please_attach_photo_transfer_receipt), Toast.LENGTH_SHORT).show();
            }


        }

        return ret;

    }

    private void BankSpinner() {
        bankList = new ArrayList<>();
        bankList.add(getString(R.string.select_bank));
        bankList.add("State Bank Of India");
        bankList.add("Punjab National Bank");
        bankList.add("ICIC Bank");


        ArrayAdapter genderArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bankList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                return super.getDropDownView(position, convertView, parent);
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                tv.setTextColor(Color.TRANSPARENT);

                // Return the view
                return tv;
            }
        };

        genderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectbank_spn.setAdapter(genderArrayAdapter);
        selectbank_spn.performClick();




    }

    private void applyPromocodeApi() {
        if(new InternetCheck(AddMoneyActivity.this).isConnect())
        {
            dailogHelper.showDailog();
            ApplyPromocode promocode=new ApplyPromocode();
            promocode.setPromocode(promocode_ed.getText().toString().toUpperCase());
            Date  date=Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String current_date=dateFormat.format(date);
            Log.e("currentdate",current_date);
            promocode.setCurrentDate(current_date);
            promocode.setUserId(SharedPreferenceWriter.getInstance(this).getString(GlobalVariables._id));
            promocode.setPrice(Long.parseLong(total_price_txt.getText().toString().split("S")[0]));


            RetroInterface api_service=RetrofitInit.getConnect().createConnection();
            Call<ApplyPromocode> call=api_service.applyPromocode(promocode,SharedPreferenceWriter.getInstance(this).getString(GlobalVariables.jwtToken));
            call.enqueue(new Callback<ApplyPromocode>() {
                @Override
                public void onResponse(Call<ApplyPromocode> call, Response<ApplyPromocode> response) {
                    if(response.isSuccessful()) {
                        dailogHelper.dismissDailog();
                        ApplyPromocode server_response = response.body();
                        if (server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS)) {
                            discount_txt.setText("-"+server_response.getData().getDiscount()+"SAR");
                            long netprice=Long.parseLong(total_price_txt.getText().toString().split("S")[0])-(server_response.getData().getDiscount());
                            netTotal_txt.setText(netprice+"SAR");
                        } else if (server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                        {
                            discount_txt.setText("-0SAR");
                            netTotal_txt.setText(total_price_txt.getText().toString());
                            Toast.makeText(AddMoneyActivity.this, server_response.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApplyPromocode> call, Throwable t) {
                    dailogHelper.dismissDailog();
                    Toast.makeText(AddMoneyActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    discount_txt.setText("-0SAR");
                    netTotal_txt.setText(total_price_txt.getText().toString());
                }
            });


        }else
        {



        }
    }


    private void Banktransferpopup() {
        if(new InternetCheck(this).isConnect())
        {
            dailogHelper.showDailog();
            Payment payment=new Payment();
            payment.setUserId(SharedPreferenceWriter.getInstance(this).getString(GlobalVariables._id));
            payment.setStoreId(getIntent().getStringExtra(GlobalVariables.storeId));
            payment.setAmount(Integer.parseInt(getIntent().getStringExtra(GlobalVariables.totalPrice)));
            payment.setPoints(Integer.parseInt(getIntent().getStringExtra(GlobalVariables.points)));
            payment.setTransactionId("1234567890");
            payment.setTransactionStatus("Success");
            payment.setPaymentType(paymentType);
            RequestBody requestBody;
            MultipartBody.Part photo = null;
            if(paymentType.equalsIgnoreCase("Bank"))
            {
                if(imagePath!=null)
                {
                    requestBody=RequestBody.create(MediaType.parse("image/*"),attach_photo);
                    photo=MultipartBody.Part.createFormData("receipt",attach_photo.getName(),requestBody);

                }
                payment.setBankName(selectbank_txt.getText().toString());
                payment.setAccountNumber(accountno_ed.getText().toString().trim());
                payment.setAccountHolderName(bankaccountholder_ed.getText().toString().trim());

            }
            // promocode applied than only

            if(promocode) {
                payment.setDiscountAmount(Integer.parseInt(discount_txt.getText().toString().split("S")[0].substring(1)));
                payment.setPromoCode(promocode_ed.getText().toString().trim());

            }
            payment.setTransactionType("Demo");

            PaymentServiceBody serviceBody=new PaymentServiceBody(payment);



            RetroInterface api_service=RetrofitInit.getConnect().createConnection();
            Call<Payment> call=api_service.payment(photo,serviceBody.getData());
            call.enqueue(new Callback<Payment>() {
                @Override
                public void onResponse(Call<Payment> call, Response<Payment> response) {
                    if(response.isSuccessful()) {
                        dailogHelper.dismissDailog();
                        Payment server_response = response.body();

                        if (server_response.getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS)) {
                            setupBankTransferPopup(server_response.getResponseMessage());
                        } else if (server_response.getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                        {
                            Toast.makeText(AddMoneyActivity.this, server_response.getResponseMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<Payment> call, Throwable t) {
                    dailogHelper.dismissDailog();
                    Toast.makeText(AddMoneyActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });







        }
        else
        {
            Toast toast=Toast.makeText(this, getString(R.string.check_internet), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();

        }


    }

    private void setupBankTransferPopup(String responseMessage) {
        final Dialog dialog=new Dialog(AddMoneyActivity.this,android.R.style.Theme_Black);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_bank_transfer_payment_popup);
        LinearLayout closell=dialog.findViewById(R.id.closell);
        TextView response_txt=dialog.findViewById(R.id.response_txt);
        response_txt.setText(responseMessage);
        dialog.setCancelable(true);
        closell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(AddMoneyActivity.this,CategorySelectionActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
    }
}
