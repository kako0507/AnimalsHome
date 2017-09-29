package com.james.animalshome;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by James on 2017/8/30.
 */

public class ImageAdapterGridView extends ArrayAdapter<Animals> {
    private Context mContext;
    private String TAG = ImageAdapterGridView.class.getSimpleName();
    ArrayList<Animals> mGridData = new ArrayList<Animals>();
    Bitmap bitmap;
    private int layoutResourceId;

    public ImageAdapterGridView(Context mContext, int layoutResourceId, ArrayList<Animals> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.mContext = mContext;
        this.mGridData = mGridData;
        this.layoutResourceId = layoutResourceId;
    }

    public int getCount() {
        if (mGridData.size() == 0) {
            return 0;
        } else {
            return mGridData.size();
        }
    }

    public void setGridData(ArrayList<Animals> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    public Animals getItem(int position) {
        return mGridData.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) row.findViewById(R.id.grid_text);
            holder.imageView = (ImageView) row.findViewById(R.id.grid_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Animals animals = mGridData.get(position);
        if(animals.getName().equals("")){
            holder.textView.setText("無");
        }else{
            holder.textView.setText(animals.getName());
        }
        if(animals.getPic().toString().equals("無") ||animals.getPic().toString().equals("") ){
            Picasso.with(mContext)
                    .load(R.mipmap.no_image_text)
                    .into(holder.imageView);
        }else{
            Picasso.with(mContext)
                    .load("http://163.29.36.110/uploads/images/medium/" + animals.getPic())
                    .into(holder.imageView);
        }

        return row;
    }


    static class ViewHolder {
        ImageView imageView;
        TextView textView;
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
                //imageView.setImageBitmap(image);
            }
        }
    }
}
