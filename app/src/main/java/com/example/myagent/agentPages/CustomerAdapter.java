package com.example.myagent.agentPages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myagent.R;
import com.example.myagent.objects.User;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> users;

    public CustomerAdapter(Context context, ArrayList<User> users){
       this.context=context;
       this.users=users;
    }

    @NonNull
    @Override
    public CustomerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_customer_item_at_agent_search,parent,false);
        return new CustomerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.MyViewHolder holder, int position) {
        holder.customerName.setText(users.get(position).getFirstName()+" "+users.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView customerName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName= itemView.findViewById(R.id.customer_name_at_recyclerview_item);
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
