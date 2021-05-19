package com.sunny.icarusinbloom.recycler_elem;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.sunny.icarusinbloom.R;

import java.util.List;

public class DiaryItemAdapter extends RecyclerView.Adapter<DiaryItemAdapter.ViewHolder>{

    private List<DiaryItem> itemList;
    OnListItemClick2 listItemClick;

    public DiaryItemAdapter(List<DiaryItem> itemList, OnListItemClick2 listItemClick) {
        this.itemList=itemList;
        this.listItemClick=listItemClick;
    }

    public void setPlants(List<DiaryItem> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.diary_item,parent,false);
        return new DiaryItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.content.setText(itemList.get(position).getContent());
        holder.date.setText(itemList.get(position).getDate());
        if(itemList.get(position).getImage().equals("null")) {
            holder.photo.setImageResource(R.drawable.plant);
        }
        else {
            Picasso.get().load(Uri.parse(itemList.get(position).getImage())).centerCrop().rotate(90).fit().into(holder.photo);
        }

    }

    @Override
    public int getItemCount() {
        if(itemList!=null)
            return itemList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        TextView date;
        ImageView photo;
        Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.diaryContent);
            date = itemView.findViewById(R.id.diaryDate);
            photo = itemView.findViewById(R.id.diaryImage);
            delete = itemView.findViewById(R.id.deleteDiaryEntry);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItemClick.onClick(itemList.get(getAdapterPosition()));
                }
            });
        }


    }

    public interface OnListItemClick2{
        void onClick(DiaryItem diaryItem);
    }
}
