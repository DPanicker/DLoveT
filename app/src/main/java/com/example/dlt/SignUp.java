package com.example.dlt;

/*import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignUp extends AppCompatActivity {

    private EditText edtEmail1;
    private EditText edtPassword1;
    private Button btSignUp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        final DatabaseHelper dbHelper = new DatabaseHelper(this);
        btSignUp = findViewById(R.id.btSignup);
        edtEmail1 = findViewById(R.id.emailinput1);
        edtPassword1 = findViewById(R.id.passwordinput1);
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyValidation()) {
                    dbHelper.addUser(new User(edtEmail1.getText().toString(), edtPassword1.getText().toString()));
                    Toast.makeText(SignUp.this, "Added User", Toast.LENGTH_SHORT).show();
                    edtEmail1.setText("");
                    edtPassword1.setText("");
                }else{
                    Toast.makeText(SignUp.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private boolean emptyValidation() {
        if (TextUtils.isEmpty(edtEmail1.getText().toString()) || TextUtils.isEmpty(edtPassword1.getText().toString())) {
            return true;
        }else {
            return false;
        }
    }
} */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUp  extends AppCompatActivity implements View.OnClickListener {


    //EditText editTextItemName,editTextBrand;
    //Button buttonAddItem;
     EditText edtEmail1;
     EditText edtPassword1;
     Button btSignUp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);

        edtEmail1 = (EditText)findViewById(R.id.emailinput1);
        edtPassword1 = (EditText)findViewById(R.id.passwordinput1);

        btSignUp = (Button)findViewById(R.id.btSignup);
        btSignUp.setOnClickListener(this);


    }

    //This is the part where data is transferred from Your Android phone to Sheet by using HTTP Rest API calls

    private void   addItemToSheet() {

        final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");
        final String name = edtEmail1.getText().toString().trim();
        final String pass = edtPassword1.getText().toString().trim();




        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbwQZ99eLDwSIG7GHV5nR4JbkLqD46eQd_oEmcwm5qoCZHvMwzA/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(SignUp.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","addItem");
                parmas.put("itemName",name);
                parmas.put("brand",pass);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }




    @Override
    public void onClick(View v) {

        if(v==btSignUp){
            addItemToSheet();

            //Define what to do when button is clicked
        }
    }
}
