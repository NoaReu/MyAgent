package com.example.myagent.agentPages;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myagent.R;
import com.example.myagent.objects.Document;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.DocumentFragment;

import java.util.List;

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
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(Uri.parse(snapshot.getString("path")), "application/pdf");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    itemView.getContext().startActivity(intent);
                                } catch (Exception e) {
                                    Toast.makeText(itemView.getContext(), "המסמך לא הצליח להיטען" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    Toast.makeText(itemView.getContext(), "המסמך לא הצליח להיטען" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    Toast.makeText(itemView.getContext(), "המסמך לא הצליח להיטען" + e.getMessage(), Toast.LENGTH_LONG).show();

                                }
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
    }
}
