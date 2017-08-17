package com.savefood.gui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.savefood.R;
import com.savefood.controllers.ProductListAdapter;
import com.savefood.persistency.Category;
import com.savefood.persistency.DBHelper;
import com.savefood.persistency.Product;
import com.savefood.persistency.ProductRepo;
import com.savefood.persistency.ProductsType;
import com.savefood.persistency.ProductsTypesRepo;

import org.parceler.apache.commons.lang.StringUtils;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EnterProductActivity extends AppCompatActivity{
    private static final String ADD_NEW_PRODUCT = "Add new...";

    private DBHelper dbHelper = new DBHelper(this);
    private final ProductsTypesRepo productsTypesRepo = new ProductsTypesRepo(dbHelper);
    private final ProductRepo productRepo = new ProductRepo(dbHelper);

    private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    /**
     *Initialises a screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_product);


        final List<String> categories = new ArrayList<String>();

        Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Category.toStringArray());
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner prodTypeSpinner = (Spinner) findViewById(R.id.productTypeSpinner);
                updateProductTypeSpinnerOptions(((TextView) view).getText().toString(), prodTypeSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner prodTypeSpinner = (Spinner) findViewById(R.id.productTypeSpinner);
        ArrayAdapter<String> prodTypeSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        prodTypeSpinner.setAdapter(prodTypeSpinnerAdapter);
        prodTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) ((LinearLayout)parent.getParent()).findViewById(R.id.newProductType);

                if (ADD_NEW_PRODUCT.equals(parent.getAdapter().getItem( position).toString())) { //if it is the last entry selected: "Add new ..."
                    textView.setEnabled(true);
                    textView.setVisibility(View.VISIBLE);
                } else {
                    textView.setEnabled(false);
                    textView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button saveButton = (Button) findViewById(R.id.saveProduct);
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prodTypeName = ((Spinner) findViewById(R.id.productTypeSpinner)).getSelectedItem().toString();
                String newProdTypeName = (((EditText) findViewById(R.id.newProductType)).getText().toString());
                String category = ((Spinner) findViewById(R.id.categorySpinner)).getSelectedItem().toString();
                String quantityStr = ((EditText) findViewById(R.id.quantity)).getText().toString();
                String dateStr = ((EditText) findViewById(R.id.expiryDate)).getText().toString();

                Product product = new Product();

                if(prodTypeName.equals(ADD_NEW_PRODUCT)){
                    ProductsType createdProductType = new ProductsType();
                    createdProductType.setName(newProdTypeName);
                    createdProductType.setCategory(category);

                    createdProductType.setId(productsTypesRepo.insert(createdProductType));
                    product.setProductType(createdProductType);
                } else{
                    ProductsType productsType = productsTypesRepo.getProductTypeByNameAndCategory(prodTypeName,category);
                    product.setProductType(productsType);
                }

                try {
                    validateForm(prodTypeName, newProdTypeName, quantityStr, dateStr);

                    product.setQuantity(Integer.parseInt(quantityStr));

                    try {
                        product.setExpireDate(formatter.parse(dateStr));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //checking if it is an update/modify action or a new product entry
                    int productID = getIntent().getIntExtra(ProductListActivity.PRODUCT_ID_PARAMETER, -1);
                    if (productID == -1) {
                        productRepo.insert(product);
                    } else {
                        product.setId(productID);
                        productRepo.update(product);
                    }


                    Toast.makeText(v.getContext(), "Product stored.", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (IllegalArgumentException exc) {
                    Toast.makeText(v.getContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView expiryDateText = (TextView) findViewById(R.id.expiryDate);
        expiryDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Calendar cal = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                        ((TextView) v).setText(formatter.format(cal.getTime()));
                    }
                },
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
        expiryDateText.setKeyListener(null);


        int productID = getIntent().getIntExtra(ProductListActivity.PRODUCT_ID_PARAMETER, -1);
        if (productID > -1) {
            Product existingProduct = productRepo.findByID(productID);
            prePopulateAllFields(existingProduct);
        }
    }

    /**
     * Checks if product type,quantity, expiry date are filled in.
     * @param prodTypeName
     * @param newProdTypeName
     * @param quantityStr
     * @param dateStr
     */
    private void validateForm(String prodTypeName, String newProdTypeName, String quantityStr, String dateStr) {
        if (ADD_NEW_PRODUCT.equals(prodTypeName)) {
            if (StringUtils.isBlank(newProdTypeName)) {
                throw new IllegalArgumentException("Incorrect new product type name!");
            }
        }

        if (StringUtils.isEmpty(quantityStr)) {
            throw new IllegalArgumentException("Empty quantity number!");
        }

        if (StringUtils.isEmpty(dateStr)) {
            throw new IllegalArgumentException("Empty expiration date!");
        }
    }

    /**
     * Updates product types spinner
     * @param category
     * @param productTypeSpinner
     */
    private void updateProductTypeSpinnerOptions(String category, Spinner productTypeSpinner) {
        final List<String> prodTypesStrList = productsTypesRepo.getProductTypesByCategory(category);
        prodTypesStrList.add(ADD_NEW_PRODUCT);

        ArrayAdapter<String> prodTypeSpinnerAdapter = ((ArrayAdapter<String>)productTypeSpinner.getAdapter());
        prodTypeSpinnerAdapter.clear();
        prodTypeSpinnerAdapter.addAll(prodTypesStrList);
    }


    /**
     * Populate the fields of GUI(used in update)
     * @param existingProduct
     */
    private void prePopulateAllFields(Product existingProduct) {
        Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        int categoryPositionToSelect = ((ArrayAdapter) categorySpinner.getAdapter()).getPosition(existingProduct.getProductType().getCategory());
        categorySpinner.setSelection(categoryPositionToSelect);
        categorySpinner.setEnabled(false);

        Spinner productTypeSpinner = (Spinner) findViewById(R.id.productTypeSpinner);
        updateProductTypeSpinnerOptions(existingProduct.getProductType().getCategory(), productTypeSpinner);
        int prodTypePositionToSelect = ((ArrayAdapter) productTypeSpinner.getAdapter()).getPosition(existingProduct.getProductType().getName());
        productTypeSpinner.setSelection(prodTypePositionToSelect );
        productTypeSpinner.setEnabled(false);

        EditText quantity = (EditText) findViewById(R.id.quantity);
        quantity.setText(Integer.toString(existingProduct.getQuantity()));

        EditText expiryDate = (EditText) findViewById(R.id.expiryDate);
        expiryDate.setText(formatter.format(existingProduct.getExpireDate()));
    }
}
