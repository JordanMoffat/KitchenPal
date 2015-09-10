package io.moffat.kitchenpal;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.*;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import com.parse.*;

import org.w3c.dom.Text;

public class MainActivity extends ActionBarActivity{

    private Toolbar toolbar;
   android.support.design.widget.FloatingActionButton FAB;
    public final static String EXTRA_MESSAGE = "io.moffat.kitchenpal.MESSAGE";
    private ParseQueryAdapter<ParseObject> mainAdapter;
    private ListView listView;
    private CustomAdapter newCustomAdapter;
    ProgressDialog progress;
  private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);


        TextView name = (TextView)findViewById(R.id.name);
        TextView email = (TextView)findViewById(R.id.header_email);
        String forename = ParseUser.getCurrentUser().get("forename").toString();
        String surname = ParseUser.getCurrentUser().get("Surname").toString();

        name.setText(forename + " " + surname);
        email.setText(ParseUser.getCurrentUser().getEmail());


     //   navList.setAdapter(navAdapter);

        refreshList();

        // refreshList();


        FAB = (FloatingActionButton) findViewById(R.id.fab);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddItem.class);
                //   intent.putExtra("flag", "main");
                startActivity(intent);

                //  Toast.makeText(MainActivity.this, "Hello World", Toast.LENGTH_SHORT).show();

            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kitchen Pal");
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        navigationView = (NavigationView)findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
        public boolean onNavigationItemSelected(MenuItem menuItem){
               // if(menuItem.isChecked()) menuItem.setChecked(false);
             //   else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()){

                    case R.id.add:
                        Intent i = new Intent(MainActivity.this, AddItem.class);
                        startActivity(i);
                        return true;
                    case R.id.barcodeadd:
                        Intent barcode = new Intent(MainActivity.this, BarcodeScanner.class);
                        startActivity(barcode);
                        return true;
                    case R.id.shoppinglist:
                        Intent shop = new Intent(MainActivity.this, ShoppingList.class);
                        startActivity(shop);
                        finish();
                        return true;
                    case R.id.archive:
                        Intent archive = new Intent(MainActivity.this, ArchiveActivity.class);
                        startActivity(archive);
                        finish();
                        return true;
                    case R.id.scales:
                        Toast.makeText(getApplicationContext(),"Feature not available in this version",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.LogOut:
                        ParseUser.logOut();
                        Intent logout = new Intent(MainActivity.this, LoginActivity2.class);
                        startActivity(logout);
                        return true;
                    case R.id.recipe:
                        Toast.makeText(getApplicationContext(),"Feature not available in this version",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
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
            case R.id.barcode:
                Intent i = new Intent(MainActivity.this, BarcodeScanner.class);
                startActivity(i);
                return true;
            case R.id.refresh:
                refreshList();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshList(){

        progress = ProgressDialog.show(MainActivity.this, "Loading Items", "Loading...", true);

        new Thread(new Runnable() {
            @Override
            public void run() {



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        mainAdapter = new ParseQueryAdapter<ParseObject>(MainActivity.this, "Product");
                        mainAdapter.setTextKey("productName");

                        newCustomAdapter = new CustomAdapter(MainActivity.this);

                        listView = (ListView)findViewById(R.id.ProductView);
                        listView.setAdapter(newCustomAdapter);

                        newCustomAdapter.loadObjects();


                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                ParseObject selected = (ParseObject) (listView.getItemAtPosition(position));

                                String objectId = selected.getObjectId();
                                Intent i = new Intent(MainActivity.this, EditItem.class);
                                i.putExtra("id", objectId);
                                startActivity(i);
                            }
                        });

                        progress.dismiss();
                    }
                });
            }
        }).start();
    }

    private void addDrawerItems(){

    }

}
