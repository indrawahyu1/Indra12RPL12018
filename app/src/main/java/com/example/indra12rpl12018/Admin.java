package com.example.indra12rpl12018;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private rv_adapter rv_adapter;
    private ArrayList<rv_model> modelAdminArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        swipeRefresh = findViewById(R.id.swipe_view);


        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.post(new Runnable() {
            private void doNothing() {

            }

            @Override
            public void run() {
                getCustomer();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getCustomer() {
        swipeRefresh.setRefreshing(true);
        AndroidNetworking.get("http://1192.168.1.12/tugasapi/show_user.php")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        swipeRefresh.setRefreshing(false);
                        if (rv_adapter != null) {
                            rv_adapter.clearData();
                            rv_adapter.notifyDataSetChanged();
                        }
                        if (modelAdminArrayList != null)  modelAdminArrayList.clear();
                        try {

                            String status = response.getString("STATUS");
                            if (status.equalsIgnoreCase("SUCCESS")) {
                                JSONObject payload = response.getJSONObject("PAYLOAD");
                                JSONArray data = payload.getJSONArray("DATA");
                                Log.d("Data", String.valueOf(data));
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject item = data.getJSONObject(i);

                                    rv_model madmin = new rv_model();

                                    madmin.setId(item.getString("ID"));
                                    madmin.setNama(item.getString("NAMA"));
                                    madmin.setNohp(item.getString("NOHP"));
                                    madmin.setEmail(item.getString("EMAIL"));
                                    madmin.setNoktp(item.getString("NOKTP"));
                                    madmin.setAlamt(item.getString("ALAMAT"));
                                    modelAdminArrayList.add(madmin);

                                }
                                show();
                                Log.d("Array", String.valueOf(modelAdminArrayList.size()));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("anError", anError.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void onRefresh() {
        getCustomer();
    }

    public void show(){
        rv_adapter = new rv_adapter(Admin.this, modelAdminArrayList);
        recyclerView.setAdapter(rv_adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getCustomer();
        rv_adapter.notifyDataSetChanged();
        show();
    }

}