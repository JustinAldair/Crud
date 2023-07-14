package com.example.orderxpress;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class Inicio extends AppCompatActivity {

    private TextView rediRegistrate;
    private EditText usuario, password;
    private EditText edtLpassword;
    private ImageView passwordIcon;
    private boolean passwordVisible = false;

    //Google
    private Button btnIniciarSesion;
    //private TextView rediRegistrate;
    //private EditText usuario, password;
    //private Button btnIniciarSesion;
    private Button btnIniSeGoogle;

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //Gogle
        rediRegistrate = findViewById(R.id.rediRegistrate);
        usuario = findViewById(R.id.etIUsuario);
        password = findViewById(R.id.etIPassword);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnIniSeGoogle = findViewById(R.id.sInWhithGoogle);

            // Configurar el cliente de inicio de sesión de Google Sign-In
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnIniSeGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar sesión con Google
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

                // Cambiar a la siguiente actividad después de 3 segundos
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Cambiar a la siguiente actividad
                        Intent intent = new Intent(Inicio.this, MainActivityMenu.class);
                        startActivity(intent);
                        finish();
                    }
                }, 8000); // Esperar 3 segundos antes de cambiar de actividad
            }
        });

        //Fin google
        TextView textView = findViewById(R.id.rediRegistrate);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear el Intent para abrir la nueva actividad
                Intent intent = new Intent(Inicio.this, registrarCuenta.class);
                startActivity(intent);
                finish();
            }
        });
        TextView textView2 = findViewById(R.id.recuperarPassword);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear el Intent para abrir la nueva actividad
                Intent intent = new Intent(Inicio.this, soliRecuperarCuenta.class);
                startActivity(intent);
                finish();
            }
        });


        usuario=(EditText) findViewById(R.id.etIUsuario);
        password=(EditText) findViewById(R.id.etIPassword);
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);

        edtLpassword = findViewById(R.id.etIPassword);
        passwordIcon = findViewById(R.id.passwordicon);
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordVisible = !passwordVisible;
                updatePasswordVisibility();
            }
        });

        rediRegistrate = (TextView) findViewById(R.id.rediRegistrate);
        usuario = (EditText) findViewById(R.id.etIUsuario);
        password = (EditText) findViewById(R.id.etIPassword);
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });
    }

    private void updatePasswordVisibility() {
        if (passwordVisible) {
            passwordIcon.setImageResource(R.drawable.eye);
            edtLpassword.setTransformationMethod(null); // Mostrar contraseña
        } else {
            passwordIcon.setImageResource(R.drawable.eyeoff);
            edtLpassword.setTransformationMethod(new PasswordTransformationMethod()); // Ocultar contraseña
        }

        // Mover el cursor al final del texto
        edtLpassword.setSelection(edtLpassword.getText().length());
    }

    // Transicion a interfaz de Regristrar cuenta
    public void cambiarRegistro(View view) {
        Intent cmRegistro = new Intent(getApplicationContext(), registrarCuenta.class);
        startActivity(cmRegistro);
        finish();
    }

    public void recuperarContra(View view) {
        Intent cmSoliContra = new Intent(getApplicationContext(), soliRecuperarCuenta.class);
        startActivity(cmSoliContra);
        finish();
    }

    private void iniciarSesion() {
        String username = usuario.getText().toString().trim();
        String psw = password.getText().toString().trim();

        // Realiza las validaciones necesarias
        if (username.isEmpty()) {
            usuario.setError("Ingrese un nombre de usuario");
            usuario.requestFocus();
            return;
        } else if (username.length() > 25) {
            usuario.setError("El nombre de usuario no puede tener más de 25 caracteres");
            usuario.requestFocus();
            return;
        } else if (psw.isEmpty()) {
            password.setError("Ingrese una contraseña");
            password.requestFocus();
            return;
        } else if (psw.length() > 25) {
            password.setError("La contraseña no puede tener más de 25 caracteres");
            password.requestFocus();
            return;
        }

        // Validaciones exitosas, se puede iniciar sesion
        iniciarSesion2(username, psw);
    }


    private void iniciarSesion2(String usuario, String password) {
        String url = APIUtils.getFullUrl("login");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("usuario", usuario);
            jsonObject.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(Inicio.this, "No se ha podido Iniciar Sesion", Toast.LENGTH_SHORT).show();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // La solicitud fue exitosa y se recibió una respuesta del servidor
                        // Procesar la respuesta del servidor
                        try {
                            String message = response.getString("message");
                            JSONObject userObject = response.optJSONObject("user");

                            if (userObject != null) {
                                int userId = userObject.optInt("id", 0);
                                String userName = userObject.optString("name", "");
                                String userEmail = userObject.optString("email", "");

                                Log.d("Inicio", message);
                                Log.d("Inicio", "User ID: " + userId);
                                Log.d("Inicio", "User Name: " + userName);
                                Log.d("Inicio", "User Email: " + userEmail);

                                if (message.equals("Inicio de sesión exitoso")) {
                                    // La conexión fue exitosa y el usuario está autenticado
                                    // Redireccionar a MainActivity
                                    Intent intent = new Intent(Inicio.this, MainActivityMenu.class);
                                    startActivity(intent);
                                    finish(); // Opcional: Finaliza la actividad actual si no se necesita volver a ella
                                    Toast.makeText(Inicio.this, "Inicio Exitoso", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Manejar la excepción JSONException aquí
                            Toast.makeText(Inicio.this, "No se ha podido Iniciar Sesion", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Ocurrió un error en la solicitud
                        // Registrar el error en los registros de la aplicación
                        Log.e("Inicio", "Error en la solicitud HTTP: " + error.toString());
                        Toast.makeText(Inicio.this, "No se ha podido Iniciar Sesion", Toast.LENGTH_SHORT).show();
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Intento de inicio de sesión con Google exitoso, obtén la cuenta de Google
                GoogleSignInAccount account = task.getResult(ApiException.class);

                // Aquí puedes obtener los datos de la cuenta de Google y realizar las acciones necesarias
                String idToken = account.getIdToken();
                String displayName = account.getDisplayName();
                String email = account.getEmail();
                Uri photoUrl = account.getPhotoUrl();

                // Redirige al usuario a la siguiente actividad
                Intent intent = new Intent(Inicio.this, MainActivityMenu.class);
                startActivity(intent);
                finish(); // Opcionalmente, puedes finalizar la actividad actual si no necesitas volver a ella

            } catch (ApiException e) {
                // Error en el inicio de sesión con Google
                Log.e("Inicio", "Error en el inicio de sesión con Google: " + e.getMessage());
            }
        }
    }

}
