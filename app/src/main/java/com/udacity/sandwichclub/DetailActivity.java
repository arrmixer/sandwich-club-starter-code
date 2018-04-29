package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //populate text fields
    private TextView alsoKnowAs;
    private TextView description;
    private TextView placeOfOrigin;
    private TextView ingredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try{
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);

            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());
        }catch (JSONException je){
            je.printStackTrace();
        }


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
            //bind views
            alsoKnowAs = findViewById(R.id.also_known_info);
            description = findViewById(R.id.description_info);
            placeOfOrigin = findViewById(R.id.place_of_origin_info);
            ingredients = findViewById(R.id.ingrediants_info);

            //set text to TextViews
            alsoKnowAs.setText(words(sandwich.getAlsoKnownAs()));
            description.setText(sandwich.getDescription());
            placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
            ingredients.setText(words(sandwich.getIngredients()));



    }
    //Make set of possible Strings for Also Known as
    //and Ingredients list
    private String words(List<String> words ){
        StringBuilder sb = new StringBuilder();
        for(String word : words){

            sb.append(word);
            if(words.indexOf(word) == words.size() - 1){
                break;
            }
            sb.append(", ");
        }
        return sb.toString();
    }
}
