package io.moffat.kitchenpal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import android.support.design.widget.FloatingActionButton;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

public class ShoppingList extends ActionBarActivity {

    private Toolbar toolbar;
    FloatingActionButton FAB;
    public final static String EXTRA_MESSAGE = "io.moffat.kitchenpal.MESSAGE";
    private ParseQueryAdapter<ParseObject> mainAdapter;
    private ListView listView;
    private ShoppingListAdapter newCustomAdapter;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_activity);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
