package io.moffat.kitchenpal;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jordan on 10/09/2015.
 */

public class RecipeAdapter extends BaseAdapter implements ListAdapter {


    private final Activity activity;
    private final JSONArray jsonArray;


    public RecipeAdapter (Activity activity, JSONArray jsonArray){
        assert activity !=null;
        assert jsonArray != null;

        this.jsonArray = jsonArray;
        this.activity = activity;

    }

    @Override
    public int getCount(){
        if(null==jsonArray)
            return 0;
        else
            return jsonArray.length();
    }

    @Override
    public JSONObject getItem(int position){
        if(jsonArray==null) return null;
        else
            return jsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position){

        return position;
    }

    @Override
    public View getView (final int position, View v, ViewGroup parent){
        if (v == null) {
            v = View.inflate(activity, R.layout.recipe_item, null);
        }

        CircleImageView icon = (CircleImageView)v.findViewById(R.id.recipeIcon);
        TextView title = (TextView) v.findViewById(R.id.recipeTitle);
        TextView supplier = (TextView) v.findViewById(R.id.supplier);


        JSONObject JSdata = getItem(position);
        if(null!=JSdata){
            try {
                if (JSdata.has("title")) {
                    title.setText(JSdata.getString("title"));
                }
                if (JSdata.has("publisher")) {
                    supplier.setText(JSdata.getString("publisher"));
                }
                if (JSdata.has("image_url")) {
                    String image = JSdata.getString("image_url");

                    String[] imagearray = image.split("\\.");
                    String extension = imagearray[imagearray.length - 1];

                    String tag = new String();

                    if (extension.equals("jpg")) {
                        if (URLUtil.isValidUrl(image)) {
                            Picasso.with(activity)
                                    .load(image)
                                    .centerCrop()
                                    .tag(tag)
                                    .error(R.drawable.icon01)
                                    .resize(50, 50)
                                    .into(icon);

                        } else {
                            Picasso.with(activity)
                                    .load(R.drawable.icon01)
                                    .centerCrop()
                                    .resize(50, 50)
                                    .tag(tag)
                                    .into(icon);

                        }

                    }
                }
            } catch (JSONException e) {

                Toast.makeText(activity, "Error finding recipes",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
       final ImageView fav = (ImageView)v.findViewById(R.id.favbutton);
        fav.setOnClickListener(new View.OnClickListener() {
            int buttonOps = 0;
            @Override
            public void onClick(View v) {


                    fav.setImageResource(R.drawable.ic_favorite_white_24dp);

                    JSONObject selected = (JSONObject) getItem(position);
                    ParseObject saved = new ParseObject("recipe");
                    saved.put("user", ParseUser.getCurrentUser());
                    try {
                        saved.put("imageUrl", selected.get("image_url"));
                        saved.put("title", selected.get("title"));
                        saved.put("publisher", selected.get("publisher"));
                        saved.put("source", selected.get("source_url"));
                        saved.put("archived", false);
                    } catch (Exception e) {

                    }
                    saved.saveInBackground();

                    Toast.makeText(activity, "Added to favourites",
                            Toast.LENGTH_SHORT).show();
               
            }
        });

        return v;
    }


}

