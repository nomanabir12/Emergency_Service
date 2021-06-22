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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    Button login,signUp;
    private FirebaseAuth mAuth;


    EditText email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.Login);
        signUp=findViewById(R.id.register);
        email=findViewById(R.id.Email_Log);
        password=findViewById(R.id.Password_Log);
        mAuth= FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String emaiil,password1;
                emaiil=email.getText().toString().trim();
                password1=password.getText().toString().trim();
                if(TextUtils.isEmpty(emaiil) && TextUtils.isEmpty(password1)) {
                    Toast.makeText(Login.this, "Fill Up field first...", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Login.this, "Enter  6 bit Password", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(emaiil) || TextUtils.isEmpty(password1)) {
                    Toast.makeText(Login.this, "Email or Password is Empty..", Toast.LENGTH_SHORT).show();

                }
                else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(emaiil).matches()) {
                        email.setError("Invalid Email.");
                        email.setFocusable(true);
                        Toast.makeText(Login.this, "Invalid Email", Toast.LENGTH_LONG).show();


                    }
                    else {
                        LoginUser(emaiil,password1);
                    }
                }

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();
            }
        });

    }

    private void LoginUser(final String emaiil, String password1) {
        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Login...");
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(emaiil,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    FirebaseUser user=mAuth.getCurrentUser();
                    String user1=user.getEmail();
                    if (user1.equals("arifulpub143@gmail.com")) {
                        Intent intent = new Intent(getApplicationContext(), Add_question.class);
                        intent.putExtra("user_email", emaiil);

                        startActivity(intent);
                        email.setText(null);
                        password.setText(null);
                        Toast.makeText(Login.this, "" + emaiil, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), User_log.class);
                        intent.putExtra("user_email", emaiil);
                        startActivity(intent);
                        Toast.makeText(Login.this, "" + emaiil, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();;
                Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}