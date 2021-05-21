package com.espeedboat.admin.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espeedboat.admin.R;

public class TransaksiViewHolder extends RecyclerView.ViewHolder {
    private TextView username, email, orderDate, person, status, from, fromDate, fromTime, to, toDate, toTime;
    private LinearLayout status_wrapper;

    public TransaksiViewHolder(@NonNull View itemView) {
        super(itemView);
        getIds();
    }

    private void getIds() {
        username = (TextView) itemView.findViewById(R.id.tl_username);
        email = (TextView) itemView.findViewById(R.id.tl_email);
        orderDate = (TextView) itemView.findViewById(R.id.tl_order_date);
        person = (TextView) itemView.findViewById(R.id.tl_person);
        status = (TextView) itemView.findViewById(R.id.tl_status);
        from = (TextView) itemView.findViewById(R.id.tl_from);
        fromDate = (TextView) itemView.findViewById(R.id.tl_from_date);
        fromTime = (TextView) itemView.findViewById(R.id.tl_from_time);
        to = (TextView) itemView.findViewById(R.id.tl_to);
        toDate = (TextView) itemView.findViewById(R.id.tl_to_date);
        toTime = (TextView) itemView.findViewById(R.id.tl_to_time);
        status_wrapper = (LinearLayout) itemView.findViewById(R.id.tl_status_wrapper);
    }

    public TextView getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

    public TextView getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public TextView getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate.setText(orderDate);
    }

    public TextView getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from.setText(from);
    }

    public TextView getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate.setText(fromDate);
    }

    public TextView getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime.setText(fromTime);
    }

    public TextView getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person.setText(person);
    }

    public TextView getStatus() {
        return status;
    }

    public void setStatus(String status) {

        String text = "Default";
        int color = R.drawable.status_default;
        if (status.equals("terkonfirmasi")) {
            text = "Success";
            color = R.drawable.status_green;
        } else if (status.equals("digunakan")) {
            text = "Used";
            color = R.color.primary;
        } else if (status.equals("menunggu konfirmasi")) {
            text = "Waiting";
            color = R.drawable.status_yellow;
        } else if (status.equals("menunggu pembayaran")) {
            text = "Waiting";
            color = R.drawable.status_yellow;
        } else if (status.equals("dibatalkan")) {
            text = "Canceled";
            color = R.drawable.status_red;
        } else if (status.equals("expired")) {
            text = "Expired";
            color = R.drawable.status_orange;
        }
        this.status.setText(text);
        this.status_wrapper.setBackgroundResource(color);
    }

    public TextView getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to.setText(to);
    }

    public TextView getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate.setText(toDate);
    }

    public TextView getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime.setText(toTime);
    }
}
