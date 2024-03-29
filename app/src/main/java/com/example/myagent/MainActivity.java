package com.example.myagent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;
import com.example.myagent.agentPages.AgentRegistration;
import com.example.myagent.agentPages.AgentSuitsList;
import com.example.myagent.agentPages.CreateNewUserFragment;
import com.example.myagent.agentPages.CustomerInfoAtAgent;
import com.example.myagent.agentPages.DocumentsListAtAgent;
import com.example.myagent.agentPages.DownloadDocumentFromAgentFragment;
import com.example.myagent.agentPages.HomePageAgentFragment;
import com.example.myagent.agentPages.SearchCustomerAtAgent;
import com.example.myagent.objects.Document;
import com.example.myagent.objects.User;
import com.example.myagent.userPages.PdfViewer;
import com.example.myagent.userPages.UserDocumentsAtUser;
import com.example.myagent.userPages.UserHomePageFragment;
import com.example.myagent.userPages.UserLoginPageFragment;
import com.example.myagent.userPages.suitPages.AccidentDescription;
import com.example.myagent.userPages.suitPages.SuitDriverInfo;
import com.example.myagent.userPages.suitPages.SuitInfoInstructionPageForUser;
import com.example.myagent.userPages.suitPages.SuitSide2CarLicenceFragment;
import com.example.myagent.userPages.suitPages.SuitSubmitSuccessfully;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    public interface LoginResponse{
        void  response(boolean success,String message);
    }
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private SharedPreferences mPreferences;
    private String sharedPrefFile ="sharedPreferences";
    private FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    public FirebaseFirestore db;
    String agentUID;
    FirebaseUser currentUser;
    User agent;
    boolean anAget;
    public List<User> userForRecyclerView;
    FirebaseStorage storage;
    FirebaseAppCheck appCheck;
    FragmentTransaction fragmentTransaction;


    User infoUser; // only at agent- to specify the user handling by the agent
//    User appAgent; // will be initialized only at signed in agent function
    User appUser; // will be initialized only at signed in user function for user app
    public User getAppAgent(){
        return this.agent;
    }
    public User getInfoUser(){
        return this.infoUser;
    }

    private static String validNumbers="1234567890";
    private static String validCapital="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String validLetters="'אבגדהוזחטיכךלמםנןסעפףצץקרשתabcdefghijklmnopqrstuvw\"xyz";
    private static String validSpecial="!#$%&@*?";

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

        FirebaseApp.initializeApp(this);
        appCheck = FirebaseAppCheck.getInstance();
        appCheck.installAppCheckProviderFactory(DebugAppCheckProviderFactory.getInstance());

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userForRecyclerView=new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_activity, new EntrancePageFragment()).commit();
        storage = FirebaseStorage.getInstance();


    }
    public void logOut(){
//        fragmentTransaction.replace(R.id.main_activity, new EntrancePageFragment());
        mAuth.signOut();
        onDestroy();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void setInfoUser(User user) {

//        WriteBatch batch= db.batch();
//        db.collection("users")
//                .whereEqualTo("userID",user.getUserId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                   DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
//                    batch.update(db.collection("users").document(documentSnapshot.getId())
//                            ,"address",user.getAddress(),"firstName",user.getFirstName(), "lastName",user.getLastName(), "phone",user.getPhone());
//                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(MainActivity.this, "מידע הוזן לבסיס הנתונים", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });


    }

    public void updateUserInfoAtDB() {
//        mAuth.getFirebaseAuthSettings().
//        db.collection("users").endBefore()

    }

    public void switchToAgentDocToCustomer() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new DownloadDocumentFromAgentFragment()).addToBackStack(null).commit();
    }
    public void switchToCustomersDocumentsPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new DocumentsListAtAgent()).addToBackStack(null).commit();
    }
    public void switchToUserInfoPage(User user) {
        this.infoUser=user;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new CustomerInfoAtAgent()).addToBackStack(null).commit();
    }
    public void switchToSearchUserPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new SearchCustomerAtAgent()).addToBackStack(null).commit();
    }

    public void switchToSuitDriverPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new SuitDriverInfo()).addToBackStack(null).commit();
    }

    public void switchToUserDocumentsList() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new UserDocumentsAtUser()).addToBackStack(null).commit();
    }
    public void switchToSuitsListPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new AgentSuitsList()).addToBackStack(null).commit();
    }
    public void switchToCreateUserPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new CreateNewUserFragment()).addToBackStack(null).commit();
    }
    public void switchToSuitPage1() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new SuitInfoInstructionPageForUser()).addToBackStack(null).commit();
    }
    public void switchToSuitPage2() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new SuitSide2CarLicenceFragment()).addToBackStack(null).commit();
    }
    public void switchToSuitPage3() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new AccidentDescription()).addToBackStack(null).commit();
    }
    public void switchToSuitPage4() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new SuitSubmitSuccessfully()).addToBackStack(null).commit();
    }
    public void switchToUserHomePage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new UserHomePageFragment()).addToBackStack(null).commit();
    }
    public void switchToRegistration() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new AgentRegistration()).addToBackStack(null).commit();
    }

    public void switchToLoginPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new UserLoginPageFragment()).addToBackStack(null).commit();
    }

    public void switchToLoginPdfPage(String path) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, new PdfViewer(path)).addToBackStack(null).commit();
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
                            Toast.makeText(MainActivity.this,"נשלח מייל לאיפוס סיסמה",Toast.LENGTH_LONG).show();
                            Log.d("reset_password", "Email sent.");
                        }
                    }
                });

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

    public void login(String email, String pw ,LoginResponse callback) {
        // if(!emailLog.isEmpty() && !passLog.isEmpty())
        mAuth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
//                            userEmail = emailLog;
                            String uid = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = db.collection("users").document(mAuth.getUid());
                            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot task1 = task.getResult();
                                    task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            callback.response(true,null);
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            if(task1.getBoolean("anAgent"))//check if (user.isAnAgent())
                                            {
                                                agent=task.getResult().toObject(User.class);
                                                anAget=true;
//                                                agent =new User(task1.getString("firstName"),task1.getString("lastName"),task1.getString("agentId"),task1.getString("phone"));
                                                fragmentTransaction.replace(R.id.main_activity, new HomePageAgentFragment()).addToBackStack(null).commit();
                                            } else { // a user
                                                anAget=false;
                                                agent =new User(task1.getString("firstName"),task1.getString("lastName"),task1.getString("userId"),task1.getString("agentId"),task1.getString("phone"),task1.getString("email"),task1.getString("address"));
//                                                agent=db.collection("users").whereEqualTo("agentId",task1.getString("agentId")).get().getResult().getDocuments().get(0).toObject(User.class);
                                                fragmentTransaction.replace(R.id.main_activity, new UserHomePageFragment()).addToBackStack(null).commit();
                                            }
                                        }
                                    });
                                }
                            });
                        } else {
                            callback.response(false,"you failed to login");

                        }
                    }
                });



    }
    public void createNewUserForAgent(@NonNull User user) {
        String upw = passwordGenerator();
        mAuth.createUserWithEmailAndPassword(user.getEmail(),upw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "משתמש נרשם בהצלחה", Toast.LENGTH_SHORT).show();
                    agentUID=task.getResult().getUser().getUid();
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    firebaseFirestore.collection("users").document(task.getResult().getUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
//                            Toast.makeText(MainActivity.this,"success upload data to db",Toast.LENGTH_LONG).show();
                            task.getResult().getUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(MainActivity.this, "משתמש חדש נוצר. על מנת להתחבר יש להנחות את המשתמש ליצור סיסמה חדשה על ידי כניסה לאיפוס סיסמה באפליקציה", Toast.LENGTH_LONG).show();
                                    String message="שלום "+user.getFirstName()+". קיבלת משתמש חדש לאפליקציית הסוכן שלי. על מנת להתחבר למערכת יש להיכנס עם המייל שלך והסיסמה שלך היא "+upw+" . תודה שבחרת בסוכן הביטוח שלך. נסיעה בטוחה";
                                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                        if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                                            sendSMSMessage(user.getPhone(),message);
                                        }else{
                                            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                                        }
                                    }
                                }
                            });
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

    //TODO: not working!! check why...
    private void sendSMSMessage(String phone, String message){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message, null, null);
            Toast.makeText(this,"Message has sent", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Failed to send message", Toast.LENGTH_SHORT).show();
        }
    }

