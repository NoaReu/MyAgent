package com.example.myagent.services;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myagent.objects.Agent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class DBService {
    private static DBService INSTANCE;

    private  FirebaseFirestore db;
    private  FirebaseAuth mAuth;

    public static synchronized DBService getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new DBService();

        }
        return INSTANCE;
    }
    private DBService() {
        this.db = FirebaseFirestore.getInstance();
        this.mAuth=FirebaseAuth.getInstance();
    }






}
