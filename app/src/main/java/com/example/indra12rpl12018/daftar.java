package com.example.indra12rpl12018;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;

public class daftar extends AppCompatActivity {

    TextView readyaccount;
    private Button btndaftar;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        btndaftar = findViewById(R.id.btndaftar);
        final TextInputEditText  txtemail = findViewById(R.id.user1);
        final TextInputEditText txtusername1 = findViewById(R.id.username2);
        final TextInputEditText txtnohp = findViewById(R.id.Nomorhp1);
        final TextInputEditText txtnoktp = findViewById(R.id.ktp1);
        final TextInputEditText txtalamat = findViewById(R.id.alamat1);
        final TextInputEditText txtpass1 = findViewById(R.id.password2);

        readyaccount = (TextView)findViewById(R.id.readyaccount);
        readyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtemail.getText().toString();
                String username1 = txtusername1.getText().toString();
                String nohp = txtnohp.getText().toString();
                String noktp = txtnoktp.getText().toString();
                String alamat = txtalamat.getText().toString();
                String pass = txtpass1.getText().toString();

//                Toast.makeText(daftar.this, email + " - " + username1 + " - " + nohp + " - " + noktp+ " - " +alamat+ " - " +pass, Toast.LENGTH_LONG).show();

                HashMap<String, String> body = new HashMap<>();
                body.put("noktp", noktp);
                body.put("email", email);
                body.put("password", pass);
                body.put("nama", username1);
                body.put("nohp", nohp);
                body.put("alamat", alamat);
                body.put("role", "1");
//                Toast.makeText(daftar.this, body.toString(), Toast.LENGTH_LONG).show();

                AndroidNetworking.post("http://192.168.43.109/api/register.php")
                        .addBodyParameter(body)
//                        .setOkHttpClient(((Initial) getApplication()).getOkHttpClient())
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Indra", "respon : " + response);
                                String status = response.optString("STATUS");
                                String message = response.optString("MESSAGE");
                                if (status.equalsIgnoreCase("SUCCESS")) {
                                    Intent intent = new Intent(daftar.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(daftar.this, message, Toast.LENGTH_SHORT).show();
                                    finish();
                                    finishAffinity();
                                }
                                else {
                                    Toast.makeText(daftar.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(daftar.this, "Kesalahan Internal", Toast.LENGTH_SHORT).show();
                                Log.d("Indra", "onError: " + anError.getErrorBody());
                                Log.d("Indra", "onError: " + anError.getLocalizedMessage());
                                Log.d("Indra", "onError: " + anError.getErrorDetail());
                                Log.d("Indra", "onError: " + anError.getResponse());
                                Log.d("Indra", "onError: " + anError.getErrorCode());
                            }
                        });
            }
        });
    }
}