//    private void sendEmail(String to, String cc, String subject, String message, String messageAfterComplete) {
//        Log.i("Send email", "");
//        String[] TO = {""};
//        String[] CC = {""};
//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//
//        emailIntent.setData(Uri.parse("mailto:"));
//        emailIntent.setType("text/plain");
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
//        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
//
//        try {
//            startActivity(Intent.createChooser(emailIntent, ""));
//            finish();
//            if(!messageAfterComplete.isEmpty()) Log.i("Finished sending email...", "");
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void registerAgent(User agent , String email, String pw){

        mAuth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "success to register", Toast.LENGTH_SHORT).show();
                    agentUID=task.getResult().getUser().getUid();
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    firebaseFirestore.collection("users").document(mAuth.getUid()).set(agent).addOnSuccessListener(new OnSuccessListener<Void>() {
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
//                    task.getException();
                    Toast.makeText(MainActivity.this, "משתמש זה קיים. נסה שחזור סיסמה", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //static function for validation
    public static boolean isValidEmail(String email){
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        return false;
    }

    public static boolean isValidName(@NonNull String string){
        for(int i=0; i<string.length(); i++){
            if(!validCapital.contains(string.charAt(i)+"") && !validLetters.contains(string.charAt(i)+"") && string.charAt(i)!='-' && string.charAt(i)!=' ')
                Log.e("validation", string.charAt(i)+"");
                return false;
        }
        return true;
    }

    public static boolean isValidID(@NonNull String id){
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

    public static boolean isValidPhone(@NonNull String string) {
        if(string.length()>13)return false;
        if(string.contains("-")) string=string.replaceAll("-","");
        for(int i=0; i<string.length(); i++){
            if(!validNumbers.contains(string.charAt(i)+""))
                return false;
        }
        return true;
    }
    public static boolean isValidAddress(String string) {
        string=string.trim();
        for(char c : string.toCharArray()){
            if(!validLetters.contains(c+"") && !validCapital.contains(c+"") && !validNumbers.contains(c+"") && c!=' '){
                return false;
            }
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

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        FirebaseAuth.getInstance().signOut();
//    }
}