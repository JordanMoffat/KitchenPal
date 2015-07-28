package io.moffat.kitchenpal;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toolbar;

import com.parse.*;

import java.util.Date;

public class AddItem extends ActionBarActivity {

    EditText editText;
    EditText ISDN;
    EditText ExpiryDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Spinner spinnercategory = (Spinner)findViewById(R.id.spinnercategory);
        String[] items = new String[]{"Meat", "Vegetables", "Ready Meal", "Juice", "Milk", "Leftovers"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        spinnercategory.setAdapter(adapter);

        Button save = (Button) findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseObject Product = new ParseObject("Product");

          EditText nameText = (EditText) findViewById(R.id.editText);
             String nameString = editText.getText().toString();

             EditText barcodeText = (EditText) findViewById(R.id.ISDN);
             String barcodeString = ISDN.getText().toString();


                Product.put("productname", nameString);
                Product.put("ISDN", barcodeString);
                Product.put("Expiry", "24-8-2015T00:00:00Z");
                Product.put("Type", "Liquid");
                Product.put("Username", "Admin");
                Product.saveInBackground();
                //Product.put();



                Intent i = new Intent(AddItem.this, MainActivity.class);
                startActivity(i);
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


    public void onClick(){


    ParseObject SaveData = new ParseObject("Save");

  //      EditText nameText = (EditText) findViewById(R.id.editText);
    //    String nameString = editText.getText().toString();

     //   EditText barcodeText = (EditText) findViewById(R.id.ISDN);
      //  String barcodeString = ISDN.getText().toString();

   //     EditText expiryText = (EditText) findViewById(R.id.ExpiryDate);
    //    int day = ExpiryDate.getDayOfMonth();

      //  Date exdt = new Date(ExpiryDate.getDa)
     //   Date expireDate = ExpiryDate.getText().






       // String nametext = (EditText) findViewById(R.id.editText);

     //   String prodname = editText.getText().ToString();

        //get info from boxes and set to parse object
        //also might need to get an actionlistener for the class types
        //save item here

    }
}
