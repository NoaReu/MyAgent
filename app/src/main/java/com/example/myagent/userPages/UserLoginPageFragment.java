package com.example.myagent.userPages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myagent.MainActivity;
import com.example.myagent.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserLoginPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserLoginPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserLoginPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserLoginPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserLoginPageFragment newInstance(String param1, String param2) {
        UserLoginPageFragment fragment = new UserLoginPageFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_login_page, container, false);
        spinner = (ProgressBar) view.findViewById(R.id.spinner);
        TextView email = view.findViewById(R.id.usernameUserForgotPassword);
        TextView pW = view.findViewById(R.id.PasswordUSERLOGIN);
        Button confirmBtn = (Button) view.findViewById(R.id.loginBtnForgotPassword);
        Button forgotPW = (Button) view.findViewById(R.id.forgotMyPassBtnEntrancePage);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                boolean canBeCheckedInDB = true;
                if (email.getText().toString().trim().equals("") || !MainActivity.isValidEmail(email.getText().toString().trim()) ||
                        pW.getText().toString().trim().equals("") || !MainActivity.isValidPW(pW.getText().toString().trim())) {
                    canBeCheckedInDB = false;
                    Toast.makeText(getActivity(), "שם משתמש ו/או סיסמה אינם תקינים", Toast.LENGTH_SHORT).show();
                }
                if (canBeCheckedInDB) {
                    spinner.setVisibility(View.VISIBLE);
                    mainActivity.login(email.getText().toString().trim(), pW.getText().toString().trim(), new MainActivity.LoginResponse() {
                        @Override
                        public void response(boolean success, String message) {
                            if (!success) {
                                Toast.makeText(getContext(), "you failed to login", Toast.LENGTH_LONG).show();
                            }
                            spinner.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
        forgotPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.switchToForgotPasswordPage();
            }
        });

        return view;
    }
}