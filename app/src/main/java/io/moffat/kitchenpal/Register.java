package io.moffat.kitchenpal;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

import com.parse.ParseException;


public class Register extends ActionBarActivity {

    final EditText username = (EditText) findViewById(R.id.editTextUsername);
    final EditText password = (EditText) findViewById(R.id.password);
    final EditText confirmPassword = (EditText) findViewById(R.id.confirmEmail);
    final EditText email = (EditText) findViewById(R.id.emailText);
    final EditText forename = (EditText) findViewById(R.id.editTextForename);
    final EditText surname = (EditText) findViewById(R.id.editTextSurname);
    private Boolean cancel;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Button registery = (Button) findViewById(R.id.btnRegister);
        registery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(password.getText())){
                    cancel = true;
                    password.setError("Cannot be empty");
                }
                if(TextUtils.isEmpty(username.getText())){
                    cancel = true;
                    username.setError("Cannot be empty");
                }
                if(TextUtils.isEmpty(email.getText())){
                    cancel = true;
                    email.setError("Cannot be empty");
                }
                if(TextUtils.isEmpty(confirmPassword.getText())){
                    cancel = true;
                    confirmPassword.setError("Cannot be empty");
                }
                if (TextUtils.isEmpty(forename.getText())){
                    cancel = true;
                    forename.setError("Cannot be empty");
                }
                if (TextUtils.isEmpty(surname.getText())){
                    cancel = true;
                    surname.setError("Cannot be empty");
                }

                if (!cancel) {
                    if (passwordMatch() == true) {
                        if (validEmail() == true) {
                            ParseUser user = new ParseUser();
                            user.setUsername(username.getText().toString());
                            user.setPassword(password.getText().toString());
                            user.setEmail(email.getText().toString());

                            user.put("forename", forename.getText().toString());
                            user.put("surname", surname.getText().toString());

                            user.signUpInBackground(new SignUpCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Intent i = new Intent(Register.this, LoginActivity2.class);
                                        startActivity(i);
                                    } else {
                                        //error handling here
                                    }

                                }
                            });
                        } else {
                            password.setError("Passwords do not match");
                            password.requestFocus();
                        }
                    } else {
                        email.setError("Not a valid email");
                        email.requestFocus();
                        //do feedback here
                    }
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean passwordMatch(){

        if (password.getText().toString() == confirmPassword.getText().toString()){
            return true;
        } else {
            return false;
        }

    }

    public boolean validEmail(){

        if (email.getText().toString().contains("@")){
            return true;
        } else {
            return false;
        }

    }
}
