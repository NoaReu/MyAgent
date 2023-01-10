package com.example.myagent.agentPages;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.SharedValues;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myagent.EntrancePageFragment;
import com.example.myagent.MainActivity;
import com.example.myagent.R;
import com.example.myagent.objects.Document;
import com.example.myagent.userPages.PdfViewer;
import com.example.myagent.userPages.RecyclerViewInterfaceItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.DocumentFragment;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class UserDocumentsAdapter extends RecyclerView.Adapter<UserDocumentsAdapter.UserDocumentHolder>{

    Context context;
    List<Document> documents;
    RecyclerViewInterfaceItem recyclerViewInterface;
    public UserDocumentsAdapter(Context context, List<Document> documents, RecyclerViewInterfaceItem recyclerViewInterface){
        this.context=context;
        this.documents=documents;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public UserDocumentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserDocumentHolder(LayoutInflater.from(context).inflate(R.layout.document_item_at_user_documents_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserDocumentHolder holder, int position) {

        holder.docName.setText(documents.get(position).getDocumentName());
        final int pos = position;
        holder.docName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("App","das");
                recyclerViewInterface.onClick(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    public static class UserDocumentHolder extends RecyclerView.ViewHolder{
        private static final int STORAGE_PERMISSION_CODE = 100;

        private static final String TAG = "PERMISSION_TAG";

        TextView docName;

        public UserDocumentHolder(@NonNull View itemView) {
            super(itemView);
            docName= itemView.findViewById(R.id.document_name_at_doc_item_at_agent);

        }

    }

}
