package com.example.myagent.agentPages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myagent.MainActivity;
import com.example.myagent.R;
import com.example.myagent.objects.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNewUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNewUserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateNewUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateNewUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateNewUserFragment newInstance(String param1, String param2) {
        CreateNewUserFragment fragment = new CreateNewUserFragment();
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
        View view= inflater.inflate(R.layout.fragment_create_new_user, container, false);
        EditText firstName= (EditText) view.findViewById(R.id.first_name_text_create_user);
        EditText lastName= (EditText) view.findViewById(R.id.last_name_text_create_user);
        EditText userId= (EditText) view.findViewById(R.id.id_number_text_create_user);
        EditText phone= (EditText) view.findViewById(R.id.phone_number_text_create_user);
        EditText email= (EditText) view.findViewById(R.id.mail_address_text_create_user);
        EditText address= (EditText) view.findViewById(R.id.address_text_create_user);
        Button regUserBTN= (Button) view.findViewById(R.id.register_user_btn);

        regUserBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean canBeReg=true;
                if(firstName.getText().toString().length()<2 || !MainActivity.isValidString(firstName.getText().toString())){
                    Toast.makeText(getActivity(), "הכנס שם משתמש תקין", Toast.LENGTH_SHORT).show();
                    firstName.requestFocus();
                    canBeReg=false;
                }else if(lastName.getText().toString().trim().length()<2 || !MainActivity.isValidString(lastName.getText().toString().trim())){
                    Toast.makeText(getActivity(), "הכנס שם משפחה תקין", Toast.LENGTH_SHORT).show();
                    lastName.requestFocus();
                    canBeReg=false;
                }else if(userId.getText().toString().trim().length()!=9 ||!MainActivity.isValidID(userId.getText().toString().trim())){
                    Toast.makeText(getActivity(), "הכנס מספר תעודת זהות תקין בעל 9 ספרות", Toast.LENGTH_SHORT).show();
                    userId.requestFocus();
                    canBeReg=false;
                }else if(phone.getText().toString().trim().length()<10 || !phone.getText().toString().trim().startsWith("05") || !MainActivity.isValidPhone(phone.getText().toString().trim())){
                    Toast.makeText(getActivity(), "הכנס מספר נייד תקין", Toast.LENGTH_SHORT).show();
                    phone.requestFocus();
                    canBeReg=false;
                }else if(!MainActivity.isValidEmail(email.getText().toString().trim())){
                    Toast.makeText(getActivity(), "הכנס כתובת מייל תקינה", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                    canBeReg=false;
                }else if(address.getText().toString().trim().length()<4 || !MainActivity.isValidString(address.getText().toString().trim())){
                    Toast.makeText(getActivity(), "הכנס כתובת בית תקינה", Toast.LENGTH_SHORT).show();
                    address.requestFocus();
                    canBeReg=false;
                }
                if(canBeReg){
                    MainActivity mainActivity=(MainActivity) getActivity();
                    User user= new User(firstName.getText().toString(),
                            lastName.getText().toString(),
                            userId.getText().toString(),
                            mainActivity.getAppAgent().getAgentId(),
                            phone.getText().toString(),
                            email.getText().toString(),
                            address.getText().toString());
                    mainActivity.createNewUserForAgent(user);

                }



            }
        });






        return view;
    }
}