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
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import java.text.ParseException;


public class ForgottenPassword extends ActionBarActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button resetButton = (Button) findViewById(R.id.btnReset);



        resetButton.setOnClickListener(new View.OnClickListener() {



            EditText email = (EditText) findViewById(R.id.emailText);


            @Override
            public void onClick(View v) {

                    ParseUser.requestPasswordResetInBackground(email.getText().toString(), new RequestPasswordResetCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {


                            Toast.makeText(getApplicationContext(), "Password Email Sent",
                                    Toast.LENGTH_SHORT).show();

                            Intent newIntent = new Intent(ForgottenPassword.this, LoginActivity2.class);
                            startActivity(newIntent);
                        }
                    });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forgotten_password, menu);
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
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public boolean isEmailValid(String email) {
      if (email.contains("@")){
          return true;
      } else {
          return false;
      }
      }
}
