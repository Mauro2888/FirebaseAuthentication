package com.auth.authentication;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.auth.authentication.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {


  private ActivityRegisterBinding mBinding;
  private FirebaseAuth mAuth;
  private ProgressDialog mProgress;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    mAuth = FirebaseAuth.getInstance();
    //get current ser if online

    if (mAuth.getCurrentUser() != null){
      finish();
      startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    //set data binding for activity main.xml
    mBinding = DataBindingUtil.setContentView(this,R.layout.activity_register);
    mBinding.btnRegister.setOnClickListener(this);
    mBinding.btnSignIn.setOnClickListener(this);
    mProgress = new ProgressDialog(this);


  }

  @Override public void onClick(View v) {
    if (v == mBinding.btnRegister){
      registerUser();
    }
    if (v == mBinding.btnSignIn){
      startActivity(new Intent(this,LoginActivity.class));
    }
  }

  private void registerUser() {

    String user = mBinding.email.getText().toString();
    String password = mBinding.password.getText().toString();


    if (TextUtils.isEmpty(user)){
      Toast.makeText(this, "Please Insert a valid Email", Toast.LENGTH_SHORT).show();

      return;
    }
    if (TextUtils.isEmpty(password)){
      Toast.makeText(this, "Please Insert a valid Password", Toast.LENGTH_SHORT).show();
    }

    mProgress.setMessage("Registering User");
    mProgress.show();

    mAuth.createUserWithEmailAndPassword(user,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
      @Override public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()){
          Toast.makeText(RegisterUser.this, "register ok", Toast.LENGTH_SHORT).show();
          startActivity(new Intent(getApplicationContext(),MainActivity.class));
          mProgress.dismiss();
        }
        else {
          Toast.makeText(RegisterUser.this, "Unable to register account mail invalid or User is already registered", Toast.LENGTH_SHORT).show();
          mProgress.dismiss();
        }

      }
    });

  }
}
