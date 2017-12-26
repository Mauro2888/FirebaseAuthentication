package com.auth.authentication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.auth.authentication.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private ActivityMainBinding mainBinding;
  private FirebaseAuth mAuth;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
    mainBinding.btnLogOut.setOnClickListener(this);

    mAuth = FirebaseAuth.getInstance();

    if (mAuth.getCurrentUser() == null){
      finish();
      startActivity(new Intent(this,LoginActivity.class));
    }


    Toast.makeText(this, " Welcome back " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

  }

  @Override public void onClick(View v) {
    if (v == mainBinding.btnLogOut){
      mAuth.signOut();
      startActivity(new Intent(this,LoginActivity.class));
    }
  }
}
