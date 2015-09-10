package io.moffat.kitchenpal;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


public class RecipeSearch extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);
        getSupportActionBar().setHomeButtonEnabled(true);

        ListView recipes = (ListView)findViewById(R.id.recipeView);
        recipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get item location
                //get individual URL
                //pass url via intent
                //open activity and do stuff there
            }
        });


        //do url build here
        String product = new String();

        SearchBuilder build = new SearchBuilder();
        String url = build.builtURL(product);

        new Populate().execute(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_search, menu);
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

    class Populate extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            //show progress dialog
        }

        @Override
        protected String doInBackground(String... urls) {
            String chc = "";
            //do http request and add objects to array here.
            //need to do a custom adapter
            return "";
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            //setadapter here
            //arrayadapter
            //listView.setAdapter(adapter);


        }
    }
}
