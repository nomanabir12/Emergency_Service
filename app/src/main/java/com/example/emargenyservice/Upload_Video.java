package com.example.emargenyservice;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Upload_Video extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    VideoView videoView;
    EditText editText;
    Button upload;
    MediaController mediaController;
    Uri downloadUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__video);
      firebaseFirestore=FirebaseFirestore.getInstance();
      firebaseStorage=FirebaseStorage.getInstance();
      storageReference=firebaseStorage.getReference();
      videoView=findViewById(R.id.videoView);
      editText=findViewById(R.id.name);
      upload=findViewById(R.id.Login);
      mediaController=new MediaController(this);
      videoView.setMediaController(mediaController);
      mediaController.setAnchorView(
              videoView
      );
      videoView.start();


    }
}