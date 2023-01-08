
package com.example.myagent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myagent.agentPages.HomePageAgentFragment;
import com.example.myagent.userPages.UserHomePageFragment;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntrancePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntrancePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EntrancePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EntrancePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EntrancePageFragment newInstance(String param1, String param2) {
        EntrancePageFragment fragment = new EntrancePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
//            if(((MainActivity)getActivity()).anAget){
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.main_activity, new HomePageAgentFragment()).addToBackStack(null).commit();
//            }else{
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.main_activity, new UserHomePageFragment()).addToBackStack(null).commit();
//            }
//        }
//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//            FirebaseAuth.getInstance().getCurrentUser().reload();
//            ((MainActivity)getActivity()).switchToUserHomePage();
//        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_entrance_page, container, false);
        Button regBtn= (Button) view.findViewById(R.id.RegistrationBtn);
        Button connectBtn= (Button) view.findViewById(R.id.ConnectionBtn);
        Button forgotPWBtn= (Button) view.findViewById(R.id.forgotMyPassBtnEntrancePage);
        MainActivity mainActivity=(MainActivity) getActivity();
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.switchToRegistration();
            }
        });
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.switchToLoginPage();
            }
        });

        return view;
    }
}