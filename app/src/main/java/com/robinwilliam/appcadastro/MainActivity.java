package com.robinwilliam.appcadastro;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {


    private EditText editTextUsername, editTextPassword, editTextEmail;
    private Button buttonCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonCadastrar = findViewById(R.id.buttonLogin);

        if (buttonCadastrar != null) {
            buttonCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cadastrarUsuario();
                }
            });
        } else {
            Toast.makeText(this, "Botão não reconhecido", Toast.LENGTH_SHORT).show();
        }
    }

    private void cadastrarUsuario() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
            JSONObject userData = new JSONObject();
            try {
                userData.put("name", username);
                userData.put("password", password);
                userData.put("email", email);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = "http://10.0.2.2:3000/users"; 

            RequestQueue queue = Volley.newRequestQueue(this);

            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.POST, url, userData,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String mensagem = "Usuário cadastrado com sucesso!\n" +
                                                "Resposta do servidor: " + response.toString();
                                        Toast.makeText(MainActivity.this, mensagem, Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Volley Error", "Erro na requisição POST", error);
                                    Toast.makeText(MainActivity.this, "Erro na requisição", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

            queue.add(jsonRequest);

        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }
}
