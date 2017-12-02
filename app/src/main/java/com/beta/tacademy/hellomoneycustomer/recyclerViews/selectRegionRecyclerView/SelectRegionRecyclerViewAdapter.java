package com.beta.tacademy.hellomoneycustomer.recyclerViews.selectRegionRecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.activity.PostscriptDetailActivity;
import com.beta.tacademy.hellomoneycustomer.activity.RequestQuotationActivity;
import com.beta.tacademy.hellomoneycustomer.activity.SelectRegionAptSizeActivity;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainValueObject;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainFragmentPagerAdapter;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;

import java.util.ArrayList;

public class SelectRegionRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private int step;

    private ArrayList<String> stringArrayList;
    public void initItem(ArrayList<String> stringArrayList){
        this.stringArrayList = stringArrayList; //아이템 추가
        notifyDataSetChanged();
    }

    public SelectRegionRecyclerViewAdapter(Activity activity,int step){
        //변수 초기화
        this.activity = activity;
        this.step =step;
        stringArrayList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_region_recycler_view_item, parent, false);
        return new SelectRegionRecyclerViewAdapter.MainViewHolder(view);
    }

    private class MainViewHolder extends RecyclerView.ViewHolder {
        FrameLayout frameLayout;
        TextView text;

        private MainViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            frameLayout = (FrameLayout)itemView.findViewById(R.id.frameLayout);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final String valueObject = stringArrayList.get(position);
        ((MainViewHolder) holder).text.setText(valueObject);
        ((MainViewHolder) holder).frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(step == 4){
                    ((SelectRegionAptSizeActivity)activity).sizeInfo(valueObject);
                }else{
                    ((SelectRegionAptSizeActivity)activity).toNextStep(step,valueObject);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
       return stringArrayList.size();
    }
}
