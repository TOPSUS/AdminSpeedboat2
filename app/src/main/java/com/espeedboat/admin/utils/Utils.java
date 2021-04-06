package com.espeedboat.admin.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.espeedboat.admin.R;
import com.squareup.picasso.Picasso;


public class Utils {
    public static String RD_ID = "review_detail_id";
    public static String TRANSAKSI_ID = "transaksi_id";
    public static String KODE_TIKET = "kode_tiket";

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static void setScore(int value, Context context, LinearLayout target) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
               Utils.dpToPx(16, context),
                Utils.dpToPx(16, context)
        );

        for(int i=0; i < 5; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(params);
            if (i < value) {
                imageView.setImageResource(R.drawable.ic_full_star);
            } else {
                imageView.setImageResource(R.drawable.ic_stroke_star);
            }
            target.addView(imageView);
        }
    }

    public static void setImageUser(String imageUser, ImageView target) {
        Picasso.get().load(imageUser).transform(new CircleTransform()).into(target);
    }
}
