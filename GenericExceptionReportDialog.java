package com.bigw.laybymobile.android.common.dilogs;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bigw.laybymobile.android.R;
import com.bigw.laybymobile.android.common.BaseActivity;

/**
 * Created by anideshp on 7/27/2015.
 */
public class GenericExceptionReportDialog extends DialogFragment{

    private Context mContext;

    private View rootView;

    private TextView textViewErrorMessage;
    private Button buttonOk;

    private String errorTitle,errorMessage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.dialog_generic_exception_report, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext=getActivity().getApplicationContext();
        Bundle bundle=getArguments();
        errorTitle=bundle.getString(mContext.getResources().getString(R.string.KEY_ERROR_TITLE));
        errorMessage=bundle.getString(mContext.getResources().getString(R.string.KEY_ERROR_MESSAGE));
        initUI();
    }

    /**
     * Common implementation of initUI for GenericExceptionReportDialog
     */
    private void initUI(){
        getDialog().setTitle(errorTitle);
        textViewErrorMessage=(TextView)rootView.findViewById(R.id.textViewErrMessage);
        buttonOk=(Button)rootView.findViewById(R.id.buttonOK);
        textViewErrorMessage.setText(errorMessage);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
