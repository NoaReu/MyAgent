package com.example.myagent.agentPages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class SearchCustomerAtAgent extends Fragment implements RecyclerViewInterface{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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

        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.search_customer_recycler_view);

        MainActivity mainActivity = (MainActivity) getActivity();
//        List<User> items =  mainActivity.getAllCustomersForAgent();
        ArrayList<User> items =new ArrayList<User>();
        items.add(new User("אבי","כהן", "111111111","066465238","0501112222","avi@gmail.com","hasahlav 226 Barkan"));
        items.add(new User("מוטי","לוי", "222222222","066465238","0501112223","moti@gmail.com","hasahlav 226 Barkan"));
        items.add(new User("איליה","בוגוסלבסקי", "333333333","066465238","0501112224","ilya@gmail.com","hasahlav 226 Barkan"));
        items.add(new User("שלי","אורן", "444444444","066465238","0501112225","shely@gmail.com","hasahlav 226 Barkan"));


        CustomerAdapter adapter = new CustomerAdapter(this.getContext(), items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        recyclerView.setAdapter(new CustomerAdapter(getContext(),items));


        return view;
    }


    @Override
    public void onItemClick(int position) {

    }
}