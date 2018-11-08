package com.example.dennischiu.vtc_androidproject.dialog_apps;

import com.example.dennischiu.vtc_androidproject.R;
import com.example.dennischiu.vtc_androidproject.model.Information;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class dialog_apps_information extends DialogFragment {

    Button mButton;
    TextView txtContent;
    private Information mInformation;


    public static final String TAG = dialog_apps_information.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_apps_info, container);
        mButton = (Button) view.findViewById(R.id.btn_reset_ok);
        txtContent = (TextView) view.findViewById(R.id.txtcontent);


        mButton.setOnClickListener(v ->{

            dismiss();
            for(int i=0; i<3; i++) {
                txtContent.setText(mInformation.content[i]);
            }
        });

        return view;
    }
//    @Override
////    public Dialog onCreateDialog(Bundle saveInstanceState){
////        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
////
////        LayoutInflater inflater = getActivity().getLayoutInflater();
////        View view = inflater.inflate(R.layout.dialog_reset_message,null);
////        mButton = (Button) view.findViewById(R.id.btn_reset_ok);
////
////    }

    public static dialog_apps_information newInstance() {

        return new dialog_apps_information();
    }


}

