package com.instinctcoder.sqlitedb;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity implements android.view.View.OnClickListener{


    private ListView obj;
    Button btnAdd, btnGetAll;
    ArrayList studentList = new ArrayList();
    TextView student_Id ;

    @Override
    public void onClick(View view) {
        if (view == findViewById(R.id.btnGetAll)){

        }else if (view== findViewById(R.id.btnAdd)){


            Intent intent = new Intent(this,StudentDetail.class);
            intent.putExtra("student_Id",0);
            startActivity(intent);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnAdd.setOnClickListener(this);
        btnGetAll.setOnClickListener(this);

        StudentRepo repo = new StudentRepo(this);

        ArrayList<HashMap<String, String>> animalList =  repo.getStudentList();

        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                student_Id = (TextView) view.findViewById(R.id.student_Id);
                String valAnimalId = student_Id.getText().toString();
                Intent  objIndent = new Intent(getApplicationContext(),StudentDetail.class);
                objIndent.putExtra("animalId", valAnimalId);
                startActivity(objIndent);
            }
        });
        ListAdapter adapter = new SimpleAdapter( MainActivity.this,animalList, R.layout.view_student_entry, new String[] { "id","name"}, new int[] {R.id.student_Id, R.id.student_name});
        setListAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
