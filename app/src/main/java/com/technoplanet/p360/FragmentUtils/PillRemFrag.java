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
 * Created by BHARGAV on 01/11/2018.
 */

public class PillRemFrag extends Fragment {

    private FloatingActionButton btnPillRem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_pillrem,container,false);

        btnPillRem = v.findViewById(R.id.btnPillRem);

        return v;

    }
}
