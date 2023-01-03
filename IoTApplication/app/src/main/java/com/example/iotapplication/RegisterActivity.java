package com.example.iotapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iotapplication.API.UserAPIs;
import com.example.iotapplication.Adapter.LoginRegister.RegisUser;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextView tvLoginHere;
    MaterialButton btnRegis;
    EditText etRegEmail, etRegPass, etRegName;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        init();
        mAuth = FirebaseAuth.getInstance();

        btnRegis.setOnClickListener(view -> {
            createUser();
        });

        tvLoginHere.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

    }

    private void createUser() {
        String email = etRegEmail.getText().toString();
        String name = etRegName.getText().toString();
        String password = etRegPass.getText().toString();

        if (TextUtils.isEmpty(email)){
            etRegEmail.setError("Email cannot be empty");
            etRegEmail.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            etRegPass.setError("Password cannot be empty");
            etRegPass.requestFocus();
        }else{

//            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                    }else{
//                        Toast.makeText(RegisterActivity.this, "Register fail: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
            UserAPIs.userApi.postRegisUser(email, name, password).enqueue(new Callback<RegisUser>() {
                @Override
                public void onResponse(Call<RegisUser> call, Response<RegisUser> response) {
                    Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                }

                @Override
                public void onFailure(Call<RegisUser> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Register Fail", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void init(){
        tvLoginHere = findViewById(R.id.tvLogin);
        btnRegis = findViewById(R.id.btnRegis);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPass = findViewById(R.id.etRegPass);
        etRegName = findViewById(R.id.etRegName);

    }
}