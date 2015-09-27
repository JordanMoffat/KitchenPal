package io.moffat.kitchenpal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jordan on 03/08/2015.
 */
public class SavedRecipeAdapter extends ParseQueryAdapter<ParseObject> {



    public SavedRecipeAdapter(Context context){

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>(){

            public ParseQuery create(){
                ParseQuery query = new ParseQuery("recipe");
                query.whereEqualTo("user", ParseUser.getCurrentUser());
             query.whereEqualTo("archived", false);


                return query;

            }
        });
    }




    @Override
    public View getItemView(final ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.recipe_item, null);
        }


        super.getItemView(object, v, parent);


        CircleImageView icon = (CircleImageView)v.findViewById(R.id.recipeIcon);
        TextView title = (TextView) v.findViewById(R.id.recipeTitle);
        TextView supplier = (TextView) v.findViewById(R.id.supplier);
       final ImageView button = (ImageView)v.findViewById(R.id.favbutton);

        title.setText(object.getString("title"));
        supplier.setText(object.getString("publisher"));
       button.setImageResource(R.drawable.ic_favorite_white_24dp);


       Picasso.with(getContext())
                .load(object.getString("imageUrl"))
                .resize(50,50)
                .centerCrop()
                .into(icon);

        button.setOnClickListener(new View.OnClickListener() {

            int buttonOps = 0;
            @Override
            public void onClick(View v) {
                if (buttonOps == 0) {
                    button.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    buttonOps = 1;

                    ParseObject selected = object;
                    selected.put("archived", true);
                    selected.saveInBackground();

                    //     button.setImageResource(R.drawable.ic_favorite_border_white_24dp);


                    Toast.makeText(getContext(), "Removed from favourites",
                            Toast.LENGTH_SHORT).show();


                } else if (buttonOps == 1) {
                    button.setImageResource(R.drawable.ic_favorite_white_24dp);
                    buttonOps = 0;
                    ParseObject selected = object;

                    selected.put("archived", false);

                    selected.saveInBackground();
                    //   button.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                }
            }
        });

        return v;
    }
}

