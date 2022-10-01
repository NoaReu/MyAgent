package com.example.myagent.agentPages;

import com.google.firebase.firestore.DocumentSnapshot;

public interface RecyclerViewInterface {

    void onItemClick(DocumentSnapshot snapshot,int position);

}
