package com.example.myagent.agentPages;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myagent.MainActivity;
import com.example.myagent.R;
import com.example.myagent.objects.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchCustomerAtAgent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchCustomerAtAgent extends Fragment implements RecyclerViewInterface{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<User> items;
    List<User> allItems;
    RecyclerView recyclerView;

    FirestoreRecyclerAdapter adapter;
    EditText nameToSearch;
    FirebaseFirestore db;
    MainActivity mainActivity;
    Context context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchCustomerAtAgent() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchCustomerAtAgent newInstance(String param1, String param2) {
        SearchCustomerAtAgent fragment = new SearchCustomerAtAgent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_customer_at_agent, container, false);

        recyclerView = view.findViewById(R.id.search_customer_recycler_view);
        db = FirebaseFirestore.getInstance();

        Query query = db.collection("users").whereEqualTo("anAgent", false);
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>().setQuery(query,User.class).build();

        adapter = new FirestoreRecyclerAdapter<User, CustomerViewHolder>(options){

            @NonNull
            @Override
            public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_customer_item_at_agent_search,parent, false);
                return new CustomerViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull CustomerViewHolder holder, int position, @NonNull User model) {

                holder.customerName.setText(model.getFirstName()+" "+model.getLastName());
                holder.image.setImageAlpha(R.drawable.img);
            }


        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private static class CustomerViewHolder extends RecyclerView.ViewHolder{

        TextView customerName;
        ImageView image;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName= itemView.findViewById(R.id.customer_name_at_recyclerview_item);
            image= itemView.findViewById(R.id.customer_image_on_search_customer);
        }
    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot , int position) {

        mainActivity=(MainActivity) getActivity();
        mainActivity.switchToUserInfoPage(snapshot,items.get(position));


    }


}