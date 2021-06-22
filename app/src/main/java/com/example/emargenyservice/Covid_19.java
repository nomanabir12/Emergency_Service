package com.example.emargenyservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.emargenyservice.model.Blood_adapter;
import com.example.emargenyservice.model.Covid_Adapter;
import com.example.emargenyservice.model.Service_model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Covid_19 extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    Covid_Adapter getDataAdapter1;
    List<Service_model> getList;
    String url;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_19);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressHUD = KProgressHUD.create(Covid_19.this);
        //animation
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_about_card_show);
        RelativeLayout relativeLayout = findViewById(R.id.r22222);
        relativeLayout.startAnimation(animation);

        getList=new ArrayList<>();
        getDataAdapter1=new Covid_Adapter(getList);
        firebaseFirestore=FirebaseFirestore.getInstance();
        documentReference=firebaseFirestore.collection("Covid_19").document();
        recyclerView=findViewById(R.id.blog_list_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();
    }

    private void reciveData() {
        progress_check();
        firebaseFirestore.collection("Covid_19").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange ds:queryDocumentSnapshots.getDocumentChanges()) {
                    if (ds.getType()== DocumentChange.Type.ADDED) {
                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                        Service_model get=ds.getDocument().toObject(Service_model.class);
                        getList.add(get);
                        getDataAdapter1.notifyDataSetChanged();
                    }
                    progressHUD.dismiss();
                }
            }
        });
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