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
                    expiry.setText(s);

                    Format formatter2 = new SimpleDateFormat("dd/MM/yyyy");
                    String s2 = formatter2.format(object.getDate("updatedAt"));
                    added.setText(s2);


                    String qwan = object.getNumber("original").toString() + object.getString("unit");
                   original.setText(qwan);

                    if(object.getString("unit").equals("L")){
                        int value = object.getNumber("original").intValue();
                        value = (value * 1000);
                        float per = 0;
                        per = object.getNumber("weight").floatValue();
                        float percent = (value /100)*per;
                        String str = Float.toString(percent);
                        percentage.setText(str);

                        category.setText("category");

                    }

                    // object will be your game score
                } else {
                    // something went wrong
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
    }
}
