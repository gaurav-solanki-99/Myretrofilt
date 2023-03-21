package com.example.myretrofilt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.myretrofilt.Network.RetrofitInstance;
import com.example.myretrofilt.databinding.ActivityMainBinding;
import com.example.myretrofilt.databinding.FormlayoutBinding;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    FormlayoutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FormlayoutBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = Integer.parseInt(binding.tvId.getText().toString());
                String title = binding.tvTitle.getText().toString();
                String body = binding.tvDescription.getText().toString();

                RetrofitInstance.ApiInterface apiInterface = RetrofitInstance.getInstance();
                ProgressDialog pd = new ProgressDialog(MainActivity.this);
                pd.setMessage("Please wait while register..");
                pd.show();

                apiInterface.createPost(title,body,id).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful())
                        {
                            pd.dismiss();
                            Toast.makeText(MainActivity.this,"Saved",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this,ALLUserActivity.class));
                        }
                        else
                        {
                            pd.dismiss();
                            Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this,"Error"+t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
//                apiInterface.createPost(title,body,id).enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(@NonNull Call<String> stringCall, @NonNull Response<String> stringResponse) {
//
//                        if(stringResponse.isSuccessful())
//                        {
//                            pd.dismiss();
//                            Toast.makeText(MainActivity.this,"Saved",Toast.LENGTH_LONG).show();
//                        }
//                        else
//                        {
//                            pd.dismiss();
//                            Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                        pd.dismiss();
//                        Toast.makeText(MainActivity.this,"Error"+t.getMessage(),Toast.LENGTH_LONG).show();
//
//                    }
//                });


            }
        });


    }
}