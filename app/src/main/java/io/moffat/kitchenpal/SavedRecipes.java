package io.moffat.kitchenpal;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;


public class SavedRecipes extends ActionBarActivity {

    private SavedRecipeAdapter savedRecipe;
    private ParseQueryAdapter<ParseObject> mainAdapter;
    private ListView listView;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipes);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Saved Recipes");
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        navigationView = (NavigationView)findViewById(R.id.navigation_view);


        TextView name = (TextView)findViewById(R.id.name);
        TextView email = (TextView)findViewById(R.id.header_email);
        String forename = ParseUser.getCurrentUser().get("forename").toString();
        String surname = ParseUser.getCurrentUser().get("Surname").toString();

        name.setText(forename + " " + surname);
        email.setText(ParseUser.getCurrentUser().getEmail());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // if(menuItem.isChecked()) menuItem.setChecked(false);
                //   else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {


                    case R.id.srshoppingList:
                        Intent shop = new Intent(SavedRecipes.this, ShoppingList.class);
                        startActivity(shop);
                        finish();
                        return true;
                    case R.id.srarchivelist:
                        Intent archive = new Intent(SavedRecipes.this, ArchiveActivity.class);
                        startActivity(archive);
                        finish();
                        return true;
                    case R.id.srscales:
                        Toast.makeText(getApplicationContext(), "Feature not available in this version", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.LogOut:
                        ParseUser.logOut();
                        Intent logout = new Intent(SavedRecipes.this, LoginActivity2.class);
                        startActivity(logout);
                        finish();
                        return true;
                    case R.id.srmainList:
                        Intent recipe = new Intent(SavedRecipes.this, MainActivity.class);
                        startActivity(recipe);
                        finish();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }

        });
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.openDrawer,
                R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView){

                super.onDrawerOpened(drawerView);
            }

        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


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
        switch (item.getItemId()) {
            case R.id.refresh:
                create();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume(){
        super.onResume();
        create();
    }

    public void create(){

        mainAdapter = new ParseQueryAdapter<ParseObject>(SavedRecipes.this, "recipe");
        mainAdapter.setTextKey("title");


        savedRecipe = new SavedRecipeAdapter(SavedRecipes.this);

        listView = (ListView)findViewById(R.id.RecipeView);
        listView.setAdapter(savedRecipe);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ParseObject selected = (ParseObject) (listView.getItemAtPosition(position));

                Intent i = new Intent(Intent.ACTION_VIEW);


                String url = selected.getString("source");
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });



        savedRecipe.loadObjects();
    }
}
