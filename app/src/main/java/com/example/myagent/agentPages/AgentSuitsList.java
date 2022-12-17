package com.example.myagent.agentPages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        List<Document> documents = new ArrayList<>();
        documents.add(new Document("066465238","suit_035856038_28_09_22.pdf","חדש", "אפי טלאור","035856038", "gs://myagent-6cce7.appspot.com/066465238/035856038"));
        documents.add(new Document("066465238","suit_053974705_28_09_22.pdf","חדש", "אבי כהן","053974705","gs://myagent-6cce7.appspot.com/066465238/053974705"));


        return view;
    }
}