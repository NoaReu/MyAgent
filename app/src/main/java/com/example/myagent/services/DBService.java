package com.example.myagent.services;

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

    public void registerAgent(Agent agent ){
        mAuth.createUserWithEmailAndPassword(agent.getEmail(), agent.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //todo: add on complete to know we succeeded
                if (task.isSuccessful()){
                    FirebaseUser user = task.getResult().getUser();
                   // todo: send verification to know if the user is valid::  user.sendEmailVerification();
                    db.collection(agent.getId()).document(mAuth.getUid()).set(agent).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //TODO: success upload data to db
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //TODO: onFailure::  failure upload data to db
                        }
                    });
                }
                /*FirebaseAuth auth = FirebaseAuth.getInstance();
auth.signInWithEmailAndPassword(email, password)
    .addOnCompleteListener(new OnCompleteListener() {
        @Override
        public void onComplete(Task task) {
            if (task.isSuccessful()) {
                FirebaseUser user = task.getResult().getUser();
                String email = user.getEmail();
                // ...
            }
        }
    });*/
            }
        });
    }




}
