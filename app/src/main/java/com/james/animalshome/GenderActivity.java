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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

/**
 * Created by James on 2017/8/30.
 */

public class GenderActivity extends AppCompatActivity {
    private ImageView imgWoman, imgMan;
    private String TAG = GenderActivity.class.getSimpleName();
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gender);
        imgWoman = (ImageView)findViewById(R.id.imgWoman);
        imgMan= (ImageView)findViewById(R.id.imgMan);
        result = getActivityValue();
        imgMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity("man");
            }
        });
        imgWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity("woman");
            }
        });
    }

    public void startActivity(String type) {
        Intent i = new Intent(GenderActivity.this, AnimalActivity.class);

        i.putExtra("type", result);
        if (type.equals("man")) {
            i.putExtra("sex", "1");
        } else {
            i.putExtra("sex", "2");
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(i);
    }

    public String getActivityValue() {
        Intent i = getIntent();
        String result = i.getStringExtra("Type");
        return result;
    }
    public void changeColor(){
        Drawable sourceDrawable = ContextCompat.getDrawable(this,R.mipmap.woman);
        Bitmap sourceBitmap = toBitmap(sourceDrawable);
        Bitmap finalBitmap = ToChangeColor(sourceBitmap, Color.parseColor("#000000"));
        imgWoman.setImageBitmap(finalBitmap);
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
