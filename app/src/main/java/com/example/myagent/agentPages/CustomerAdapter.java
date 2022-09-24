package com.example.myagent.agentPages;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> implements Filterable {

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

        holder.customerName.setText(users.get(position).getFirstName()+" "+users.get(position).getLastName());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter= new Filter() {
        @SuppressLint("ResourceType")
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<User> filteredList = new ArrayList<>();
//            charSequence=nameToSearch.getText().toString().trim();
            if(charSequence == null || charSequence.length()==0){
                filteredList.addAll(usersFull);
            }else{
                String filteredPattern =charSequence.toString().toLowerCase().trim();
                for(User user : usersFull){
                    String fullName=user.getFirstName()+" "+user.getLastName();
                    if(fullName.toLowerCase().contains(filteredPattern))
                        filteredList.add(user);
                }
            }
            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            users.clear();
            users.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };



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


}
