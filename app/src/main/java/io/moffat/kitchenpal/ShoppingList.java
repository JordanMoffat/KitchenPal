package io.moffat.kitchenpal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class ShoppingList extends ActionBarActivity {

    private Toolbar toolbar;
    FloatingActionButton FAB;
    public final static String EXTRA_MESSAGE = "io.moffat.kitchenpal.MESSAGE";
    private ParseQueryAdapter<ParseObject> mainAdapter;
    private ListView listView;
    private ShoppingListAdapter newCustomAdapter;
    ProgressDialog progress;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_activity);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        TextView name = (TextView)findViewById(R.id.name);
        TextView email = (TextView)findViewById(R.id.header_email);
        String forename = ParseUser.getCurrentUser().get("forename").toString();
        String surname = ParseUser.getCurrentUser().get("Surname").toString();

        name.setText(forename + " " + surname);
        email.setText(ParseUser.getCurrentUser().getEmail());

        refreshList();

        // refreshList();



        FAB = (FloatingActionButton) findViewById(R.id.fab);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShoppingList.this, AddItem.class);
                startActivity(intent);


                //  Toast.makeText(MainActivity.this, "Hello World", Toast.LENGTH_SHORT).show();

            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shopping List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        navigationView = (NavigationView)findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // if(menuItem.isChecked()) menuItem.setChecked(false);
                //   else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {

                    case R.id.add:
                        Intent i = new Intent(ShoppingList.this, AddItem.class);
                        startActivity(i);
                        return true;
                    case R.id.barcodeadd:
                        Intent barcode = new Intent(ShoppingList.this, BarcodeScanner.class);
                        startActivity(barcode);
                        return true;
                    case R.id.mainList:
                        Intent main = new Intent(ShoppingList.this, MainActivity.class);
                        startActivity(main);
                        return true;
                    case R.id.archive:
                        Toast.makeText(getApplicationContext(), "Feature not available in this version", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.scales:
                        Toast.makeText(getApplicationContext(), "Feature not available in this version", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.LogOut:
                        ParseUser.logOut();
                        Intent logout = new Intent(ShoppingList.this, LoginActivity2.class);
                        startActivity(logout);
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

        switch (item.getItemId()) {
            case R.id.refresh:
                refreshList();


            case android.R.id.home:
                this.finish();
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }

    }

    public void refreshList(){

        progress = ProgressDialog.show(ShoppingList.this, "Loading Items", "Loading...", true);

        new Thread(new Runnable() {
            @Override
            public void run() {



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        mainAdapter = new ParseQueryAdapter<ParseObject>(ShoppingList.this, "Product");
                        mainAdapter.setTextKey("productName");

                        newCustomAdapter = new ShoppingListAdapter(ShoppingList.this);

                        listView = (ListView)findViewById(R.id.ShoppingListView);
                        listView.setAdapter(newCustomAdapter);

                        newCustomAdapter.loadObjects();


                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                ParseObject selected = (ParseObject) (listView.getItemAtPosition(position));

                                String objectId = selected.getObjectId();
                                Intent i = new Intent(ShoppingList.this, ShoppingListEdit.class);
                                i.putExtra("id", objectId);
                                startActivity(i);

                            }
                        });

                        progress.dismiss();
                    }
                });
            }
        }).start();

     //   mainAdapter = new ParseQueryAdapter<ParseObject>(this, "Product");



    }

}
