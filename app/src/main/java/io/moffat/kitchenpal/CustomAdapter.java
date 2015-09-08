package io.moffat.kitchenpal;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Jordan on 03/08/2015.
 */
public class CustomAdapter extends ParseQueryAdapter<ParseObject> {

    public CustomAdapter(Context context){

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>(){

            public ParseQuery create(){
                ParseQuery query = new ParseQuery("Product");
                query.whereEqualTo("username", ParseUser.getCurrentUser());
                query.whereEqualTo("mainList", true);
                query.whereEqualTo("eaten", false);
                query.whereEqualTo("discarded", false);
                query.orderByAscending("expiry");
                return query;

            }
        });
    }

    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.parse_item, null);
        }


        super.getItemView(object, v, parent);


        TextView titleTextView = (TextView) v.findViewById(R.id.text1);
        titleTextView.setText(object.getString("productName"));



        TextView timestampView = (TextView) v.findViewById(R.id.timestamp);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        TimeZone timeZone = TimeZone.getDefault();
        sdf.setTimeZone(timeZone);
        String date = sdf.format(Calendar.getInstance().getTime());
        Date datecomp = new Date();
        datecomp.getDate();

        int check = object.getDate("expiry").compareTo(datecomp);

        if (check < 0) {
            Format formatter = new SimpleDateFormat("dd/MM/yyyy");
            String s = formatter.format(object.getDate("expiry"));
            timestampView.setTextColor(Color.RED);
            timestampView.setText("Expired on "+ s);
        } else if (check > 0) {
            Format formatter = new SimpleDateFormat("dd/MM/yyyy");
            String s = formatter.format(object.getDate("expiry"));
            timestampView.setTextColor(Color.parseColor("#64DD17"));
            timestampView.setText("Expires: " + s);
    }



        return v;
    }


}

