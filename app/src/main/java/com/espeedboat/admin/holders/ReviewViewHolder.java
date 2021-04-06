package com.espeedboat.admin.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espeedboat.admin.R;
import com.espeedboat.admin.utils.CircleTransform;
import com.espeedboat.admin.utils.Util;
import com.squareup.picasso.Picasso;

public class ReviewViewHolder extends RecyclerView.ViewHolder{

    private TextView email, tanggal, review, score_text;
    private LinearLayout score;
    private ImageView imageUser;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        getIds();
    }

    public void getIds() {
        email = (TextView) itemView.findViewById(R.id.review_text_email);
        tanggal = (TextView) itemView.findViewById(R.id.review_text_tanggal);
        score = (LinearLayout) itemView.findViewById(R.id.score_wrapper);
        score_text = (TextView) itemView.findViewById(R.id.review_text_score);
        review = (TextView) itemView.findViewById(R.id.review_text_review);
        imageUser = (ImageView) itemView.findViewById(R.id.image_user);
    }

    public TextView getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public TextView getTanggal() { return tanggal; }

    public void setTanggal(String tanggal) {
        this.tanggal.setText(tanggal);
    }

    public TextView getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review.setText(review);
    }

    public TextView getScoreText() {
        return score_text;
    }

    public void setScoreText(String score_text) {
        this.score_text.setText(score_text);
    }

    public LinearLayout getScore() {
        return score;
    }

    public void setScore(int value) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                Util.dpToPx(16, itemView.getContext()),
                Util.dpToPx(16, itemView.getContext())
        );

        for(int i=0; i < 5; i++) {
            ImageView imageView = new ImageView(itemView.getContext());
            imageView.setLayoutParams(params);
            if (i < value) {
                imageView.setImageResource(R.drawable.ic_star_full);
            } else {
                imageView.setImageResource(R.drawable.ic_star_half);
            }
            score.addView(imageView);
        }
    }

    public ImageView getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        Picasso.get().load(imageUser).transform(new CircleTransform()).into(this.imageUser);
    }
}
