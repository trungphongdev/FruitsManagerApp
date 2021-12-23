package com.example.qunlcahnghoaqu.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qunlcahnghoaqu.AddFruits;
import com.example.qunlcahnghoaqu.Object.Fruits;
import com.example.qunlcahnghoaqu.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListFruitAdapter extends RecyclerView.Adapter<ListFruitAdapter.MyHolder> implements Filterable {
    ArrayList<Fruits>  listFruits;
    ArrayList<Fruits>  listFruitsAll;
    Context context;
    public  ListFruitAdapter(Context context , ArrayList<Fruits> fruits) {
        this.context = context;
        this.listFruits  =fruits;
        this.listFruitsAll = new ArrayList<>(fruits);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view  = layoutInflater.inflate(R.layout.layout_row_adapterfruit,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Fruits fruits = listFruits.get(position);
        holder.txvNameFruit.setText(fruits.getNameFruit());
        holder.txvIDfruit.setText(fruits.getIdFruit());
        byte[] img = listFruits.get(position).getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
        holder.imgFruit.setImageBitmap(bitmap);
        holder.txvDetailFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddFruits.class);
                intent.putExtra("name_fruit",holder.txvNameFruit.getText().toString().trim());
                intent.putExtra("detail",-1);
                intent.putExtra("id",holder.txvIDfruit.getText().toString().trim());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
            return listFruits.size();
    }

    // dung adapter de loc ket qua tu listfood.java , truyen list chua ki tu tim dc vao
    public  void filterList(ArrayList<Fruits> filter) {
        listFruits =  filter;
        notifyDataSetChanged();
    }

    // implement Filterable
    @Override
    public Filter getFilter() {
        return new Filter() {
            // run on background thread
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Fruits> listFilter = new ArrayList<>();
                if(constraint.toString().isEmpty()) {
                    listFilter.addAll(listFruitsAll);
                }
                else  {
                    for(Fruits fruits : listFruitsAll) {
                        if(fruits.getIdFruit().toLowerCase().contains(constraint.toString().toLowerCase())){
                            listFilter.add(fruits);
                        }
                    }
                }
               FilterResults results = new FilterResults();
                results.values = listFilter;

                return results;
            }
            //run on ui thread

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFruits.clear();
                listFruits.addAll((Collection<? extends Fruits>) results.values);
                notifyDataSetChanged();

            }
        };
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imgFruit;
        TextView txvIDfruit;
        TextView txvNameFruit;
        TextView txvDetailFruit;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgFruit = (ImageView) itemView.findViewById(R.id.imageViewFruitAdapter);
            txvIDfruit = (TextView) itemView.findViewById(R.id.textViewIDFruitAdapter);
            txvNameFruit = (TextView) itemView.findViewById(R.id.textViewNameFruitAdapter);
            txvDetailFruit = (TextView) itemView.findViewById(R.id.textViewDetailFruit);
        }
    }
}
