package com.example.root.firebasedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends Activity {

    private TextView mMessageTv;
    private EditText mMessageEt;
    private FirebaseDatabase mFireDB;
    private String mName;
    private DatabaseReference mFireDBRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mMessageTv=(TextView)findViewById(R.id.message_tv);
        mMessageEt=(EditText)findViewById(R.id.message_et);

        mFireDB=FirebaseDatabase.getInstance();
        mFireDBRef=mFireDB.getReference();

        Intent intent=getIntent();
        mName=intent.getStringExtra("myname");

        mFireDBRef.addValueEventListener(new ValueEventListener() { //To read data from the firebase DB
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {//datasnapshot means whole data will be given to us
                String message="";
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())//getchildren will get each and every message
                {
                    Chat chat=childSnapshot.getValue(Chat.class);

                    message=message+"\n"+chat.getName()+" : "+ chat.getMessage();
                }
                mMessageTv.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void send(View view)
    {
        Chat chat=new Chat();
        chat.setName(mName);
        chat.setMessage(mMessageEt.getText().toString());
        mFireDBRef.push().setValue(chat);        
    }
}
