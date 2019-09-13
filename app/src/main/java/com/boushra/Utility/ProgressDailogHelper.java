package com.boushra.Utility;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDailogHelper {
    private String message;
    private Context context;
    private ProgressDialog dialog;


    public ProgressDailogHelper(Context context,String message)
    {
        this.context=context;
        this.message=message;
        dialog=new ProgressDialog(context);
    }



    public void showDailog()
    {

        dialog.setTitle("Please wait...!");
        dialog.setMessage(message);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismissDailog()
    {
        if(dialog.isShowing())
        {
            dialog.dismiss();
        }

    }




}
