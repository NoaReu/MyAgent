package com.example.myagent.userPages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myagent.MainActivity;
import com.example.myagent.R;
import com.example.myagent.agentPages.AgentSuitsList;
import com.example.myagent.agentPages.UserDocumentsAdapter;
import com.example.myagent.objects.Document;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDocumentsAtUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDocumentsAtUser extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserDocumentsAtUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserDocumentsAtUser.
     */
    // TODO: Rename and change types and number of parameters
    public static UserDocumentsAtUser newInstance(String param1, String param2) {
        UserDocumentsAtUser fragment = new UserDocumentsAtUser();
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
        View view = inflater.inflate(R.layout.fragment_user_documents_at_user, container, false);
        RecyclerView recyclerView= view.findViewById(R.id.user_documents_at_user_recycler_view);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("documents").whereEqualTo("userId", ((MainActivity)getActivity()).getAppAgent().getUserId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Document> documentList = queryDocumentSnapshots.toObjects(Document.class);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new UserDocumentsAdapter(getContext(), documentList, new RecyclerViewInterfaceItem() {
                    @Override
                    public void onClick(int index) {
                        Document doc = documentList.get(index);
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.switchToLoginPdfPage(doc.getPath());
                    }
                }));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "לא ניתן לגשת למסמכים עבור משתמש זה", Toast.LENGTH_SHORT).show();
            }
        });









        return view;
    }






}