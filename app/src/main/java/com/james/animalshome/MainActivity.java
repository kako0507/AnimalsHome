package com.james.animalshome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

/**
 * Created by James on 2017/8/30.
 */

public class MainActivity extends AppCompatActivity {
    private ImageView imgViewCat, imgViewDog, imgOther;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        imgViewCat = (ImageView) findViewById(R.id.imgCat);
        imgViewDog = (ImageView) findViewById(R.id.imgDog);
        imgOther = (ImageView) findViewById(R.id.imgOther);
        imgViewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity("cat");
            }
        });
        imgViewDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity("dog");
            }
        });
        imgOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity("other");
            }
        });

    }

    public void startActivity(String type) {
        Intent i = new Intent(MainActivity.this, GenderActivity.class);
        if (type.equals("dog")) {
            i.putExtra("Type", "1");
        } else if (type.equals("cat")) {
            i.putExtra("Type", "2");
        } else {
            i.putExtra("Type", "3");
        }
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left_1, R.anim.slide_in_left_2);
    }
}
