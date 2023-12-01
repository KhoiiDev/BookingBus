package com.example.myappcoach;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
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

public class Login extends Fragment {

    private TextInputEditText inputEmail, pass;
    private Button btLogin;
    private TextView register, forgot;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initialize(view);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        inputEmail.setText("");
        pass.setText("");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    fragmentTransaction
                            .replace(R.id.id_form, new SignUp(), "signup")
                            .setReorderingAllowed(true)
                            .addToBackStack("Signup")
                            .commit();

                }
                progressDialog.dismiss();
            }
        });



        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String inputE = Objects.requireNonNull(inputEmail.getText()).toString().trim();
                String password = Objects.requireNonNull(pass.getText()).toString().trim();
                if(checkValue(inputE, password)){
                    createLogin(inputE, password);
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                Button btSent ;
                TextInputEditText email;
                TextView nofitication;

                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.forgotpass_layout);

                email = dialog.findViewById(R.id.inputName);
                btSent = dialog.findViewById(R.id.btSent);
                nofitication = dialog.findViewById(R.id.textViewNotification_forgotpass);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.show();
                dialog.getWindow().setAttributes(lp);
                progressDialog.dismiss();


                btSent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String emailAddress = email.getText().toString().trim();
                        FirebaseAuth auth = FirebaseAuth.getInstance();

                        auth.sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            nofitication.setText("Vui lòng kiểm tra email");
                                        }
                                        else{
                                        }
                                    }
                                });
                    }
                });

            }
        });
        return view;
    }


    private void initialize(View view){
        forgot = (TextView) view.findViewById(R.id.ForgotPass);
        register = (TextView) view.findViewById(R.id.textViewSignup);
        inputEmail = view.findViewById(R.id.inputName);
        pass = view.findViewById(R.id.inputPassword);
        btLogin = view.findViewById(R.id.btSent);
    }

    public void createLogin(String strEmail, String strPass) {
        mAuth.signInWithEmailAndPassword(strEmail, strPass)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        } else {
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    public boolean checkValue(String inputE,String password){
        if(inputE.isEmpty()){
            inputEmail.setError("Please enter your username");
            return false;
        }
        if(password.isEmpty()){
            pass.setError("Please enter your password");
            return false;
        }
        return true;
    }

}
