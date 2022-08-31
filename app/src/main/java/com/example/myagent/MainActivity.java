package com.example.myagent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myagent.agentPages.AgentRegistration;
import com.example.myagent.agentPages.AgentSuitsList;
import com.example.myagent.agentPages.CreateNewUserFragment;
import com.example.myagent.objects.Agent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private String sharedPrefFile ="sharedPreferences";
    private FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    FirebaseFirestore db;

    static String validNumbers="1234567890";
    static String validCapital="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String validLetters="abcdefghijklmnopqrstuvwxyz";
    static String validSpecial="!#$%&@";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_activity, new AgentRegistration()).commit();


    }





    public static boolean isValidString(String string){
        for(int i=0; i<string.length(); i++){
            if(string.charAt(i)<65 || (string.charAt(i)>90 && string.charAt(i)<97) || string.charAt(i)>122|| string.charAt(i)!=' '||string.charAt(i)!='-')
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
            }
            if(validLetters.contains(string.charAt(i)+"")){
                lowLetter=true;
                isValidChar=true;
            }
            if(validNumbers.contains(string.charAt(i)+"")){
                number=true;
                isValidChar=true;
            }
            if(validSpecial.contains(string.charAt(i)+"")){
                special=true;
                isValidChar=true;
            }
            if(!isValidChar)return false;
        }
        return capital && lowLetter && number && special ;
    }


}