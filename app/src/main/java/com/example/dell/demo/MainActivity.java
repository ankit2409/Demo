package com.example.dell.demo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText name,id,pwd,email;
    String nm,iD,pass,eMail;
    Button btn;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nm=name.getText().toString();
                pass=pwd.getText().toString();
                iD=id.getText().toString();
                eMail=email.getText().toString();
                mAuth.createUserWithEmailAndPassword(eMail,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user=task.getResult().getUser();
                            updateData();
                            Toast.makeText(MainActivity.this,user.getUid(),Toast.LENGTH_LONG).show();

                        }
                        else {
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(MainActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    }
                });

            }
        });


    }
    public void updateData(){
        Map<String,Object> details=new HashMap<>();
        details.put("Name",nm);
        details.put("ID",iD);
        details.put("Email",eMail);
        details.put("Password",pass);
        mRef.child(user.getUid()).setValue(details);
    }
    public void initViews()
    {
        name=(EditText)findViewById(R.id.name);
        id=(EditText)findViewById(R.id.id);
        pwd=(EditText)findViewById(R.id.password);
        email=(EditText)findViewById(R.id.email);
        btn=(Button)findViewById(R.id.register);
    }
}
