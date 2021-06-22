package com.example.emargenyservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emargenyservice.model.Service_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import es.dmoral.toasty.Toasty;

public class Add_question extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    EditText Email_Log,Email_Log1,Email_Log221;
    Button tmio_2;
    KProgressHUD progressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        tmio_2=findViewById(R.id.tmio_2);
        Email_Log=findViewById(R.id.Email_Log);
        Email_Log1=findViewById(R.id.Email_Log1);
        progressHUD = KProgressHUD.create(Add_question.this);
        Email_Log221=findViewById(R.id.Email_Log221);
        tmio_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,address,phone;
                name=Email_Log.getText().toString().trim();
                address=Email_Log1.getText().toString().trim();
                phone=Email_Log221.getText().toString().trim();
                if (TextUtils.isEmpty(name)||TextUtils.isEmpty(address)||TextUtils.isEmpty(phone)) {
                    Toasty.error(getApplicationContext(), "Empty field.", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                else {
                    dialougeInterface();
                }
            }
        });
    }

    private void dialougeInterface() {
        final Service_model service_model=new Service_model(Email_Log.getText().toString(),Email_Log1.getText().toString(),
                Email_Log221.getText().toString());
        String[] options={"Ambulance","Blood Bank","Fire Service","Gas Station",
                "Police Station","DPDC","Wasa","Medical Doctor","Police Station",
                "Consumer Rights","Child Marriage","Covid-19"};
        AlertDialog.Builder alertDialouge=new AlertDialog.Builder(this);
        alertDialouge.setTitle("Select a option");
        alertDialouge.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0) {
                    progress_check();
                    firebaseFirestore.collection("Ambulance")
                            .document(Email_Log.getText().toString())
                            .set(service_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressHUD.dismiss();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        finish();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            String message=e.getMessage();
                            Toasty.error(getApplicationContext(), ""+message, Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
                //2
               else  if(which==1) {
                    progress_check();
                    firebaseFirestore.collection("Blood_Bank")
                            .document(Email_Log.getText().toString())
                            .set(service_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressHUD.dismiss();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        finish();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            String message=e.getMessage();
                            Toasty.error(getApplicationContext(), ""+message, Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
//3
                else  if(which==2) {
                    progress_check();
                    firebaseFirestore.collection("Fire_Service")
                            .document(Email_Log.getText().toString())
                            .set(service_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressHUD.dismiss();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        finish();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            String message=e.getMessage();
                            Toasty.error(getApplicationContext(), ""+message, Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
                //4
                else  if(which==3) {
                    progress_check();
                    firebaseFirestore.collection("Gas_Station")
                            .document(Email_Log.getText().toString())
                            .set(service_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressHUD.dismiss();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        finish();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            String message=e.getMessage();
                            Toasty.error(getApplicationContext(), ""+message, Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
                //5
                else  if(which==4) {
                    progress_check();
                    firebaseFirestore.collection("Police_Station")
                            .document(Email_Log.getText().toString())
                            .set(service_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressHUD.dismiss();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        finish();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            String message=e.getMessage();
                            Toasty.error(getApplicationContext(), ""+message, Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
                //6
                else  if(which==5) {
                    progress_check();
                    firebaseFirestore.collection("DPDC")
                            .document(Email_Log.getText().toString())
                            .set(service_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressHUD.dismiss();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        finish();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            String message=e.getMessage();
                            Toasty.error(getApplicationContext(), ""+message, Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
                //7
                else  if(which==6) {
                    progress_check();
                    firebaseFirestore.collection("Wasa")
                            .document(Email_Log.getText().toString())
                            .set(service_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressHUD.dismiss();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        finish();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            String message=e.getMessage();
                            Toasty.error(getApplicationContext(), ""+message, Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
                //8
                else  if(which==7) {
                    progress_check();
                    firebaseFirestore.collection("Medical_Doctor")
                            .document(Email_Log.getText().toString())
                            .set(service_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressHUD.dismiss();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        finish();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            String message=e.getMessage();
                            Toasty.error(getApplicationContext(), ""+message, Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
                //9
                else  if(which==8) {
                    progress_check();
                    firebaseFirestore.collection("Police_station_2")
                            .document(Email_Log.getText().toString())
                            .set(service_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressHUD.dismiss();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        finish();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            String message=e.getMessage();
                            Toasty.error(getApplicationContext(), ""+message, Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
                //10
                else  if(which==9) {
                    progress_check();
                    firebaseFirestore.collection("Consumer_rights")
                            .document(Email_Log.getText().toString())
                            .set(service_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressHUD.dismiss();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        finish();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            String message=e.getMessage();
                            Toasty.error(getApplicationContext(), ""+message, Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
                //11
                else  if(which==10) {
                    progress_check();
                    firebaseFirestore.collection("Child_marrge")
                            .document(Email_Log.getText().toString())
                            .set(service_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressHUD.dismiss();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        finish();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            String message=e.getMessage();
                            Toasty.error(getApplicationContext(), ""+message, Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
                //11
                else  if(which==11) {
                    progress_check();
                    firebaseFirestore.collection("Covid_19")
                            .document(Email_Log.getText().toString())
                            .set(service_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressHUD.dismiss();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        finish();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            String message=e.getMessage();
                            Toasty.error(getApplicationContext(), ""+message, Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
                //12
                else  if(which==1) {
                    progress_check();
                    firebaseFirestore.collection("Blood_Bank")
                            .document(Email_Log.getText().toString())
                            .set(service_model)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressHUD.dismiss();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        finish();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            String message=e.getMessage();
                            Toasty.error(getApplicationContext(), ""+message, Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
            }
        });
        alertDialouge.create().show();

    }
    private void progress_check() {
        progressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }
}