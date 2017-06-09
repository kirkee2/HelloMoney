package com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.activity.QuotationDetailActivity;
import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.myQuotationRecyclerView.MyQuotationRecyclerViewAdapter;

public class MainPageFragment extends Fragment {

    MainPageViewPagerObject mainPageViewPagerObject;
    TextView region;
    TextView aptSize;
    TextView type;
    TextView currentQuotation;
    TextView leftTime;
    LinearLayout linearLayout;

    public MainPageFragment() {
        // Required empty public constructor
    }

    public static MainPageFragment newInstance(MainPageViewPagerObject mainPageViewPagerObject) {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putParcelable("mainPageViewPagerObject", mainPageViewPagerObject);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mainPageViewPagerObject = getArguments().getParcelable("mainPageViewPagerObject");
        }else{
            this.mainPageViewPagerObject = null;
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        region = (TextView)view.findViewById(R.id.region);
        aptSize = (TextView)view.findViewById(R.id.aptSize);
        type = (TextView)view.findViewById(R.id.type);
        currentQuotation = (TextView)view.findViewById(R.id.currentQuotation);
        leftTime = (TextView)view.findViewById(R.id.leftTime);
        linearLayout = (LinearLayout)view.findViewById(R.id.linearLayout);

        region.setText(mainPageViewPagerObject.getRegion1() + " " + mainPageViewPagerObject.getRegion2() + " " +  mainPageViewPagerObject.getRegion3());
        aptSize.setText(mainPageViewPagerObject.getApt() + " / " + mainPageViewPagerObject.getSize());
        type.setText(mainPageViewPagerObject.getType());


        currentQuotation.setText(String.valueOf(mainPageViewPagerObject.getCurrentQuotation()));
        leftTime.setText("마감까지 " + mainPageViewPagerObject.getLeftTime() + " 남았습니다.");

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuotationDetailActivity.class);
                intent.putExtra("id",mainPageViewPagerObject.getId());
                getActivity().startActivity(intent);
                Toast.makeText(getActivity(),"id = " + mainPageViewPagerObject.getId() + " 상세로 이동.",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
