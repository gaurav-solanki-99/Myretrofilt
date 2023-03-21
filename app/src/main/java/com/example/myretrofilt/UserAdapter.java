package com.example.myretrofilt;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myretrofilt.Network.RetrofitInstance;
import com.example.myretrofilt.databinding.SingleListBinding;
import com.example.myretrofilt.databinding.UpdatelayoutBinding;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context context;
    ArrayList<User> al;


    public UserAdapter(Context context, ArrayList<User> al) {
        this.context = context;
        this.al = al;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleListBinding binding = SingleListBinding.inflate(LayoutInflater.from(context),parent,false);

        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        holder.binding.tvheading.setText(al.get(position).title);
        holder.binding.tvSubHeading.setText(al.get(position).body);
        User user = al.get(position);
        int index =position;
        holder.binding.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(context);
                ad.setTitle("Delete");
                ad.setTitle("Are You want to delete ?");
                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RetrofitInstance.ApiInterface api = RetrofitInstance.getInstance();
                        api.deleteItem(user.id).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                if(response.isSuccessful())
                                {

                                    al.remove(index);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(context, "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });
                ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ad.show();


            }
        });

        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdatelayoutBinding binding1 = UpdatelayoutBinding.inflate(LayoutInflater.from(context));
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(binding1.getRoot());
                binding1.btnAdd.setText("Udate");
                binding1.tvId.setText(user.id+"");
                binding1.tvTitle.setText(user.title);
                binding1.tvDescription.setText(user.getBody());


                binding1.btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       int id2= Integer.parseInt(binding1.tvId.getText().toString());
                       String title2= binding1.tvTitle.getText().toString();
                       String description2= binding1.tvDescription.getText().toString();

                       RetrofitInstance.ApiInterface apiInterface = RetrofitInstance.getInstance();

                        ProgressDialog pd = new ProgressDialog(context);
                        pd.setMessage("Please wait");
                        pd.show();
                       apiInterface.updatePost(user.id,title2,description2,id2).enqueue(new Callback<User>() {
                           @Override
                           public void onResponse(Call<User> call, Response<User> response) {

                               if(response.isSuccessful())
                               {
                                   int id2= response.body().id;
                                   String title2= response.body().title;
                                   String description2= response.body().body;

                                  al.add(index,new User(user.id,id2,title2,description2));
                                  notifyDataSetChanged();

                                   Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                                   pd.dismiss();
                                   return;

                               }
                               else
                               {
                                   Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT).show();
                                   pd.dismiss();
                                   return;
                               }
                           }

                           @Override
                           public void onFailure(Call<User> call, Throwable t) {
                               Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                               pd.dismiss();
                               return;
                           }
                       });
                    }
                });
                builder.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    class  UserViewHolder extends RecyclerView.ViewHolder
    {
        SingleListBinding binding;
        public UserViewHolder(SingleListBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
