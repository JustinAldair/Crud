package com.example.orderxpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class registrarCuenta extends AppCompatActivity {
private TextView rediIniciarSesion;
private Button btnRegistrar;
private Boolean bandera=false, banderav=false;
private CheckBox opc1,opc2;
    private EditText edtLpassword;
    private ImageView passwordIcon;
    private boolean passwordVisible = false;

private EditText nombre,usuario,password,correo,telefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cuenta);

        edtLpassword = findViewById(R.id.etIPassword);
        passwordIcon = findViewById(R.id.passwordicon);
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(edtLpassword, passwordIcon);
            }
        });

        rediIniciarSesion=(TextView) findViewById(R.id.rdLogin);

        nombre=(EditText) findViewById(R.id.etNombre);
        usuario=(EditText) findViewById(R.id.etIUsuario);
        password=(EditText) findViewById(R.id.etIPassword);
        correo=(EditText) findViewById(R.id.etSRCCorreo);
        telefono=(EditText) findViewById(R.id.etNumero);

        opc1= (CheckBox) findViewById(R.id.cbRepartidor);
        opc2= (CheckBox) findViewById(R.id.cbPedidos);

        opc1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    opc2.setChecked(false); // Desmarca opc2 si opc1 está seleccionada
                    opc2.setEnabled(false); // Deshabilita opc2
                } else {
                    opc2.setEnabled(true); // Habilita opc2 cuando opc1 no está seleccionada
                }
            }
        });

        opc2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    opc1.setChecked(false); // Desmarca opc1 si opc2 está seleccionada
                    opc1.setEnabled(false); // Deshabilita opc1
                } else {
                    opc1.setEnabled(true); // Habilita opc1 cuando opc2 no está seleccionada
                }
            }
        });

        // Deseleccionar ambas opciones CheckBox al iniciar esta interfaz
        opc1.setChecked(false);
        opc2.setChecked(false);

        Button btnRegistrar = findViewById(R.id.btnRegistrarse);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
                if(bandera==true && banderav==true){
                    Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                }else{
                    cambiarLogin(v);
                    Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    //Transicion a interfaz de Login
    public void cambiarLogin(View view) {
        Intent cmLogin = new Intent(registrarCuenta.this,Inicio.class);
        startActivity(cmLogin);
        finish();
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

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void registrarUsuario() {
        String name = nombre.getText().toString().trim();
        String username = usuario.getText().toString().trim();
        String psw = password.getText().toString().trim();
        String email = correo.getText().toString().trim();
        String phone = telefono.getText().toString().trim();

        if (name.isEmpty()) {
            nombre.setError("Ingrese un nombre");
            nombre.requestFocus();
            return;
        } else if (name.length() > 40) {
            nombre.setError("El nombre no puede tener más de 40 caracteres");
            nombre.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            usuario.setError("Ingrese un nombre de usuario");
            usuario.requestFocus();
            return;
        } else if (username.length() > 25) {
            usuario.setError("El nombre de usuario no puede tener más de 25 caracteres");
            usuario.requestFocus();
            return;
        }

        if (psw.isEmpty()) {
            password.setError("Ingrese una contraseña");
            password.requestFocus();
            return;
        } else if (psw.length() > 25) {
            password.setError("La contraseña no puede tener más de 25 caracteres");
            password.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            correo.setError("Ingrese un correo electrónico");
            correo.requestFocus();
            return;
        } else if (!isValidEmail(email)) {
            correo.setError("Ingrese un correo electrónico válido");
            correo.requestFocus();
            return;
        } else if (email.length() > 40) {
            correo.setError("El correo electrónico no puede tener más de 40 caracteres");
            correo.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            telefono.setError("Ingrese un número telefónico");
            telefono.requestFocus();
            return;
        } else if (phone.length() != 10) {
            telefono.setError("El número de teléfono debe tener 10 caracteres");
            telefono.requestFocus();
            return;
        }else if (!opc1.isChecked() && !opc2.isChecked()) {
            Toast.makeText(registrarCuenta.this, "Debes seleccionar un tipo de usuario", Toast.LENGTH_SHORT).show();
            banderav=true;
        } else {
            String url = APIUtils.getFullUrl("userCreate");

            JSONObject jsonParams = new JSONObject();
            try {
                jsonParams.put("nombre", name);
                jsonParams.put("usuario", username);
                jsonParams.put("password", psw);
                jsonParams.put("correo", email);
                jsonParams.put("telefono", phone);
                jsonParams.put("tipo_usuario", opc1.isChecked() ? "repartidor" : "tomador_pedido");
                bandera=true;
            } catch (JSONException e) {
                e.printStackTrace(); bandera=false;
                banderav=false;
            }

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParams,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Respuesta del servidor", response.toString()); // Agregar esta línea para imprimir la respuesta en el logcat

                            try {
                                if (response.has("success")) {
                                    boolean success = response.getBoolean("success");
                                    if (success) {
                                        Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                        Intent regiIni = new Intent(registrarCuenta.this, Inicio.class);
                                        startActivity(regiIni);
                                        finish();
                                    } else {
                                        String errorMessage = response.getString("message");
                                        Toast.makeText(getApplicationContext(), "Error al registrar usuario: " + errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Respuesta del servidor inválida", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse != null && error.networkResponse.data != null) {
                                String responseString = new String(error.networkResponse.data);
                                Log.e("Respuesta del servidor", responseString);
                            }
                        }
                    });
            requestQueue.add(jsonObjectRequest);
        }
    }

}