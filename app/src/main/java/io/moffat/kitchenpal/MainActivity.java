package io.moffat.kitchenpal;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

import com.parse.*;

public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    ImageButton FAB;
    public final static String EXTRA_MESSAGE = "io.moffat.kitchenpal.MESSAGE";
    private ParseQueryAdapter<ParseObject> mainAdapter;
    private ListView listView;
    private CustomAdapter newCustomAdapter;
    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        new ParseDataTask().execute();

        // refreshList();



        FAB = (ImageButton) findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddItem.class);
                intent.putExtra(EXTRA_MESSAGE, "new");
                startActivity(intent);

                //  Toast.makeText(MainActivity.this, "Hello World", Toast.LENGTH_SHORT).show();

            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kitchen Pal");


    }

    private class ParseDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Loading Products");
            mProgressDialog.setMessage("Loading....");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            refreshList();
            return null;
        }

        protected void onPostExecute(Void results) {
            mProgressDialog.dismiss();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.refresh:
                refreshList();
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshList(){
        mainAdapter = new ParseQueryAdapter<ParseObject>(this, "Product");
        mainAdapter.setTextKey("productName");

        newCustomAdapter = new CustomAdapter(this);

        listView = (ListView)findViewById(R.id.ProductView);
        listView.setAdapter(newCustomAdapter);

        newCustomAdapter.loadObjects();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ParseObject selected = (ParseObject) (listView.getItemAtPosition(position));
                String selectedID = selected.getObjectId();

                Toast.makeText(getApplicationContext(), selectedID,
                        Toast.LENGTH_SHORT).show();

              //  Intent i = new Intent(MainActivity.this, AddItem.class);
                //i.putExtra("ObjectID", selectedID);
             //   startActivity(i);
            }
        });
    }

}
