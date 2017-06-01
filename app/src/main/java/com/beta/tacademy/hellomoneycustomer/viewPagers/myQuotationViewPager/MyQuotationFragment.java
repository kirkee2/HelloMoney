package com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;

import org.w3c.dom.Text;

public class MyQuotationFragment extends Fragment {
    int tmp;
    TextView tmpText;



    public MyQuotationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyQuotationFragment newInstance(int tmp) {
        MyQuotationFragment fragment = new MyQuotationFragment();
        Bundle args = new Bundle();
        args.putInt("tmp",tmp);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tmp = getArguments().getInt("tmp");
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_quotation, container, false);
        tmpText = (TextView)view.findViewById(R.id.textView);

        tmpText.setText(String.valueOf(tmp));
        return view;
    }

}
