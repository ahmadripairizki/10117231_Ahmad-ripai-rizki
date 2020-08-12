package com.ariri.myapplication;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.ArrayList;

//11 Agustus, 10117231, Ahmad Ripai Rizki, IF7
class RecycleViewAdapter extends
        RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<String> array_Nama,array_Alamat,array_Telepon,array_Medsos,array_Email;
    ProgressDialog progressDialog;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        //13 mei, 10117231, AHmad Ripai Rizki, IF7
        public TextView tv_Nama,tv_Alamat,tv_Telepon,tv_Medsos,tv_Email;
        public CardView cv_main;
        public MyViewHolder(View view) {
            super(view);
            cv_main = itemView.findViewById(R.id.cv_main);
            tv_Nama = itemView.findViewById(R.id.tv_Nama);
            tv_Alamat = itemView.findViewById(R.id.tv_Alamat);
            tv_Telepon = itemView.findViewById(R.id.tv_Telepon);
            tv_Medsos = itemView.findViewById(R.id.tv_Medsos);
            tv_Email = itemView.findViewById(R.id.tv_Email);
            progressDialog = new ProgressDialog(mContext);
        }
    }
    public RecycleViewAdapter(Context mContext, ArrayList<String> array_Nama,ArrayList<String>
                                      array_Alamat,ArrayList<String> array_Telepon,ArrayList<String> array_Medsos,
                                        ArrayList<String> array_Email) {
        super();
        this.mContext = mContext;
        this.array_Nama = array_Nama;
        this.array_Alamat = array_Alamat;
        this.array_Telepon = array_Telepon;
        this.array_Medsos = array_Medsos;
        this.array_Email = array_Email;
    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.activity_template_rv,parent,false);
        return new RecycleViewAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_Nama.setText(array_Nama.get(position));
        holder.tv_Alamat.setText(array_Alamat.get(position));
        holder.tv_Telepon.setText(array_Telepon.get(position));
        holder.tv_Medsos.setText(array_Medsos.get(position));
        holder.tv_Email.setText(array_Email.get(position));
        holder.cv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, edit.class);
                i.putExtra("Nama",array_Nama.get(position));
                i.putExtra("Alamat",array_Alamat.get(position));
                i.putExtra("Telepon",array_Telepon.get(position));
                i.putExtra("Medsos",array_Medsos.get(position));
                i.putExtra("Email",array_Email.get(position));
                ((Home_friend)mContext).startActivityForResult(i,2);
            }
        });
        holder.cv_main.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder((Home_friend)mContext)
                        .setMessage("Ingin menghapus Nama Wisata "+array_Nama.get(position)+" ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        progressDialog.setMessage("Menghapus...");
                                        progressDialog.setCancelable(false);
                                        progressDialog.show();
                                        AndroidNetworking.post("http://192.168.43.141/api-siswa/deleteSiswa.php")

                                                .addBodyParameter("NIM",""+array_Nama.get(position))
                                                .setPriority(Priority.MEDIUM)
                                                .build()
                                                .getAsJSONObject(new
                                                                         JSONObjectRequestListener() {
                                                                             @Override
                                                                             public void onResponse(JSONObject
                                                                                                            response) {
                                                                                 progressDialog.dismiss();
                                                                                 try {
                                                                                     Boolean status =
                                                                                             response.getBoolean("status");
                                                                                     Log.d("statuss",""+status);
                                                                                     String result =
                                                                                             response.getString("result");
                                                                                     if(status){
                                                                                         if(mContext instanceof
                                                                                                 Home_friend){

                                                                                             ((Home_friend)mContext).scrollRefresh();
                                                                                         }
                                                                                     }else{
                                                                                         Toast.makeText(mContext,
                                                                                                 ""+result, Toast.LENGTH_SHORT).show();
                                                                                     }
                                                                                 }catch (Exception e){
                                                                                     e.printStackTrace();
                                                                                 }
                                                                             }
                                                                             @Override
                                                                             public void onError(ANError anError) {
                                                                                 anError.printStackTrace();
                                                                             }
                                                                         });
                                    }
                                })

                        .setNegativeButton("Tidak", new
                                DialogInterface.OnClickListener()
                                { @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    dialog.cancel();
                                }
                                })
                        .show();
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return array_Nama.size();
    }
}
