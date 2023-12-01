package com.example.myappcoach;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class Account_Fragment extends Fragment {

    private TextView userEmail, userName, userPhone, userAddress, userID;
    private Button bt_change_password, btLognout;
    private ImageView imgAvatar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initialize(view);
        getInfoUser();

        btLognout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        bt_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button btSent ;
                TextInputEditText passOld, passNew, passNew2;

                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.fragment_change_password);

                passOld = dialog.findViewById(R.id.editTextTextPassword);
                passNew = dialog.findViewById(R.id.editTextTextNewPassword);
                passNew2 = dialog.findViewById(R.id.editTextTextConfigPassword);
                btSent = dialog.findViewById(R.id.button_confirm_password);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.show();
                dialog.getWindow().setAttributes(lp);

                btSent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String passO = passOld.getText().toString().trim();
                        String passN = passNew.getText().toString().trim();
                        String passN1 = passNew2.getText().toString().trim();
                        if(checkPassWord(passO, passN, passN1)){
                            createChangePassWord(passN);
                        }
                        dialog.dismiss();
                    }
                    private boolean checkPassWord(String passO, String passN, String passN1) {
                        if(passO.isEmpty()){
                            passOld.setError("Please enter your old password");
                            return false;
                        }
                        if(passN.isEmpty()){
                            passNew.setError("Please enter your new password");
                            return false;
                        }
                        if(passN1.isEmpty()){
                            passNew2.setError("Please confirm your username");
                            return false;
                        }
                        if(!passNew.equals(passN1)){
                            passNew2.setError("Please confirm your username");
                            return false;
                        }

                        return  true;
                    }
                });
            }
        });

        return view;
    }

    private void createChangePassWord(String passN) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(passN)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                        }
                    }
                });
    }

    private void initialize(View view){
        userEmail = (TextView) view.findViewById(R.id.textView_name_account);
        userName = (TextView) view.findViewById(R.id.textView_name_user);
        userPhone = (TextView) view.findViewById(R.id.textView_phone_user);
        userAddress = (TextView) view.findViewById(R.id.textView_location_user);
        userID = (TextView) view.findViewById(R.id.textView_id_user);
        imgAvatar = (ImageView) view.findViewById(R.id.imgageAvatar);
        bt_change_password = (Button) view.findViewById(R.id.button_change_password);
        btLognout = (Button) view.findViewById(R.id.buttonLognout);
    }
    private void getInfoUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                String photoUrl = String.valueOf(profile.getPhotoUrl());

                userEmail.setText(email);
                userName.setText(name);
                userID.setText(uid);
                Glide.with(this).load(photoUrl).error(R.drawable.avatar_default).into(imgAvatar);
            }
        }
    }
}