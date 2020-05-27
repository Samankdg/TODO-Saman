package com.example.todosaman;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TODOAdapter extends RecyclerView.Adapter<TODOAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyTODO> myTODOS;

    public TODOAdapter(Context c, ArrayList<MyTODO> p) {
        context = c;
        myTODOS = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_todo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myviewHolder, int i) {
        myviewHolder.titleTODO.setText(myTODOS.get(i).getTitleTODO());
        myviewHolder.descTODO.setText(myTODOS.get(i).getDescTODO());
        myviewHolder.dateTODO.setText(myTODOS.get(i).getDateTODO());

        final String gettitleTODO = myTODOS.get(i).getTitleTODO();
        final String getdescTODO = myTODOS.get(i).getDescTODO();
        final String getdateTODO = myTODOS.get(i).getDateTODO();
        final String getKeyTODO = myTODOS.get(i).getKeyTODO();

        myviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(context, EditTaskDesk.class);
                aa.putExtra("titleTODO",gettitleTODO);
                aa.putExtra("descTODO",getdescTODO);
                aa.putExtra("dateTODO",getdateTODO);
                aa.putExtra("keyTODO", getKeyTODO);
                context.startActivity(aa);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myTODOS.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTODO, descTODO, dateTODO, keyTODO;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTODO = (TextView) itemView.findViewById(R.id.titleTODO);
            descTODO = (TextView) itemView.findViewById(R.id.descTODO);
            dateTODO = (TextView) itemView.findViewById(R.id.dateTODO);
        }
    }

    public void updateList(List<MyTODO> newList)
    {
        myTODOS = new ArrayList<MyTODO>();
        myTODOS.addAll(newList);
        notifyDataSetChanged();
    }
}
