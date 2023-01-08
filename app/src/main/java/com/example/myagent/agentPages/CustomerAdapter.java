package com.example.myagent.agentPages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myagent.R;
import com.example.myagent.objects.User;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerHolder> {

    List<User> usersList;
    RecyclerViewInterface recyclerViewInterface;

    public CustomerAdapter(List<User> usersList,  RecyclerViewInterface recyclerViewInterface){
        this.usersList=usersList;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public CustomerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_customer_item_at_agent_search,parent, false);
        return new CustomerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.CustomerHolder holder, int position) {
        User user= usersList.get(position);
        holder.nameTextView.setText(user.getFirstName()+" "+user.getLastName());
        holder.image.setImageAlpha(R.drawable.img);
        holder.bind(usersList.get(position),recyclerViewInterface);
    }



    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class CustomerHolder extends RecyclerView.ViewHolder{
        private TextView nameTextView;
        private ImageView image;

        public CustomerHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView =  (TextView)itemView.findViewById(R.id.customer_name_at_recyclerview_item);
            image= itemView.findViewById(R.id.customer_image_on_search_customer);
        }

        public void bind(User user, RecyclerViewInterface recyclerViewInterface) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewInterface.onItemClick(user);
                }
            });
        }
    }
}
