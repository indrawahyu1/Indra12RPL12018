package com.example.indra12rpl12018;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button btnlogin;
    private Button btndaftar;
    private TextView newakun;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    private  SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnlogin = findViewById(R.id.btnlogin);
        final EditText txtuser = findViewById(R.id.txtuser);
        final EditText txtpass = findViewById(R.id.txtpass);

        newakun = (TextView) findViewById(R.id.newakun);
        newakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), daftar.class));
            }
        });

        btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = txtuser.getText().toString();
                String password = txtpass.getText().toString();

                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Lengkapi isian yang tersedia", Toast.LENGTH_SHORT).show();
                }else{
                    HashMap<String, String> body = new HashMap<>();
                    body.put("email", username);
                    body.put("password", password);
                    AndroidNetworking.post("http://192.168.43.193/api/login.php")
                            .addBodyParameter(body)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("Indra", "respon : " + response);
                                    String status = response.optString("STATUS");
                                    String message = response.optString("MESSAGE");
                                    if (status.equalsIgnoreCase("SUCCESS")) {
                                        JSONObject payload = response.optJSONObject("PAYLOAD");
                                        String LOGIN_ID = payload.optString("LOGIN_ID");
                                        String LOGIN_NAME = payload.optString("LOGIN_NAME");
                                        String NOMOR_HP = payload.optString("NOMOR_HP");
                                        String NOMOR_KTP = payload.optString("NOMOR_KTP");
                                        String ALAMAT = payload.optString("ALAMAT");
                                        String ROLE = payload.optString("ROLE");
                                        preferences = getSharedPreferences("DBSEPEDA", Context.MODE_PRIVATE);
                                        preferences.edit()
                                                .putString(LOGIN_ID, LOGIN_ID)
                                                .putString(LOGIN_NAME, LOGIN_NAME)
                                                .putString(NOMOR_HP, NOMOR_HP)
                                                .putString(NOMOR_KTP, NOMOR_KTP)
                                                .putString(ALAMAT, ALAMAT)
                                                .putString(ROLE, ROLE)
                                                .apply();
                                        if (ROLE.equalsIgnoreCase("Customer")) {
                                            Intent intent = new Intent(MainActivity.this, dashboard.class);
                                            startActivity(intent);
                                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                            finishAffinity();
                                        }else if (ROLE.equalsIgnoreCase("Admin")){
                                            Intent intent = new Intent(MainActivity.this, Admin.class);
                                            startActivity(intent);
                                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                            finishAffinity();
                                        }
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Toast.makeText(MainActivity.this, "Kesalahan Internal", Toast.LENGTH_SHORT).show();
                                    Log.d("Indra", "onError: " + anError.getErrorBody());
                                    Log.d("Indra", "onError: " + anError.getLocalizedMessage());
                                    Log.d("Indra", "onError: " + anError.getErrorDetail());
                                    Log.d("Indra", "onError: " + anError.getResponse());
                                    Log.d("Indra", "onError: " + anError.getErrorCode());
                                }
                            });
                }
            }
        });

    }

}


