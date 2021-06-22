package com.example.emargenyservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emargenyservice.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button singIn;
    EditText email, password;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.Email_Log);
        password = findViewById(R.id.Password_Log);
        singIn = findViewById(R.id.Login);
        mAuth= FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();


        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String emaiil,password1;
                emaiil=email.getText().toString().trim();
                password1=password.getText().toString().trim();
                if(TextUtils.isEmpty(emaiil) && TextUtils.isEmpty(password1)) {
                    Toast.makeText(Register.this, "Fill Up field first...", Toast.LENGTH_LONG).show();
                    if (TextUtils.isEmpty(emaiil)) {
                        email.setError("Error...");
                        return;
                    }
                    else if (TextUtils.isEmpty(password1)){
                        password.setError("Error");
                        return;
                    }
                }
                else if (password1.length()!=6) {
                    Toast.makeText(Register.this, "Enter  6 bit Password", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(emaiil) || TextUtils.isEmpty(password1)) {
                    Toast.makeText(Register.this, "Email or Password is Empty..", Toast.LENGTH_SHORT).show();

                }
                else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(emaiil).matches()) {
                        email.setError("Invalid Email.");
                        email.setFocusable(true);
                        Toast.makeText(Register.this, "Invalid Email", Toast.LENGTH_LONG).show();


                    }
                    else {
                        registerEmail(emaiil,password1);
                    }
                }
            }
        });
    }

    private void registerEmail(final String emaiil, final String password1) {
        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("SignUp...");
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(emaiil,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    store_data(emaiil,password1);
                    Toast.makeText(Register.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),User_log.class);
                    intent.putExtra("user_email",emaiil);
                    startActivity(intent);
                    email.setText(null);
                    password.setText(null);
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Register.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void store_data(String emaiil, String password1) {
        Users users=new Users(emaiil,password1);
        firebaseFirestore.collection("Main_users")
                .document(emaiil)
                .set(users)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                        }

                    }
                });

    }
}