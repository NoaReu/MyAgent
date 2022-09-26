package com.example.myagent.agentPages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myagent.MainActivity;
import com.example.myagent.R;
import com.example.myagent.objects.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchCustomerAtAgent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchCustomerAtAgent extends Fragment implements RecyclerViewInterface, SearchView.OnQueryTextListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<User> items;
    List<User> allItems;
    RecyclerView recyclerView;
    CustomerAdapter adapter;
    EditText nameToSearch;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchCustomerAtAgent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchCustomerAtAgent.
     */
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
        //get all agents users from DB and show them as list by recycler view

        recyclerView= (RecyclerView) view.findViewById(R.id.search_customer_recycler_view);

        nameToSearch=(EditText) view.findViewById(R.id.customer_name_for_search);
//        MainActivity mainActivity = (MainActivity) getActivity();
//        assert mainActivity != null;
//        mainActivity.getAllCustomersForAgent();

        //items = new ArrayList<>(mainActivity.userForRecyclerView);
        Toast.makeText(this.getActivity(),"המידע הוחזר מהמחלקה הראשית!", Toast.LENGTH_SHORT ).show();

//        allItems= new ArrayList<>(mainActivity.userForRecyclerView);


//        nameToSearch.setOnQueryTextListener(new )



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        setHasOptionsMenu(true);
//        MainActivity mainActivity = (MainActivity) getActivity();
//        items =  mainActivity.userForRecyclerView;
        items =new ArrayList<User>();
        items.add(new User("אבי","כהן", "111111111","066465238","0501112222","avi@gmail.com","hasahlav 226 Barkan"));
        items.add(new User("מוטי","לוי", "222222222","066465238","0501112223","moti@gmail.com","haganim 4 Petah Tikva"));
        items.add(new User("איליה","בוגוסלבסקי", "333333333","066465238","0501112224","ilya@gmail.com","hahoresh 19 Yakir"));
        items.add(new User("שלי","אורן", "444444444","066465238","0501112225","shely@gmail.com","harimon 8 Nofim"));
        items.add(new User("גבי","אילוז", "555555555","066465238","0501112226","gabi@gmail.com","hagalil 8 kfar saba"));
        items.add(new User("אורית","כהן", "666666666","066465238","0501112227","orit@gmail.com","havradim 8 hertzeliya"));
        items.add(new User("מתן","לוי", "777777777","066465238","0501112228","matan@gmail.com","haharoov 8 Nofim"));
        items.add(new User("אבי","גבאי", "888888888","066465238","0501112229","avi@gmail.com","gefen 8 hertzeliya"));
        allItems=new ArrayList<>(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerView.setAdapter(new CustomerAdapter(getContext(),items,this));
        nameToSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    view.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View view, int i, KeyEvent keyEvent) {
                            items=new ArrayList<>(allItems);
                            if(nameToSearch.getText().toString()=="" || nameToSearch.getText().toString().length()==0){
                                return false;
                            }
                            for(User user : allItems){
                                String fullName = user.getFirstName().trim()+" "+user.getLastName().trim();
                                if(!fullName.contains(nameToSearch.getText().toString().trim())){
                                    items.remove(user);
                                }
                            }
                            adapter=new CustomerAdapter(getContext(), items, (RecyclerViewInterface) recyclerView );
                            return true;
                        }
                    });
//                    Toast.makeText(getContext(),"focus is on ", Toast.LENGTH_SHORT).show();
                }

            }
        });


//        nameToSearch.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                items=new ArrayList<>(allItems);
//                if(nameToSearch.getText().toString()=="" || nameToSearch.getText().toString().length()==0){
//                    return false;
//                }
//                for(User user : allItems){
//                    String fullName = user.getFirstName().trim()+" "+user.getLastName().trim();
//                    if(!fullName.contains(nameToSearch.getText().toString().trim())){
//                        items.remove(user);
//                    }
//                }
//
//                return true;
//            }
//
//        });
//        adapter.getFilter().filter(nameToSearch.getText().toString().trim());
        adapter = new CustomerAdapter(this.getContext(), items, this);
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        final MenuItem item = menu.findItem(R.id.search_person_item);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                adapter.getFilter().filter(nameToSearch.getText().toString().trim());
                return true; // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true; // Return true to expand action view
            }
        });

    }

    @Override
    public boolean onQueryTextChange(String newText) {
         List<User> filteredUsers = filter( nameToSearch.getText().toString().trim());
//        adapter.setFilter(filteredUsers);
//        adapter.getFilter().filter(nameToSearch.getText().toString().trim());
        adapter=new CustomerAdapter(this.getContext(), filteredUsers, this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(nameToSearch.getText().toString().trim());
        return false;
    }

    private List<User> filter( String query) {
        query = query.toLowerCase();

         List<User> filteredUsers = new ArrayList<>();
        for (User user : items) {
             String text = (user.getFirstName().toLowerCase()+" "+user.getLastName()).toLowerCase();
            if (text.contains(query)) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }



    @Override
    public void onItemClick(int position) {

        MainActivity mainActivity=(MainActivity) getActivity();
        mainActivity.switchToUserInfoPage(items.get(position));

    }
}