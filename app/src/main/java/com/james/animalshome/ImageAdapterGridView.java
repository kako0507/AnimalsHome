package com.james.animalshome;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by James on 2017/8/30.
 */

public class ImageAdapterGridView extends BaseAdapter {
    private Context context;
    private String TAG = ImageAdapterGridView.class.getSimpleName();
    ArrayList<Animals> animalsInfo = new ArrayList<>();
    //private  String[]picUrls = {};
    Bitmap bitmap;
    ImageView imageView;
    TextView textView;
    View gridView;

    public ImageAdapterGridView(Context context, ArrayList animalsInfo) {
        this.context = context;
        this.animalsInfo = animalsInfo;
    }

    public int getCount() {
        if (animalsInfo.size() == 0) {
            return 0;
        } else {
            return animalsInfo.size();
        }
    }

    public Object getItem(int position) {
        return animalsInfo.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final Animals info = animalsInfo.get(position);
        //gridView = new View(context);
        View gridView = inflater.inflate(R.layout.grid_item, parent, false);
        gridView.setTag(R.id.grid_image, gridView.findViewById(R.id.grid_image));
        gridView.setTag(R.id.grid_text, gridView.findViewById(R.id.grid_text));
        imageView = (ImageView) gridView.findViewById(R.id.grid_image);
        textView = (TextView) gridView.findViewById(R.id.grid_text);
        textView.setText(info.getName());
        //new LoadImage.execute();
        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
              Picasso.with(context)
                        .load("http://163.29.36.110/uploads/images/medium/" + info.getPic())
                        .placeholder(R.drawable.progress_animation)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }
                            @Override
                            public void onError() {
                                Log.e(TAG, "onError");
                                // Try again online if cache failed
                                Picasso.with(context)
                                        .load("http://163.29.36.110/uploads/images/medium/" + info.getPic())
                                        .into(imageView);
                            }
                        });
            }
        });
        return gridView;
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            try {
                //Picasso.with(context).load("http://163.29.36.110/uploads/images/medium/"+args[0]).centerCrop().into(imageView);
                bitmap = BitmapFactory.decodeStream((InputStream) new URL("http://163.29.36.110/uploads/images/medium/" + args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                imageView.setImageBitmap(image);
            }
        }
    }
}
