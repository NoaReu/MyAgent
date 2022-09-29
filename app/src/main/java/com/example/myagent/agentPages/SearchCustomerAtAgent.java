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
//    CustomerAdapter adapter;
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


//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
////        setHasOptionsMenu(true);
////        MainActivity mainActivity = (MainActivity) getActivity();
////        items =  mainActivity.userForRecyclerView;
////        items =new ArrayList<User>();
////        items.add(new User("אבי","כהן", "111111111","066465238","0501112222","avi@gmail.com","hasahlav 226 Barkan"));
////        items.add(new User("מוטי","לוי", "222222222","066465238","0501112223","moti@gmail.com","haganim 4 Petah Tikva"));
////        items.add(new User("איליה","בוגוסלבסקי", "333333333","066465238","0501112224","ilya@gmail.com","hahoresh 19 Yakir"));
////        items.add(new User("שלי","אורן", "444444444","066465238","0501112225","shely@gmail.com","harimon 8 Nofim"));
////        items.add(new User("גבי","אילוז", "555555555","066465238","0501112226","gabi@gmail.com","hagalil 8 kfar saba"));
////        items.add(new User("אורית","כהן", "666666666","066465238","0501112227","orit@gmail.com","havradim 8 hertzeliya"));
////        items.add(new User("מתן","לוי", "777777777","066465238","0501112228","matan@gmail.com","haharoov 8 Nofim"));
////        items.add(new User("אבי","גבאי", "888888888","066465238","0501112229","avi@gmail.com","gefen 8 hertzeliya"));
//
//
////        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//
//
//        nameToSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if(!b){
//                    view.setOnKeyListener(new View.OnKeyListener() {
//                        @Override
//                        public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                            items=new ArrayList<>(allItems);
//                            if(nameToSearch.getText().toString()=="" || nameToSearch.getText().toString().length()==0){
//                                return false;
//                            }
//                            items=filter(nameToSearch.getText().toString());
//                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                            adapter = new CustomerAdapter(getContext(),items, (RecyclerViewInterface) getContext());
//                            recyclerView.setAdapter(adapter);
//                            return true;
//                        }
//                    });
////                    Toast.makeText(getContext(),"focus is on ", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//
////        nameToSearch.setOnKeyListener(new View.OnKeyListener() {
////            @Override
////            public boolean onKey(View view, int i, KeyEvent keyEvent) {
////                items=new ArrayList<>(allItems);
////                if(nameToSearch.getText().toString()=="" || nameToSearch.getText().toString().length()==0){
////                    return false;
////                }
////                for(User user : allItems){
////                    String fullName = user.getFirstName().trim()+" "+user.getLastName().trim();
////                    if(!fullName.contains(nameToSearch.getText().toString().trim())){
////                        items.remove(user);
////                    }
////                }
////
////                return true;
////            }
////
////        });
////        adapter.getFilter().filter(nameToSearch.getText().toString().trim());
////        adapter = new CustomerAdapter(this.getContext(), items, this);
////        recyclerView.setAdapter(adapter);
//    }



//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        final MenuItem item = menu.findItem(R.id.search_person_item);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
//
//        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                // Do something when collapsed
//                adapter.getFilter().filter(nameToSearch.getText().toString().trim());
//                return true; // Return true to collapse action view
//            }
//
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                // Do something when expanded
//                return true; // Return true to expand action view
//            }
//        });
//
//    }

//    @Override
//    public boolean onQueryTextChange(String newText) {
//         List<User> filteredUsers = filter( nameToSearch.getText().toString().trim());
////        adapter.setFilter(filteredUsers);
////        adapter.getFilter().filter(nameToSearch.getText().toString().trim());
//        adapter=new CustomerAdapter(this.getContext(), filteredUsers, this);
//
//        return true;
//    }

//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        adapter.getFilter().filter(nameToSearch.getText().toString().trim());
//        return false;
//    }







    @Override
    public void onItemClick(DocumentSnapshot snapshot , int position) {

        mainActivity=(MainActivity) getActivity();
        mainActivity.switchToUserInfoPage(snapshot,items.get(position));


    }


}