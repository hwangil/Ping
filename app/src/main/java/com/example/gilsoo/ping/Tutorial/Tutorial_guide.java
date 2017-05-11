package com.example.gilsoo.ping.Tutorial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.gilsoo.ping.R;

public class Tutorial_guide extends android.support.v4.app.Fragment {
    int imageResource = -1;

    public static Tutorial_guide newInstance(int imageResource) {
        Bundle args = new Bundle();
        Tutorial_guide fragment = new Tutorial_guide();
        args.putInt("imageResource", imageResource);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout = null;
        Bundle bundle = getArguments();
        if(bundle != null ){
            imageResource = bundle.getInt("imageResource");
            linearLayout = (LinearLayout) inflater.inflate(R.layout.activity_tutorial_guide, container, false);
            ImageView imageView = (ImageView)linearLayout.findViewById(R.id.tutorialGuideImage);
//          #1
//            Bitmap bitmap =  decodeSampledBitmapFromResource(getResources(), imageResource,
//                    getContext().getResources().getDisplayMetrics().widthPixels, getContext().getResources().getDisplayMetrics().heightPixels);
//
//            imageView.setImageBitmap(bitmap)

            // out of memory 안뜨는지 확인

//          #2
//            Glide.with(imageView.getContext()).load(imageResource).override(getContext().getResources().getDisplayMetrics().widthPixels,
//                    getContext().getResources().getDisplayMetrics().heightPixels).into(imageView);
//          #3
            Glide.with(imageView.getContext()).load(imageResource).centerCrop().into(imageView);
        }

        return linearLayout;
    }

}
