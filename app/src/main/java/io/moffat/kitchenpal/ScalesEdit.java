package io.moffat.kitchenpal;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//UPCdatabase.org

public class ScalesEdit extends ActionBarActivity {


    private Toolbar toolbar;
    private Date expiry;
    Calendar myCalendar = Calendar.getInstance();
    private Button search;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scales_edit);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Edit Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onCreateHandler();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //don't really need anything in here, except colours. add barcode icon when i can
        getMenuInflater().inflate(R.menu.menu_scales_edit, menu);

        return true;
    }

    public void kill() {

        finish();
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
            case R.id.clear:
                EditText name = (EditText)findViewById(R.id.ProductName);
                EditText quan = (EditText)findViewById(R.id.quantity);
                EditText isdn = (EditText)findViewById(R.id.ISDN);
                EditText date = (EditText)findViewById(R.id.expiry_date);

                name.setText("");
                quan.setText("");
                isdn.setText("");
                date.setText("");

                return true;
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.search:
                Intent search = new Intent (ScalesEdit.this, RecipeSearch.class);
                EditText product = (EditText)findViewById(R.id.ProductName);
                search.putExtra("ingredient", product.getText().toString());
                startActivity(search);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        TimeZone tz = TimeZone.getTimeZone("GMT0");
        sdf.setTimeZone(tz);

        EditText update = (EditText) findViewById(R.id.expiry_date);

        update.setText(sdf.format(myCalendar.getTime()));
    }

    public void onCreateHandler() {

        final EditText ProductName;
        final EditText ISDN_text;
        final EditText expiry_date;

        final Spinner spinnercategory = (Spinner) findViewById(R.id.spinnercategory);
        final String[] items = new String[]{"Juice", "Dairy"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        spinnercategory.setAdapter(adapter);

        final Spinner quantityCategory = (Spinner) findViewById(R.id.measureSpinner);
        final String[] quantities = new String[]{"ml", "L"};
        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, quantities);
        quantityCategory.setAdapter(quantityAdapter);


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

                new DatePickerDialog(ScalesEdit.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        Button save = (Button) findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("scales");
                query.getInBackground("sBdw9q2dqH", new GetCallback<ParseObject>() {
                    public void done(ParseObject editProduct, ParseException e) {
                        if (e == null) {

                            editProduct.put("name", ProductName.getText().toString());
                            editProduct.put("ISDN", ISDN_text.getText().toString());

                            String dateString = expiry_date.getText().toString();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date convertedDate = new Date();
                            TimeZone tz = TimeZone.getTimeZone("GMT0");
                            dateFormat.setTimeZone(tz);

                            try {
                                convertedDate = dateFormat.parse(dateString);
                            } catch (java.text.ParseException x) {
                                e.printStackTrace();
                            }
                            editProduct.put("expiry", convertedDate);
                            editProduct.put("type", spinnercategory.getSelectedItem().toString());
                            editProduct.put("quantity", Float.parseFloat(quantity.getText().toString()));
                            //  editProduct.put("username", ParseUser.getCurrentUser());
                            editProduct.put("unit", quantityCategory.getSelectedItem().toString());

                            editProduct.saveInBackground();
                            Toast.makeText(getApplicationContext(), "Saved",
                                    Toast.LENGTH_SHORT).show();

                            kill();
                        } else {


                            Toast.makeText(getApplicationContext(), "Error Saving",
                                    Toast.LENGTH_SHORT).show();
                            // Now let's update it with some new data. In this case, only cheatMode and score
                            // will get sent to the Parse Cloud. playerName hasn't changed.


                        }
                    }
                });
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

        //   Intent i = getIntent();
        //  if (i.hasExtra("id")){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("scales");
        query.getInBackground("sBdw9q2dqH", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    //   parseObject.getString("productName");
                    ProductName.setText(object.getString("name"));

                    //Sets the category spinner
                    int index = -1;
                    for (int i = 0; i < items.length; i++) {
                        if (items[i].equals(object.getString("type"))) {
                            index = i;
                            break;
                        }
                    }
                    if (index >= 0) {
                        spinnercategory.setSelection(index);
                    } else {
                        spinnercategory.setSelection(0);
                    }

                    int UnitIndex = -1;
                    for (int j = 0; j < quantities.length; j++) {
                        if (quantities[j].equals(object.getString("unit"))) {
                            UnitIndex = j;
                            break;
                        }
                    }
                    if (UnitIndex >= 0) {
                        quantityCategory.setSelection(UnitIndex);
                    } else {
                        quantityCategory.setSelection(0);
                    }
                    //sets the Barcode number and the quantity
                    ISDN_text.setText(object.getString("ISDN"));
                    float q = object.getNumber("quantity").floatValue();
                    String s1 = Float.toString(q);
                    quantity.setText(s1);

                    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String s = formatter.format(object.getDate("expiry"));
                    expiry_date.setText(s);

                } else {

                }
            }
        });
    }


    class BarcodeSearch extends AsyncTask<String, Void, JSONObject> {

        protected void onPreExecute() {
            progress = ProgressDialog.show(ScalesEdit.this, "Finding Barcode", "Searching....", true);
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

            } catch (Exception e) {
                System.out.println(e);
                //return restParse;
                return null;
            }
            // return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            if (jsonObject.has("error")) {
                EditText name = (EditText) findViewById(R.id.ProductName);
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
