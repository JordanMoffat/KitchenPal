package io.moffat.kitchenpal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jordan on 30/08/2015.
 */
public class ShoppingListFrag extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.shopping_list_frag, container, false);
        return rootView;

    }
}
