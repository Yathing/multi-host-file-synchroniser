package com.lucky.filesync;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndStringRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MyRegister extends AppCompatActivity {

    Button register;
    TextView name;
    TextView username;
    TextView password;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_register);

        register = (Button) findViewById(R.id.registerbut);
        name = (TextView) findViewById(R.id.name);
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        email = (TextView) findViewById(R.id.email);

    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doRegister(View view) {
        if (name.getText().toString().length()<2 || username.getText().toString().length()<2 || password.getText().toString().length()<2 || email.getText().toString().length()<2 || !email.getText().toString().contains("@") ){
            Toast.makeText(MyRegister.this, "Details are incorrect" , Toast.LENGTH_SHORT).show();
        }else {

            JSONObject obj = new JSONObject();

            try {
                obj.put("name", name.getText().toString());
                obj.put("email", email.getText().toString());
                obj.put("username", username.getText().toString());
                obj.put("password", password.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AndroidNetworking.post("http://46.101.20.26:3000/users/register")
                    .addJSONObjectBody(obj)
                    .setContentType("application/json; charset=utf-8")
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if ((boolean) response.get("success")){
                                    SharedPreferences sp = getSharedPreferences("FileSync", 0);
                                    SharedPreferences.Editor Ed=sp.edit();
                                    Ed.putString("Username",username.getText().toString() );
                                    Ed.putString("Password",password.getText().toString());
                                    Ed.apply();
                                    Intent i = new Intent(MyRegister.this, MainActivity.class);
                                    startActivity(i);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            System.out.println(error);
                            Toast.makeText(getApplicationContext(), "Whoopsie-Daisy! That's a failed registration.", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    public void goLogin(View view) {
        Intent i = new Intent(MyRegister.this, MyLogin.class);
        startActivity(i);
    }
}
