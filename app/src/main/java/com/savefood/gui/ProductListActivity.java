package com.savefood.gui;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.savefood.controllers.ProductListAdapter;
import com.savefood.R;
import com.savefood.notification.NotificationBuilder;
import com.savefood.persistency.DBHelper;
import com.savefood.persistency.Product;
import com.savefood.notification.NotificationReceiver;
import com.savefood.persistency.ProductRepo;
import com.savefood.rest.ApiService;
import com.savefood.rest.RestClient;
import com.savefood.rest.object.v2.Hit;
import com.savefood.rest.object.v2.Response;

import org.parceler.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Call;

public class ProductListActivity extends AppCompatActivity {
    public static final String SEARCH_QUERY_PARAMETER = "searchQuery";
    public static final String PRODUCT_ID_PARAMETER = "productID";

    private ProductRepo productRepo;

    private boolean hasOneSelectedItem = false;


    public void setHasOneSelectedItem(boolean hasOneSelectedItem) {
        this.hasOneSelectedItem = hasOneSelectedItem;
    }

    /**
     * Initialises a screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        DBHelper dbHelper = new DBHelper(this);
        productRepo = new ProductRepo(dbHelper);

        ListView mListView = (ListView) findViewById(R.id.listView);
        //set notification
        setAlarm();

       //set notification END

        //Declaring Array adapter

        ProductListAdapter productListAdapter = new ProductListAdapter(this, productRepo);
        //Setting the android ListView's adapter to the newly created adapter
        mListView.setAdapter(productListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //The position where the list item is clicked is obtained from the
                //the parameter position of the android listview
                int itemPosition = position;

                //Get the String value of the item where the user clicked
                int productID =  ((Product)((ListView) findViewById(R.id.listView)).getItemAtPosition(position)).getId();

                //In order to start displaying new activity we need an intent
                Intent intent = new Intent(getApplicationContext(),EnterProductActivity.class);

                intent.putExtra(PRODUCT_ID_PARAMETER,productID);

                //Here we will pass the previously created intent as parameter
                startActivity(intent);

            }
        });


        CheckBox multiselect = (CheckBox) findViewById(R.id.checkBox);
        multiselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProductListAdapter) ((ListView) findViewById(R.id.listView)).getAdapter()).notifyDataSetChanged();
            }
        });

        Button addProdButton = (Button) findViewById(R.id.addProductButton);
        addProdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ProductListActivity.this, EnterProductActivity.class);
                ProductListActivity.this.startActivity(myIntent);
            }
        });

    }

    /**
     * Refresh the ProductListActivity screen
     */
    @Override
    public void onResume() {
        super.onResume();
        ((ProductListAdapter) ((ListView) findViewById(R.id.listView)).getAdapter()).notifyDataSetChanged();
    }

    private void setAlarm() {
        NotificationBuilder builder = new NotificationBuilder(this);
        builder.scheduleNotification(new Intent(this, ProductListActivity.class),"SaveFood", "Expiring products", "You have one or several expiring products. Click here to find out which."  );
    }


    /**
     * Initialises an option menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        List<Product> selectedProductList = ((ProductListAdapter)((ListView) findViewById(R.id.listView)).getAdapter()).getSelectedProductList();

        switch (item.getItemId()) {
            case R.id.searchRecipesMenu:
                StringBuilder queryString = new StringBuilder();

                for(Product prod: selectedProductList) {
                    queryString.append(prod.getProductType().getName()).append(' ');
                }

                //if we haven't checked any products, it's pointless to search
                if (queryString.toString().length() > 0 ) {
                    Intent intent = new Intent(this, RecipesListActivity.class);

                    //Putting the Id of image as an extra in intent
                    Intent searchQuery = intent.putExtra(SEARCH_QUERY_PARAMETER, queryString.toString());

                    //Here we will pass the previously created intent as parameter
                    startActivity(intent);
                }

                return true;
            case R.id.deleteProductsMenu:
                for(Product prod: selectedProductList) {
                    productRepo.delete(prod.getId());
                }
                Toast.makeText(this, selectedProductList.size() + " products deleted.", Toast.LENGTH_SHORT).show();
                ((ProductListAdapter)((ListView) findViewById(R.id.listView)).getAdapter()).notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Unable or disable option menu, depending on items selection
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(hasOneSelectedItem) {
            menu.getItem(0).setEnabled(true);
            menu.getItem(1).setEnabled(true);
        } else {
            menu.getItem(0).setEnabled(false);
            menu.getItem(1).setEnabled(false);

        }

        return super.onPrepareOptionsMenu(menu);

    }

}
