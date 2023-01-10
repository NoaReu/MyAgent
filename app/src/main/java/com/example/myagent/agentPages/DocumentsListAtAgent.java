package com.example.myagent.agentPages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myagent.MainActivity;
import com.example.myagent.R;
import com.example.myagent.objects.Document;
import com.example.myagent.userPages.RecyclerViewInterfaceItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DocumentsListAtAgent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocumentsListAtAgent extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseFirestore db;
    List<Document> documents;
    MainActivity mainActivity;

    public DocumentsListAtAgent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DocumentsListAtAgent.
     */
    // TODO: Rename and change types and number of parameters
    public static DocumentsListAtAgent newInstance(String param1, String param2) {
        DocumentsListAtAgent fragment = new DocumentsListAtAgent();
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
        View view =  inflater.inflate(R.layout.fragment_documents_list_at_agent, container, false);
        mainActivity=(MainActivity) getActivity();

        Button toUploadDocPageBtn= view.findViewById(R.id.upload_doc_page_agent_to_customer);
        toUploadDocPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.switchToAgentDocToCustomer();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.search_document_recycler_view);
        db= FirebaseFirestore.getInstance();
        db.collection("documents").whereEqualTo("userId", mainActivity.getInfoUser().getUserId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                documents= task.getResult().toObjects(Document.class);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new UserDocumentsAdapter(getContext(), documents, new RecyclerViewInterfaceItem() {
                    @Override
                    public void onClick(int index) {
                        Document doc = documents.get(index);
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.switchToLoginPdfPage(doc.getPath());
                    }
                }));

            }
        });


        return view;
    }
}