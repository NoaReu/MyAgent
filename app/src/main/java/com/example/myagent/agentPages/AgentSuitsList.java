package com.example.myagent.agentPages;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myagent.MainActivity;
import com.example.myagent.R;
import com.example.myagent.objects.Document;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Logging;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
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
//    final long THREE_MEGABYTE = 1024 * 1024 * 3;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    private long downloadId;

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
        Spinner docStatusSpinner;
        TextView customer;

        public DocViewHolder(@NonNull View itemView){
            super(itemView);
            docName= itemView.findViewById(R.id.document_name_at_doc_item);
            docStatusSpinner = (Spinner) itemView.findViewById(R.id.document_status_at_doc_item);
            customer = itemView.findViewById(R.id.customer_id_at_doc_item);
        }
    }

    public class DocAdapter extends RecyclerView.Adapter<DocViewHolder>  {//implements AdapterView.OnItemSelectedListener

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


        @RequiresApi(api = Build.VERSION_CODES.O_MR1)//todo: what??????
        @Override
        public void onBindViewHolder(@NonNull DocViewHolder holder, int position) {

            holder.docName.setText(documents.get(holder.getLayoutPosition()).getDocumentName());
            holder.customer.setText(documents.get(holder.getLayoutPosition()).getUserFullName());


            ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                    .createFromResource(getContext(),R.array.state_of_status, android.R.layout.simple_spinner_item);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            holder.docStatusSpinner.setPrompt(documents.get(holder.getLayoutPosition()).getStatus());
//            spinnerAdapter.setAutofillOptions(documents.get(position).getStatus());//???

            holder.docStatusSpinner.setAdapter(spinnerAdapter);
//            holder.docStatusSpinner.announceForAccessibility(documents.get(holder.getLayoutPosition()).getStatus());

            holder.docStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { // elements of spinner selected!
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    db.collection("documents")
                            .whereEqualTo("agentId", documents.get(holder.getLayoutPosition()).getAgentId())
                            .whereEqualTo("userId", documents.get(holder.getLayoutPosition()).getUserId())
                            .whereEqualTo("documentName", documents.get(holder.getLayoutPosition()).getDocumentName())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if(task.isSuccessful() && task!=null) {
                                String docId ="";
                                for(QueryDocumentSnapshot d : task.getResult()){
                                    docId= d.getId();
                                    break;
                                }
                                db.collection("documents")
                                        .document(docId).update("status",parent.getItemAtPosition(position).toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    Toast.makeText(getContext(), "succeeded "+parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                                                    Log.d("spinnerUpdate", "succeeded");
                                                }else {
                                                    Toast.makeText(getContext(), "failed "+parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                                                    Log.d("spinnerUpdate", "failed");

                                                }
                                            }
                                        });
                            }
                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
//                    String state =  documents.get(holder.getLayoutPosition()).getStatus();
//
//                    spinnerAdapter.setAutofillOptions(state);
//                    holder.docStatusSpinner.setPrompt(documents.get(holder.getLayoutPosition()).getStatus());
                }
            });


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.R)
                @Override
                public void onClick(View v) {
//                    FirebaseStorage storage=FirebaseStorage.getInstance();
//                    StorageReference storageReference = storage.getReference();
//
//                    StorageReference reference = storageReference.child("066465238/035856038/suit_035856038_28_09_22.pdf");

                    ProgressDialog pd = new ProgressDialog(getContext());
                    pd.setTitle(holder.docName.getText().toString());
                    pd.setMessage("המתן בזמן שהקובץ נטען");
                    pd.setIndeterminate(true);
                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pd.show();

//                    File file=new File(getExternalFilesDir(null),"Dummy");



                    File file = new File (Environment.DIRECTORY_DOWNLOADS);

                    //todo: request fo specific file
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myagent-6cce7.appspot.com/o/066465238%2F335477931%2F1673011447889?alt=media&token=569b34b5-f0e7-41b6-a813-4548ed15d7d3");
                    DownloadManager.Request request=null;

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        request=new DownloadManager.Request(uri)
                                .setTitle(holder.docName.getText().toString())
                                .setDescription("Downloading")
                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                .setDestinationUri(Uri.fromFile(file))
                                .setRequiresCharging(false)
                                .setAllowedOverMetered(true)
                                .setAllowedOverRoaming(true);
                    }
                    else{
                        request=new DownloadManager.Request(uri)
                                .setTitle(holder.docName.getText().toString())
                                .setDescription("Downloading")
                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                .setDestinationUri(Uri.fromFile(file))
                                .setAllowedOverRoaming(true);
                    }

