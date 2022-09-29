package com.example.myagent.agentPages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myagent.R;
import com.example.myagent.objects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private List<User> users;
    private List<User> usersFull;
//    private TextView nameToSearch;

    public CustomerAdapter(Context context, List<User> users, RecyclerViewInterface recyclerViewInterface){
        this.recyclerViewInterface=recyclerViewInterface;
        this.context=context;
        this.users=users;
        this.usersFull= new ArrayList<>(users);
//
    }

    @NonNull
    @Override
    public CustomerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_customer_item_at_agent_search,parent,false);
//        this.nameToSearch=(EditText)view.findViewById(R.id.customer_name_for_search);
        return new CustomerAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.MyViewHolder holder, int position) {
/*assigning values to the view we created in the recycler view row layout file based on the position of the recycler view*/
        holder.customerName.setText(users.get(position).getFirstName()+" "+users.get(position).getLastName());
        holder.image.setImageAlpha( R.drawable.img);
    }

    @Override
    public int getItemCount() {
        /*the recycler view wants to know the amount of items to display*/
        return users.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
/* grabbing the view from each row layout file (like the on create method for each row. */
        TextView customerName;
        ImageView image;

        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            customerName= itemView.findViewById(R.id.customer_name_at_recyclerview_item);
            image= itemView.findViewById(R.id.customer_img_at_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){

                        int pos= getAbsoluteAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
//                            recyclerViewInterface.onItemClick(itemView.,pos);
                        }
                    }
                }
            });
        }

    }


}
