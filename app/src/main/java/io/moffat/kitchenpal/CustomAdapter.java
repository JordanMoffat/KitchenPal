package io.moffat.kitchenpal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseFile;

/**
 * Created by Jordan on 03/08/2015.
 */
public class CustomAdapter extends ParseQueryAdapter<ParseObject> {

    public CustomAdapter(Context context){

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>(){
            public ParseQuery create(){
                ParseQuery query = new ParseQuery("Product");
                query.whereEqualTo("username", "Admin");
                return query;

            }
        });
    }

    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent){
        if (v == null) {
            v = View.inflate(getContext(), R.layout.parse_item, null);
        }

    super.getItemView(object, v, parent);

        TextView titleTextView = (TextView) v.findViewById(R.id.text1);
        titleTextView.setText(object.getString("productName"));

        TextView timestampView = (TextView) v.findViewById(R.id.timestamp);
        timestampView.setText(object.getString("expiry"));


        return v;
    }


}
