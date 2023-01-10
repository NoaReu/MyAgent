package com.example.myagent.agentPages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myagent.EntrancePageFragment;
import com.example.myagent.MainActivity;
import com.example.myagent.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageAgentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageAgentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePageAgentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageAgentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageAgentFragment newInstance(String param1, String param2) {
        HomePageAgentFragment fragment = new HomePageAgentFragment();
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
    ProgressBar spinner1;
    ProgressBar spinner2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_page_agent, container, false);
        spinner1 = (ProgressBar) view.findViewById(R.id.spinner_suit_list_at_agent);
        spinner2 = (ProgressBar) view.findViewById(R.id.spinner_SearchCustomerAtAgent);
        Button createUser= (Button) view.findViewById(R.id.createUser_HOME_PAGE_AGENT);
        Button searchUser= (Button) view.findViewById(R.id.searchUser_HOME_PAGE_AGENT);
//        Button insurances= (Button) view.findViewById(R.id.insurances_HOME_PAGE_AGENT);
        Button suits= (Button) view.findViewById(R.id.Suits_HOME_PAGE_AGENT);
        Button exit = (Button) view.findViewById(R.id.BTN_logoutAgentHomePage);
        TextView name = (TextView) view.findViewById((R.id.WelcomeAgentName));
        MainActivity mainActivity = (MainActivity) getActivity();
        name.setText(" " +mainActivity.getAppAgent().getFirstName()+" "+mainActivity.getAppAgent().getLastName());

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                FragmentTransaction fragmentTransaction = ( ((MainActivity)getActivity()).getSupportFragmentManager()).beginTransaction();

                fragmentTransaction.add(R.id.main_activity, new EntrancePageFragment()).addToBackStack(null).commit();
//                FirebaseFirestore.getInstance().terminate();
//                FirebaseFirestore.getInstance().terminate();

            }
        });
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity= (MainActivity) getActivity();
                mainActivity.switchToCreateUserPage();
            }
        });

        searchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity= (MainActivity) getActivity();
                mainActivity.switchToSearchUserPage();
            }
        });
//        insurances.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MainActivity mainActivity= (MainActivity) getActivity();
//                mainActivity.switchToInsurancesPage();
//            }
//        });
        suits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity mainActivity= (MainActivity) getActivity();
                mainActivity.switchToSuitsListPage();

            }

        });

        return view;

    }

}