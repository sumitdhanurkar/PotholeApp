package com.example.mylocationoverfbdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class MainActivity extends AppCompatActivity {

    Button btnGenerateOtp, btnSignIn;
    EditText phoneNumber, Otp;
    TextView timer;
    Spinner spinner;

    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    private String verificationCodeSent;
    String getPhoneNumber, getOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        btnGenerateOtp = findViewById(R.id.btn_generate_otp);
        btnSignIn = findViewById(R.id.btn_sign_in);

        phoneNumber = findViewById(R.id.phoneEditText);
        Otp = findViewById(R.id.otpEditText);
        timer = findViewById(R.id.timer);

        ArrayAdapter<String> countrycodes = new ArrayAdapter<String>(this, R.layout.spinner_item, CountryDetails.countrycodes);

        spinner.setAdapter(countrycodes);

        firebaseLogin();

        btnGenerateOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spinnerText = spinner.getSelectedItem().toString();
                String phone = phoneNumber.getText().toString();

                if(phone == null || phone.trim().isEmpty()){
                    phoneNumber.setError("Provide Phone Number");
                    return;
                }
            }
        });



    }

    private void firebaseLogin() {
        auth=FirebaseAuth.getInstance();

        callbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(MainActivity.this, "Verification Completed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(MainActivity.this, "Verification Failed", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCodeSent=s;
                Toast.makeText(MainActivity.this, "Code Sent", Toast.LENGTH_LONG).show();

            }
        };

    }
}
