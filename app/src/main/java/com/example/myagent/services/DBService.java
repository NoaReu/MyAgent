package com.example.myagent.services;

import com.google.firebase.firestore.FirebaseFirestore;

public class DBService {
    private static DBService INSTANCE;

    private FirebaseFirestore db;

    public static synchronized DBService getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new DBService();
        }
        return INSTANCE;
    }
    private DBService() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void getDataForAgent(String DBType ){

    }




}
