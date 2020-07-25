package com.example.todo_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditTaskActivity extends AppCompatActivity {
    TextView addtitle, addDesc, adddate;
    EditText titleDoes, dateDoes;
    TextInputEditText descDoes;
    Button btnUpdate, btnDelete;
//    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        titleDoes = findViewById(R.id.titleDoes);
        descDoes = findViewById(R.id.descDoes);
        dateDoes = findViewById(R.id.dateDoes);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        titleDoes.setText(getIntent().getStringExtra("titledoes"));
        descDoes.setText(getIntent().getStringExtra("descdoes"));
        dateDoes.setText(getIntent().getStringExtra("datedoes"));

        final String keykeyDoes = getIntent().getStringExtra("keydoes");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference =FirebaseDatabase.getInstance().getReference().child("DoesApp").child("Task" +keykeyDoes);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titledoes").setValue(titleDoes.getText().toString());
                        dataSnapshot.getRef().child("descdoes").setValue(descDoes.getText().toString());
                        dataSnapshot.getRef().child("datedoes").setValue(dateDoes.getText().toString());
                        dataSnapshot.getRef().child("keydoes").setValue(keykeyDoes);
                        Intent a = new Intent(EditTaskActivity.this, MainActivity.class);
                        //a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //a.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference1 =FirebaseDatabase.getInstance().getReference().child("DoesApp").child("Task"+keykeyDoes);
                reference1.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent a = new Intent(EditTaskActivity.this, MainActivity.class);
                            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                            startActivity(a);
                        } else {
                            Toast.makeText(getApplicationContext(), "Failure!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}