package io.moffat.kitchenpal;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

import com.parse.ParseException;


public class Register extends ActionBarActivity {

    EditText username;
    EditText password ;
    EditText confirmPassword;
    EditText email;
    EditText forename ;
    EditText surname ;
    EditText confirmEmail;

    private Boolean cancel = false;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Button registery = (Button) findViewById(R.id.btnRegister);
        registery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            cancel = false;


                username = (EditText) findViewById(R.id.editTextUsername);
                password = (EditText) findViewById(R.id.EditTextpassword);
                confirmPassword = (EditText) findViewById(R.id.editTextConfirmPass);
                email = (EditText) findViewById(R.id.editTextEmail);
                forename = (EditText) findViewById(R.id.editTextForename);
                surname = (EditText) findViewById(R.id.editTextSurname);
                confirmEmail = (EditText) findViewById(R.id.confirmEmail);


               resetFocus();
                resetError();



                if (password.getText().toString().equals("")){
                    cancel = true;
                    password.setError("Cannot be empty");
                }
                if(username.getText().toString().equals("")){
                    cancel = true;
                    username.setError("Cannot be empty");
                }
                if(email.getText().toString().equals("")){
                    cancel = true;
                    email.setError("Cannot be empty");
                }
                if(confirmPassword.getText().toString().equals("")){
                    cancel = true;
                    confirmPassword.setError("Cannot be empty");
                }
                if (forename.getText().toString().equals("")){
                    cancel = true;
                    forename.setError("Cannot be empty");
                }
                if (surname.getText().toString().equals("")){
                    cancel = true;
                    surname.setError("Cannot be empty");
                }

//                cancel = false;

                if (cancel == false) {
                    if (emailMatch(email.getText().toString(), confirmEmail.getText().toString()) ==true) {
                        if (passwordMatch(password.getText().toString(), confirmPassword.getText().toString()) == true) {
                            if (validEmail(email.getText().toString()) == true) {

                                RegisterUser(username.getText().toString(), password.getText().toString(), email.getText().toString(), forename.getText().toString(), surname.getText().toString());

                            } else {
                                email.setError("Not a valid email");
                                email.requestFocus();
                            }
                        } else {

                            //do feedback herepassword.setError("Passwords do not match");
                            password.requestFocus();
                        }
                    } else {
                        confirmEmail.setError("Email addresses do not match");
                        confirmEmail.requestFocus();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void RegisterUser(String username, String password, String email, String forename, String Surname){

        final ProgressDialog spin = new ProgressDialog(Register.this);
        spin.setTitle("Signing Up");
        spin.setMessage("Please wait...");
        spin.show();

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

      //  user.put("forename", forename);
      //  user.put("surname", Surname);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    Toast.makeText(getApplicationContext(), "Sign Up Successful! Welcome " + ParseUser.getCurrentUser().getUsername().toString(),
                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Register.this, LoginActivity2.class);
                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Error signing up",
                            Toast.LENGTH_SHORT).show();

                    System.out.println(e);
                }

            }
        });

        spin.dismiss();
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

    public boolean passwordMatch(String password, String confirm){

        if (password.equals(confirm)){
            return true;
        } else {
            return false;
        }

    }

    public boolean validEmail(String email){

        if (email.contains("@") && (email.contains("."))){
            return true;
        } else {
            return false;
        }

    }

    public boolean emailMatch(String email, String confirmEmail){

        if (email.equals(confirmEmail)){
            return true;
        } else {
            return false;
        }

    }

    public void resetError(){
        username.setError(null);
        password.setError(null);
        confirmPassword.setError(null);
        email.setError(null);
        forename.setError(null);
        surname.setError(null);
        confirmEmail.setError(null);

    }

    public void resetFocus(){
        username.clearFocus();
        password.clearFocus();
        confirmPassword.clearFocus();
        email.clearFocus();
        forename.clearFocus();
        surname.clearFocus();
        confirmEmail.clearFocus();
    }
}
