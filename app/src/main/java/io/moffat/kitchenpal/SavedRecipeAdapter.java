package io.moffat.kitchenpal;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Jordan on 03/08/2015.
 */
public class SavedRecipeAdapter extends ParseQueryAdapter<ParseObject> {

    public SavedRecipeAdapter(Context context){

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>(){

            public ParseQuery create(){
                ParseQuery query = new ParseQuery("recipe");
                query.whereEqualTo("username", ParseUser.getCurrentUser());
             //   query.whereEqualTo("arch  ived", false);

                return query;

            }
        });
    }



    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.recipe_item, null);
        }


        super.getItemView(object, v, parent);


        CircularImageView icon = (CircularImageView)v.findViewById(R.id.recipeIcon);
        TextView title = (TextView) v.findViewById(R.id.recipeTitle);
        TextView supplier = (TextView) v.findViewById(R.id.supplier);
       final ImageView button = (ImageView)v.findViewById(R.id.favbutton);


        title.setText(object.getString("title"));
        supplier.setText(object.getString("publisher"));
        button.setImageResource(R.drawable.ic_favorite_white_24dp);

        Picasso.with(getContext())
                .load(object.getString("image_url"))
                .resize(50,50)
                .centerCrop()
                .into(icon);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // ParseObject selected = (ParseObject);
               // selected.put("archived", true);

             //   button.setImageResource(R.drawable.ic_favorite_border_white_24dp);

            }
        });

        return v;
    }


}

