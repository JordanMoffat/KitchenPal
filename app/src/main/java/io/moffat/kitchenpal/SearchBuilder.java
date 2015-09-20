package io.moffat.kitchenpal;

/**
 * Created by Jordan on 09/08/2015.
 */
public class SearchBuilder {


    public String builtURL(String queryBuilder){
        String queryBuild = "";

        if (queryBuilder !=null){
            for (int i =0; i<queryBuilder.length(); i++){
                if(Character.isWhitespace(queryBuilder.charAt(i))) {
                    queryBuild = queryBuild + "&20";
                }else {
                    queryBuild = queryBuild + queryBuilder.charAt(i);
                }
            }
        }

        queryBuild = queryBuild.replaceAll("[^a-zA-Z0-9/]" , "&20");

        String pre = "http://food2fork.com/api/search?key=ceb7e716ec7e3e82354c9c03d076b46b&q=";
        String url = pre + queryBuild;

        return url;
    }
}
