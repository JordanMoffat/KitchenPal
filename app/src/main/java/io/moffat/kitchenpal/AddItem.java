package io.moffat.kitchenpal;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.parse.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//UPCdatabase.org

public class AddItem extends ActionBarActivity {


    private Toolbar toolbar;
    private Date expiry;
    Calendar myCalendar = Calendar.getInstance();
    private Button search;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_item);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Add Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        onCreateHandler();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //don't really need anything in here, except colours. add barcode icon when i can
        getMenuInflater().inflate(R.menu.menu_add_item, menu);

        return true;
    }

    public void kill() {
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
        switch (item.getItemId()){
            case R.id.barcode:
                Intent i = new Intent(AddItem.this, BarcodeScanner.class);
                startActivity(i);

            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        EditText update = (EditText) findViewById(R.id.expiry_date);

        update.setText(sdf.format(myCalendar.getTime()));
    }

    public void onCreateHandler() {

        final EditText ProductName;
        final EditText ISDN_text;
        final EditText expiry_date;

        final Spinner spinnercategory = (Spinner) findViewById(R.id.spinnercategory);
        String[] items = new String[]{"Meat", "Vegetables", "Ready Meal", "Juice", "Milk", "Leftovers", "Dairy", "Sunderies", "Snacks", "Bread"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        spinnercategory.setAdapter(adapter);

        final CheckBox listFlag = (CheckBox) findViewById(R.id.shoppingCheckBox);
        //Intent i = getIntent();
      //  String intentFlag = i.getStringExtra("flag");


        Intent i =getIntent();
        if (i.hasExtra("flag")){

            //   EditText code = (EditText) findViewById(R.id.ISDN);
            URLBuilder url = new URLBuilder();

            String barcodeString = i.getStringExtra("barcode");

            String message = url.builtURL(barcodeString);
            new BarcodeSearch().execute(message);

        }

            search = (Button) findViewById(R.id.btnSearch);
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do rest stuff here
                }
            });

            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

            };

            ProductName = (EditText) findViewById(R.id.ProductName);
            ISDN_text = (EditText) findViewById(R.id.ISDN);
            expiry_date = (EditText) findViewById(R.id.expiry_date);
            final EditText quantity = (EditText) findViewById(R.id.quantity);


            expiry_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new DatePickerDialog(AddItem.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                }
            });

            Button save = (Button) findViewById(R.id.btnSave);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String dateString = expiry_date.getText().toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date convertedDate = new Date();

                    try {
                        convertedDate = dateFormat.parse(dateString);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }


                    ParseObject newProduct = new ParseObject("Product");



                    CheckBox mainFlag = (CheckBox) findViewById(R.id.mainCheckBox);


                    if (listFlag.isChecked() && mainFlag.isChecked()) {

                        newProduct.put("productName", ProductName.getText().toString());
                        newProduct.put("ISDN", ISDN_text.getText().toString());
                        newProduct.put("expiry", convertedDate);
                        newProduct.put("type", spinnercategory.getSelectedItem().toString());
                        newProduct.put("quantity", quantity.getText().toString());
                        newProduct.put("username", "Admin");
                        newProduct.put("shoppingList", true);
                        newProduct.put("mainList", true);
                        newProduct.put("eaten", false);
                        newProduct.put("discarded", false);
                        ProgressDialog SaveProgress = new ProgressDialog(AddItem.this);

                        SaveProgress.setTitle("Saving Product");
                        SaveProgress.setMessage("Saving...");
                        SaveProgress.show();

                        newProduct.saveInBackground();
                        SaveProgress.dismiss();

                        String message = ProductName.getText().toString() + " added to list";

                        Toast.makeText(getApplicationContext(), message,
                                Toast.LENGTH_SHORT).show();

                        kill();

                    } else if (!listFlag.isChecked() && mainFlag.isChecked()) {
                        newProduct.put("productName", ProductName.getText().toString());
                        newProduct.put("ISDN", ISDN_text.getText().toString());
                        newProduct.put("expiry", convertedDate);
                        newProduct.put("type", spinnercategory.getSelectedItem().toString());
                        newProduct.put("quantity", quantity.getText().toString());
                        newProduct.put("username", "Admin");
                        newProduct.put("mainList", true);
                        newProduct.put("shoppingList", false);
                        newProduct.put("eaten", false);
                        newProduct.put("discarded", false);

                        ProgressDialog SaveProgress = new ProgressDialog(AddItem.this);

                        SaveProgress.setTitle("Saving Product");
                        SaveProgress.setMessage("Saving...");
                        SaveProgress.show();

                        newProduct.saveInBackground();
                        SaveProgress.dismiss();

                        String message = ProductName.getText().toString() + " added to list";

                        Toast.makeText(getApplicationContext(), message,
                                Toast.LENGTH_SHORT).show();

                        kill();
                    } else if (listFlag.isChecked() && !mainFlag.isChecked()) {
                        newProduct.put("productName", ProductName.getText().toString());
                        newProduct.put("ISDN", ISDN_text.getText().toString());
                        newProduct.put("expiry", convertedDate);
                        newProduct.put("type", spinnercategory.getSelectedItem().toString());
                        newProduct.put("quantity", quantity.getText().toString());
                        newProduct.put("username", "Admin");
                        newProduct.put("eaten", false);
                        newProduct.put("discarded", false);
                        newProduct.put("mainList", false);
                        newProduct.put("shoppingList", true);

                        ProgressDialog SaveProgress = new ProgressDialog(AddItem.this);

                        SaveProgress.setTitle("Saving Product");
                        SaveProgress.setMessage("Saving...");
                        SaveProgress.show();

                        newProduct.saveInBackground();
                        SaveProgress.dismiss();

                        String message = ProductName.getText().toString() + " added to list";

                        Toast.makeText(getApplicationContext(), message,
                                Toast.LENGTH_SHORT).show();

                        kill();

                    } else if (!listFlag.isChecked() && !mainFlag.isChecked()) {
                        Toast.makeText(getApplicationContext(), "List not selected!",
                                Toast.LENGTH_SHORT).show();
                    }

                    // newProduct.put("mainList", true);


                }
            });

            Button search = (Button) findViewById(R.id.btnSearch);
            search.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {


                    EditText code = (EditText) findViewById(R.id.ISDN);
                    URLBuilder url = new URLBuilder();
                    String barcodeString = code.getText().toString();

                    String message = url.builtURL(barcodeString);
                    new BarcodeSearch().execute(message);
                }


            });

    }

        class BarcodeSearch extends AsyncTask<String, Void, JSONObject> {

            protected void onPreExecute() {
                progress = ProgressDialog.show(AddItem.this, "Finding Barcode", "Searching....", true);
            }

            @Override
            protected JSONObject doInBackground(String... urls) {



                JSONObject jsonProduct = new JSONObject();
                JSONParser barcodeParse = new JSONParser();
                String restParse = barcodeParse.getJSON(urls[0]);
                try {
                    JSONArray objectArray = new JSONArray(restParse);

                    jsonProduct = objectArray.getJSONObject(0);

                    return jsonProduct;
                    //do some shit here to turn json array into json object

                }catch(Exception e)
                {
                    System.out.println(e);
                    //return restParse;
                    return null;
                }
               // return jsonObject;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                //  TextView tv = (TextView) findViewById(R.id.txtView);
                // tv.setText(jsonObject.toString());
                if(jsonObject.has("error")){
                    EditText name = (EditText)findViewById(R.id.ProductName);
                    Toast.makeText(getApplicationContext(), "Barcode not found in Database",
                            Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                } else if (jsonObject.has("name") && (jsonObject.has("ean"))) {

                    try {
                        EditText name = (EditText) findViewById(R.id.ProductName);
                        name.setText(jsonObject.getString("name"));
                        EditText barcode = (EditText) findViewById(R.id.ISDN);
                        barcode.setText(jsonObject.getString("ean"));

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                progress.dismiss();
            }
        }

}
