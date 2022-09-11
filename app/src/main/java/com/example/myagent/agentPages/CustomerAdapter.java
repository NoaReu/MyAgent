package com.example.myagent.agentPages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myagent.R;
import com.example.myagent.objects.User;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerItemAtAgentViewHolder> {

    Context context;
    List<User> items;

    public CustomerAdapter(Context context, List<User> items){
        this.context=context;
        this.items=items;
    }


    @NonNull
    @Override
    public CustomerItemAtAgentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerItemAtAgentViewHolder(LayoutInflater.from(context).inflate(R.layout.customer_item_at_agent_search,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerItemAtAgentViewHolder holder, int position) {
        holder.customerName.setText(items.get(position).getFirstName()+" "+items.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
