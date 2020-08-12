package com.ariri.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home_friend extends AppCompatActivity {

    //11 Agustus, 10117231, Ahmad Ripai Rizki, IF7
    SwipeRefreshLayout srl_main;
    RecyclerView rv_main;
    ArrayList<String> array_Nama,array_Alamat,array_Telepon,array_Medsos,array_Email;
    ProgressDialog progressDialog;
    RecycleViewAdapter recycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_friend);

        srl_main = findViewById(R.id.srl_main);
        rv_main = findViewById(R.id.rv_main);
        progressDialog = new ProgressDialog(this);
        rv_main.hasFixedSize();
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv_main.setLayoutManager(layoutManager);
        srl_main.setOnRefreshListener(new
                                              SwipeRefreshLayout.OnRefreshListener() {
                                                  @Override
                                                  public void onRefresh() {
                                                      scrollRefresh();
                                                      srl_main.setRefreshing(false);
                                                  }
                                              });
        scrollRefresh();
    }
    public void scrollRefresh(){
        progressDialog.setMessage("Mengambil Data.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        },1200);
    }
    void initializeArray(){
        array_Nama = new ArrayList<String>();
        array_Alamat = new ArrayList<String>();
        array_Telepon = new ArrayList<String>();
        array_Medsos = new ArrayList<String>();
        array_Email = new ArrayList<String>();
        array_Nama.clear();
        array_Alamat.clear();
        array_Telepon.clear();
        array_Medsos.clear();
        array_Email.clear();
    }
    public void getData(){
        initializeArray();
        AndroidNetworking.get("http://192.168.43.141/api-wisata/getData.php")
                .setTag("Get Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();

                        try{
                            Boolean status = response.getBoolean("status");
                            if(status){
                                JSONArray ja =
                                        response.getJSONArray("result");
                                Log.d("respon",""+ja);
                                for(int i = 0 ; i < ja.length() ; i++){
                                    JSONObject jo = ja.getJSONObject(i);
                                    array_Nama.add(jo.getString("Nama"));
                                    array_Alamat.add(jo.getString("Alamat"));
                                    array_Telepon.add(jo.getString("Tanggal"));
                                    array_Medsos.add(jo.getString("Medsos"));
                                    array_Email.add(jo.getString("Email"));
                                }
                                recycleViewAdapter = new
                                        RecycleViewAdapter(Home_friend.this,array_Nama,array_Alamat
                                        ,array_Telepon,array_Medsos,array_Email);
                                rv_main.setAdapter(recycleViewAdapter);
                            }else{
                                Toast.makeText(Home_friend.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                                recycleViewAdapter = new
                                        RecycleViewAdapter(Home_friend.this,array_Nama,array_Alamat
                                        ,array_Telepon,array_Medsos,array_Email);
                                rv_main.setAdapter(recycleViewAdapter);
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                    }
                });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutambah, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.menu_add){
            Intent i = new Intent(Home_friend.this,add.class);
            startActivityForResult(i,1);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                scrollRefresh();
            }else if(resultCode==RESULT_CANCELED){
                Toast.makeText(this, "Canceled",
                        Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode==2){
            if(resultCode==RESULT_OK){
                scrollRefresh();
            }else if(resultCode==RESULT_CANCELED){
                Toast.makeText(this, "Canceled",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
}


