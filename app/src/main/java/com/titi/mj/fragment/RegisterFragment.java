package com.titi.mj.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.titi.mj.activity.MainmenuActivity;
import com.titi.mj.R;
import com.titi.mj.model.LoginResponse;
import com.titi.mj.model.RegisterResponse;
import com.titi.mj.model.locale.PrefModel;
import com.titi.mj.utils.SharedPref;
import com.titi.mj.utils.network.APIClient;
import com.titi.mj.utils.network.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private Button mButtonRegister;
    private SharedPref mPreference;
    private TextView mTextLogin;
    private EditText mEdtEmailRegister, mEdtFullnameRegister,mEdtPasswordRegister, mEdtPasswordConfirmRegister;
    private String FIELD_REQUIRED = "This can't be empty";
    private String FIELD_NOT_SAME = "This password and confirmation is not same";
    private PrefModel userModel;
    private boolean isPreferencesEmpty = false;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        mPreference = new SharedPref(getContext());

        userModel = mPreference.getPreferences();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        mTextLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLoginFragment();
            }
        });

    }

    private void goLoginFragment() {
        Fragment mFragment = new LoginFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_main, mFragment);
        transaction.commit();
    }

    void init(View view){
        mButtonRegister = view.findViewById(R.id.btn_do_register);
        mTextLogin = view.findViewById(R.id.tv_login_register);
        mEdtEmailRegister = view.findViewById(R.id.et_email_register);
        mEdtFullnameRegister = view.findViewById(R.id.et_name_register);
        mEdtPasswordRegister = view.findViewById(R.id.et_password_register);
        mEdtPasswordConfirmRegister = view.findViewById(R.id.et_password_confirm_register);
    }

    void validate(){
        String email = mEdtEmailRegister.getText().toString().trim();
        String fullname = mEdtFullnameRegister.getText().toString().trim();
        String password = mEdtPasswordRegister.getText().toString().trim();
        String passwordConfirm = mEdtPasswordConfirmRegister.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            mEdtEmailRegister.setError(FIELD_REQUIRED);
            mEdtEmailRegister.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(fullname)){
            mEdtFullnameRegister.setError(FIELD_REQUIRED);
            mEdtFullnameRegister.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)){
            mEdtPasswordRegister.setError(FIELD_REQUIRED);
            mEdtPasswordRegister.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(passwordConfirm)){
            mEdtPasswordConfirmRegister.setError(FIELD_REQUIRED);
            mEdtPasswordConfirmRegister.requestFocus();
            return;
        }

        if (!passwordConfirm.equals(password)){
            mEdtPasswordRegister.setText("");
            mEdtPasswordConfirmRegister.setText("");
            mEdtPasswordRegister.requestFocus();

            Toast.makeText(getContext(), FIELD_NOT_SAME, Toast.LENGTH_SHORT).show();
            return;
        }

        getResponse(email, fullname, password);

    }

    private void getResponse(String str_email, String str_fullname, String str_password) {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RegisterResponse> call = apiInterface.doRegist(str_fullname, str_email, str_password, str_password);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()){

                    String gson_response = response.body().toString();
                    Toast.makeText(getContext(), gson_response, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

//        startActivity(new Intent(getContext(), MainmenuActivity.class));
//        getActivity().finish();
//
//        saveUser(str_email, str_password, str_fullname, true);

    }

    private void saveUser(String email, String password, String fullname, boolean status){
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setFullname(fullname);
        userModel.setLoggedin(status);

        mPreference.setPreferences(userModel);
        Toast.makeText(getContext(), "Data tersimpan", Toast.LENGTH_SHORT).show();

    }
}
