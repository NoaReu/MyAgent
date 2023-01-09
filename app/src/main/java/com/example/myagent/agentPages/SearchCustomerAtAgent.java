package com.example.myagent.agentPages;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myagent.MainActivity;
import com.example.myagent.R;
import com.example.myagent.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchCustomerAtAgent extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<User> itemsFromFirebase = new ArrayList<>();
    List<User> filterdItems = new ArrayList<>();
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
        return view;
    }

    private void loadUsersFromDb(View view) {
        itemsFromFirebase.clear();
        db.collection("users").whereEqualTo("anAgent", false).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    if (!documents.isEmpty()) {
                        for (DocumentSnapshot doc : documents) {
                            itemsFromFirebase.add(doc.toObject(User.class));
                        }
                        filterList("");
                        adapter = new CustomerAdapter(filterdItems, new RecyclerViewInterface() {
                            @Override
                            public void onItemClick(User user) {
//                                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
                                mainActivity = (MainActivity) getActivity();
                                mainActivity.switchToUserInfoPage(user);
                            }
                        });
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(adapter);
                        //try to show less items on list by writing person to search
                        nameToSearch = view.findViewById(R.id.customer_name_for_search);
                        nameToSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void afterTextChanged(Editable s) {
                                // TODO Auto-generated method stub
                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                // TODO Auto-generated method stub
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                Log.d("App", "text:" + s);
                                filterList(s.toString());
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "No objects from DB!!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void filterList(String text) {
        if (text == null || text == "") {
            filterdItems = new ArrayList<>(itemsFromFirebase);
        } else {
            filterdItems.clear();
            for (User user : itemsFromFirebase) {
                if (user.getFirstName().contains(text) || user.getLastName().contains(text)) {
                    filterdItems.add(user);
                }
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filterdItems = new ArrayList<>(itemsFromFirebase);
        nameToSearch = view.findViewById(R.id.customer_name_for_search);
        searchBTN = view.findViewById(R.id.search_customer_btn);
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!nameToSearch.equals(null) && !nameToSearch.equals("")) {
                    for (User user : filterdItems) {
                        String name = user.getFirstName() + " " + user.getLastName();
                        if (!name.contains(nameToSearch.getText().toString())) {
                            itemsFromFirebase.remove(user);
                        }
                    }
                    adapter.usersList.clear();
                    adapter.usersList.addAll(itemsFromFirebase);
                    adapter.notifyDataSetChanged();
//                    adapter= new CustomerAdapter(items,  new RecyclerViewInterface(){
//                        @Override
//                        public void onItemClick(User user) {
////                                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
//                            mainActivity=(MainActivity) getActivity();
//                            mainActivity.switchToUserInfoPage(user);
//                        }
//                    });

                } else {
                    itemsFromFirebase = new ArrayList<>(filterdItems);
//                    adapter= new CustomerAdapter(items,  new RecyclerViewInterface(){
//                        @Override
//                        public void onItemClick(User user) {
////                                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
//                            mainActivity=(MainActivity) getActivity();
//                            mainActivity.switchToUserInfoPage(user);
//                        }
//                    });
                    adapter.usersList.clear();
                    adapter.usersList.addAll(itemsFromFirebase);
                    adapter.notifyDataSetChanged();

                }
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
                nameToSearch.setText("");
            }
        });
    }

}