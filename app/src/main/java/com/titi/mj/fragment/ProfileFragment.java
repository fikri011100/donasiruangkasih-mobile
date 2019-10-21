package com.titi.mj.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.titi.mj.R;
import com.titi.mj.activity.MainActivity;
import com.titi.mj.model.locale.PrefModel;
import com.titi.mj.utils.SharedPref;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    ImageView mImgEdit;
    CircleImageView mImgProfile;
    TextView mTxtEditPersonal, mTxtEditPassword;
    Button mBtnLogout, mBtnSavePassword, mBtnSavePersonal;
    MaterialEditText mEdtFullname, mEdtEmail, mEdtPhoneno, mEdtPassword,
            mEdtNewPassword, mEdtConfirmPassword;

    private PrefModel model;
    private SharedPref pref;
    private String FIELD_REQUIRED = "This can't be empty";
    private String FIELD_NOT_SAME = "This password and confirmation is not same";


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pref = new SharedPref(getContext());
        model = pref.getPreferences();

        init(view);

        mBtnSavePassword.setVisibility(View.GONE);
        mBtnSavePersonal.setVisibility(View.GONE);
        mEdtNewPassword.setVisibility(View.GONE);
        mEdtConfirmPassword.setVisibility(View.GONE);

        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogout();
            }
        });

        mTxtEditPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enablePersonalEdit();
            }
        });

        mTxtEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enablePasswordEdit();
            }
        });

        mImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "this feature didn't work correctly", Toast.LENGTH_SHORT).show();
            }
        });

        mBtnSavePersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatePersonal();
            }
        });

        mBtnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatePassword();
            }
        });
    }

    private void init(View view) {
        mImgEdit = view.findViewById(R.id.iv_change_profile);
        mImgProfile = view.findViewById(R.id.iv_image_profile);
        mTxtEditPersonal = view.findViewById(R.id.tv_edit_personal_profile);
        mTxtEditPassword = view.findViewById(R.id.tv_edit_password_profile);
        mBtnSavePersonal = view.findViewById(R.id.btn_save_personal_profile);
        mBtnSavePassword = view.findViewById(R.id.btn_save_password_profile);
        mBtnLogout = view.findViewById(R.id.btn_logout);

        mEdtFullname = view.findViewById(R.id.et_fullname_profile);
        mEdtEmail = view.findViewById(R.id.et_email_profile);
        mEdtPhoneno = view.findViewById(R.id.et_phoneno_profile);
        mEdtPassword = view.findViewById(R.id.et_password_profile);
        mEdtNewPassword = view.findViewById(R.id.et_new_password_profile);
        mEdtConfirmPassword = view.findViewById(R.id.et_confirm_password_profile);

        mEdtFullname.setText(model.getFullname());
        mEdtEmail.setText(model.getEmail());
        mEdtPhoneno.setText(model.getPhoneno());
        mEdtPassword.setText(model.getPassword());
    }

    private void enablePersonalEdit() {
        mTxtEditPersonal.setVisibility(View.GONE);
        mBtnSavePersonal.setVisibility(View.VISIBLE);

        mEdtFullname.setEnabled(true);
        mEdtEmail.setEnabled(true);
        mEdtPhoneno.setEnabled(true);
    }

    private void enablePasswordEdit() {
        mTxtEditPassword.setVisibility(View.GONE);
        mBtnSavePassword.setVisibility(View.VISIBLE);
        mEdtNewPassword.setVisibility(View.VISIBLE);
        mEdtConfirmPassword.setVisibility(View.VISIBLE);

        mEdtPassword.setEnabled(true);
        mEdtNewPassword.setEnabled(true);
        mEdtConfirmPassword.setEnabled(true);

        mEdtPassword.setText("");
        mEdtPassword.setHint("Current Password");
    }

    private void validatePersonal() {
        String fullname = mEdtFullname.getText().toString().trim();
        String phoneno = mEdtPhoneno.getText().toString().trim();
        String email = mEdtEmail.getText().toString().trim();

        if (TextUtils.isEmpty(fullname)) {
            mEdtFullname.setError(FIELD_REQUIRED);
            mEdtFullname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            mEdtEmail.setError(FIELD_REQUIRED);
            mEdtEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phoneno)) {
            mEdtPhoneno.setError(FIELD_REQUIRED);
            mEdtPhoneno.requestFocus();
            return;
        }

        if (fullname.equals(model.getFullname()) && email.equals(model.getEmail())){
            disableEditPersonal();
            return;
        }

        saveUser(email, model.getPassword(), fullname, true);
        disableEditPersonal();

    }

    private void validatePassword() {
        String password = mEdtPassword.getText().toString().trim();
        String new_password = mEdtNewPassword.getText().toString().trim();
        String confirm_password = mEdtConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(password)) {
            mEdtPassword.setError(FIELD_REQUIRED);
            mEdtPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(new_password)) {
            mEdtNewPassword.setError(FIELD_REQUIRED);
            mEdtNewPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirm_password)) {
            mEdtConfirmPassword.setError(FIELD_REQUIRED);
            mEdtConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(model.getPassword())){
            Toast.makeText(getContext(), "Your current password is wrong", Toast.LENGTH_SHORT).show();
            mEdtPassword.setText("");
            mEdtPassword.requestFocus();
            return;
        }

        if (!confirm_password.equals(new_password)){
            mEdtNewPassword.setText("");
            mEdtConfirmPassword.setText("");
            mEdtNewPassword.requestFocus();

            Toast.makeText(getContext(), FIELD_NOT_SAME, Toast.LENGTH_SHORT).show();
        }

        if (new_password.equals(model.getPassword())){
            disablePasswordEdit();
            return;
        }

        saveUser(model.getEmail(), new_password, model.getFullname(), true);
        disablePasswordEdit();

    }

    private void disableEditPersonal() {
        mTxtEditPersonal.setVisibility(View.VISIBLE);
        mBtnSavePersonal.setVisibility(View.GONE);

        mEdtFullname.setEnabled(false);
        mEdtEmail.setEnabled(false);
        mEdtPhoneno.setEnabled(false);
    }

    private void disablePasswordEdit() {
        mTxtEditPassword.setVisibility(View.VISIBLE);
        mBtnSavePassword.setVisibility(View.GONE);
        mEdtNewPassword.setVisibility(View.GONE);
        mEdtConfirmPassword.setVisibility(View.GONE);

        mEdtPassword.setEnabled(false);
        mEdtNewPassword.setEnabled(false);
        mEdtConfirmPassword.setEnabled(false);

        mEdtNewPassword.setText("");
        mEdtConfirmPassword.setText("");
        mEdtPassword.setText(model.getPassword());
    }

    private void doLogout() {
        SharedPref.clearPreferences();
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }

    private void saveUser(String email, String password, String fullname, boolean status){
        model.setEmail(email);
        model.setPassword(password);
        model.setFullname(fullname);
        model.setLoggedin(status);

        pref.setPreferences(model);
        Toast.makeText(getContext(), "Data tersimpan", Toast.LENGTH_SHORT).show();

    }
}
