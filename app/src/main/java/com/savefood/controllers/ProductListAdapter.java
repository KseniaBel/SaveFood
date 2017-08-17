package com.savefood.controllers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.savefood.R;
import com.savefood.gui.ProductListActivity;
import com.savefood.persistency.Product;
import com.savefood.persistency.ProductRepo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Ksu on 26-Nov-15.
 */
public class ProductListAdapter extends ArrayAdapter<Product> {


    private ProductRepo productRepo;

    public ProductListAdapter(Context context, ProductRepo productRepo) {
        super(context, -1, productRepo.listSortedProducts());
        this.productRepo = productRepo;
    }

    /**
     * Get id of product on the specified position
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return super.getItem(position).getId();
    }

    /**
     * Creates a graphical representation of every product (used by ListView)
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.product_list_item_view, parent, false);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkBox2);
        TextView productTextView = (TextView) rowView.findViewById(R.id.textView);
        TextView expiryDateTextView = (TextView) rowView.findViewById(R.id.textView2);

        if (!isMultiSelect(parent)) {
            checkBox.setVisibility(View.INVISIBLE);
            ((ProductListActivity)getContext()).setHasOneSelectedItem(false);
        } else {
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getItem(position).setSelected(((CheckBox) v).isChecked());
                    ((ProductListActivity) getContext()).setHasOneSelectedItem(hasOneCheckedBox(parent));
                }
            });

        }
        ((ProductListActivity)getContext()).invalidateOptionsMenu();

        Product selectedProduct = getItem(position);

        productTextView.setText(selectedProduct.getProductType().getName());

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        expiryDateTextView.setText(formatter.format(selectedProduct.getExpireDate()));

        Date now = new Date();
        Calendar oneDayBackCal = Calendar.getInstance();
        oneDayBackCal.setTime(selectedProduct.getExpireDate());
        oneDayBackCal.add(Calendar.HOUR, - 24);

        if (selectedProduct.getExpireDate().getTime()<= now.getTime()) {
            rowView.setBackgroundColor(Color.RED);
        } else if (oneDayBackCal.getTimeInMillis() < now.getTime()) {
            rowView.setBackgroundColor(Color.YELLOW);
        }

        return rowView;
    }

    /**
     * Return true if at least one of the boxes is checked
     * @param parent
     * @return
     */
    private boolean hasOneCheckedBox(ViewGroup parent) {
        for (int counter =0; counter < getCount(); counter ++) {
            if (getItem(counter).isSelected()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return true if multiselect box is checked
     * @param parent
     * @return
     */
    private boolean isMultiSelect(ViewGroup parent) {
        return ((CheckBox)((LinearLayout)parent.findViewById(R.id.listView).getParent()).findViewById(R.id.checkBox)).isChecked();
    }

    /**
     * Update the list of products
     */
    @Override
    public void notifyDataSetChanged() {
        setNotifyOnChange(false);//to avoid infinite loop
        clear();
        List<Product> productList = productRepo.listSortedProducts();
        addAll(productList);
        super.notifyDataSetChanged();
    }

    /**
     * Return a list of selected products
     * @return
     */
    public List<Product> getSelectedProductList() {
        List<Product> productList = new ArrayList<>();

        for (int counter =0; counter < getCount(); counter ++) {
            if (getItem(counter).isSelected()) {
                productList.add(getItem(counter));
            }
        }

        return productList;
    }
}
