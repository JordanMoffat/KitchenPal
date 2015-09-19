package io.moffat.kitchenpal;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        if(null==jsonArray) return null;
        else
            return jsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position){

        return 0;
    }

    @Override
    public View getView (int position, View v, ViewGroup parent){
        if (v == null) {
            v = View.inflate(activity, R.layout.recipe_item, null);
        }

        CircularImageView icon = (CircularImageView)v.findViewById(R.id.recipeIcon);
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
                if (JSdata.has("image_url")){
                    String image = JSdata.getString("image_url");
                    String[] imagearray = image.split("\\.");
                    String extension = imagearray[imagearray.length-1];

                    if (image != null) {
                        Picasso.with(activity)
                                .load(image)
                                .centerCrop()
                                .error(R.drawable.icon01)
                                .resize(50, 50)
                                .into(icon);
                    }
                } else {
                    Picasso.with(activity)
                            .load(R.drawable.icon01)
                            .centerCrop()
                            .resize(50,50)
                            .into(icon);
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }
        ImageView fav = (ImageView)v.findViewById(R.id.favbutton);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Fav",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }


}

