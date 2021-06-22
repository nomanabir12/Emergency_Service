package com.example.emargenyservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.emargenyservice.model.Book_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Add_newctivity2 extends AppCompatActivity {
    ActionBar actionBar;
    Toolbar toolbar;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    VideoView videoView;
    EditText editText;
    Button upload;
    private static final int video_pic_galary_code=100;
    private static final int video_pic_camera_code=101;
    private static final int camera_request_code=102;
    MediaController mediaController;
    Uri downloadUri;
    private String[] cameraPermisssion;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_newctivity2);
        toolbar=findViewById(R.id.toolbar);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        videoView=findViewById(R.id.videoView);
        editText=findViewById(R.id.name);
        upload=findViewById(R.id.Login);
        floatingActionButton=findViewById(R.id.floating);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadVideo();

            }
        });
        //caremea
        cameraPermisssion=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText().toString().toLowerCase().trim()))
                {
                    Toast.makeText(Add_newctivity2.this, "Enter video name", Toast.LENGTH_SHORT).show();
                }
                else {
                    videoDialouge();
                }
            }
        });




    }

    private void uploadVideo() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        StorageReference sRef = storageReference.child("Videos/" + System.currentTimeMillis() + ".mp4");
        sRef.putFile(downloadUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        final Uri downloadUri=uriTask.getResult();
                        if (uriTask.isSuccessful()) {

                            /*Map<String, Object> user = new HashMap<>();
                            user.put("name", editTextFilename.getText().toString());
                            user.put("pdf", downloadUri.toString().trim());*/
                            Book_model upload=new Book_model(editText.getText().toString(),downloadUri.toString().trim());
                            firebaseFirestore.collection("Video")
                                    .document(editText.getText().toString().toLowerCase())
                                    .set(upload)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // progressHUD.dismiss();
                                                // textSend_user();
                                                Toast.makeText(getApplicationContext(), "Book Added", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            //Log.w(TAG, "Error adding document", e);
                                            //progressHUD.dismiss();
                                            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    })
                            ;



                            //Upload upload = new Upload(editTextFilename.getText().toString(), downloadUri.toString());
                            //mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();


                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    }
                });
    }

    private void videoDialouge() {
        String[] options={"Camera","Galary"};
        AlertDialog.Builder alertDialouge=new AlertDialog.Builder(Add_newctivity2.this);
        alertDialouge.setTitle("Pick Video Form");
        alertDialouge.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    }
                    else {
                        openCamera(); 
                    }
                }
                else if (which==1) {
                    get_galary();
                }


            }
        });
        alertDialouge.create().show();
    }

    private void openCamera() {
        Intent intent1=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent1,camera_request_code);

    }

    private void get_galary() {
        Intent intent1=new Intent();
        intent1.setType("video/*");
        intent1.setAction(Intent.ACTION_GET_CONTENT);
        startActivity(intent1);

    }

    private void requestCameraPermission()
    {
        ActivityCompat.requestPermissions(Add_newctivity2.this,cameraPermisssion,camera_request_code);
    }
    private boolean checkCameraPermission()
    {
        boolean result1= ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
        boolean result2= ContextCompat.checkSelfPermission(this,Manifest.permission.WAKE_LOCK)== PackageManager.PERMISSION_GRANTED;
        return result1 && result2;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case camera_request_code:
                boolean camera=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                boolean stroage=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                if (camera && stroage) {
                    openCamera();
                }
                else {
                    Toast.makeText(this, "Permission is not given", Toast.LENGTH_SHORT).show();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==RESULT_OK) {
            if(requestCode==video_pic_galary_code) {
                downloadUri=data.getData(); 
                gotoVideoView();
                videoDialouge1(downloadUri);
            }
            else {
                downloadUri=data.getData();
                gotoVideoView();
                videoDialouge1(downloadUri);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void videoDialouge1(Uri downloadUri) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        StorageReference sRef = storageReference.child("Videos/" + System.currentTimeMillis() + ".mp4");
        sRef.putFile(downloadUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        final Uri downloadUri=uriTask.getResult();
                        if (uriTask.isSuccessful()) {

                            /*Map<String, Object> user = new HashMap<>();
                            user.put("name", editTextFilename.getText().toString());
                            user.put("pdf", downloadUri.toString().trim());*/
                            Book_model upload=new Book_model(editText.getText().toString(),downloadUri.toString().trim());
                            firebaseFirestore.collection("Video")
                                    .document(editText.getText().toString().toLowerCase())
                                    .set(upload)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // progressHUD.dismiss();
                                                // textSend_user();
                                                Toast.makeText(getApplicationContext(), "Book Added", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            //Log.w(TAG, "Error adding document", e);
                                            //progressHUD.dismiss();
                                            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    })
                            ;



                            //Upload upload = new Upload(editTextFilename.getText().toString(), downloadUri.toString());
                            //mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();


                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    }
                });
    }

    private void gotoVideoView() {
        MediaController mediaController=new MediaController(this);
        mediaController.setAnchorView(videoView)
        ;
        videoView.setMediaController(mediaController)
        ;
      videoView.setVideoURI(downloadUri);
      videoView.requestFocus();
      videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
          @Override
          public void onPrepared(MediaPlayer mp) {
              videoView.pause();
          }
      });
    }
}