package io.moffat.kitchenpal;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Jordan on 08/08/2015.
 */
public class KitchenPalApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        Parse.initialize(this, "fKFcxAML9tqkehYq0UxpMhkjSLIVdZEgIXTNLEtt", "poN3brKJyOkYPNZQWlCHMbzWHJAZIW4TMzQETGoJ");
    }

}
