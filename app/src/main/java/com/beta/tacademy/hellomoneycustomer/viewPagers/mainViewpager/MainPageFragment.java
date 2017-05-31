package com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
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

    public MainPageFragment(MainPageViewPagerObject mainPageViewPagerObject) {
        this.mainPageViewPagerObject = mainPageViewPagerObject;
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
        if(mainPageViewPagerObject.getType() == 0){
            type.setText("전세자금 대출");
        }else{
            type.setText("주택담보 대출");
        }

        currentQuotation.setText(mainPageViewPagerObject.getCurrentQuotation()+"");
        leftTime.setText("마감까지 " + mainPageViewPagerObject.getLeftTime() + " 남았습니다.");

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"id = " + mainPageViewPagerObject.getId() + " 상세로 이동.",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
