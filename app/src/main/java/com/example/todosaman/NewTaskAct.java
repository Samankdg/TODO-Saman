package com.example.todosaman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.EventListener;
import java.util.Random;

public class NewTaskAct extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView titlepage, addtitle , adddesc, adddate;
    EditText titleTODO, descTODO;
    Button btnSaveTask, btnCancel, button;
    DatabaseReference reference;
    Integer TODONum=new Random().nextInt();
    String keyTODO = Integer.toString(TODONum);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        final Button button = (Button) findViewById(R.id.dateTODO);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        titlepage = findViewById(R.id.titlepage);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);

        titleTODO = findViewById(R.id.titleTODO);
        descTODO = findViewById(R.id.descTODO);
//        dateTODO = findViewById(R.id.dateTODO);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert data to database
                reference = FirebaseDatabase.getInstance().getReference().child("BoxTODO").child("TODO" + TODONum);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titleTODO").setValue(titleTODO.getText().toString());
                        dataSnapshot.getRef().child("descTODO").setValue(descTODO.getText().toString());
                        dataSnapshot.getRef().child("dateTODO").setValue(button.getText().toString());
                        dataSnapshot.getRef().child("keyTODO").setValue(keyTODO);

                        Intent a = new Intent(NewTaskAct.this,MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.otf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.otf");

        //customize font
        titlepage.setTypeface(MMedium);
        addtitle.setTypeface(MLight);
        titleTODO.setTypeface(MMedium);

        adddesc.setTypeface(MLight);
        descTODO.setTypeface(MMedium);

        adddate.setTypeface(MLight);


        btnSaveTask.setTypeface(MMedium);
        btnCancel.setTypeface(MLight);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.dateTODO);
        textView.setText(currentDateString);

    }
}
