package com.example.todosaman;

import android.content.Intent;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    TextView titlepage, subtitlepage, endpage;
    Button btnAddNew;

    DatabaseReference reference;
    RecyclerView ourTODO;
    ArrayList<MyTODO> list;
    TODOAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlepage= findViewById(R.id.titlepage);
        subtitlepage=findViewById(R.id.subtitlepage);
        endpage=findViewById(R.id.endpage);

        btnAddNew = findViewById(R.id.btnAddNew);

        //importing font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.otf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.otf");

        //customizing font
        titlepage.setTypeface(MMedium);
        subtitlepage.setTypeface(MLight);
        endpage.setTypeface(MLight);

        btnAddNew.setTypeface(MLight);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, NewTaskAct.class);
                startActivity(a);
            }
        });

        //working with data
        ourTODO = findViewById(R.id.ourTODO);
        ourTODO.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyTODO>();

        //get data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("BoxTODO");

        reference.keepSynced(true);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //set code to retrive data and replace layout
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    MyTODO p = dataSnapshot1.getValue(MyTODO.class);
                    list.add(p);
                }
                todoAdapter = new TODOAdapter(MainActivity.this, list);
                ourTODO.setAdapter(todoAdapter);
                todoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //set code to show an error
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.mainmenu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput = newText.toLowerCase();
        List<MyTODO> newList = new ArrayList<>();

        for (MyTODO name : list)
        {
            if (name.getTitleTODO().contains(userInput)){
                newList.add(name);
            }
        }
        todoAdapter.updateList(newList);
        return true;
    }
}
