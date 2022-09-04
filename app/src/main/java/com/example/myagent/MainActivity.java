package com.example.myagent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.example.myagent.agentPages.AgentRegistration;
import com.example.myagent.agentPages.AgentSuitsList;
import com.example.myagent.agentPages.CreateNewUserFragment;
import com.example.myagent.objects.Agent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private String sharedPrefFile ="sharedPreferences";
    private FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    FirebaseFirestore db;
    String agentUID;

    static String validNumbers="1234567890";
    static String validCapital="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String validLetters="'אבגדהוזחטיכךלמםנןסעפףצץקרשתabcdefghijklmnopqrstuvw\"xyz";
    static String validSpecial="!#$%&@*?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        agentUID="";

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_activity, new AgentRegistration()).commit();


    }

    public void registerAgent(Agent agent , String email, String pw){

       //todo: check if agent is already exist
        mAuth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "success to register", Toast.LENGTH_SHORT).show();
                    agentUID=task.getResult().getUser().getUid();
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    firebaseFirestore.collection("agents").document(mAuth.getUid()).set(agent).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MainActivity.this,"success upload data to db",Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,"failed upload data to db",Toast.LENGTH_LONG).show();

                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "failed to register", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean isValidEmail(String email){
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        return false;
    }

    public static boolean isValidString(String string){
        for(int i=0; i<string.length(); i++){
            if(!validCapital.contains(string.charAt(i)+"") && !validLetters.contains(string.charAt(i)+"") && string.charAt(i)!='-')
               Log.e("validation", string.charAt(i)+"");
                return false;
        }
        return true;
    }

    public static boolean isValidID(String id){
        int sum=0;
        boolean isEven=true;
        for (int i = 0; i < id.length(); i++) {
            if(!validNumbers.contains(id.charAt(i)+""))return false;
            int dig=Integer.parseInt(id.charAt(i)+"");
            if(isEven)sum+=dig;
            else{
                int temp=dig*2;
                if(temp<=9) sum+=temp;
                else sum+=1+temp%10;
            }
        }
        if(sum%10==0) return true;
        return false;
    }

    public static boolean isValidPhone(String string) {
        if(string.length()>13)return false;
        if(string.contains("-")) string=string.replaceAll("-","");
        for(int i=0; i<string.length(); i++){
            if(!validNumbers.contains(string.charAt(i)+""))
                return false;
        }
        return true;
    }
    public static boolean isValidPW(String string) {
        string=string.trim();
        if(string.length()<8) return false;
        boolean capital=false;
        boolean lowLetter=false;
        boolean number=false;
        boolean special=false;
        for (int i = 0; i < string.length(); i++) {
            boolean isValidChar=false;
            if(validCapital.contains(string.charAt(i)+"")){
                capital=true;
                isValidChar=true;
                Log.d("validation", "capital");
            }
            if(validLetters.contains(string.charAt(i)+"")){
                lowLetter=true;
                isValidChar=true;
                Log.d("validation", "lower letter");
            }
            if(validNumbers.contains(string.charAt(i)+"")){
                number=true;
                isValidChar=true;
                Log.d("validation", "number");
            }
            if(validSpecial.contains(string.charAt(i)+"")){
                special=true;
                isValidChar=true;
                Log.d("validation", "special");
            }
            if(!isValidChar)return false;
        }
        return capital && lowLetter && number && special ;
    }


}