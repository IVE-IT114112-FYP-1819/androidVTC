package com.example.dennischiu.vtc_androidproject.dialog_apps;

import com.example.dennischiu.vtc_androidproject.R;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class dialog_apps_information extends DialogFragment {

    Button mButton;
    ListView lv;
    ArrayAdapter<String> adapter;
    String[] content = {
            "1.Input your information (FirstName, LastName and your emergency contact person)",
            "\n2.Please remember to click Save button to save you data ",
            "\n3.Click the Clock button to set up next alarm time, and click SET button",
            "\n4.When time up, please use the 'Touch ID' to pass the checking. ",
            "\n5.After 'Touch ID' checking, solve the simple mathematics question.",
            "\n6.After solve mathematics question, set the next alarm time please."
    };

    public static final String TAG = dialog_apps_information.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_apps_info, container);
        mButton = (Button) view.findViewById(R.id.btn_reset_ok);
        lv=(ListView) view.findViewById(R.id.lslistview);
        adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,content);
        lv.setAdapter(adapter);

        mButton.setOnClickListener(v ->{

            dismiss();

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

