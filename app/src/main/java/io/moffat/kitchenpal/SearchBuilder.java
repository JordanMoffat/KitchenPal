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

        //link + queryBuild
        return queryBuild;
    }


}
