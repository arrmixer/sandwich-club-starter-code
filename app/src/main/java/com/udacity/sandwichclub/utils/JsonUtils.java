package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    //names in JSON
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) throws JSONException {
        //List used store JSON array
        //of different sandwich aliases if available
        List<String> alsoKnownAs = new ArrayList<>();

        //List used store JSON array
        //of different ingredients if available
        List<String> ingredients = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        String mainName = jsonObject.getJSONObject(NAME).getString(MAIN_NAME);

        //get JSONArray and then put it's values
        //into the alsoKnownAs list
        JSONArray names = jsonObject.getJSONObject(NAME).getJSONArray(ALSO_KNOWN_AS);
        for (int i = 0; i < names.length(); i++) {

            alsoKnownAs.add(names.get(i).toString());
        }


        String placeOfOrigin = jsonObject.getString(PLACE_OF_ORIGIN);
        String description = jsonObject.getString(DESCRIPTION);
        String image = jsonObject.getString(IMAGE);

        //get JSONArray and then put it's values
        //into the ingredients list
        JSONArray ingredientJSONArray = jsonObject.getJSONArray(INGREDIENTS);
        for (int i = 0; i < ingredientJSONArray.length(); i++) {

            ingredients.add(ingredientJSONArray.get(i).toString());
        }




        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);


    }


}
