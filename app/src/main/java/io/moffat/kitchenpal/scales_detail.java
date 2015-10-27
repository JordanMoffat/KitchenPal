package io.moffat.kitchenpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class scales_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scales_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        final TextView isdn = (TextView)findViewById(R.id.scalesISDN);
        final TextView expiry = (TextView) findViewById(R.id.scalesExpiry);
        final   TextView reading = (TextView) findViewById(R.id.scalesreading);
        final   TextView percentage = (TextView) findViewById(R.id.percentage);
        final   TextView original = (TextView) findViewById(R.id.volume);
        final TextView category = (TextView)findViewById(R.id.category);
        final  TextView added = (TextView) findViewById(R.id.added);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("scales");
        query.getInBackground("sBdw9q2dqH", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    toolBarLayout.setTitle(object.getString("name"));
                    isdn.setText(object.getString("ISDN"));

                    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String s = formatter.format(object.getDate("expiry"));
                    expiry.setText("Expires: " + s);

                    Format formatter2 = new SimpleDateFormat("dd/MM/yyyy");

                    String s2 = formatter2.format(object.getUpdatedAt());
                    added.setText("Added on " + s2);




                    String weightstring = (object.getString("weight") + "ml");
                    reading.setText(weightstring);

                    String qwan = (object.getNumber("quantity").toString() + object.getString("unit"));
                    original.setText(qwan);

                    if(object.getString("unit").equals("L")){
                        float original = object.getNumber("quantity").floatValue();
                        original = (original*1000);

                        float reading = Float.valueOf(object.getString("weight"));

                        float total = ((reading/original)*100);

                        if (total > 100){
                            total = 100;
                        }
                        String out = Float.toString(total);



                        percentage.setText(out + "%");


                    } else {
                        float original = object.getNumber("quantity").floatValue();
                       // original = (original*1000);

                        float reading = Float.valueOf(object.getString("weight"));

                        float total = ((reading/original)*100);
                        if (total > 100){
                            total = 100;
                        }

                        String out = Float.toString(total);


                        percentage.setText(out + "%");
                    }

                    category.setText(object.getString("type"));
               //     added.setText(object.getDate("updatedAt").toString());

                    //somethings not working here

                    // object will be your game score
                } else {
                    toolBarLayout.setTitle("Error");
                }
            }
        });








        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(scales_detail.this, ScalesEdit.class);
                startActivity(edit);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
