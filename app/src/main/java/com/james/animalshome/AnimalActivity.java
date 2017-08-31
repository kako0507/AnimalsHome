package com.james.animalshome;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.util.ArrayList;


public class AnimalActivity extends AppCompatActivity {
    private GridView gridView;
    private BottomNavigationView navigation;
    private String TAG = AnimalActivity.class.getSimpleName();
    private String result[] = new String[5];
    private final ArrayList<Animals> animalInfo = new ArrayList<>();
    private String imgPath = "http://163.29.36.110/uploads/images/medium/";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        gridView = (GridView) findViewById(R.id.gridview) ;
        result = getActivityValue();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        gridView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        new HttpAsyncTask().execute(" http://163.29.36.110/amlapp/Query/AcceptList.ashx?type=" + result[0] +"&sex="+result[1]);
    }
    @Override
    public void onBackPressed() {
        //goToHome();
        onBackPressed();
        return;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            getData(urls[0]);
            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            gridView.setAdapter(new ImageAdapterGridView(AnimalActivity.this, animalInfo));
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            animalInfo.clear();
        }
    }

    public void getData(String url) {
        try {
            String json = Jsoup.connect(url).ignoreContentType(true).execute().body();
            String output = json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1);
            ArrayList<Animals> item = new ArrayList<Animals>();
            Log.e(TAG, " json output : " + output);
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String name = jsonObject.getString("name");
                String pic = jsonObject.getString("pic");
                String tid = jsonObject.getString("tid");
                String acceptnum = jsonObject.getString("acceptnum");
                String webid = jsonObject.getString("id");
                animalInfo.add(new Animals(name, pic, tid,acceptnum,webid));
                //addingArrList(item,i);

                //Log.e("TAG", "name:" + name + ", tid:" + tid + ", pic:" + pic);
                //Log.e("TAG", "acceptnum:" + acceptnum + ", id:" + id );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String[] getActivityValue(){
        Intent i = getIntent();
        result[0] = i.getStringExtra("type");
        result[1] = i.getStringExtra("sex");
        return result;
    }
    public void goToHome(){
        Intent i = new Intent(AnimalActivity.this,MainActivity.class);
        startActivity(i);
    }
    public ArrayList<Animals> addingArrList(ArrayList<Animals> item, int i) {
        item.add(new Animals(
                animalInfo.get(i).getName(),
                animalInfo.get(i).getPic(),
                animalInfo.get(i).getTid(),
                animalInfo.get(i).getAcceptnum(),
                animalInfo.get(i).getWebId())
        );
        return item;
    }
}
