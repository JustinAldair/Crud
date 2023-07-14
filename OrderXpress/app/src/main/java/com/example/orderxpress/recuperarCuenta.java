package com.example.orderxpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class recuperarCuenta extends AppCompatActivity {
    private Button confirmar;
    private Button confirmar2;
    private EditText psw1, psw2;

    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private ImageView passwordIcon;
    private ImageView confirmPasswordIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_cuenta);

        confirmar= (Button) findViewById(R.id.btnRegistrarse);
        psw1=(EditText)findViewById(R.id.etIPassword);
        psw2=(EditText)findViewById(R.id.conetIPassword);

        confirmar = findViewById(R.id.btnRegistrarse);

        confirmPasswordIcon = findViewById(R.id.passwordicon2);
        confirmPasswordEditText = findViewById(R.id.conetIPassword);
        passwordEditText = findViewById(R.id.etIPassword);
        passwordIcon = findViewById(R.id.passwordicon);

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(passwordEditText, passwordIcon);
            }
        });

        confirmPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(confirmPasswordEditText, confirmPasswordIcon);
            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarCuenta();
            }
        });
    }

    private void recuperarCuenta() {
        String contra = psw1.getText().toString().trim();
        String confirContra = psw2.getText().toString().trim();

        if (contra.isEmpty()) {
            psw1.setError("Ingrese un nuevo password");
            psw1.requestFocus();
            return;
        } else if (contra.length() > 25) {
            psw1.setError("La contraseña no puede tener más de 25 caracteres");
            psw1.requestFocus();
            return;
        }

        if (confirContra.isEmpty()) {
            psw2.setError("Ingrese de nuevo el password");
            psw2.requestFocus();
            return;
        } else if (confirContra.length() > 25) {
            psw2.setError("La contraseña no puede tener más de 25 caracteres");
            psw2.requestFocus();
            return;
        }

        if (!contra.equals(confirContra)) {
            psw2.setError("Las contraseñas no coinciden");
            psw2.requestFocus();
            return;
        } else {
            Intent cmLogin = new Intent(getApplicationContext(), Inicio.class);
            startActivity(cmLogin);
            finish();
        }
    }


    private void togglePasswordVisibility(EditText editText, ImageView icon) {
        int inputType = editText.getInputType();
        if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            icon.setImageResource(R.drawable.eyeoff);
        } else {
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            icon.setImageResource(R.drawable.eye);
        }
        editText.setSelection(editText.getText().length());
    }



}