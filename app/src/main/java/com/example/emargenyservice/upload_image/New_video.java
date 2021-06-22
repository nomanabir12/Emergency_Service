package com.example.emargenyservice.upload_image;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.emargenyservice.MainActivity;
import com.example.emargenyservice.R;
import com.example.emargenyservice.model.Book_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class New_video extends AppCompatActivity {
    Button upload_btn,main_btn;
    private static  final int VIDEO_REQUEST=1;
    VideoView videoView;
    Uri downloadUri;
    MediaController mediaController;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_video);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        upload_btn=findViewById(R.id.upload_btn);
        videoView=findViewById(R.id.videoView);
        storageReference= FirebaseStorage.getInstance().getReference();


        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        mediaController=new MediaController(New_video.this);
                        mediaController.setAnchorView(videoView);
                        videoView.setMediaController(mediaController);
                    }
                });
            }
        });
        videoView.start();
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setType("video/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1,"Choose a video"),VIDEO_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==VIDEO_REQUEST && resultCode==RESULT_OK && data.getData()!=null) {
            downloadUri=data.getData();
            videoView.setVideoURI(downloadUri);
            videoDialouge1(downloadUri);
        }
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
                        final Uri downloadUri1=uriTask.getResult();
                        if (uriTask.isSuccessful()) {
                            Video_Model video_model=new Video_Model(firebaseAuth.getCurrentUser().getEmail(),downloadUri1.toString().trim());
                            firebaseFirestore.collection("User_videos")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("main")
                                    .document(UUID.randomUUID().toString())
                                    .set(video_model)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                            }
                                        }
                                    });

                            /*Map<String, Object> user = new HashMap<>();
                            user.put("name", editTextFilename.getText().toString());
                            user.put("pdf", downloadUri.toString().trim());*/



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

}