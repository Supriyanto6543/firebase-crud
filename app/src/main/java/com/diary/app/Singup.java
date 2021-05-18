package com.diary.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.diary.app.databinding.ActivitySigninBinding;
import com.diary.app.databinding.ActivitySignupBinding;
import com.diary.app.model.UserDiary;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Singup extends AppCompatActivity {

    ActivitySignupBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;
    DatabaseReference reference;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(Singup.this);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(Singup.this);
        reference = FirebaseDatabase.getInstance().getReference("UserDiary");

        preferences = getSharedPreferences("kitasinau", MODE_PRIVATE);
        editor = preferences.edit();

        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Singup.this, Signin.class));
            }
        });

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.name.getText().toString();
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                storeDatatoFirebase(name, email, password);
            }
        });
    }

    private void storeDatatoFirebase(String name, String email, String password){
        dialog.setMessage("Please wait . . .");
        dialog.show();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    String unique = user.getUid();
                    UserDiary diary = new UserDiary(name, email, "");

                    reference.child(unique).setValue(diary).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()){
                                editor.putString("unique", unique);
                                editor.commit();
                                Toast.makeText(Singup.this, "Register berhasil", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });

                }else{
                    Toast.makeText(Singup.this, "Register gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}