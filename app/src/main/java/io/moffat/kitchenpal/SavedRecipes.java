package io.moffat.kitchenpal;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

import org.json.JSONException;
import org.json.JSONObject;


public class SavedRecipes extends ActionBarActivity {

    private SavedRecipeAdapter savedRecipe;
    private ParseQueryAdapter<ParseObject> mainAdapter;
    private ListView listView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipes);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Saved Recipes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      create();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_recipes, menu);
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

    public void create(){

        mainAdapter = new ParseQueryAdapter<ParseObject>(SavedRecipes.this, "recipe");


        savedRecipe = new SavedRecipeAdapter(SavedRecipes.this);

        listView = (ListView)findViewById(R.id.RecipeView);
        listView.setAdapter(savedRecipe);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ParseObject selected = (ParseObject) (listView.getItemAtPosition(position));

                Intent i = new Intent(Intent.ACTION_VIEW);


                String url = selected.getString("source_url");
                i.setData(Uri.parse(url));
                startActivity(i);
                startActivity(i);
            }
        });

        savedRecipe.loadObjects();
    }
}
