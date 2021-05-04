package com.espeedboat.admin.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espeedboat.admin.R;

public class TicketHolder extends RecyclerView.ViewHolder {
    private TextView name, numberIdCard, code, status;

    public TicketHolder(@NonNull View itemView) {
        super(itemView);
        getIds();
    }

    private void getIds() {
        this.name = itemView.findViewById(R.id.ticket_name);
        this.numberIdCard = itemView.findViewById(R.id.ticket_no_id_card);
        this.code = itemView.findViewById(R.id.ticket_code);
        this.status = itemView.findViewById(R.id.ticket_status);
    }

    public TextView getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public TextView getNumberIdCard() {
        return numberIdCard;
    }

    public void setNumberIdCard(String numberIdCard) {
        this.numberIdCard.setText(numberIdCard);
    }

    public TextView getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }

    public void setCode(String code) {
        this.code.setText(code);
    }

    public TextView getCode() {
        return code;
    }
}