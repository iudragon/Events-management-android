package com.events_pool;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostCompetitionsActivity extends AppCompatActivity {
    private EditText mEvent;
    private EditText mVenue;
    private EditText mDate;
    private EditText getmEventDesc;

    private DatabaseReference mDatabaseReference;



    private Button mSubmitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_competitions);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#262626")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mEvent=findViewById(R.id.event);
        mVenue=findViewById(R.id.venue);
        mDate=findViewById(R.id.date);
        getmEventDesc=findViewById(R.id.event_desc);
        mSubmitBtn=findViewById(R.id.submitbutton);




        mDatabaseReference=FirebaseDatabase.getInstance().getReference().child("Events");


        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startposting();

            }
        });
    }



    private void startposting() {

        String event_val=mEvent.getText().toString().trim();
        String venue_val=mVenue.getText().toString().trim();
        String date_val=mDate.getText().toString().trim();
        String event_desc_val=getmEventDesc.getText().toString().trim();

        if(!TextUtils.isEmpty(venue_val) && !TextUtils.isEmpty(event_desc_val) && !TextUtils.isEmpty(event_val)  && !TextUtils.isEmpty(date_val)){

            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            String uid=user.getUid();

            DatabaseReference  eventsPost=mDatabaseReference.push();
            eventsPost.child("eventname").setValue(event_val);
            eventsPost.child("venue").setValue(venue_val);
            eventsPost.child("date").setValue(date_val);
            eventsPost.child("desc").setValue(event_desc_val);
            eventsPost.child("uid").setValue(uid);

            Toast.makeText(PostCompetitionsActivity.this,"Event posted successfully",Toast.LENGTH_LONG).show();

            startActivity(new Intent(PostCompetitionsActivity.this,MainActivity.class));

        }else{

            Toast.makeText(this, "Fields should not be blank", Toast.LENGTH_SHORT).show();
        }



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id==android.R.id.home) {

            finish();
        }


        return super.onOptionsItemSelected(item);

    }
}


