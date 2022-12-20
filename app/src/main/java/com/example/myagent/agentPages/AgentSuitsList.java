package com.example.myagent.agentPages;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myagent.R;
import com.example.myagent.objects.Document;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.FirebaseAppCheckTokenProvider;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgentSuitsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgentSuitsList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    List<Document> documents;


    public AgentSuitsList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgentSuitsList.
     */
    // TODO: Rename and change types and number of parameters
    public static AgentSuitsList newInstance(String param1, String param2) {
        AgentSuitsList fragment = new AgentSuitsList();
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
        View view = inflater.inflate(R.layout.fragment_agent_suits_list, container, false);
        recyclerView = view.findViewById(R.id.new_suits_at_agent_recycler_view);

        db= FirebaseFirestore.getInstance();
        db.collection("documents").whereEqualTo("agentId", "066465238").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                documents= task.getResult().toObjects(Document.class);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new DocAdapter(getContext(),documents));

            }
        });








        return view;
    }


    public class DocViewHolder extends RecyclerView.ViewHolder{

        TextView docName;
        TextView docStatus;


        public DocViewHolder(@NonNull View itemView){
            super(itemView);
            docName= itemView.findViewById(R.id.document_name_at_doc_item);
            docStatus= itemView.findViewById(R.id.document_status_at_doc_item);

        }
    }

    public class DocAdapter extends RecyclerView.Adapter<DocViewHolder>{

        Context context;
        List<Document> documents;

        public DocAdapter(Context context, List<Document> documents){
            this.context=context;
            this.documents=documents;
        }
        @NonNull
        @Override
        public DocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DocViewHolder(LayoutInflater.from(context).inflate(R.layout.document_list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull DocViewHolder holder, int position) {

            holder.docName.setText(documents.get(position).getDocumentName());
            holder.docStatus.setText(documents.get(position).getStatus());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseStorage storage=FirebaseStorage.getInstance();
                    StorageReference reference = storage.getReference();

                    try {
                        File file= File.createTempFile(
                                prefix(documents.get(holder.getLayoutPosition()).getDocumentName()),
                                suffix(documents.get(holder.getLayoutPosition()).getDocumentName())
                        );
                        Toast.makeText(getContext(), file.getName(), Toast.LENGTH_SHORT).show();
                        storage.getReferenceFromUrl("gs://myagent-6cce7.appspot.com/"+
                                documents.get(holder.getLayoutPosition()).getAgentId()+"/"+
                                documents.get(holder.getLayoutPosition()).getUserId()+"/"+
                                holder.docName)
                                .getFile(file)
                                .addOnCompleteListener(task -> {
                                    FirebaseAppCheck appCheck = FirebaseAppCheck.getInstance();
                                    appCheck.installAppCheckProviderFactory(DebugAppCheckProviderFactory.getInstance());
                                    Toast.makeText(getContext(), task.getResult().getTotalByteCount()+"", Toast.LENGTH_SHORT).show();
                                    if(task.isSuccessful()){
                                        Toast.makeText(getContext(), "file downloaded", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "file hasn't been downloaded", Toast.LENGTH_SHORT).show();

                                    }
                                });
//                    gs://myagent-6cce7.appspot.com/066465238/035856038/suit_035856038_28_09_22.pdf
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "error trying to create location to file on device", Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return documents.size();
        }
    }


    public static String prefix(String name){
        return name.substring(0, name.lastIndexOf('.'));
    }
    public static String suffix(String name){
        return name.substring(name.lastIndexOf('.')+1);
    }
}