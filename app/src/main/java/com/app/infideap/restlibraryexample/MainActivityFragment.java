package com.app.infideap.restlibraryexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner_method);
        ArrayAdapter <String>arrayAdapter =
                new ArrayAdapter<>(getActivity(),
                        android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.method)
                );


        spinner.setAdapter(arrayAdapter);


        return rootView;
    }
}
