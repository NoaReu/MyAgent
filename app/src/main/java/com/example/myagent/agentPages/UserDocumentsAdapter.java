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

    public UserDocumentsAdapter(Context context, List<Document> documents){
        this.context=context;
        this.documents=documents;
    }

    @NonNull
    @Override
    public UserDocumentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserDocumentHolder(LayoutInflater.from(context).inflate(R.layout.document_item_at_user_documents_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserDocumentHolder holder, int position) {

        holder.docName.setText(documents.get(position).getDocumentName());
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
            docName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("documents").document(docName.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot snapshot) {

                            if(snapshot!=null) {
                                Document d = snapshot.toObject(Document.class);

                                    String format = "https://drive.google.com/viewerng/viewer?embedded=true&url=%s";
                                    String fullPath = String.format(Locale.ENGLISH, format, d.getPath());




                            }else{
                                Toast.makeText(itemView.getContext(), "אין מסמכים לתצוגה", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(itemView.getContext(), "לא ניתן לפנות אל המסמך", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
/*
        public boolean checkPermission(){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                //Android is 11(R) or above
                return Environment.isExternalStorageManager();
            }
            else{
                //Android is below 11(R)
                int write = ContextCompat.checkSelfPermission(itemView.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int read = ContextCompat.checkSelfPermission(itemView.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

                return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
            }
        }

        private void createFolder(){
            //get folder name
            String folderName = "MyAgentDownloads";

            //create folder using name we just input
            File file = new File(Environment.getExternalStorageDirectory() + "/" + folderName);
            //create folder
            boolean folderCreated = file.mkdir();

            //show if folder created or not
            if (folderCreated) {
                Toast.makeText(itemView.getContext(), "Folder Created....\n" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(itemView.getContext(), "Folder not created...", Toast.LENGTH_SHORT).show();
            }

        }
        private void requestPermission(){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                //Android is 11(R) or above
                try {
                    Log.d(TAG, "requestPermission: try");

                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                    intent.setData(uri);
                    storageActivityResultLauncher.launch(intent);
                }
                catch (Exception e){
                    Log.e(TAG, "requestPermission: catch", e);
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    storageActivityResultLauncher.launch(intent);
                }
            }
            else {
                //Android is below 11(R)
                ActivityCompat.requestPermissions(
                        this.,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_CODE
                );
            }
        }

        private ActivityResultLauncher<Intent> storageActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d(TAG, "onActivityResult: ");
                        //here we will handle the result of our intent
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                            //Android is 11(R) or above
                            if (Environment.isExternalStorageManager()){
                                //Manage External Storage Permission is granted
                                Log.d(TAG, "onActivityResult: Manage External Storage Permission is granted");
                                createFolder();
                            }
                            else{
                                //Manage External Storage Permission is denied
                                Log.d(TAG, "onActivityResult: Manage External Storage Permission is denied");
                                Toast.makeText(this, "Manage External Storage Permission is denied", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            //Android is below 11(R)
                        }
                    }
                }
        );

*/


    }
}
