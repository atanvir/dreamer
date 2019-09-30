package com.boushra.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.boushra.Activity.CategorySelectionActivity;
import com.boushra.Activity.LoginActivity;
import com.boushra.Activity.MyWalletActivity;
import com.boushra.Model.Data;
import com.boushra.Model.UpdateUserProfile;
import com.boushra.Model.User;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.AddServiceBody;
import com.boushra.Utility.TakeImage;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;
import com.boushra.Utility.Validation;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class NavigationProfileFragment extends Fragment {
    @BindView(R.id.save_btn) Button save_btn;
    @BindView(R.id.camera_im) ImageView camera_im;
    @BindView(R.id.profile_im) CircleImageView profile_im;
    @BindView(R.id.notification_im) ImageView notification_im;
    @BindView(R.id.full_name_ed) EditText full_name_ed;
    @BindView(R.id.username_ed) EditText username_ed;
    @BindView(R.id.email_ed) EditText email_ed;
    @BindView(R.id.phone_ed) EditText phone_ed;
    @BindView(R.id.pob_ed) EditText pob_ed;
    @BindView(R.id.maritalstatus_txt) TextView maritalstatus_txt;
    @BindView(R.id.gendertxt) TextView gendertxt;
    @BindView(R.id.genderSpn) Spinner genderSpn;
    @BindView(R.id.martialStatusSpn) Spinner martialStatusSpn;
    @BindView(R.id.dob_ed) EditText dob_ed;
    @BindView(R.id.pointtxt) TextView pointtxt;
    @BindView(R.id.countrycode_txt) TextView countrycode_txt;

    List<String> genderlist;
    List<String> martailList;
    private final int PERMISSION_CODE=12;
    private final int CAMERA_REQUEST = 100;
    private final int GALLERY_PICTURE = 101;
    private File captureMediaFile;
    private File UserImageFile;
    public static String mCurrentPhotoPath1="";
    String image64;
    final int  PERMISSION_REQUEST_CODE=200;
    private static final String IMAGES_DIR = "Boushra";
    private final int CAMERA_PIC_REQUEST = 11, REQ_CODE_PICK_IMAGE = 1;
    private File fileFlyer;
    private String imagePath = null;
    private int START_VERIFICATION = 1001;
    private DatePickerDialog.OnDateSetListener datePickerListener;
    private ProgressDailogHelper dailogHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_navigation, container, false);
        ButterKnife.bind(this, view);
        init();
        dailogHelper=new ProgressDailogHelper(getActivity(),"");
        getUserDetails();


        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                Date date= null;
                try {
                    date = simpleDateFormat.parse(dayOfMonth+"/"+(month+1)+"/"+year);
                    StringBuilder builder=new StringBuilder();
                    builder.append(simpleDateFormat.format(date));
                    dob_ed.setText(builder);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };

        SpinnerClick();



        return view;
    }

    private void SpinnerClick() {
        martialStatusSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maritalstatus_txt.setText(martailList.get(position));
                maritalstatus_txt.setError(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        genderSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gendertxt.setText(genderlist.get(position));
                gendertxt.setError(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void init() {
        save_btn.setOnClickListener(this::OnClick);
        camera_im.setOnClickListener(this::OnClick);
        notification_im.setOnClickListener(this::OnClick);
        gendertxt.setOnClickListener(this::OnClick);
        maritalstatus_txt.setOnClickListener(this::OnClick);
        profile_im.setOnClickListener(this::OnClick);
        dob_ed.setOnClickListener(this::OnClick);
        pointtxt.setOnClickListener(this::OnClick);
        countrycode_txt.setOnClickListener(this::OnClick);
        pointtxt.setText(""+SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables.totalPoints));
    }


    private void getUserDetails() {
        dailogHelper.showDailog();
        RetroInterface api_service = RetrofitInit.getConnect().createConnection();
        User user = new User();
        user.setUserId(SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables._id));
        user.setLangCode("en");
        Call<User> call = api_service.getUserDetails(user, SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables.jwtToken));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User server_response = response.body();
                    if (server_response.getStatus().equalsIgnoreCase("SUCCESS")) {
                        dailogHelper.dismissDailog();
                        Glide.with(getActivity()).load(server_response.getData().getProfilePic()).into(profile_im);
                        username_ed.setText(server_response.getData().getUsername());
                        full_name_ed.setText(server_response.getData().getName());
                        email_ed.setText(server_response.getData().getEmail());
                        phone_ed.setText(server_response.getData().getMobileNumber());
                        pob_ed.setText(server_response.getData().getBirthPlace());
                        if(server_response.getData().getGender()!=null)
                        {
                            gendertxt.setText(server_response.getData().getGender());
                        }
                        if(server_response.getData().getMaritalStatus()!=null)
                        {
                            maritalstatus_txt.setText(server_response.getData().getMaritalStatus());
                        }

                        dob_ed.setText(server_response.getData().getDob());
                        countrycode_txt.setText(server_response.getData().getCountryCode());


                    } else if (server_response.getStatus().equalsIgnoreCase("FAILURE")) {
                        dailogHelper.dismissDailog();
                        if(server_response.getResponseMessage().equalsIgnoreCase(GlobalVariables.invalidoken))
                        {
                            Toast.makeText(getActivity(),getString(R.string.other_device_logged_in),Toast.LENGTH_LONG).show();
                            getActivity().finish();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            SharedPreferenceWriter.getInstance(getActivity()).clearPreferenceValues();
                            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        // Log.w(TAG, "getInstanceId failed", task.getException());
                                        return;
                                    }

                                    String auth_token = task.getResult().getToken();
                                    Log.w("firebaese","token: "+auth_token);
                                    SharedPreferenceWriter.getInstance(getActivity()).writeStringValue(GlobalVariables.firebase_token,auth_token);
                                }
                            });
                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


    }


    @OnClick()
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.notification_im:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.replace, new NotificationFragment()).commit();
                break;

            case R.id.camera_im:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(getActivity(), CAMERA) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    ) {

                        requestPermissions(new String[]
                                {
                                        CAMERA,
                                        WRITE_EXTERNAL_STORAGE,
                                        READ_EXTERNAL_STORAGE
                                }, PERMISSION_REQUEST_CODE);

                    } else {
                        profileBottomLayout();
                    }
                }



                break;
            case R.id.save_btn:
                if (checkValidation()) {
                    UpdateProfileAPI();

                }
                break;

            case R.id.gendertxt:
                GenderSpinner();
                break;
            case R.id.maritalstatus_txt:
                MaritalStatusSpinner();
                break;
            case R.id.profile_im:

                break;

            case R.id.dob_ed:
                Date date= Calendar.getInstance().getTime();
                DatePickerDialog dialog=new DatePickerDialog(getActivity(), android.app.AlertDialog.THEME_HOLO_LIGHT,datePickerListener,Integer.parseInt(new SimpleDateFormat("yyyy").format(date)),
                        Integer.parseInt(new SimpleDateFormat("MM").format(date))-1,Integer.parseInt(new SimpleDateFormat("dd").format(date)));
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
                break;

            case R.id.pointtxt:
                Intent intent=new Intent(getActivity(), MyWalletActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void UpdateProfileAPI() {
        dailogHelper.showDailog();
        RetroInterface api_service = RetrofitInit.getConnect().createConnection();
        UpdateUserProfile userSetting = new UpdateUserProfile();
        userSetting.setUserId(SharedPreferenceWriter.getInstance(getActivity()).getString(GlobalVariables._id));
        userSetting.setEmail(email_ed.getText().toString().trim());
        userSetting.setName(full_name_ed.getText().toString().trim());
        userSetting.setUsername(username_ed.getText().toString().trim());
        userSetting.setGender(gendertxt.getText().toString());
        userSetting.setMaritalStatus(maritalstatus_txt.getText().toString());
        userSetting.setBirthPlace(pob_ed.getText().toString().trim());
        userSetting.setDob(dob_ed.getText().toString().trim());
        AddServiceBody body = new AddServiceBody(userSetting);

        RequestBody profile_body;
        MultipartBody.Part prfpic = null;

        if (imagePath != null) {
            File file = new File(imagePath);
            profile_body = RequestBody.create(MediaType.parse("image/*"), file);
            prfpic = MultipartBody.Part.createFormData("profilePic", file.getName(), profile_body);
        }



            Call<UpdateUserProfile> call = api_service.updateUserProfile(prfpic, body.getBody());
            call.enqueue(new Callback<UpdateUserProfile>() {
                @Override
                public void onResponse(Call<UpdateUserProfile> call, Response<UpdateUserProfile> response) {
                    UpdateUserProfile server_response = response.body();
                    if (server_response.getStatus().equalsIgnoreCase("SUCCESS")) {
                        dailogHelper.dismissDailog();
                       // getFragmentManager().beginTransaction().replace(R.id.replace,new Cate()).commit();
                        startActivity(new Intent(getActivity(), CategorySelectionActivity.class));
                        Toast.makeText(getActivity(), server_response.getResponseMessage(), Toast.LENGTH_LONG).show();

                    } else if (server_response.getStatus().equalsIgnoreCase("FAILURE")) {
                        dailogHelper.dismissDailog();
                        if(server_response.getResponseMessage().equalsIgnoreCase(GlobalVariables.invalidoken))
                        {
                            Toast.makeText(getActivity(),getString(R.string.other_device_logged_in),Toast.LENGTH_LONG).show();
                            getActivity().finish();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            SharedPreferenceWriter.getInstance(getActivity()).clearPreferenceValues();
                            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        // Log.w(TAG, "getInstanceId failed", task.getException());
                                        return;
                                    }

                                    String auth_token = task.getResult().getToken();
                                    Log.w("firebaese","token: "+auth_token);
                                    SharedPreferenceWriter.getInstance(getActivity()).writeStringValue(GlobalVariables.firebase_token,auth_token);
                                }
                            });
                        }
                        //Toast.makeText(getActivity(), server_response.getResponseMessage(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<UpdateUserProfile> call, Throwable t) {


                }
            });
        
    }


    private void MaritalStatusSpinner() {
        martailList = new ArrayList<>();
        martailList.add("Marital status");
        martailList.add("Single");
        martailList.add("Married");

        ArrayAdapter genderArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, martailList){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
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
        martialStatusSpn.setAdapter(genderArrayAdapter);
        martialStatusSpn.performClick();

    }

    private void GenderSpinner() {
        genderlist = new ArrayList<>();
        genderlist.add("Gender");
        genderlist.add("Male");
        genderlist.add("Female");

        ArrayAdapter genderArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, genderlist) {
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
        genderSpn.setAdapter(genderArrayAdapter);
        genderSpn.performClick();
    }



    private boolean checkValidation() {
        boolean ret=true;

        if(!Validation.hasText(full_name_ed,getString(R.string.please_enter_full_name))
        || !Validation.hasText(username_ed,getString(R.string.enter_username))
        || !Validation.email(email_ed,getString(R.string.enter_email))
        || !Validation.isPhoneNumber(phone_ed,true)
        || !Validation.hasText(pob_ed,getString(R.string.please_enter_pob))
        || !Validation.hasText(dob_ed,getString(R.string.please_enter_dob))
        || gendertxt.getText().toString().equalsIgnoreCase("Gender")
        || maritalstatus_txt.getText().toString().equalsIgnoreCase("Marital status")

        )
        {
            if(!Validation.hasText(full_name_ed,getString(R.string.please_enter_full_name)))
            {
                ret=false;
                full_name_ed.requestFocus();
            }
            else if(!Validation.hasText(username_ed,getString(R.string.enter_username)))
            {
                ret=false;
                username_ed.requestFocus();
            }
            else if(!Validation.email(email_ed,getString(R.string.enter_email)))
            {
                ret=false;
                email_ed.requestFocus();
            }
            else if(!Validation.isPhoneNumber(phone_ed,true))
            {
                ret=false;
                phone_ed.requestFocus();

            }
            else if(!Validation.hasText(pob_ed,getString(R.string.please_enter_pob)))
            {
                ret=false;
                pob_ed.requestFocus();
            }
            else if(!Validation.hasText(dob_ed,getString(R.string.please_enter_dob)))
            {
                ret=false;
                dob_ed.requestFocus();
            }
            else if(gendertxt.getText().toString().equalsIgnoreCase("Gender"))
            {
                ret=false;
                gendertxt.setText(getString(R.string.please_select_gender));
                gendertxt.setError("");
                gendertxt.requestFocus();
                gendertxt.setFocusable(true);
                maritalstatus_txt.setError(null);
            }
            else if(maritalstatus_txt.getText().toString().equalsIgnoreCase("Marital status"))
            {
                ret=false;
                maritalstatus_txt.setText(getString(R.string.please_select_marital_status));
                maritalstatus_txt.setError("");
                maritalstatus_txt.requestFocus();
                maritalstatus_txt.setFocusable(true);
                gendertxt.setError(null);
            }
        }

        return ret;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && writeAccepted && readAccepted) {
                       // Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
                        profileBottomLayout();

                    }else {

                        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    public void profileBottomLayout() {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.update_pic_layout, null);
        ImageView camera = (ImageView) popupView.findViewById(R.id.camera);
        ImageView gallery = (ImageView) popupView.findViewById(R.id.gallery);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Upload photo");
        alertDialog.setView(popupView);
        final AlertDialog dialog = alertDialog.show();
        alertDialog.setCancelable(true);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    dialog.dismiss();
                    Intent intent = new Intent(getActivity(), TakeImage.class);
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
                Intent intent = new Intent(getActivity(), TakeImage.class);
                intent.putExtra("from", "gallery");
                startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == START_VERIFICATION) {
            if (resultCode == RESULT_OK) {
                getActivity().setResult(RESULT_OK);
                getActivity().finish();
            }
        } else if (resultCode == RESULT_OK) {
            if (data.getStringExtra("filePath") != null) {
                imagePath = data.getStringExtra("filePath");
                fileFlyer = new File(data.getStringExtra("filePath"));

                if (fileFlyer.exists() && fileFlyer != null) {
                    profile_im.setImageURI(Uri.fromFile(fileFlyer));
                }
            }
        } else if (requestCode == 1 && resultCode == RESULT_CANCELED) {
//            getActivity().finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    }


