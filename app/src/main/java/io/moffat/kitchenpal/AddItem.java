package io.moffat.kitchenpal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.parse.*;

import java.util.Date;

public class AddItem extends ActionBarActivity {


    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    final EditText ProductName;
       final EditText ISDN_text;
        final EditText expiry_date;



        setContentView(R.layout.activity_add_item);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Add Item");

    //    Spinner spinnercategory = (Spinner)findViewById(R.id.spinnercategory);
        String[] items = new String[]{"Meat", "Vegetables", "Ready Meal", "Juice", "Milk", "Leftovers"};
     //   ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
      //  spinnercategory.setAdapter(adapter);

        ProductName = (EditText) findViewById(R.id.ProductName);
        ISDN_text = (EditText)findViewById(R.id.ISDN);
        expiry_date = (EditText)findViewById(R.id.expiry_date);

        Button save = (Button) findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                ParseObject newProduct = new ParseObject("Product");


                newProduct.put("productName", ProductName.getText().toString());
                newProduct.put("ISDN", ISDN_text.getText().toString());
                newProduct.put("expiry", expiry_date.getText().toString());
                newProduct.put("type", "Liquid");
                newProduct.put("username", "Admin");
                newProduct.put("listFlag", "main");
                newProduct.put("shoppingList", false);
                newProduct.put("eaten", false);
                newProduct.put("discarded", false);
                newProduct.saveInBackground();


                String message = ProductName.getText().toString() + " added to list";

                Toast.makeText(getApplicationContext(), message,
                    Toast.LENGTH_SHORT).show();

                kill();


            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //don't really need anything in here, except colours. add barcode icon when i can
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
    }
    public void kill(){
        Intent i = new Intent(this, MainActivity.class);
       // i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        //finish();
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



}
