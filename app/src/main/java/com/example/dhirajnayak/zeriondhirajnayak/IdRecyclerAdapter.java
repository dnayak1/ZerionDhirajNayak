package com.example.dhirajnayak.zeriondhirajnayak;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dhirajnayak.zeriondhirajnayak.model.IdValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by dhirajnayak on 9/25/17.
 */

public class IdRecyclerAdapter extends RecyclerView.Adapter<IdRecyclerAdapter.IdRecyclerViewHolder> {
    ArrayList<IdValue> arrayListId=new ArrayList<>();
    Context mContext;
    private IdListener idListener;

    public IdRecyclerAdapter(Context mContext, ArrayList<IdValue> arrayListId, IdListener idListener) {
        this.arrayListId = arrayListId;
        this.mContext = mContext;
        this.idListener = idListener;

    }



    @Override
    public IdRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.id_list_layout,parent,false);
        IdRecyclerAdapter.IdRecyclerViewHolder idRecyclerViewHolder=new IdRecyclerAdapter.IdRecyclerViewHolder(view);
        return idRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(IdRecyclerViewHolder holder, int position) {
        final IdValue idValue=arrayListId.get(position);

        holder.textViewId.setText(String.valueOf("ID : "+idValue.getId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idListener.idDetail(idValue);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListId.size();
    }

    public static class IdRecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView textViewId;

        public IdRecyclerViewHolder(View itemView) {
            super(itemView);
            textViewId= (TextView) itemView.findViewById(R.id.textViewId);
        }
    }

    interface IdListener
    {
        void idDetail(IdValue idValue);
    }

}