//                    DownloadManager.Request request = new DownloadManager.Request(uri);
//                    request.setTitle("My File");
//                    request.setDescription("Downloading");
//                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                    request.setVisibleInDownloadsUi(false);
//                    request.setDestinationUri(Uri.parse("file://" + "stam" + "/myfile.pdf"));

                    DownloadManager downloadManager=(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//                    DownloadManager.COLUMN_STATUS.
                    downloadId=downloadManager.enqueue(request);



//                    try {
//                        final File localFile = File.createTempFile(
//                                prefix(documents.get(holder.getLayoutPosition()).getDocumentName()),
//                                suffix(documents.get(holder.getLayoutPosition()).getDocumentName()));
//                        reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                                Log.e("firebase ", ";local tem file created  created " + localFile.toString());
//                                Toast.makeText(getContext(), "נוצר קובץ מקומי", Toast.LENGTH_LONG).show();
////                                File.
//                                if (!isVisible()){
//                                    Toast.makeText(getContext(), "לא ניתן לצפות במסמך", Toast.LENGTH_LONG).show();
//                                }
//                                if (localFile.canRead()){
//                                    pd.dismiss();
//                                    Toast.makeText(getContext(), "ההורדה הסתיימה בהצלחה", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
//                    } catch (IOException e) {
//                        Log.e("firebase ", ";local temp file not created " + e.toString());
//                        Toast.makeText(getContext(), "לא ניתן לייצר קובץ מקומי", Toast.LENGTH_LONG).show();
//                        e.printStackTrace();
//                    }
//                    final File rootPath = new File(Environment.getStorageDirectory(), "MY AGENT DOWNLOADS");
//                    if (!rootPath.exists()) {
//                        rootPath.mkdirs();
//                        Toast.makeText(getContext(), "נוצרה ספריה להכנסת קובץ", Toast.LENGTH_LONG).show();
//                    }
//                    final File localFile = new File(rootPath, holder.docName.getText().toString());

//                    reference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                        @Override
//                        public void onSuccess(byte[] bytes) {
//                            Log.e("firebase ", ";local tem file created  created " + localFile.toString());
//                            if (!isVisible()){
//                                Toast.makeText(getContext(), "לא ניתן לצפות במסמך", Toast.LENGTH_LONG).show();
//                            }
//                            if (localFile.canRead()){
//                                pd.dismiss();
//                                Toast.makeText(getContext(), "ההורדה הסתיימה בהצלחה", Toast.LENGTH_LONG).show();
//                            }
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            pd.dismiss();
//                            Log.e("firebase ", ";local tem file not created  created " + e.toString());
//                            Toast.makeText(getContext(), "לא בוצעה הורדה. משהו השתבש", Toast.LENGTH_LONG).show();
//                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Toast.makeText(getContext(),e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    });
//                    storageReference.getFile(new File("gs://myagent-6cce7.appspot.com/066465238/035856038/suit_035856038_28_09_22.pdf")).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                            Log.e("firebase ", ";local tem file created  created " + localFile.toString());
//                            if (!isVisible()){
//                                Toast.makeText(getContext(), "ההורדה הסתיימה בהצלחה", Toast.LENGTH_LONG).show();
//                            }
//                            if (localFile.canRead()){
//                                pd.dismiss();
//                            }
//                            Toast.makeText(getContext(), "ההורדה הסתיימה בהצלחה", Toast.LENGTH_LONG).show();
//                        }
//                    }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(@NonNull FileDownloadTask.TaskSnapshot snapshot) {
//                            pd.setTitle(holder.docName.getText().toString());
//                            pd.setMessage("Downloading Please Wait!");
//                            pd.setIndeterminate(true);
//                            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                            pd.show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            pd.dismiss();
//                            Log.e("firebase ", ";local tem file not created  created " + e.toString());
//                            Toast.makeText(getContext(), "לא בוצעה הורדה. משהו השתבש", Toast.LENGTH_LONG).show();
//                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Toast.makeText(getContext(),e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    });
//                   storageReference.getRoot().getFile(new File(reference.child("066465238").child("035856038") + holder.docName.getText().toString()).getAbsoluteFile()).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                       @Override
//                       public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                           Log.e("firebase ", ";local tem file created  created " + localFile.toString());
//                            if (!isVisible()){
//                                Toast.makeText(getContext(), "ההורדה הסתיימה בהצלחה", Toast.LENGTH_LONG).show();
//                            }
//                            if (localFile.canRead()){
//                                pd.dismiss();
//                            }
//                            Toast.makeText(getContext(), "ההורדה הסתיימה בהצלחה", Toast.LENGTH_LONG).show();
//
//                       }
//                   }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
//                       @Override
//                       public void onProgress(@NonNull FileDownloadTask.TaskSnapshot snapshot) {
//
//                       }
//                   }).addOnFailureListener(new OnFailureListener() {
//                       @Override
//                       public void onFailure(@NonNull Exception e) {
//
//                       }
//                   });
//                           .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                            Log.e("firebase ", ";local tem file created  created " + localFile.toString());
//
//
//                            if (!isVisible()){
//                                Toast.makeText(getContext(), "ההורדה הסתיימה בהצלחה", Toast.LENGTH_LONG).show();
//                            }
//
//                            if (localFile.canRead()){
//
//                                pd.dismiss();
//                            }
//                            Toast.makeText(getContext(), "ההורדה הסתיימה בהצלחה", Toast.LENGTH_LONG).show();
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            pd.dismiss();
//                            Log.e("firebase ", ";local tem file not created  created " + exception.toString());
//                            Toast.makeText(getContext(), "לא בוצעה הורדה. משהו השתבש", Toast.LENGTH_LONG).show();
//
//                        }
//                    });
//



//                            storage.getReferenceFromUrl("gs://myagent-6cce7.appspot.com/"
//                                    +documents.get(holder.getLayoutPosition()).getAgentId()+"/"
//                                    +documents.get(holder.getLayoutPosition()).getUserId() +"/"
//                                    +documents.get(holder.getLayoutPosition()).getDocumentName());
//                    reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//
//                            Log.e("firebase ", ";local tem file created  created " + localFile.toString());
//
//
//                            if (!isVisible()){
//                                Toast.makeText(getContext(), "ההורדה הסתיימה בהצלחה", Toast.LENGTH_LONG).show();
//                            }
//
//                            if (localFile.canRead()){
//
//                                pd.dismiss();
//                            }
//                            Toast.makeText(getContext(), "ההורדה הסתיימה בהצלחה", Toast.LENGTH_LONG).show();
//
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            pd.dismiss();
//                            Log.e("firebase ", ";local tem file not created  created " + exception.toString());
//                            Toast.makeText(getContext(), "לא בוצעה הורדה. משהו השתבש", Toast.LENGTH_LONG).show();
//
//                        }
//                    });



//                    reference.getBytes(THREE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                        @Override
//                        public void onSuccess(byte[] bytes) {
//                            try {
//                                File file= File.createTempFile(
//                                            prefix(documents.get(holder.getLayoutPosition()).getDocumentName()),
//                                            suffix(documents.get(holder.getLayoutPosition()).getDocumentName())
//                                    );
//                            File rootPath = new File(Environment.getExternalStorageDirectory(),"MY AGENT DOWNLOADS");
//                            if(!rootPath.exists()) {
//                                rootPath.mkdirs();
//                            }
//                            final File localFile = new File(rootPath,"imageName.txt");
//
//                        }
//                    });

//                    reference.getFile(file).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
//                                        Toast.makeText(getContext(), "file has been downloaded!!!", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }

//                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            Toast.makeText(getContext(), "file has been downloaded!!!", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(getContext(), "file hasn't been downloaded", Toast.LENGTH_SHORT).show();
//                        }
//                    });

//                    reference.getBytes(THREE_MEGABYTE).addOnCompleteListener(new OnCompleteListener<byte[]>() {
//                        @Override
//                        public void onComplete(@NonNull Task<byte[]> task) {
//                            try {
//                                File file= File.createTempFile(
//                                        prefix(documents.get(holder.getLayoutPosition()).getDocumentName()),
//                                        suffix(documents.get(holder.getLayoutPosition()).getDocumentName())
//                                );
//                                reference.getFile(file).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
//                                        Toast.makeText(getContext(), "file has been downloaded!!!", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });


//                    try {
//                        File file= File.createTempFile(
//                                prefix(documents.get(holder.getLayoutPosition()).getDocumentName()),
//                                suffix(documents.get(holder.getLayoutPosition()).getDocumentName())
//                        );
//                        Toast.makeText(getContext(), file.getName(), Toast.LENGTH_SHORT).show();
////                    gs://myagent-6cce7.appspot.com/066465238/035856038/suit_035856038_28_09_22.pdf
//                        reference+
//                                +
//                                +
//                                holder.docName)
//                                .getFile(file)
//                                .addOnCompleteListener(task -> {
////                                    FirebaseApp.initializeApp(getContext());
//                                    if(task.isSuccessful() && task != null) {
////                                        Toast.makeText(getContext(), task.getResult().getTotalByteCount() + "", Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(getContext(), "file downloaded", Toast.LENGTH_SHORT).show();
//                                    }else if (task.isCanceled()){
//                                        Toast.makeText(getContext(), "file hasn't been downloaded", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        Toast.makeText(getContext(), "error trying to create location to file on device", Toast.LENGTH_SHORT).show();
//
//                    }

                }
            });
        }
        private BroadcastReceiver onDownloadComplete=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long id=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
                if(downloadId==id){
                    Toast.makeText(getActivity(), "Download Completed", Toast.LENGTH_SHORT).show();
                }
            }
        };

        private void requestPermission(){
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Toast.makeText(getContext(), "Please Give Permission to Upload File", Toast.LENGTH_SHORT).show();
            }
            else{
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
            }
        }
        private boolean checkPermission(){
            int result= ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(result== PackageManager.PERMISSION_GRANTED){
                return true;
            }
            else{
                return false;
            }
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