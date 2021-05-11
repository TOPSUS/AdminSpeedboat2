package com.espeedboat.admin.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espeedboat.admin.R;
import com.espeedboat.admin.utils.CircleTransform;
import com.squareup.picasso.Picasso;

public class BeritaHolder extends RecyclerView.ViewHolder {
    private TextView judul, created;
    private ImageView image;

    public BeritaHolder(@NonNull View itemView) {
        super(itemView);
        getIds();
    }
    private void getIds() {
        judul = (TextView) itemView.findViewById(R.id.judul);
        created = (TextView) itemView.findViewById(R.id.created);
        image = (ImageView) itemView.findViewById(R.id.thumbnail);
    }
    public TextView getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul.setText(judul);
    }

    public TextView getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created.setText(created);
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(String image) {
        Picasso.get().load(image).into(this.image);
    }
}
