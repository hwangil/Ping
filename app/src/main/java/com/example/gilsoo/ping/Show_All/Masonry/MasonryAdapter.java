package com.example.gilsoo.ping.Show_All.Masonry;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.gilsoo.ping.Item.CardItem;
import com.example.gilsoo.ping.R;

import java.util.ArrayList;

/**
 * Created by gilsoo on 2016-06-28.
 */
public class MasonryAdapter extends RecyclerView.Adapter<MasonryAdapter.MasonryView> {

    ArrayList<CardItem> cardList;
    Context context;
    CustomItemClickListener listener;
    RequestManager requestManager = null;

    public MasonryAdapter(Context context, CustomItemClickListener listener, ArrayList<CardItem> cardList, RequestManager requestManager) {  // ArrayList 인자로 넘겨줘서 생성해야함
        this.context = context;
        this.listener = listener;
        this.cardList = cardList;
        this.requestManager = requestManager;
    }

    public MasonryAdapter(Context context, CustomItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        final View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        final MasonryView masonryView = new MasonryView(layoutView);
        layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, masonryView.getAdapterPosition());
                if (cardList.get(masonryView.getAdapterPosition()).isSelected()) {                              // 선택된 카드들 색깔 변경
                    Log.d("isSelected", "선택됨");
                    masonryView.cardListItem.setBackgroundColor(Color.parseColor("#85D5D5D5"));
                } else {
                    Log.d("isSelected", "선택안됨");
                    masonryView.cardListItem.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                }
            }
        });
        layoutView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongItemClick(v, masonryView.getAdapterPosition());
                return true;
            }
        });

        return masonryView;
    }

    @Override
    public void onBindViewHolder(MasonryView holder, int position) {
        // Todo. 카드 width height 정보도? -> 그러면 캡쳐 크기 조정도 필요함.
        if (cardList.get(position).isSelected()) {                              // 선택된 카드들 색깔 변경
            Log.d("isSelected", "선택됨");
            holder.cardListItem.setBackgroundColor(Color.parseColor("#85D5D5D5"));
        } else {
            Log.d("isSelected", "선택안됨");
            holder.cardListItem.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }

        if (cardList.get(position).getImg() != null) {
                requestManager.load(Integer.parseInt(cardList.get(position).getImg())).placeholder(R.drawable.logo)
                        .error(R.drawable.appjam_visual_woman_on).fitCenter().thumbnail(0.04f).into(holder.cardImage);
                Log.d("gilsoo_Glide", ""+position + " th is loading");
        }

        holder.cardGroup.setText("Group : " + cardList.get(position).getGroupInfo());
        holder.cardMemo.setText(cardList.get(position).getMemo());
        //Todo. 크기 지정
        // ****** resource image 크기 구하는 코드 ********//
//        BitmapFactory.Options dimensions = new BitmapFactory.Options();         //
//        dimensions.inJustDecodeBounds = true;
//        Bitmap mBitmap = BitmapFactory.decodeResource(context.getResources(), Integer.parseInt(cardList.get(position).getImg()), dimensions);
//        int width =  dimensions.outWidth;
//        int height = dimensions.outHeight;

        // ****** pixel to dp ********//
//        float widthdp = width / context.getResources().getDisplayMetrics().density;         // pixel to dp
//        float heightdp = height / context.getResources().getDisplayMetrics().density;       // pixel to dp


        // ****** layout width, height 설정 ********//
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cardList.get(position).getWidth(), context.getResources().getDisplayMetrics()),
//                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cardList.get(position).getHeight(), context.getResources().getDisplayMetrics()));
//
//        holder.cardListItem.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    // 내부 클래스 MasonryView
    class MasonryView extends RecyclerView.ViewHolder {
        ImageView cardImage;
        TextView cardGroup;
        LinearLayout cardListItem;
        TextView cardMemo;

        public MasonryView(View itemView) {
            super(itemView);
            cardImage = (ImageView) itemView.findViewById(R.id.cardImage);
            cardGroup = (TextView) itemView.findViewById(R.id.cardGroup);
            cardMemo = (TextView) itemView.findViewById(R.id.cardMemo);
            cardListItem = (LinearLayout) itemView.findViewById(R.id.cardListItem);
        }
    }


}
