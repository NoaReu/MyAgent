package com.example.myagent.agentPages;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myagent.R;

public  class CustomerItemAtAgentViewHolder extends RecyclerView.ViewHolder {
   TextView customerName;

    public CustomerItemAtAgentViewHolder(@NonNull View itemView) {
        super(itemView);
        customerName=itemView.findViewById(R.id.customer_name_at_item);

    }
}
