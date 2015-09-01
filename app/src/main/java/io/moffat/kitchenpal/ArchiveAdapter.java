package io.moffat.kitchenpal;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jordan on 03/08/2015.
 */
public class ArchiveAdapter extends ParseQueryAdapter<ParseObject> {

    public ArchiveAdapter(Context context){

        super(context, new QueryFactory<ParseObject>(){

            public ParseQuery create(){
                ParseQuery query = new ParseQuery("Product");

                query.whereEqualTo("username", ParseUser.getCurrentUser());
                //where mainlist and shopping list = false
                //where eaten or discarded = true
                query.orderByAscending("productName");
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
        timestampView.setText(object.getString("ISDN"));


        return v;
    }
}

