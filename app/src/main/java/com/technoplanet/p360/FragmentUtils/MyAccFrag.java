package com.technoplanet.p360.FragmentUtils;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technoplanet.p360.R;

/**
 * Created by BHARGAV on 02/11/2018.
 */

public class MyAccFrag extends Fragment {

    private FloatingActionButton delAddr;
    private FloatingActionButton billAddr;
    private FloatingActionButton myInfo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_myacc,container,false);

        delAddr = v.findViewById(R.id.delAddr);
        billAddr = v.findViewById(R.id.billAddr);
        myInfo = v.findViewById(R.id.myInfo);

        return v;
    }
}
