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
import com.example.myagent.objects.Agent;
import com.example.myagent.objects.User;
import com.example.myagent.userPages.UserLoginPageFragment;
import com.example.myagent.userPages.suitPages.SuitSide2CarLicenceFragment;
import com.example.myagent.userPages.suitPages.SuitUserCarPicturesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private String sharedPrefFile ="sharedPreferences";
    private FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    FirebaseFirestore db;
    String agentUID;
    FirebaseUser currentUser;
    Agent appAgent; // will be initialized only at signed in agent function
    User appUser; // will be initialized only at signed in user function
    public Agent getAppAgent(){
        return this.appAgent;
    }
    static String validNumbers="1234567890";
    static String validCapital="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String validLetters="'אבגדהוזחטיכךלמםנןסעפףצץקרשתabcdefghijklmnopqrstuvw\"xyz";
    static String validSpecial="!#$%&@*?";

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        agentUID="";

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_activity, new EntrancePageFragment()).addToBackStack(null).commit();


    }
    public void switchToSuitPage1() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new SuitSide2CarLicenceFragment()).addToBackStack(null).commit();
    }
    public void switchToSuitPage2() { // Todo:change destination
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new SuitUserCarPicturesFragment()).addToBackStack(null).commit();
    }
    public void switchToSuitPage3() {// Todo:change destination
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new SuitUserCarPicturesFragment()).addToBackStack(null).commit();
    }
    public void switchToSuitPage4() {// Todo:change destination
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new SuitUserCarPicturesFragment()).addToBackStack(null).commit();
    }
    public void switchToSuitPage5() {// Todo:change destination
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new SuitUserCarPicturesFragment()).addToBackStack(null).commit();
    }
    public void switchToRegistration() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new AgentRegistration()).addToBackStack(null).commit();
    }
    public void switchToLoginPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new UserLoginPageFragment()).addToBackStack(null).commit();
    }
    public void switchToForgotPasswordPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new ForgotPassword()).addToBackStack(null).commit();
    }


    public void sendNewPassword(String email) {
        //TODO: search for user in FB authentication if exist

        //if user exist create new password and update the authentication. after that send an email with the new password
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,"אימייל נשלח",Toast.LENGTH_LONG).show();
                            Log.d("reset_password", "Email sent.");
                        }
                    }
                });
        //if user no exist toast משתמש זה אינו רשום במערכת
    }
    public static String passwordGenerator(){
        String validChars="1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!#$%&@*?";
        String pWord ="";
        boolean isGoodPW = false;
        Random r= new Random();
        while (!isGoodPW){
            for (int i = 0; i < 8; i++) {
                pWord+=validChars.charAt(r.nextInt(70));
            }
            if(isValidPW(pWord)) isGoodPW=true;
        }
        return pWord;
    }

    public void login(String email, String pw ) {

    }
    public void createNewUserForAgent(User user) {
        String upw = passwordGenerator();
        mAuth.createUserWithEmailAndPassword(user.getEmail(),upw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "success user registration", Toast.LENGTH_SHORT).show();
                    agentUID=task.getResult().getUser().getUid();
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    firebaseFirestore.collection("users").document(task.getResult().getUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        //TODO: send to user email with the password to the app


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
                            switchToLoginPage();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,"failed upload data to db",Toast.LENGTH_LONG).show();

                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "משתמש זה קיים. נסה שחזור סיסמה", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // TODO: send to agent email with the password to the app
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