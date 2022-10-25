package com.example.myagent.agentPages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myagent.MainActivity;
import com.example.myagent.R;
import com.example.myagent.objects.User;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerInfoAtAgent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerInfoAtAgent extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;

    public CustomerInfoAtAgent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerInfoAtAgent.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerInfoAtAgent newInstance(String param1, String param2) {
        CustomerInfoAtAgent fragment = new CustomerInfoAtAgent();
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
        View view= inflater.inflate(R.layout.fragment_customer_info_at_agent, container, false);
        firebaseDatabase= FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        


        MainActivity mainActivity=(MainActivity)getActivity();
        assert mainActivity != null;
        User infoUser=mainActivity.getInfoUser();
        EditText firstName = view.findViewById(R.id.private_name_customer_info_at_agent);
        firstName.setText(infoUser.getFirstName());
        EditText lastName = view.findViewById(R.id.last_name_customer_info_at_agent);
        lastName.setText(infoUser.getLastName());
        EditText userId = view.findViewById(R.id.id_number_customer_info_at_agent);
        userId.setText(infoUser.getUserId());
        EditText phone = view.findViewById(R.id.phone_number_customer_info_at_agent);
        phone.setText(infoUser.getPhone());
        EditText email = view.findViewById(R.id.mail_address_customer_info_at_agent);
        email.setText(infoUser.getEmail());
        EditText address = view.findViewById(R.id.address_customer_info_at_agent);
        address.setText(infoUser.getAddress());
        Button update= view.findViewById(R.id.update_customer_info);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean canUpdate=true;
                if(firstName.getText().toString().length()<2 || MainActivity.isValidName(firstName.getText().toString())) {
                    Toast.makeText(getActivity(), "שם פרטי לא תקין", Toast.LENGTH_SHORT).show();
                    canUpdate=false;
                    firstName.requestFocus();
                }else if(lastName.getText().toString().length()<2 || MainActivity.isValidName(lastName.getText().toString())){
                    Toast.makeText(getActivity(),"שם משפחה לא תקין",Toast.LENGTH_SHORT).show();
                    canUpdate=false;
                    lastName.requestFocus();
                }else if(!MainActivity.isValidPhone(phone.getText().toString())){
                    Toast.makeText(getActivity(),"מספר טלפון לא תקין",Toast.LENGTH_SHORT).show();
                    canUpdate=false;
                    phone.requestFocus();
                }else if(!MainActivity.isValidAddress(address.getText().toString()) || address.getText().toString().trim().split(" ").length<3){
                    Toast.makeText(getActivity(),"כתובת לא תקינה",Toast.LENGTH_SHORT).show();
                    canUpdate=false;
                    address.requestFocus();
                }
                if(canUpdate) {
                    infoUser.setFirstName(firstName.getText().toString().trim());
                    infoUser.setLastName(lastName.getText().toString().trim());
                    infoUser.setPhone(phone.getText().toString().trim());
                    infoUser.setAddress(address.getText().toString().trim());
                    mainActivity.setInfoUser(infoUser);
                    mainActivity.updateUserInfoAtDB();
                    Toast.makeText(getActivity(),"המידע עודכן במערכת", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button goToDocuments = view.findViewById(R.id.go_to_customer_documents_btn);
        goToDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.switchToCustomersDocumentsPage();
            }
        });

        return view;
    }
}