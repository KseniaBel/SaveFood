package com.savefood.gui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.savefood.R;
import com.savefood.rest.ApiService;
import com.savefood.rest.RestClient;
import com.savefood.rest.object.v2.Hit;
import com.savefood.rest.object.v2.Response;

import java.io.IOException;
import java.util.List;

import retrofit.Call;

public class RecipesListActivity extends AppCompatActivity {

    private RestClient restClient;
    private ListView listView;

    /**
     * Initialise recipe screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        listView = (ListView) findViewById(R.id.listRecipes);

        restClient = new RestClient();
        ApiService apiService = restClient.getApiService();
        String searchQuery = getIntent().getStringExtra(ProductListActivity.SEARCH_QUERY_PARAMETER);

        Call<Response> responseCall = apiService.searchRecipes(searchQuery, 0, 1000);
        Response responseBody = null;
        try {
            retrofit.Response<Response> response = responseCall.execute();
            responseBody = response.body();

            if (responseBody == null && response.errorBody() != null) {
                Toast.makeText(this, response.errorBody().string(), Toast.LENGTH_LONG).show();
            } else {

                List<Hit> hits = responseBody.getHits();

                //check if anything was found
                if (hits.size() == 0) {
                    Toast.makeText(this, "No recipes were found!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    ArrayAdapter<Hit> listAdapter = new ArrayAdapter<Hit>(this, android.R.layout.simple_list_item_1, android.R.id.text1, hits);

                    listView.setAdapter(listAdapter);

                    // ListView Item Click Listener
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        /**
                         * If one of the item selected open url of the recipe
                         *
                         * @param parent
                         * @param view
                         * @param position
                         * @param id
                         */
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            // ListView Clicked item index
                            int itemPosition = position;

                            // ListView Clicked item value
                            Hit itemValue = (Hit) listView.getItemAtPosition(position);


                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemValue.getRecipe().getUrl()));
                            startActivity(browserIntent);
                        }
                    });
                }
            }
        } catch (IOException e) {
            Toast.makeText(this,  "Cannot establish REST service connection!", Toast.LENGTH_LONG).show();
            finish();
        }


    }
}
