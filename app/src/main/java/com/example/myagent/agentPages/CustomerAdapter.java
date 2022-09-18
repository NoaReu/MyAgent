package com.example.myagent.agentPages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myagent.R;
import com.example.myagent.objects.User;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<User> users;

    public CustomerAdapter(Context context, ArrayList<User> users, RecyclerViewInterface recyclerViewInterface){
        this.recyclerViewInterface=recyclerViewInterface;
       this.context=context;
       this.users=users;

    }

    @NonNull
    @Override
    public CustomerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_customer_item_at_agent_search,parent,false);
        return new CustomerAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.MyViewHolder holder, int position) {

        holder.customerName.setText(users.get(position).getFirstName()+" "+users.get(position).getLastName());
        holder.image.setImageAlpha(R.drawable.avatar);
//        holder.image.setImageIcon(R.drawable.avatar);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView customerName;
        ImageView image;

        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            customerName= itemView.findViewById(R.id.customer_name_at_recyclerview_item);
            image= itemView.findViewById(R.id.customer_image_on_search_customer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos= getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

//    Context context;
//    List<User> items;
//
//    public CustomerAdapter(Context context, List<User> items){
//        this.context=context;
//        this.items=items;
//    }
//
//
//    @NonNull
//    @Override
//    public CustomerItemAtAgentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new CustomerItemAtAgentViewHolder(LayoutInflater.from(context).inflate(R.layout.customer_item_at_agent_search,parent,false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CustomerItemAtAgentViewHolder holder, int position) {
//        holder.customerName.setText(items.get(position).getFirstName()+" "+items.get(position).getLastName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
}
