package com.example.sqliteassignment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    EditText name, idno;
    CheckBox MaleCheck, FemaleCheck, APTCheck, SWECheck;
    Button btnInsert, btnUpdate, btnDelete, btnView;

    DBHelper2 db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        name = findViewById(R.id.name);
        idno = findViewById(R.id.idno);
        MaleCheck = findViewById(R.id.MaleCheck);
        FemaleCheck = findViewById(R.id.FemaleCheck);
        APTCheck = findViewById(R.id.APTCheck);
        SWECheck = findViewById(R.id.SWECheck);

        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);

        db = new DBHelper2(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = name.getText().toString();
                String idnoTXT = idno.getText().toString();
                String genderTXT = MaleCheck.isChecked() ? "Male" : "Female";
                String courseTXT = APTCheck.isChecked() ? "Applied Computer Technology" : "Software Engineering";

                boolean check_insert = db.insertUserData(nameTXT, idnoTXT, genderTXT, courseTXT);

                if (check_insert) {
                    Toast.makeText(MainActivity2.this, "New Entry Inserted!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity2.this, "Failed :(", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = name.getText().toString();
                String idnoTXT = idno.getText().toString();
                String genderTXT = MaleCheck.isChecked() ? "Male" : "Female";
                String courseTXT = APTCheck.isChecked() ? "Applied Computer Technology" : "Software Engineering";

                boolean check_update = db.updateUserData(nameTXT, idnoTXT, genderTXT, courseTXT);

                if (check_update) {
                    Toast.makeText(MainActivity2.this, "Entry Updated!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity2.this, "Failed :(", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = name.getText().toString();

                boolean check_deleted = db.deleteData(nameTXT);

                if (check_deleted) {
                    Toast.makeText(MainActivity2.this, "Entry Deleted!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity2.this, "Failed :(", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db.getData();

                if (res.getCount() == 0) {
                    Toast.makeText(MainActivity2.this, "No entry exists :')", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while (res.moveToNext()) {
                    buffer.append("Name: " + res.getString(0) + "\n");
                    buffer.append("ID No: " + res.getString(1) + "\n");
                    buffer.append("Gender: " + res.getString(2) + "\n");
                    buffer.append("Course: " + res.getString(3) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setCancelable(true);
                builder.setTitle("Student Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}
