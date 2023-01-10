package com.example.myagent.userPages;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.google.firebase.storage.FirebaseStorage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserHomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHomePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserHomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserHomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserHomePageFragment newInstance(String param1, String param2) {
        UserHomePageFragment fragment = new UserHomePageFragment();
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
    ProgressBar spinner;
    ProgressBar spinner2;//not used!!
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_home_page, container, false);
        spinner = (ProgressBar) view.findViewById(R.id.spinner_suit_list_at_agent);
        spinner2 = (ProgressBar) view.findViewById(R.id.spinner_UserDocumentsAtUser);
        Button toDocuments = view.findViewById(R.id.user_home_page_to_documents_btn);
        Button toSuit = (Button) view.findViewById(R.id.user_home_page_to_new_suit_btn);
        TextView helloUser = view.findViewById(R.id.WelcomeUserName);

        MainActivity mainActivity = (MainActivity) getActivity();
        String name= mainActivity.getAppAgent().getFirstName()+" "+mainActivity.getAppAgent().getLastName();
        helloUser.setText(name);


        Button exit = (Button) view.findViewById(R.id.BTN_logoutUserHomePage);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = ( mainActivity.getSupportFragmentManager()).beginTransaction();
                fragmentTransaction.add(R.id.main_activity, new EntrancePageFragment()).addToBackStack(null).commit();




            }
        });


        toDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.switchToUserDocumentsList();
            }
        });
        toSuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.switchToSuitPage1();
            }
        });



        return view;
    }
}