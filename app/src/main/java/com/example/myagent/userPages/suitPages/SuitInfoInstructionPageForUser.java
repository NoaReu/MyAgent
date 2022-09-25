package com.example.myagent.userPages.suitPages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myagent.MainActivity;
import com.example.myagent.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuitInfoInstructionPageForUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuitInfoInstructionPageForUser extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SuitInfoInstructionPageForUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuitInfoInstructionPageForUser.
     */
    // TODO: Rename and change types and number of parameters
    public static SuitInfoInstructionPageForUser newInstance(String param1, String param2) {
        SuitInfoInstructionPageForUser fragment = new SuitInfoInstructionPageForUser();
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
        View view= inflater.inflate(R.layout.fragment_suit_info_instruction_page_for_user, container, false);
        Button continueBTN= (Button) view.findViewById(R.id.continue_to_suit_btn);
        continueBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.switchToSuitPage2();
            }
        });


        return view;
    }
}