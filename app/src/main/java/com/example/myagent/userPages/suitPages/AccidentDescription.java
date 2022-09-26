package com.example.myagent.userPages.suitPages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myagent.MainActivity;
import com.example.myagent.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccidentDescription#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccidentDescription extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccidentDescription() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccidentDescription.
     */
    // TODO: Rename and change types and number of parameters
    public static AccidentDescription newInstance(String param1, String param2) {
        AccidentDescription fragment = new AccidentDescription();
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
        View view = inflater.inflate(R.layout.fragment_accident_description, container, false);
        MainActivity mainActivity = (MainActivity)getActivity();
        Button goToNextPage= view.findViewById(R.id.continue_to_suit3_btn);
        RadioGroup radioGroup=view.findViewById(R.id.choose_who_drove_group);



        goToNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if another driver is involved. if false- submit the information
                //else- go to page of driver info for more information
                int checkId = radioGroup.getCheckedRadioButtonId();
                if(checkId==-1){
                    Toast.makeText(getActivity(), "יש לציין האם נהג אחר נהג בשעת התאונה", Toast.LENGTH_SHORT).show();
                }else if(checkId==R.id.radio_who_drove1){
//                    mainActivity.switchToSuitPage4();
                }else if(checkId==R.id.radio_who_drove2) {

//                    mainActivity.switchToSuitDriverPage();
                }
            }
        });

        return view ;
    }
}