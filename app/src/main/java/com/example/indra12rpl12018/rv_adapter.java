package com.example.indra12rpl12018;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class rv_adapter extends RecyclerView.Adapter<rv_adapter.ScansDataViewHolder> {
    private List<rv_model> mList;
    private Context mCtx;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }


    public rv_adapter(Context ctx, List<rv_model> list) {
        this.mCtx = ctx;
        this.mList = list;
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
        accountsScansDataViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(aModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mList !=null? mList.size():0);
    }

    public class ScansDataViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama,tvJenis, tvKode;
        private EditText nama,jenis,text;



        public ScansDataViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = (TextView) itemView.findViewById(R.id.tvNama);
            tvJenis = (TextView) itemView.findViewById(R.id.tvJenis);
            tvKode = (TextView) itemView.findViewById(R.id.tvKode);


        }
    }
    public interface OnItemClickCallback {
        void onItemClicked(rv_model data);
    }
}
