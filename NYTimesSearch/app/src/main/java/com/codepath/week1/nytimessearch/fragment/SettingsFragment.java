package com.codepath.week1.nytimessearch.fragment;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.week1.nytimessearch.R;

import java.text.ParseException;
import java.util.Date;


public class SettingsFragment extends DialogFragment {
    private OnFragmentInteractionListener mListener;
    private TextView tvBeginDate;
    private Spinner sortOrder;
    private CheckBox cbArts, cbFashion, cbSports;
    private Button saveBtn;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String title) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvBeginDate = (TextView) view.findViewById(R.id.etBeginDate);
        sortOrder = (Spinner) view.findViewById(R.id.sortOrder);

        cbArts = (CheckBox) view.findViewById(R.id.cbArts);
        cbFashion = (CheckBox) view.findViewById(R.id.cbFashion);
        cbSports = (CheckBox) view.findViewById(R.id.cbSports);
        saveBtn = (Button) view.findViewById(R.id.btSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                try {
                    sendBackResult();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sendBackResult() throws ParseException {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        OnFragmentInteractionListener listener = (OnFragmentInteractionListener) getActivity();
        String beginDate = tvBeginDate.getText().toString();
        String order = sortOrder.getSelectedItem().toString();
        Boolean arts = cbArts.isChecked();
        Boolean fashion = cbFashion.isChecked();
        Boolean sports = cbSports.isChecked();
        SimpleDateFormat spf=new SimpleDateFormat("mm/dd/yyyy");
        if(TextUtils.isEmpty(beginDate)) {
            beginDate="01/01/2010";
        }
        Date newDate=spf.parse(beginDate);
        spf= new SimpleDateFormat("yyyymmdd");
        String new_date = spf.format(newDate);

        listener.setBeginDate(new_date);
        listener.setSortOrder(order);
        listener.setCbArts(arts);
        listener.setCbFashion(fashion);
        listener.setCbSports(sports);
        listener.onFinishSettingsDialog();
        dismiss();
    }


    public interface OnFragmentInteractionListener {
        void setSortOrder(String sortOrder);
        void setBeginDate(String beginDate);
        void setCbArts(Boolean cbArts);
        void setCbFashion(Boolean cbFashion);
        void setCbSports(Boolean cbSports);
        void onFinishSettingsDialog();
    }
}
