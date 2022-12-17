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

import com.example.myagent.R;
import com.example.myagent.objects.Document;

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
        List<Document> documents = new ArrayList<>();
        documents.add(new Document("066465238","suit_035856038_28_09_22.pdf","חדש", "אפי טלאור","035856038", "gs://myagent-6cce7.appspot.com/066465238/035856038"));
        documents.add(new Document("066465238","suit_053974705_28_09_22.pdf","חדש", "אבי כהן","053974705","gs://myagent-6cce7.appspot.com/066465238/053974705"));



        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new DocAdapter(getContext(),documents));




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
        }

        @Override
        public int getItemCount() {
            return documents.size();
        }
    }

}