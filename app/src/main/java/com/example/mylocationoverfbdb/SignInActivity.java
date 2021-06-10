package com.example.mylocationoverfbdb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText mEmail, mPass;
    private TextView mTextView;
    private Button signInBtn;
    private FirebaseAuth mAuth;

    SharedPreferences ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mEmail =findViewById(R.id.email_signin);
        mPass = findViewById(R.id.passsignin);
        signInBtn = findViewById(R.id.signin_btn);
        mTextView = findViewById(R.id.textView2);

        ref = getSharedPreferences("myapp",MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();

        mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, Login_Activity.class));
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String sEmail = ref.getString("email","");
                String sPass = ref.getString("pass","");

                loginUser();
            }
        });
    }
    private void loginUser() {
        String email = mEmail.getText().toString();
        String pass = mPass.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!pass.isEmpty()){
                mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(SignInActivity.this, "Login Successfully!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this, MapActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignInActivity.this, "Login Failed !!", Toast.LENGTH_SHORT).show();
                    }
                });

            }else{
                mPass.setError("Empty Fields Are not Allowed");
            }

        }else if(email.isEmpty()){
            mEmail.setError("Empty fields Are not Allowed");
        }else{
                mEmail.setError("Please Enter correct Email");
            }
    }

}

