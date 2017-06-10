package com.beta.tacademy.hellomoneycustomer.viewPagers.myQuotationViewPager;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.myQuotationRecyclerView.MyQuotationRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.attr.padding;

public class MyQuotationFragment extends Fragment {
    RecyclerView recyclerView;
    MyQuotationRecyclerViewAdapter myQuotationFragmentPagerAdapter;
    ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList;


    public MyQuotationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyQuotationFragment newInstance(ArrayList<MainPageViewPagerObject> mainPageViewPagerObjectArrayList) {
        MyQuotationFragment fragment = new MyQuotationFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("mainPageViewPagerObjectArrayList",mainPageViewPagerObjectArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mainPageViewPagerObjectArrayList = getArguments().getParcelableArrayList("mainPageViewPagerObjectArrayList");
        }

        myQuotationFragmentPagerAdapter = new MyQuotationRecyclerViewAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_quotation, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false); //RecyclerView에 설정 할 LayoutManager 초기화

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myQuotationFragmentPagerAdapter);

        myQuotationFragmentPagerAdapter.init(mainPageViewPagerObjectArrayList);
        return view;
    }
}
