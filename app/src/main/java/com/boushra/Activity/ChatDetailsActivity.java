package com.boushra.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.boushra.Adapter.ChatAdapter;
import com.boushra.Adapter.ChatListAdapter;
import com.boushra.Fragment.NavigationProfileFragment;
import com.boushra.Model.ChatHistory;
import com.boushra.Model.Data;
import com.boushra.R;
import com.boushra.Retrofit.RetroInterface;
import com.boushra.Retrofit.RetrofitInit;
import com.boushra.Utility.GlobalVariables;
import com.boushra.Utility.InternetCheck;
import com.boushra.Utility.NotificationUtils;
import com.boushra.Utility.ProgressDailogHelper;
import com.boushra.Utility.SharedPreferenceWriter;
import com.boushra.Utility.socketFileUploader.FileUploadManager;
import com.boushra.Utility.socketFileUploader.UploadFileMoreDataReqListener;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ChatDetailsActivity extends AppCompatActivity {
    @BindView(R.id.backLL) LinearLayout backLL;
    @BindView(R.id.profile_image_im) CircleImageView profile_image_im;
    @BindView(R.id.chatRV) RecyclerView chatRV;
    @BindView(R.id.mesage_ed) EditText mesage_ed;
    @BindView(R.id.send_iv) ImageView send_iv;
    @BindView(R.id.textname) TextView textname;
    @BindView(R.id.mic_iv) LottieAnimationView mic_iv;
    private Socket mSocket;
    Object chat_details;
    Data chatData;
    ProgressDailogHelper dailogHelper;
    List<Data> dataList;
    CountDownTimer timer;
    Boolean recording=false,playing=false;
    private MediaRecorder mRecorder;
    String fileName;
    File audio;
    int lastProgress,lastposition;
    private MediaPlayer mPlayer;
    int clickcount=0;
    private HashMap<String, String> media_path = new HashMap<>();
    final int PERMISSION_REQUEST_CODE2 = 400;
    String fcm;
    boolean login=false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();
        connectSockettest();
        getChatHistoryApi();

        Log.e("user_id",SharedPreferenceWriter.getInstance(ChatDetailsActivity.this).getString(GlobalVariables._id));

    }

    private void connectSockettest() {
        try
        {
            mSocket= IO.socket("http://18.218.65.12:4002");

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("room join", onLogin);
        mSocket.on("message", onNewMessage);
        mSocket.on("room leave",onRoomLeave);
        mSocket.connect();

        try {
            JSONObject object = new JSONObject();
            object.put("roomId", chatData.getRoomId());
            Log.e("roomId",chatData.getRoomId());
            mSocket.emit("room join", object);
            Log.e("sendData", String.valueOf(object));

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

//    @Override
//    protected void onPause(){
//        super.onPause();
//        Log.e("stop","yes");
//        try
//        {
//            JSONObject object=new JSONObject();
//            object.put(GlobalVariables.roomId,chatData.getRoomId());
//            mSocket.emit("room leave",object);
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//    }
//

    private Emitter.Listener onRoomLeave=new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("room leave","Yes");

                }
            });
        }
    };

    private Emitter.Listener onConnect = args -> runOnUiThread(() -> {


        //Toast.makeText(ChatDetailsActivity.this, "Connected", Toast.LENGTH_SHORT).show();

    });

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //DialogFactory.showToast(getApplicationContext(), getString(R.string.disconnected));
                   //Toast.makeText(ChatDetailsActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // DialogFactory.showLog("ERROR CONNECT", "ERROR CONNECT");
                   Toast.makeText(ChatDetailsActivity.this, "NETWORK ERROR", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

//    @Override
//    public void onPause() {
//        super.onPause();
//
//        mSocket.disconnect();
//        mSocket.off(Socket.EVENT_CONNECT, onConnect);
//        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
//        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
//        mSocket.off("message", onNewMessage);
//        mSocket.off("room join", onLogin);
//        mSocket.off("room leave",onRoomLeave);
//    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    login=true;


                   // Toast.makeText(ChatDetailsActivity.this, getString(R.string.login_successfull), Toast.LENGTH_SHORT).show();


                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String server_data = args[0].toString();
                    Log.e("data",server_data);
                  //  Toast.makeText(ChatDetailsActivity.this, server_data, Toast.LENGTH_SHORT).show();
                    JSONObject data = null;
                    if(server_data.equalsIgnoreCase("You have been sent request already to forecaster. Please wait for forecaster reply"))
                    {
                        setupPopDailog(server_data);
                    }
                    else if(server_data.equalsIgnoreCase("Maximum time finish so can not chat now"))
                    {
                        setupPopDailog(server_data);
                    }
                    else if(server_data.equalsIgnoreCase("This chat has been off now.You can not chat due to time limit of 30 mints.")) {
                        setupPopDailog(server_data);
                    }


                    else {
                        Log.e("data", server_data);

                        try {

                            data=new JSONObject(args[0].toString());
                            settingAdapter(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }


            });
        }

        private void settingAdapter(JSONObject jsondata) {
            String rooomId;
            String message;
            String receiverId;
            String senderId;
            String messageType;
            String createdAt;
            String data="";
            String media="";

            Log.e("new mesaage", String.valueOf(data));

            try {

                mesage_ed.setText("");
                rooomId = jsondata.getString("roomId");
                receiverId = jsondata.getString("receiverId");
                senderId = jsondata.getString("senderId");
                message = jsondata.getString("message");
                messageType = jsondata.getString("messageType");
                if(messageType.equalsIgnoreCase("Media")) {
                    data = jsondata.getString("Data");
                    media=jsondata.getString("media");
                }
                createdAt = jsondata.getString("createdAt");



               // dataList.add(new Data(rooomId, senderId, receiverId, message, messageType, createdAt));

                if(messageType.equalsIgnoreCase("Media"))
                {
                    dataList.add(new Data(rooomId, senderId, receiverId, message, messageType, createdAt,media));
                }
                else
                {
                    dataList.add(new Data(rooomId, senderId, receiverId, message, messageType, createdAt));
                }

                ChatAdapter adapter = new ChatAdapter(ChatDetailsActivity.this, dataList);
                chatRV.setLayoutManager(new LinearLayoutManager(ChatDetailsActivity.this));
                chatRV.setAdapter(adapter);
                chatRV.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                chatRV.scrollToPosition(adapter.getItemCount()-1);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void setupPopDailog(String server_data) {
        final Dialog dialog=new Dialog(ChatDetailsActivity.this,android.R.style.Theme_Black);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_bank_transfer_payment_popup);
        TextView response_txt=dialog.findViewById(R.id.response_txt);
        response_txt.setText(server_data);
        LinearLayout closell=dialog.findViewById(R.id.closell);
        dialog.setCancelable(true);
        closell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();



    }


    private void getChatHistoryApi() {
        if(new InternetCheck(ChatDetailsActivity.this).isConnect())
        {

            dailogHelper.showDailog();
            RetroInterface api_service= RetrofitInit.getConnect().createConnection();
            ChatHistory history=new ChatHistory();
            history.setRoomId(chatData.getRoomId());
            Call<ChatHistory> call=api_service.getchatHistory(history, SharedPreferenceWriter.getInstance(ChatDetailsActivity.this).getString(GlobalVariables.jwtToken));
            call.enqueue(new Callback<ChatHistory>() {
                @Override
                public void onResponse(Call<ChatHistory> call, Response<ChatHistory> response) {
                    if(response.isSuccessful())
                    {
                        dailogHelper.dismissDailog();
                        if(response.body().getStatus().equalsIgnoreCase(GlobalVariables.SUCCESS))
                        {
                            dataList=response.body().getData();
                            ChatAdapter adapter = new ChatAdapter(ChatDetailsActivity.this, dataList);
                            chatRV.setLayoutManager(new LinearLayoutManager(ChatDetailsActivity.this));
                            chatRV.setAdapter(adapter);
                            chatRV.setVisibility(View.VISIBLE);
                            adapter.notifyDataSetChanged();
                            chatRV.scrollToPosition(adapter.getItemCount()-1);

                        }else if(response.body().getStatus().equalsIgnoreCase(GlobalVariables.FAILURE))
                        {
                            Toast.makeText(ChatDetailsActivity.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();

                        }


                    }
                }

                @Override
                public void onFailure(Call<ChatHistory> call, Throwable t) {

                }
            });

        }else
        {
            Toast toast= Toast.makeText(this, getString(R.string.check_internet), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

    }


    private void init() {
        backLL.setOnClickListener(this::OnClick);
        profile_image_im.setOnClickListener(this::OnClick);
        send_iv.setOnClickListener(this::OnClick);
        chatData=getIntent().getParcelableExtra("chat_detail");
        dailogHelper=new ProgressDailogHelper(this,"");
        dataList=new ArrayList<>();
        fcm=getIntent().getStringExtra("FCM");
        if(fcm!=null) {
            if (fcm.equalsIgnoreCase("YES")) {
                NotificationUtils.clearNotifications(this);
                chatData=new Data(getIntent().getStringExtra(GlobalVariables.roomId),
                        getIntent().getStringExtra(GlobalVariables.receiverId),
                        getIntent().getStringExtra(GlobalVariables.senderId),
                        getIntent().getStringExtra(GlobalVariables.name),
                        getIntent().getStringExtra(GlobalVariables.profile));

                textname.setText(chatData.getName());
                Glide.with(this).load(chatData.getProfilePic()).into(profile_image_im);
            }
        }
        else {
            Glide.with(this).load(chatData.getForecasterData().getProfilePic()).into(profile_image_im);
            textname.setText(chatData.getForecasterData().getName());
        }
        mic_iv.setOnClickListener(this::OnClick);

    }

    @OnClick()
    void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.profile_image_im:
                Intent intent=new Intent(ChatDetailsActivity.this,ProfileSetupActivity.class);
                 startActivity(intent);
                finish();
                break;

            case R.id.backLL:
                super.onBackPressed();
                break;

            case R.id.send_iv:
                if(login)
                {
                    if(!mesage_ed.getText().toString().trim().isEmpty())
                    {
                        sendMessage(mesage_ed.getText().toString());

                    }
                }
                else
                {
                    Toast.makeText(this, getString(R.string.socket_error), Toast.LENGTH_SHORT).show();
                }



                break;

            case R.id.mic_iv:
                if(clickcount%2==0) {
                    if(checkAudioPermission()) {
                        clickcount = clickcount + 1;
                        recordingAudio();
                    }
                }else
                {
                    clickcount=clickcount+1;
                    stoppingAudio();

                }

                break;

        }
    }

    private boolean checkAudioPermission() {
        boolean ret = true;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                ret = false;


                requestPermissions(new String[]
                        {
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, PERMISSION_REQUEST_CODE2);

            } else {

                ret = true;
            }
        }
        return ret;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("room join", onLogin);
        mSocket.off("message", onNewMessage);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0)
        {
            switch (requestCode)
            {
                case PERMISSION_REQUEST_CODE2:
                    if(grantResults.length>0)
                    {
                        boolean audioaccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                        boolean readAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                        boolean writeAccepted=grantResults[2]==PackageManager.PERMISSION_GRANTED;

                        if(audioaccepted && readAccepted && writeAccepted)
                        {
                            //Toast.makeText(ProfileSetupActivity.this,"Recording Started",Toast.LENGTH_LONG).show();
                            clickcount=clickcount+1;
                            recordingAudio();
                        }
                        else
                        {
                            // Toast.makeText(ProfileSetupActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();

                        }
                    }

                    break;
            }
        }

    }





    private void recordingAudio() {
        if(timer!=null)
        {
            timer.cancel();
            timer.onFinish();
        }


        recording=true;
        playing=false;
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        File root = android.os.Environment.getExternalStorageDirectory();
        File file = new File(root.getAbsolutePath() + "/Boushra/Audios/Dreamer/Chat/Send/");

        if (!file.exists()) {
            file.mkdirs();
        }
        else
        {
            file.delete();
            file.mkdirs();
        }

        fileName =  root.getAbsolutePath() + "/Boushra/Audios/Dreamer/Chat/Send/" + String.valueOf(System.currentTimeMillis() + ".mp3");
        Log.d("filename",fileName);
        mRecorder.setOutputFile(fileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        audio=new File(fileName);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stopPlaying();
        lastProgress = 0;
        lastposition=0;
        mic_iv.setAnimation("recording.json");
        mic_iv.setProgress(0);
        mic_iv.playAnimation();
        mic_iv.setScaleX(1.9f);
        mic_iv.setScaleY(1.9f);







        timer=new CountDownTimer(30500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {


            }

            @Override
            public void onFinish() {
                if(recording)
                {
                    recording=false;
                    mic_iv.pauseAnimation();
//                    mic_iv.setImageResource(R.drawable.microphone_black_shape);
                    stopPlaying();
                    try {
                        mRecorder.stop();
                        mRecorder.release();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    Log.e("timer_stop","yes");
                    Toast.makeText(ChatDetailsActivity.this,"You can record upto 30 secounds",Toast.LENGTH_LONG).show();
                }
            }
        }.start();



    }


    private void stoppingAudio() {
        try {
            mRecorder.stop();
            mRecorder.release();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try
        {
            recording=false;
            playing=false;
            mPlayer.release();

            if(timer!=null) {
                timer.cancel();
                timer.onFinish();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        mic_iv.cancelAnimation();
        mic_iv.setImageResource(R.drawable.mike);
        mic_iv.setScaleX(1);
        mic_iv.setScaleY(1);

        sendAndGetBinaryData(audio.getAbsolutePath());

    }

    private void sendAndGetBinaryData(String path) {
        String uni_code = String.valueOf(System.currentTimeMillis());
        media_path.put(uni_code, path);
        if (media_path.size() == 1) {
            uploadFileOnServer(media_path);
        }

    }

    private void uploadFileOnServer(HashMap<String, String> map) {
        if (map.size() > 0)
        {
            for (String entry : map.keySet())
            {
                String key = entry;
                String value = map.get(key);
                // new FileUploadTask(value, key).execute();//Value ==== Media Path, key========Unicode
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    new FileUploadTask(value, key).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");

                break;
            }
        }

    }

    private class FileUploadTask extends AsyncTask<String, Integer, String> {
        private String file_path = "";
        private String room_id = "";
        private String receiver_id = "";
        private String attachment_type = "Media";
        private String uni_code = "";

        private UploadFileMoreDataReqListener callback;
        private FileUploadManager mFileUploadManager;

        public FileUploadTask(String file_path, String uni_code)
        {
            this.file_path = file_path;
            room_id = chatData.getRoomId();
            receiver_id = chatData.getReceiverId();
            this.uni_code = uni_code;
            attachment_type = "Media";
        }

        @Override
        protected void onPreExecute() {
            mFileUploadManager = new FileUploadManager();

        }

        @Override
        protected String doInBackground(String... params)
        {
            boolean isSuccess = mSocket.connected();
            if (isSuccess) {
                mFileUploadManager.prepare(file_path, ChatDetailsActivity.this);

                // This function gets callback when server requests more data
                setUploadFileMoreDataReqListener(mUploadFileMoreDataReqListener);
                // This function will get a call back when upload completes
                setUploadFileCompleteListener();
                // Tell server we are ready to start uploading ..
                if (mSocket.connected())
                {
                    JSONArray jsonArr = new JSONArray();
                    JSONObject res = new JSONObject();
                    try {
                        res.put("Name", mFileUploadManager.getFileName());
                        res.put("Data", mFileUploadManager.getData());
                        res.put("Size", mFileUploadManager.getFileSize());
                        res.put("roomId", room_id);
                        res.put("senderId",chatData.getSenderId());
                        res.put("receiverId",chatData.getReceiverId());
                        res.put("message","Attach voice");
                        res.put("messageType","Media");
                        jsonArr.put(res);
                        mSocket.emit("uploadFileStart", jsonArr);

                    } catch (JSONException e) {
                        Log.e("error",e.getMessage());

                        //TODO: Log errors some where..
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            if (s == null) {
                return;
            }
            if (s.equalsIgnoreCase("OK"))
            {
                media_path.remove(uni_code);
                mFileUploadManager.close();
                Log.e("aaya","yes");

                mSocket.off("uploadFileMoreDataReq", uploadFileMoreDataReq);
                //  mSocket.off("uploadFileCompleteRes", onCompletedddd);
//                uploadFileOnServer(media_path);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // super.onProgressUpdate(values);
            if (values[0] > 107) {
                Log.e("progressup", String.valueOf(values[0]));
                if (media_path.containsKey(uni_code)) {
                    onPostExecute("OK");
                }
            }
        }


        private UploadFileMoreDataReqListener mUploadFileMoreDataReqListener = new UploadFileMoreDataReqListener()
        {
            @Override
            public void uploadChunck(int offset, int percent)
            {
                // Read the next chunk
                Log.e("progresss", String.valueOf(percent));

                mFileUploadManager.read(offset);
                if (mSocket.connected()) {
                    JSONArray jsonArr = new JSONArray();
                    JSONObject res = new JSONObject();



                    try {
                        res.put("Name", mFileUploadManager.getFileName());
                        res.put("Data", mFileUploadManager.getData());
                        res.put("Size", mFileUploadManager.getBytesRead());
                        res.put("roomId", room_id);
                        res.put("senderId", chatData.getSenderId());
                        res.put("receiverId", chatData.getReceiverId());
                        res.put("message","Attach voice");

                        res.put("messageType", "Media");

                        jsonArr.put(res);
                        // This will trigger server 'uploadFileChuncks' function
                        mSocket.emit("uploadFileChuncks", jsonArr);
                        Log.e("upload started","yes");

                    } catch (JSONException e) {
                        //TODO: Log errors some where..
                    }
                }
            }

            @Override
            public void err(JSONException e) {
                // TODO Auto-generated method stub
            }
        };

        Emitter.Listener uploadFileMoreDataReq = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.e("callback", String.valueOf(args[0]));
                for (int jj = 0; jj < args.length; jj++) {
                }
                try {
                    JSONObject json_data = (JSONObject) args[0];
                    int place = json_data.getInt("Place");
                    int percent = json_data.getInt("Percent");
                    publishProgress(json_data.getInt("Percent"));

                    callback.uploadChunck(place, percent);

                } catch (JSONException e) {
                    callback.err(e);
                }

            }
        };


        Emitter.Listener onCompletedddd = new Emitter.Listener()
        {
            @Override
            public void call(Object... args) {
                Log.e("Complete_callback", String.valueOf(args[0]));
                JSONObject json_data = (JSONObject) args[0];
                if (json_data.has("IsSuccess"))
                {
                    publishProgress(110);
                    return;
                }
            }
        };

        private void setUploadFileMoreDataReqListener(final UploadFileMoreDataReqListener callbackk)
        {
            callback = callbackk;
            mSocket.on("uploadFileMoreDataReq", uploadFileMoreDataReq);
        }

        private void setUploadFileCompleteListener() {
            mSocket.on("uploadFileCompleteRes", onCompletedddd);
        }
    }





    private void stopPlaying() {
        try{
            mPlayer.release();
        }catch (Exception e){
            e.printStackTrace();
        }
        mPlayer = null;
    }



    private void sendMessage(String message) {
        try {
            JSONObject object=new JSONObject();
            object.put(GlobalVariables.roomId,chatData.getRoomId());
            object.put(GlobalVariables.senderId,chatData.getSenderId());
            object.put(GlobalVariables.receiverId,chatData.getReceiverId());
            object.put(GlobalVariables.message,message);
            object.put(GlobalVariables.messageType,"Text");
            Log.e("socketdata:", String.valueOf(object));
            mSocket.emit("message",object);


        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
