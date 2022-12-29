package com.example.myagent.agentPages;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
public class SearchCustomerAtAgent extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<User> items=new ArrayList<>();
    List<User> allItems;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerviewLayoutManager;

    CustomerAdapter adapter;
    EditText nameToSearch;
    FirebaseFirestore db;
    MainActivity mainActivity;
    ImageView searchBTN;
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
        recyclerviewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerviewLayoutManager);

        db = FirebaseFirestore.getInstance();

        loadUsersFromDb(view);
//        nameToSearch.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                loadUsersFromDb(view);
//                return false;
//            }
//        });

        return view;
    }

    private void loadUsersFromDb(View view) {
        items.clear();
        db.collection("users").whereEqualTo("anAgent",false).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<DocumentSnapshot> documents= task.getResult().getDocuments();
                    if(!documents.isEmpty()){
                        for (DocumentSnapshot doc: documents) {
                            items.add(doc.toObject(User.class));
                        }
                        adapter= new CustomerAdapter(items,  new RecyclerViewInterface(){
                            @Override
                            public void onItemClick(User user) {
//                                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
                                mainActivity=(MainActivity) getActivity();
                                mainActivity.switchToUserInfoPage(user);
                            }
                        });
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(adapter);
                        //try to show less items on list by writing person to search
                        nameToSearch=view.findViewById(R.id.customer_name_for_search);
//                        nameToSearch.setOnKeyListener(new View.OnKeyListener() {
//                            @Override
//                            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                                Toast.makeText(getContext(), nameToSearch.getText().toString()+" test", Toast.LENGTH_SHORT).show();
//                                return false;
//                            }
//                        });
                    }else{
                        Toast.makeText(getContext(), "No objects from DB!!!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allItems=new ArrayList<>(items);
        nameToSearch=view.findViewById(R.id.customer_name_for_search);
        searchBTN = view.findViewById(R.id.search_customer_btn);
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!nameToSearch.equals(null) && !nameToSearch.equals("")){
                    for(User user : allItems){
                        String name=user.getFirstName()+" "+user.getLastName();
                        if(!name.contains(nameToSearch.getText().toString())){
                            items.remove(user);
                        }
                    }
                    adapter.usersList.clear();
                    adapter.usersList.addAll(items);
                    adapter.notifyDataSetChanged();
//                    adapter= new CustomerAdapter(items,  new RecyclerViewInterface(){
//                        @Override
//                        public void onItemClick(User user) {
////                                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
//                            mainActivity=(MainActivity) getActivity();
//                            mainActivity.switchToUserInfoPage(user);
//                        }
//                    });

                }else {
                    items=new ArrayList<>(allItems);
//                    adapter= new CustomerAdapter(items,  new RecyclerViewInterface(){
//                        @Override
//                        public void onItemClick(User user) {
////                                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
//                            mainActivity=(MainActivity) getActivity();
//                            mainActivity.switchToUserInfoPage(user);
//                        }
//                    });
                    adapter.usersList.clear();
                    adapter.usersList.addAll(items);
                    adapter.notifyDataSetChanged();

                }
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
                nameToSearch.setText("");
            }
        });
//        nameToSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(!hasFocus){
//
//                }
//            }
//        });
//        nameToSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//
//
//                return false;
//            }
//        });




    }
    /* //experiment
    nameToSearch=view.findViewById(R.id.search_customer_recycler_view);
    if(!nameToSearch.equals(null) && !nameToSearch.equals("")){
        for(User user : allItems){
            String name=user.getFirstName()+" "+user.getLastName();
            if(!name.contains(nameToSearch.getText().toString())){
                items.remove(user);
            }
        }
    }

    */
}