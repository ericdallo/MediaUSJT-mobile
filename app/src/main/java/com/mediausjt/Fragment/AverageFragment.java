package com.mediausjt.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mediausjt.R;
import com.mediausjt.util.FragmentUtil;

public class AverageFragment extends Fragment {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.average_frag, container, false);

        FragmentManager manager = getChildFragmentManager();
        FragmentUtil.setFragmentManager(manager);

        FragmentUtil.showLogic();

        return rootView;
    }

}
