package com.lucky.filesync;

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
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity allows the user to
 */
public class MyRegister extends AppCompatActivity {

    private Button register;
    private TextView name;
    private TextView username;
    private TextView password;
    private TextView email;

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


    public void doRegister(View view) {
        if (name.getText().toString().length() < 2 || username.getText().toString().length() < 2 || password.getText().toString().length() < 2 || email.getText().toString().length() < 2 || !email.getText().toString().contains("@")) {
            Toast.makeText(MyRegister.this, "Details are incorrect", Toast.LENGTH_SHORT).show();
        } else {

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
                                if ((boolean) response.get("success")) {
                                    SharedPreferences sp = getSharedPreferences("FileSync", 0);
                                    SharedPreferences.Editor Ed = sp.edit();
                                    Ed.putString("Username", username.getText().toString());
                                    Ed.putString("Password", password.getText().toString());
                                    Ed.apply();
                                    Intent i = new Intent(MyRegister.this, FileActivity.class);
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
