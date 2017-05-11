package com.example.gilsoo.ping.Tutorial;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.gilsoo.ping.R;

public class Tutorial extends android.support.v4.app.Fragment {

    int imageResource = -1;

    public static Tutorial newInstance(int imageResource) {
        Bundle args = new Bundle();
        Tutorial fragment = new Tutorial();
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
            linearLayout = (LinearLayout) inflater.inflate(R.layout.activity_tutorial, container, false);
            ImageView imageView = (ImageView)linearLayout.findViewById(R.id.tutorialImage);

//          #1
//            Bitmap bitmap =  decodeSampledBitmapFromResource(getResources(), imageResource,
//                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 360, getContext().getResources().getDisplayMetrics()),
//                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 416, getContext().getResources().getDisplayMetrics()));
//
//            BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
//            imageView.setBackground(ob);                                  // 그냥 imageview에 setImage입히면 위에 마진생김 -> background로 하려고

//          #2
            Glide.with(imageView.getContext()).load(imageResource).centerCrop().into(imageView);
        }

        return linearLayout;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 2;
        Log.d("gilsoo_Tutorial", "height : " + String.valueOf(height) + " width : " + String.valueOf(width) + " reqHeight : "
                + String.valueOf(reqHeight) + " reqWidth : "  + String.valueOf(reqWidth) );
        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
