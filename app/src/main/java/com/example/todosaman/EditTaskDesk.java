package com.example.todosaman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class EditTaskDesk extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView adddate;
    EditText titleTODO, descTODO;
    Button btnSaveUpdate, btnDelete, button;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_desk);

        final Button button = (Button) findViewById(R.id.dateTODO);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        titleTODO = findViewById(R.id.titleTODO);
        descTODO = findViewById(R.id.descTODO);
//        dateTODO = findViewById(R.id.dateTODO);
        adddate = findViewById(R.id.adddate);

        btnSaveUpdate = findViewById(R.id.btnSaveUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        //get value from previous page
        titleTODO.setText(getIntent().getStringExtra("titleTODO"));
        descTODO.setText(getIntent().getStringExtra("descTODO"));

        final String keykeyTODO = getIntent().getStringExtra("keyTODO");

        reference = FirebaseDatabase.getInstance().getReference().child("BoxTODO").child("TODO" + keykeyTODO);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Intent a = new Intent(EditTaskDesk.this, MainActivity.class);
                            startActivity(a);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Failure!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //make an event for button
        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titleTODO").setValue(titleTODO.getText().toString());
                        dataSnapshot.getRef().child("descTODO").setValue(descTODO.getText().toString());
                        dataSnapshot.getRef().child("dateTODO").setValue(button.getText().toString());
                        dataSnapshot.getRef().child("keyTODO").setValue(keykeyTODO);

                        Intent a = new Intent(EditTaskDesk.this, MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
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
