package com.codepath.week1.nytimessearch.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.week1.nytimessearch.R;


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
            @Override
            public void onClick(View v) {
                sendBackResult();
            }
        });
    }

    public void sendBackResult() {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        OnFragmentInteractionListener listener = (OnFragmentInteractionListener) getActivity();
        String beginDate = tvBeginDate.getText().toString();
        String order = sortOrder.getSelectedItem().toString();
        Boolean arts = cbArts.isChecked();
        Boolean fashion = cbFashion.isChecked();
        Boolean sports = cbSports.isChecked();

        listener.setBeginDate(beginDate);
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
