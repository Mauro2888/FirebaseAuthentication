package com.auth.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.auth.authentication.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

  private ActivityLoginBinding mLoginBinding;
  private FirebaseAuth mAuth;
  private ProgressDialog mProgress;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    mAuth = FirebaseAuth.getInstance();

    //get current ser if online

    if (mAuth.getCurrentUser() != null){
      finish();
      startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    mLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
    mLoginBinding.textSignUp.setOnClickListener(this);
    mLoginBinding.btnLogin.setOnClickListener(this);

    mProgress = new ProgressDialog(this);


  }

  @Override public void onClick(View v) {
    if (v == mLoginBinding.textSignUp){
      finish();
      startActivity(new Intent(this,RegisterUser.class));
    }
    if (v == mLoginBinding.btnLogin){
      loginIn();
    }
  }

  private void loginIn() {

    String user = mLoginBinding.email.getText().toString();
    String password = mLoginBinding.password.getText().toString();


    if (TextUtils.isEmpty(user)){
      Toast.makeText(this, "Please Insert a valid Email", Toast.LENGTH_SHORT).show();

      return;
    }
    if (TextUtils.isEmpty(password)){
      Toast.makeText(this, "Please Insert a valid Password", Toast.LENGTH_SHORT).show();
    }

    mProgress.setMessage("Loggin");
    mProgress.show();

    mAuth.signInWithEmailAndPassword(user,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
      @Override public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()){
          finish();
          startActivity(new Intent(getApplicationContext(),MainActivity.class));

        }
      }
    });

  }
}
