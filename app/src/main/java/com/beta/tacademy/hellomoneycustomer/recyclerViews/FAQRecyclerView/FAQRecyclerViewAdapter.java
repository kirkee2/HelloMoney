package com.beta.tacademy.hellomoneycustomer.recyclerViews.FAQRecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;

import java.util.ArrayList;

public class FAQRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<FAQValueObject> faqValueObjectArrayList;

    public FAQRecyclerViewAdapter(){
        faqValueObjectArrayList = new ArrayList<>();
    }

    public void addItem(FAQValueObject faqValueObject){
        faqValueObjectArrayList.add(faqValueObject);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_recycler_view_item, parent, false);
        return new FAQItemViewHolder(view);
    }

    private class FAQItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        ImageView expandToggle;
        CardView cardView;
        private FAQItemViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
            title = (TextView)itemView.findViewById(R.id.title);
            content = (TextView)itemView.findViewById(R.id.content);
            expandToggle = (ImageView)itemView.findViewById(R.id.expandToggle);
        }
    }

    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final FAQValueObject valueObject = faqValueObjectArrayList.get(position);

        ((FAQItemViewHolder) holder).title.setText(valueObject.getTitle());
        ((FAQItemViewHolder) holder).content.setText(valueObject.getContent());
        ((FAQItemViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(valueObject.isOpen()){
                    valueObject.setOpen(false);
                    ((FAQItemViewHolder) holder).title.setTypeface(null, Typeface.NORMAL);
                    ((FAQItemViewHolder) holder).content.animate().translationX(1000).setDuration(150).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            ((FAQItemViewHolder) holder).content.animate().translationX(0);
                            ((FAQItemViewHolder) holder).content.setVisibility(View.GONE);
                        }
                    });
                    ((FAQItemViewHolder) holder).expandToggle.setImageResource(R.drawable.expendable_close);
                }else{
                    valueObject.setOpen(true);
                    ((FAQItemViewHolder) holder).title.setTypeface(null, Typeface.BOLD);

                    ((FAQItemViewHolder) holder).content.animate().translationX(-1000).setDuration(150).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            ((FAQItemViewHolder) holder).content.animate().translationX(0);
                            ((FAQItemViewHolder) holder).content.setVisibility(View.VISIBLE);
                        }
                    });
                    ((FAQItemViewHolder) holder).expandToggle.setImageResource(R.drawable.expendable_open);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return faqValueObjectArrayList.size();
    }
}