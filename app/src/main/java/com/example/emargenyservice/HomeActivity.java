package com.example.emargenyservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
private Toolbar mainToolbar;
private String current_user_id;
private BottomNavigationView mainBottomNav;
private DrawerLayout mainDrawer;
private ActionBarDrawerToggle mainToggle;
private NavigationView mainNav;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ChatIt");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        mainDrawer=findViewById(R.id.main_activity);
        mainNav = findViewById(R.id.main_nav);
        mainNav.setNavigationItemSelectedListener(this);
        mainToggle = new ActionBarDrawerToggle(this,mainDrawer,toolbar,R.string.open,R.string.close);
        mainDrawer.addDrawerListener(mainToggle);
        mainToggle.setDrawerIndicatorEnabled(true);
        mainToggle.syncState();
        CardView dailyCheck=findViewById(R.id.dailyCheck);
        dailyCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Ambulance.class));
            }
        });
        //2
        CardView luckeySpain=findViewById(R.id.luckeySpain);
        luckeySpain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Blood_bank.class));
            }
        });
        //3
        CardView card9=findViewById(R.id.card9);
        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Fire_service.class));
            }
        });
        //4
        CardView gas=findViewById(R.id.gas);
        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Gas_station.class));
            }
        });
        //5
        CardView policestation=findViewById(R.id.policestation);
        policestation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Police_station.class));
            }
        });
        //6
        CardView dpdc=findViewById(R.id.dpdc);
        dpdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DPDC.class));
            }
        });
        //6
        CardView wasa=findViewById(R.id.wasa);
        wasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Wasa.class));
            }
        });

        //7
        CardView medicalDoctor=findViewById(R.id.medicalDoctor);
        medicalDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Medical_doctor.class));
            }
        });
        //8
        CardView police_2=findViewById(R.id.police_2);
        police_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Police2.class));
            }
        });
        //9
        CardView consumer=findViewById(R.id.consumer);
        consumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Consumer_right.class));
            }
        });
        //10
        CardView child_ma=findViewById(R.id.child_ma);
        child_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Child_marrage.class));
            }
        });
        //10
        CardView covid_19=findViewById(R.id.covid_19);
        covid_19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Covid_19.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.call_us,menu);
        return true;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_find:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

                break;
            case R.id.action_following:
                Toasty.info(getApplicationContext(), "Here is dashbord for you.", Toast.LENGTH_SHORT, true).show();

                break;
            case R.id.video:
                if (firebaseUser==null) {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
                else {
                    startActivity(new Intent(getApplicationContext(), User_log.class));
                }

                break;
            case R.id.action_followers:
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));

                break;
            case R.id.Login:
                if (firebaseUser==null) {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
                else {
                    Toasty.success(getApplicationContext(), "You are already login here.", Toast.LENGTH_SHORT, true).show();
                }

                break;
            case R.id.feedback:
                startActivity(new Intent(getApplicationContext(),Feedback.class));

                break;

        }
        DrawerLayout drawer = findViewById(R.id.main_activity);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed()   {
        AlertDialog.Builder warning = new AlertDialog.Builder(this)
                .setTitle("Do you want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {


                        dialog.dismiss();
                        finishAffinity();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ToDO: delete all the notes created by the Anon user

                        // TODO: delete the anon user

                        dialog.dismiss();
                    }
                });

        warning.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mainToggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (item.getItemId()){

            case R.id.logout1:

                callUs();
                return true;

            case R.id.logout:

                logout();
                return true;

            default:
                return false;

        }

    }

    private void callUs() {
        AlertDialog.Builder warning = new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        dialog.dismiss();
                        finishAffinity();




                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ToDO: delete all the notes created by the Anon user

                        // TODO: delete the anon user

                        dialog.dismiss();
                    }
                });

        warning.show();
    }

    private void logout() {
        AlertDialog.Builder warning = new AlertDialog.Builder(this)
                .setTitle("Call Us")
                .setMessage("Are you want to Call Us?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        String number="01718599223";
                        if (TextUtils.isEmpty(number))
                        {
                            Toast.makeText(getApplicationContext(), "get Verify First", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String s="tel:"+number;
                            Intent intent=new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse(s));
                            startActivity(intent);
                        }

dialog.dismiss();


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ToDO: delete all the notes created by the Anon user

                        // TODO: delete the anon user

                        dialog.dismiss();
                    }
                });

        warning.show();
    }
}