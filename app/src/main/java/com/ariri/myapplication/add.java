package com.ariri.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class add extends AppCompatActivity {

    //11 Agustus, 10117231, Ahmad Ripai Rizki, IF7

    com.rengwuxian.materialedittext.MaterialEditText
            et_Nama,et_Alamat,et_Telepon,et_Medsos,et_Email;
    String Nama,Alamat,Telepon,Medsos,Email;
    Button btn_submit;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        et_Nama = findViewById(R.id.et_Nama);
        et_Alamat = findViewById(R.id.et_Alamat);
        et_Telepon = findViewById(R.id.et_Telepon);
        et_Medsos = findViewById(R.id.et_Medsos);
        et_Email = findViewById(R.id.et_Email);
        btn_submit = findViewById(R.id.btn_submit);
        progressDialog = new ProgressDialog(this);
        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Menambahkan Data...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Nama = et_Nama.getText().toString();
                Alamat = et_Alamat.getText().toString();
                Telepon = et_Telepon.getText().toString();
                Medsos = et_Medsos.getText().toString();
                Email = et_Email.getText().toString();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        validasiData();
                    }
                },1000);
            }
        });
    }


    private void validasiData() {
        if(Nama.equals("") || Alamat.equals("") ||
                Telepon.equals("") || Medsos.equals("") || Email.equals("")){
            progressDialog.dismiss();
            Toast.makeText(add.this, "Periksa kembali data yang anda masukkan !", Toast.LENGTH_SHORT).show();
        }else {
            kirimData();
        }
    }

    private void kirimData() {
        AndroidNetworking.post("http://192.168.43.141/api-wisata/tambahSiswa.php")
                .addBodyParameter("Nama",""+Nama)
                .addBodyParameter("Alamat",""+Alamat)
                .addBodyParameter("Telepon",""+Telepon)
                .addBodyParameter("Medsos",""+Medsos)
                .addBodyParameter("Email",""+Email)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {


                    @Override
                    public void onResponse(JSONObject response) {

                        progressDialog.dismiss();
                        Log.d("cekTambah",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan = response.getString("result");
                            Toast.makeText(add.this, ""+pesan,
                                    Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                new AlertDialog.Builder(add.this)
                                        .setMessage("Berhasil Menambahkan Data !")
                                        .setCancelable(false)
                                        .setPositiveButton("Kembali", new
                                                DialogInterface.OnClickListener() {


                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        Intent i = getIntent();
                                                        setResult(RESULT_OK,i);
                                                        add.this.finish();
                                                    }
                                                })
                                        .show();
                            }
                            else{
                                new AlertDialog.Builder(add.this)
                                        .setMessage("Gagal Menambahkan Data !")
                                        .setPositiveButton("Kembali", new
                                                DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent i = getIntent();
                                                        setResult(RESULT_CANCELED,i);
                                                        add.this.finish();
                                                    }
                                                })
                                        .setCancelable(false)
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onError(ANError anError) {
                        Log.d("ErrorTambahData",""+anError.getErrorBody());
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.menu_back){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
