package com.example.iotapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iotapplication.API.UserAPIs;
import com.example.iotapplication.Adapter.LoginRegister.LoginResponse;
import com.example.iotapplication.Adapter.LoginRegister.User;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity  {

    TextView tvRegisHere;
    EditText etSigEmail, etSigPass;
    MaterialButton btnLogin;
    FirebaseAuth mAuth;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView ivGoogle;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    SharedPreferences sharedPreferences;
    public static final String userID = "user_id";
    public static final String fileName = "login";
    public static final String Username = "username";
    public static final String Password = "password";


//    GoogleSignInAccount account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        init();

        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Username)){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        }
//        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
//        databaseReference = firebaseDatabase.getReference(GmailLogInfo.class.getSimpleName());



//        ivGoogle = findViewById(R.id.ivGoogle);

//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc = GoogleSignIn.getClient(this,gso);
//
//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//        if(acct!=null){
//            navigatetoSecondActivity();
//        }


//        ivGoogle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//
//            }
//        });


//        mAuth = FirebaseAuth.getInstance();

//        btnLogin.setOnClickListener(view -> {
//            loginUser();
//        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        tvRegisHere.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

//    public void signIn(){
//        Intent signInIntent = gsc.getSignInIntent();
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
//        Log.d("HAHA", "signIn: ");
//
//        if(account != null){
//            Log.d("HAHA", "account.getDisplayName()");
//            addGmailtoFirebase(account.getDisplayName(),account.getEmail());
//        }else{
//            Toast.makeText(LoginActivity.this,"Cannot find the account",Toast.LENGTH_SHORT).show();
//        }
//        startActivityForResult(signInIntent,1000);
//    }
//
//    public void addGmailtoFirebase(String name, String email){
//        databaseReference.push().setValue(name);
////        databaseReference.push().setValue(email);
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == 1000){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//
//            try {
//                task.getResult(ApiException.class);
//                navigatetoSecondActivity();
//            } catch (ApiException e) {
//                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    public void navigatetoSecondActivity(){
//        finish();
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        Log.d("THE NEW:", "navigatetoSecondActivity: ");
//        startActivity(intent);
//    }

    private void loginUser() {
        String email = etSigEmail.getText().toString();
        String password = etSigPass.getText().toString();

        if (TextUtils.isEmpty(email)){
            etSigEmail.setError("Email cannot be empty");
            etSigEmail.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            etSigPass.setError("Password cannot be empty");
            etSigPass.requestFocus();
        }else {
//            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    }else{
//                        Toast.makeText(LoginActivity.this, "Login failed: ", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            Map<String, String> fields = new HashMap<>();
//            fields.put("username_or_email", email);
//            fields.put("password", password);
            User user = new User(email,password);

            //POST user account
            UserAPIs.userApi.postLoginUser(user).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    if(response.isSuccessful()) {
                        LoginResponse userResponse = response.body();
                        String userIDs = userResponse.getData().getUser_id();


                        Log.e("Response Result: ", response.toString());
//                    Log.d("User Result: ", userID);
                        Log.e("TAG", "response 33: " + userIDs);
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                        if (response.body() != null) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(userID, userIDs);
                            editor.putString(Username, email);
                            editor.putString(Password, password);
                            editor.commit();



                            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Cannot find the account", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
                    Log.e("ErrorLogin Message: ",t.getMessage());
                }
            });

//            UserAPIs.userApi.callDevice().enqueue(new Callback<Device>() {
//                @Override
//                public void onResponse(Call<Device> call, Response<Device> response) {
//                    try {
//                        Toast.makeText(LoginActivity.this, "Get data successfully", Toast.LENGTH_SHORT).show();
//                        Device device = response.body();
//
//                        Log.e("Data Error: ", response.toString());
//                        Log.d("Name:",device.getDetailDevice().getName().toString());
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<Device> call, Throwable t) {
//                    Toast.makeText(LoginActivity.this, "Cannot get data", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }


    public void init(){
        tvRegisHere = findViewById(R.id.tvRegis);
        etSigEmail = findViewById(R.id.etSigEmail);
        etSigPass = findViewById(R.id.etSigPass);
        btnLogin = findViewById(R.id.btnLogin);
    }


}