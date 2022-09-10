package com.example.myagent.agentPages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myagent.MainActivity;
import com.example.myagent.R;
import com.example.myagent.objects.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgentRegistration#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgentRegistration extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AgentRegistration() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgentRegistration.
     */
    // TODO: Rename and change types and number of parameters
    public static AgentRegistration newInstance(String param1, String param2) {
        AgentRegistration fragment = new AgentRegistration();
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
        View view= inflater.inflate(R.layout.fragment_agent_registration, container, false);
        Button regBtn = (Button) view.findViewById(R.id.confirm_agent_info_btn);
        TextView privateName= (TextView)  view.findViewById(R.id.reg_agent_private_name_textview);
        TextView lastName= (TextView)  view.findViewById(R.id.reg_agent_last_name_textview);
        TextView agentId= (TextView)  view.findViewById(R.id.reg_agent_id_textview);
        TextView phone= (TextView)  view.findViewById(R.id.reg_agent_phone_number_textview);
        TextView email= (TextView)  view.findViewById(R.id.reg_agent_email_textview);
        TextView pw= (TextView)  view.findViewById(R.id.reg_agent_password_textview);
        TextView confPw= (TextView)  view.findViewById(R.id.reg_agent_confirmation_password_textview);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean canWeGoToDBRegistration=true;
                if(privateName.getText().toString().length()<2 || MainActivity.isValidString(privateName.getText().toString())) {
                    Toast.makeText(getActivity(), "שם פרטי לא תקין", Toast.LENGTH_SHORT).show();
                    privateName.requestFocus();
                    canWeGoToDBRegistration=false;
                }
                else if(lastName.getText().toString().length()<2 || MainActivity.isValidString(lastName.getText().toString())) {
                    Toast.makeText(getActivity(), "שם משפחה לא תקין", Toast.LENGTH_SHORT).show();
                    lastName.requestFocus();
                    canWeGoToDBRegistration=false;
                }
                else if(agentId.getText().toString().length()!=9 || !MainActivity.isValidID(agentId.getText().toString())){
                    Toast.makeText(getActivity(), "תעודת זהות לא תקינה יש להזין 9 ספרות", Toast.LENGTH_SHORT).show();
                    agentId.requestFocus();
                    canWeGoToDBRegistration=false;
                }
                else if( !MainActivity.isValidPhone(phone.getText().toString())){
                    Toast.makeText(getActivity(), "מספר טלפון לא תקין", Toast.LENGTH_SHORT).show();
                    phone.requestFocus();
                    canWeGoToDBRegistration=false;
                }
                //todo: email Validation!!!! - search for function
                else if(!MainActivity.isValidEmail(email.getText().toString().trim())){
                    Toast.makeText(getActivity(), "כתובת המייל אינה תקינה", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                    canWeGoToDBRegistration=false;
                }
                else if(pw.getText().toString().length()<8 || !MainActivity.isValidPW(pw.getText().toString()) ||
                        !confPw.getText().toString().equals(pw.getText().toString())){
                    pw.requestFocus();
                    canWeGoToDBRegistration=false;
                    Toast.makeText(getActivity(), "הסיסמה או אימות הסיסמה אינם תקינים", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "סיסמה חייבת להיות בת 8 ספרות ולהכיל אותיות לועזיות קטנות וגדולות, מספרים ותוים מיוחדים !#$%&@*?", Toast.LENGTH_SHORT).show();
                }
               if(canWeGoToDBRegistration){
                   User agent=new User(
                           privateName.getText().toString(),
                           lastName.getText().toString(),
                           agentId.getText().toString(),
                           phone.getText().toString());

                   MainActivity mainActivity=(MainActivity) getActivity();
                   mainActivity.registerAgent(agent, email.getText().toString() ,pw.getText().toString());
               }
            }
        });


        return view;
    }
}