package io.moffat.kitchenpal;

/**
 * Created by Jordan on 09/08/2015.
 */
public class SearchBuilder {


    public String builtURL(String queryBuilder){
        String queryBuild = new String();


        if (queryBuilder !=null){
            for (int i =0; i<queryBuilder.length(); i++){
                if(Character.isWhitespace(queryBuilder.charAt(i))) {
                    queryBuild = queryBuild + "q=";
                }else {
                     queryBuild = queryBuild + queryBuilder.charAt(i);
                    }
                }
            }
        return queryBuild;
    }
    //http://api.upcdatabase.org/json/38231fc5c2fca1055f13a18306c4c817/

}
