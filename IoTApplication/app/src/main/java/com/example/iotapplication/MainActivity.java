package com.example.iotapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iotapplication.Fragment.HomeFragment;
import com.example.iotapplication.Fragment.ProfileFragment;
import com.example.iotapplication.Fragment.SettingsFragment;
import com.example.iotapplication.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    FirebaseAuth mAuth;
    MaterialButton btnLogout;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView ivGoogle;
    GoogleSignInAccount acct;
    Button btnGetRequest, btnPostRequest;
    TextView txtResultValue;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    public static final String fileName = "login";
    public static final String Username = "username";
    public static final String Password = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Username)){
            Toast.makeText(MainActivity.this, "Hello "+sharedPreferences.getString(Username,""), Toast.LENGTH_SHORT).show();
        }









//        getSharePref();

//        getSupportActionBar().hide(); //this line hides action bar


//        mAuth = FirebaseAuth.getInstance();

//        User user = new User("hellovinh","123456@");
//        Gson gson = new Gson();
//        String json = gson.toJson(user);
//        Log.e("String JSON",json);

//        ivGoogle = findViewById(R.id.ivGoogle);
//
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc = GoogleSignIn.getClient(this, gso);





        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i){
                    case 0:
                        replaceFragment(new HomeFragment());
                        break;
                    case 1:
                        replaceFragment(new SettingsFragment());
                        break;
                    case 2:
                        replaceFragment(new ProfileFragment());
                        break;
                }
                return true;

            }
        }

        );
    }

//    public static SharedPreferences getSharePref(){
//        SharedPreferences newShare = sharedPreferences;
//        Log.d("sharePref: ", String.valueOf(newShare.contains(Username)));
//        return newShare;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


    }

//    @Override
    protected void onStart() {
        super.onStart();
////        FirebaseUser user = mAuth.getCurrentUser();
////        acct = GoogleSignIn.getLastSignedInAccount(this);
////        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
////        String user_email = sessionManagement.getSession();
////        Log.d("String Check: ",user_email);

        if (!sharedPreferences.contains(Username)){

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
//
////        User user = null;
////        String check = user.getUsername_or_email().toString();
////        if (user == null && acct == null){
////            startActivity(new Intent(MainActivity.this, LoginActivity.class));
////        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


}