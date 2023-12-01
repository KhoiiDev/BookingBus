package com.example.myappcoach;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignUp extends Fragment {

    private TextInputEditText inputEmail ,pass, confirmPass;
    private Button btSignup;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_singup, container, false);
        initialize(view);
        mAuth = FirebaseAuth.getInstance();

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputE = Objects.requireNonNull(inputEmail.getText()).toString().trim();
                String password = Objects.requireNonNull(pass.getText()).toString().trim();
                String password2 = Objects.requireNonNull(confirmPass.getText()).toString().trim();
                if(checkInput(inputE, password, password2)){
                    createAccount(inputE, password);
                }
            }
        });

        return view;
    }
    private void createAccount(String inputE, String password) {
        mAuth.createUserWithEmailAndPassword(inputE, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        } else {


                        }
                    }
                });
    }


    public boolean checkInput(String inputE, String password, String password2){
        if(inputE.isEmpty()){
            inputEmail.setError("Please enter your email");
            return false;
        }
        if(password.isEmpty()){
            pass.setError("Please enter your password");
            return false;
        }
        if(password.length() < 6){
            pass.setError("Please enter a password of at least 6 characters");
            return false;
        }
        if(password2.isEmpty() ){
            confirmPass.setError("Please confirm your password");
            return false;
        }
        if(!password2.equals(password)){
            confirmPass.setError("Please confirm your password again");
            return false;
        }
        return true;
    }


    private void initialize(View view){
        inputEmail = view.findViewById(R.id.enterEmail);
        pass = view.findViewById(R.id.enterPassword);
        confirmPass = view.findViewById(R.id.enterConfirmPassword);
        btSignup = view.findViewById(R.id.btSignup);
    }
}
