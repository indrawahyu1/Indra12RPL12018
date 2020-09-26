package com.example.indra12rpl12018;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class rv_adapter extends RecyclerView.Adapter<rv_adapter.ScansDataViewHolder> {
    private ArrayList<rv_model> mList;
    private Context mCtx;

    public rv_adapter(Context ctx, ArrayList<rv_model> mList) {
        this.mCtx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ScansDataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_rv, viewGroup,false);
        return new ScansDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScansDataViewHolder accountsScansDataViewHolder, int position) {
        final rv_model aModel = mList.get(position);

        accountsScansDataViewHolder.tvNama.setText(aModel.getNama());
        accountsScansDataViewHolder.tvKode.setText(aModel.getId());
        accountsScansDataViewHolder.tvJenis.setText(aModel.getEmail());
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clearData() {
        int size = this.mList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.mList.remove(0);
            }
        }
    }


    public class ScansDataViewHolder extends RecyclerView.ViewHolder {
        CardView cvItem;
        TextView tvNama,tvJenis, tvKode;
        private EditText nama,jenis,text;



        public ScansDataViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = (TextView) itemView.findViewById(R.id.tvNama);
            tvJenis = (TextView) itemView.findViewById(R.id.tvJenis);
            tvKode = (TextView) itemView.findViewById(R.id.tvKode);
            cvItem = (CardView) itemView.findViewById(R.id.cvItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mCtx, UserActivity.class);
                    i.putExtra("EXTRA_CUSTOMER", mList.get(getAdapterPosition()));
                    mCtx.startActivity(i);
                }
            });
        }
    }

}
