package com.bigw.laybymobile.android.common.dilogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by anideshp on 10/8/2015.
 */
public class DialogConformation extends DialogFragment{

    public static String TAG = DialogConformation.class.getSimpleName();

    public static final String MESSAGE="message";

    private Context mContext;

    private String title;
    private String message;

    private ConfimationListener confimationListener;

    /**
     * Constructor for initializing the DialogConformation
     */
    public DialogConformation(){
        super();
    }

    /**
     *  ConfimationListener Interface
     */
    public interface  ConfimationListener{
        /**
         * Common implementation of onOKPressed.
         */
        public void onOKPressed();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    /**
     *
     * @param confimationListener
     * The confimationListener
     */
    public void setConfirmationListener(ConfimationListener confimationListener){
        this.confimationListener=confimationListener;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * Creates Dialog
     * @param savedInstanceState
     * @return alertDialogBuilder
     */
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        message=getArguments().getString(MESSAGE);
        alertDialogBuilder.setMessage(message+" ?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(confimationListener!=null){
                    confimationListener.onOKPressed();
                    dialog.dismiss();
                }
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return alertDialogBuilder.create();
    }
}
