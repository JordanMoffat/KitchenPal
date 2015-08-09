package io.moffat.kitchenpal;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jordan on 03/08/2015.
 */
public class ShoppingListAdapter extends ParseQueryAdapter<ParseObject> {

    public ShoppingListAdapter(Context context){

        super(context, new QueryFactory<ParseObject>(){

            public ParseQuery create(){
                ParseQuery query = new ParseQuery("Product");
                query.whereEqualTo("username", "Admin");
                query.whereEqualTo("shoppingList", true);
                query.orderByAscending("expiry");
                return query;

            }
        });
    }

    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.shopping_list_item, null);
        }


        super.getItemView(object, v, parent);


        TextView titleTextView = (TextView) v.findViewById(R.id.text1);
        titleTextView.setText(object.getString("productName"));


        TextView timestampView = (TextView) v.findViewById(R.id.timestamp);
        //  timestampView.setText(object.getDate("expiry").toString());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(Calendar.getInstance().getTime());
        Date datecomp = new Date();
        datecomp.getDate();

        int check = object.getDate("expiry").compareTo(datecomp);

        if (check < 0) {
            timestampView.setTextColor(Color.RED);
            timestampView.setText(object.getDate("expiry").toString());
        } else if (check > 0) {
            timestampView.setTextColor(Color.parseColor("#64DD17"));
            timestampView.setText(object.getDate("expiry").toString());
    }

        return v;
    }


}

