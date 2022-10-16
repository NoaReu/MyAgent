package com.example.myagent.agentPages;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myagent.MainActivity;
import com.example.myagent.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DownloadDocumentFromAgentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DownloadDocumentFromAgentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseStorage storage;
    FirebaseFirestore database;
    Button chooseDocBtn,uploadDocToStorageBtn;
    TextView notification;
    Uri docUri;
    MainActivity mainActivity;

    public DownloadDocumentFromAgentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DownloadDocumentFromAgent.
     */
    // TODO: Rename and change types and number of parameters
    public static DownloadDocumentFromAgentFragment newInstance(String param1, String param2) {
        DownloadDocumentFromAgentFragment fragment = new DownloadDocumentFromAgentFragment();
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
        View view = inflater.inflate(R.layout.fragment_download_document_from_agent, container, false);
        mainActivity=(MainActivity)getActivity();
        storage = FirebaseStorage.getInstance();
        chooseDocBtn = view.findViewById(R.id.choose_doc_to_upload_btn);
        uploadDocToStorageBtn = view.findViewById(R.id.upload_document_btn);
        notification = view.findViewById(R.id.document_notification);
        chooseDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectPdf();
                }else{
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);

                }
            }
        });

        uploadDocToStorageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(docUri!=null)
                    uploadFile(docUri);
                else
                    Toast.makeText(getContext(), "יש לבחור קובץ", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==9 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }else{
            Toast.makeText(getContext(), "יש לאשר הרשאה לגישה למסמכים", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPdf(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==86 && resultCode==-1 && data!=null){
            docUri = data.getData();
        } else{
            Toast.makeText(getContext(), "יש לבחור מסמך תחילה", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadFile(Uri docUri){
        String fileName = System.currentTimeMillis()+"";
        StorageReference reference = storage.getReference();//return root path
        reference.child(mainActivity.getAppAgent().getAgentId())
                .child(mainActivity.getInfoUser().getUserId())
                .child(fileName)
                .putFile(docUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url=taskSnapshot.getUploadSessionUri().toString();
//                        database.collection("documents").document().
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        });


    }


}