package com.example.myagent.agentPages;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SuitsAtAgentAdapter extends RecyclerView.Adapter<SuitsAtAgentAdapter.SuitViewHolder>{

    @NonNull
    @Override
    public SuitsAtAgentAdapter.SuitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SuitsAtAgentAdapter.SuitViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class SuitViewHolder extends RecyclerView.ViewHolder{

        public SuitViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
