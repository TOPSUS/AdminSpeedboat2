package com.espeedboat.admin.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.espeedboat.admin.R;
import com.squareup.picasso.Picasso;

public class Util {
    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static void setScore(int value, Context context, LinearLayout target) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                Util.dpToPx(16, context),
                Util.dpToPx(16, context)
        );

        for(int i=0; i < 5; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(params);
            if (i < value) {
                imageView.setImageResource(R.drawable.ic_star_full);
            } else {
                imageView.setImageResource(R.drawable.ic_star_half);
            }
            target.addView(imageView);
        }
    }

    public static void setImageUser(String imageUser, ImageView target) {
        Picasso.get().load("http://admin.espeedboat.xyz/storage/image_users/"+imageUser).transform(new CircleTransform()).into(target);
//        Picasso.get().load(imageUser).transform(new CircleTransform()).into(target);
    }

    public static String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}
