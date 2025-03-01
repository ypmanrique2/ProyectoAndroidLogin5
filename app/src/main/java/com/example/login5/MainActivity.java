package com.example.login5;

import static com.example.login5.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnIngresar = findViewById(R.id.ingresar);
        btnIngresar.setOnClickListener(view -> {
            EditText txtUsuario = findViewById(R.id.usuario);
            EditText txtClave = findViewById(R.id.clave);
            String usuario = txtUsuario.getText().toString();
            String clave = txtClave.getText().toString();
            new Thread (() -> {
            try {
                URL url = new URL("http://10.0.2.2:3000/login?usuario="+usuario+"&clave="+clave);
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("GET");
                int codigoRespuesta = conexion.getResponseCode();
                if (codigoRespuesta==200){
                    System.out.println("Has iniciado sesión correctamente");
                    Intent intent = new Intent(MainActivity.this, Principal.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    System.out.println("Error al iniciar sesión");
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            }).start();
        });

        Button btnRegistrarse = findViewById(R.id.registrarse);
        btnRegistrarse.setOnClickListener(view -> {
           Intent intent = new Intent (MainActivity.this, RegisterActivity.class);
           //startActiviy(intent);
        });

    }
}