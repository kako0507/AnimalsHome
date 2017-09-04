package com.james.animalshome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by James on 2017/8/30.
 */

public class YearsActivity extends AppCompatActivity {
    private ImageView imgBaby, imgYoung, imgForman, imgWisdom;
    private String TAG = YearsActivity.class.getSimpleName();
    private String result [] = new String[5];
    private DatabaseReference ref;
    private String count;
    String animal = "";
    private DatabaseReference usersRef = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_year);
        imgBaby = (ImageView) findViewById(R.id.imgBaby);
        imgYoung = (ImageView) findViewById(R.id.imgYoung);
        imgForman = (ImageView) findViewById(R.id.imgStrong);
        imgWisdom = (ImageView) findViewById(R.id.imgOld);
        result = getActivityValue();
        imgBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity("baby");
            }
        });
        imgYoung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity("young");
            }
        });
        imgForman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity("strong");
            }
        });
        imgWisdom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity("old");
            }
        });
    }

    public void startActivity(String type) {

        Intent i = new Intent(YearsActivity.this, AnimalActivity.class);
        i.putExtra("type", result[0]);
        i.putExtra("sex", result[1]);
        if (type.equals("baby")) {
            i.putExtra("age", "20");
        } else if (type.equals("young")){
            i.putExtra("age", "21");
        }else if (type.equals("strong")){
            i.putExtra("age", "22");
        }else if (type.equals("old")){
            i.putExtra("age", "23");
        }
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left_1, R.anim.slide_in_left_2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right_1, R.anim.slide_in_right_2);
    }

    public String[] getActivityValue() {
        Intent i = getIntent();
        result[0] = i.getStringExtra("type");
        result[1] = i.getStringExtra("sex");
        return result;
    }

    public void writeNewAnimals(final String result, final String sex) {
        ref = FirebaseDatabase.getInstance().getReference();
        if (result.equals("1")) {
            animal = "dog";
        } else if (result.equals("2")) {
            animal = "cat";
        } else if (result.equals("3")) {
            animal = "other";
        }
        usersRef = ref.child("Animals").child(animal);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (final DataSnapshot type : dataSnapshot.getChildren()) {
                        if (type.getKey().equals(sex)) {
                            count = type.getValue().toString();
                            int a = Integer.parseInt(count) + 1;
                            usersRef.child(sex).setValue(a);
                        }
                    }

                    //usersRef.child(sex).setValue(a);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void changeColor() {
        Drawable sourceDrawable = ContextCompat.getDrawable(this, R.mipmap.woman);
        Bitmap sourceBitmap = toBitmap(sourceDrawable);
        Bitmap finalBitmap = ToChangeColor(sourceBitmap, Color.parseColor("#000000"));
        imgBaby.setImageBitmap(finalBitmap);
    }

    public static Bitmap toBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap ToChangeColor(Bitmap sourceBitmap, int color) {
        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
                sourceBitmap.getWidth() - 1, sourceBitmap.getHeight() - 1);
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 1);
        p.setColorFilter(filter);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);
        return resultBitmap;
    }
}
