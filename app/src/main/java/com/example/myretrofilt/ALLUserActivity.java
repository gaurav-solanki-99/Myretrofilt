package com.example.myretrofilt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myretrofilt.Network.RetrofitInstance;
import com.example.myretrofilt.databinding.AlluserlayoutBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ALLUserActivity extends AppCompatActivity
{


    AlluserlayoutBinding binding;
    ArrayList<User> al;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=AlluserlayoutBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        RetrofitInstance.ApiInterface apiInterface = RetrofitInstance.getInstance();
        apiInterface.getALLUser().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {

                if(response.isSuccessful())
                {
                   al= response.body();

                   UserAdapter adapter = new UserAdapter(ALLUserActivity.this,al);
                   binding.rv.setLayoutManager(new LinearLayoutManager(ALLUserActivity.this));
                   binding.rv.setAdapter(adapter);


                }

            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(ALLUserActivity.this, "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
