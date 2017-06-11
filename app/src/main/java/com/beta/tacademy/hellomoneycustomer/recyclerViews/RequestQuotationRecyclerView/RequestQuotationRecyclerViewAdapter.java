package com.beta.tacademy.hellomoneycustomer.recyclerViews.RequestQuotationRecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.activity.RequestQuotationActivity;
import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainValueObject;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by kirkee on 2017. 5. 31..
 */

public class RequestQuotationRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int SYSTEM_CHATTING= 0;
    public static final int MY_CHATTING = 1;

    private ArrayList<RequestQuotationValueObject> requestQuotationValueObjectArrayList;
    private int previousStep;
    private int step;

    public RequestQuotationRecyclerViewAdapter(){
        //변수 초기화
        requestQuotationValueObjectArrayList = new ArrayList<>();
        step = 0;
        previousStep = 0;
    }

    public void addItem(RequestQuotationValueObject requestQuotationValueObject){
        this.requestQuotationValueObjectArrayList.add(requestQuotationValueObject);
        notifyDataSetChanged();
    }
    public void initItem(ArrayList<RequestQuotationValueObject> requestQuotationValueObjectArrayList){
        this.requestQuotationValueObjectArrayList = requestQuotationValueObjectArrayList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        //Header와 Item를 구분하여 view 설정

        if (viewType == SYSTEM_CHATTING) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_quotation_recycler_view_system_items, parent, false);
            return new RequestQuotationRecyclerViewAdapter.SystemChattingViewHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_quotation_recycler_view_my_items, parent, false);
            return new RequestQuotationRecyclerViewAdapter.MyChattingViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        //position의 type 반환
        if (requestQuotationValueObjectArrayList.get(position).getType() == 0){
            return SYSTEM_CHATTING;
        }else{
            return MY_CHATTING;
        }
    }

    private class SystemChattingViewHolder extends RecyclerView.ViewHolder {
        TextView systemChatting;
        ImageView info;

        private SystemChattingViewHolder(View itemView) {
            super(itemView);
            systemChatting = (TextView) itemView.findViewById(R.id.systemChattingText);
            info = (ImageView)itemView.findViewById(R.id.info);
        }
    }

    private class MyChattingViewHolder extends RecyclerView.ViewHolder {
        TextView myChatting;
        TextView fix;
        private MyChattingViewHolder(View itemView) {
            super(itemView);

            myChatting = (TextView) itemView.findViewById(R.id.myChattingText);
            fix = (TextView)itemView.findViewById(R.id.fix);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final RequestQuotationValueObject valueObject = requestQuotationValueObjectArrayList.get(position);
        if(holder instanceof RequestQuotationRecyclerViewAdapter.SystemChattingViewHolder){

            ((SystemChattingViewHolder) holder).systemChatting.setText(valueObject.getText());
            if(valueObject.isInfo()){
                ((SystemChattingViewHolder) holder).info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HelloMoneyCustomerApplication.getInstance(), valueObject.getStep() + "단계입니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                ((SystemChattingViewHolder) holder).info.setVisibility(View.INVISIBLE);
            }
        } else{
            ((MyChattingViewHolder) holder).myChatting.setText(valueObject.getText());
            ((MyChattingViewHolder) holder).fix.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HelloMoneyCustomerApplication.getInstance(), valueObject.getStep() + "단계 수정하지마 이 자식아 귀찮으니깐.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return requestQuotationValueObjectArrayList.size();
        //전체 item의 갯수 반환
    }
    private class DoneCustomDialog extends Dialog {

        Button no;
        Button yes;
        public DoneCustomDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.done_custom_dialog);

            yes = (Button)findViewById(R.id.yes);
            no = (Button)findViewById(R.id.no);

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }
}
