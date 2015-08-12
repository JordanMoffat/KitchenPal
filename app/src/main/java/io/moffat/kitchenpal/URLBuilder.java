package io.moffat.kitchenpal;

/**
 * Created by Jordan on 09/08/2015.
 */
public class URLBuilder {


    public String builtURL(String code){
        String url = new String();
        url = "http://api.ean-search.org/api?token=jord525723&op=barcode-lookup&format=json&ean=" + code;
        return url;
    }
    //http://api.upcdatabase.org/json/38231fc5c2fca1055f13a18306c4c817/

}
