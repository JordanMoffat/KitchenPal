package io.moffat.kitchenpal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RecipeSearch extends ActionBarActivity {

    ProgressDialog progress;
    RecipeAdapter recipeAdapter;
    private Toolbar toolbar;
 //   JSONArray jArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);

        Intent i = getIntent();
        String intentstring = i.getStringExtra("ingredient");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Recipes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //do url build here
        String product = new String();

        product = i.getStringExtra("ingredient");
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

    class Populate extends AsyncTask<String, String, JSONArray> {

        protected void onPreExecute() {
            progress = ProgressDialog.show(RecipeSearch.this, "Finding Recipes", "Searching....", true);
        }

        @Override
        protected JSONArray doInBackground(String... urls) {
            JSONParser recipeParse = new JSONParser();
            String rawJSON = recipeParse.getJSON(urls[0]);

            try {

                if (rawJSON != null) {
                    JSONObject object = new JSONObject(rawJSON);
                    JSONArray jArray = object.getJSONArray("recipes");
                    return jArray;
                } else {
                    JSONArray jArray = null;
                    return jArray;
                }

            } catch(Exception e) {
                System.out.println(e);
                Toast.makeText(getApplicationContext(), "Error Searching for Recipes",
                        Toast.LENGTH_SHORT).show();

             JSONArray jArray = null;
                return jArray;
            }
            //do http request and add objects to array here.
            //need to do a custom adapter
           // return null;
        }

        @Override
        protected void onPostExecute(JSONArray jArray) {

            if (jArray != null) {
                final ListView recipes = (ListView) findViewById(R.id.recipeView);

                recipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        JSONObject selected = (JSONObject) (recipes.getItemAtPosition(position));

                        String url = null;
                        try {
                            url = selected.getString("source_url");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);

                    }
                });
               final String tag ="";
                recipes.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {


                /*        Picasso picasso = Picasso.with(getApplicationContext());
                        if (scrollState == SCROLL_STATE_IDLE ||
                                scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                            picasso.resumeTag(tag);
                        } else {
                            picasso.pauseTag(tag);
                        }
*/                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                    //    Picasso picasso = Picasso.with(getApplicationContext());
                      //  picasso.pauseTag(tag);
                    }
                });

                recipeAdapter = new RecipeAdapter(RecipeSearch.this, jArray);//jArray is your json array
                recipes.setAdapter(recipeAdapter);

            } else {
                Toast.makeText(getApplicationContext(), "No Recipes Found", Toast.LENGTH_SHORT).show();
                finish();
            }



            progress.dismiss();
        }
    }
}
