package com.james.animalshome;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by James on 2017/8/30.
 */

public class MainActivity extends AppCompatActivity {
    private ImageView imgViewCat, imgViewDog, imgOther;
    private String TAG = MainActivity.class.getSimpleName();
    private FirebaseAnalytics mFirebaseAnalytics;
    private DatabaseReference ref;
    String count;
    TinyDB tinydb;
    String alreadyGj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        tinydb = new TinyDB(MainActivity.this);
        alreadyGj = tinydb.getString("GJ");
        ckInstall();
        if(alreadyGj.equals("")){
            alreadyGj="true";
        }
        new AppUpdater(this)
                .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
                .setDisplay(Display.DIALOG)
                .showAppUpdated(false)  // 若已是最新版本, 則 true: 仍會提示之, false: 不會提示之
                .start();
        setContentView(R.layout.activity_main);
        imgViewCat = (ImageView) findViewById(R.id.imgCat);
        imgViewDog = (ImageView) findViewById(R.id.imgDog);
        imgOther = (ImageView) findViewById(R.id.imgOther);
        imgViewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity("cat");
                //writeNewAnimals("cat");
            }
        });
        imgViewDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity("dog");
                //writeNewAnimals("dog");
            }
        });
        imgOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity("other");
               // writeNewAnimals("other");
            }
        });
    }
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
    @Override
    public void onBackPressed() {
        if(alreadyGj.toString().equals("true")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("◎ 給個 5 星好評\n◎ 一同支持領養代替購買\n◎ 可回饋問題讓我們知道")
                    .setTitle("感恩您的使用")
                    .setCancelable(false)
                    .setPositiveButton("讚", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intentDL = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.james.animalshome"));
                            startActivity(intentDL);
                        }
                    })
                    .setNegativeButton("已經讚囉", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNeutralButton("不再提示", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            tinydb.putString("GJ", "false");
                            MainActivity.this.finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }else{
            MainActivity.this.finish();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        if (msg != null) {
            Log.e("FCM", "msg:" + msg);
        }
    }

    public void writeNewAnimals(final String animals) {

        ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference usersRef = ref.child("Animals");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (final DataSnapshot type : dataSnapshot.getChildren()) {
                        if (type.getKey().equals(animals)) {
                            count = type.getValue().toString();
                            int a = Integer.parseInt(count) + 1;
                            //usersRef.child(animals).setValue(a);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void ckInstall(){
        PackageInfo packageInfo;
        String packagename = "com.james.animalsallhome";
        try {
            packageInfo = this.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if(packageInfo ==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("◎ 全國版本上架囉! \n\n◎ 一同支持領養代替購買")
                    .setTitle("【全國】浪浪需要家-下載")
                    .setCancelable(true)
                    .setPositiveButton("愛心下載", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intentDL = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.james.animalsallhome"));
                            startActivity(intentDL);
                        }
                    })
                    .setNeutralButton("下次再說", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void startActivity(String type) {
        Intent i = new Intent(MainActivity.this, GenderActivity.class);
        if (type.equals("dog")) {
            i.putExtra("type", "1");
        } else if (type.equals("cat")) {
            i.putExtra("type", "2");
        } else {
            i.putExtra("type", "3");
        }
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left_1, R.anim.slide_in_left_2);
    }
}
