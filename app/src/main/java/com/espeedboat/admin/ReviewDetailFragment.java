package com.espeedboat.admin;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.interfaces.FinishActivity;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.model.ReviewDetail;
import com.espeedboat.admin.model.ReviewDetailOrder;
import com.espeedboat.admin.service.ReviewService;
import com.espeedboat.admin.utils.Constants;
import com.espeedboat.admin.utils.SessionManager;
import com.espeedboat.admin.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;

public class ReviewDetailFragment extends Fragment {

    View view;
    private TextView email, tanggal, review, score_text, from_loc, from_date, from_time, to_loc, to_date,
            to_time, speedboat_name, person, total_price;
    private LinearLayout score;
    private ImageView imageUser;
    private Integer review_id = 0;
    private ReviewService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.review_id = getArguments().getInt(Constants.RD_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_review_detail, container, false);
        this.service = RetrofitClient.getClient().create(ReviewService.class);
        getData();

        return view;
    }

    private void getData() {
        if (review_id != 0) {
            Call<Response> getReview = service.getReviewDetail(new SessionManager(view.getContext()).getAuthToken(), review_id);

            getReview.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            setIds();
                            setValue(response.body().getData().getReviewDetail(), response.body().getData().getReviewDetailOrder());
                        } else {
                            Toast.makeText(getActivity(), response.body().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.e("ERROR [ReviewDetailFragment] ", t.getMessage());
                    Toast.makeText(getActivity(),  t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void setIds() {
        this.email = (TextView) view.findViewById(R.id.rd_email);
        this.tanggal = (TextView) view.findViewById(R.id.rd_tanggal);
        this.score = (LinearLayout) view.findViewById(R.id.rd_score_wrapper);
        this.score_text = (TextView) view.findViewById(R.id.rd_score);
        this.review = (TextView) view.findViewById(R.id.rd_review);
        this.imageUser = (ImageView) view.findViewById(R.id.rd_image_user);
        this.from_loc = (TextView) view.findViewById(R.id.rd_from_loc);
        this.from_date = (TextView) view.findViewById(R.id.rd_from_date);
        this.from_time = (TextView) view.findViewById(R.id.rd_from_time);
        this.to_loc = (TextView) view.findViewById(R.id.rd_to_loc);
        this.to_date = (TextView) view.findViewById(R.id.rd_to_date);
        this.to_time = (TextView) view.findViewById(R.id.rd_to_time);
        this.speedboat_name = (TextView) view.findViewById(R.id.rd_speedboat);
        this.person = (TextView) view.findViewById(R.id.rd_person);
        this.total_price = (TextView) view.findViewById(R.id.rd_price);
    }

    private void setValue(ReviewDetail data, ReviewDetailOrder data_order) {
        Util.setScore(data.getScore(), this.getContext(), this.score);
        Util.setImageUser(data.getImage(), this.imageUser);

        this.email.setText(data.getEmail());
        this.tanggal.setText(data.getTanggal());
        this.score_text.setText(data.getScore().toString());
        this.review.setText(data.getReview());
        this.from_loc.setText(data_order.getFromPlace());
        this.from_date.setText(data_order.getFromDate());
        this.from_time.setText(data_order.getFromTime());
        this.to_loc.setText(data_order.getToPlace());
        this.to_date.setText(data_order.getToDate());
        this.to_time.setText(data_order.getToTime());
        this.speedboat_name.setText(data_order.getSpeedboat());
        this.person.setText(data_order.getPerson() + " Persons");
        this.total_price.setText(data_order.getPrice().toString());
    }
}