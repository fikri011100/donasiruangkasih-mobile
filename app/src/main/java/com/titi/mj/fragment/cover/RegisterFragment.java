package com.titi.mj.fragment.cover;


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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.titi.mj.activity.MainActivity;
import com.titi.mj.R;
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
    private ProgressBar mLoading;
    private Button mButtonRegister;
    private SharedPref mPreference;
    private TextView mTextLogin, mTextDummy;
    private EditText mEdtEmailRegister, mEdtFullnameRegister,mEdtPasswordRegister, mEdtPasswordConfirmRegister, mEdtPhoneRegister;
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
        getFragmentManager().beginTransaction().replace(R.id.container_cover, mFragment).commit();
    }

    void init(View view){
        mButtonRegister = view.findViewById(R.id.btn_do_register);
        mTextLogin = view.findViewById(R.id.tv_login_register);
        mTextDummy = view.findViewById(R.id.tv_dummy_register);
        mEdtEmailRegister = view.findViewById(R.id.et_email_register);
        mEdtFullnameRegister = view.findViewById(R.id.et_name_register);
        mEdtPasswordRegister = view.findViewById(R.id.et_password_register);
        mEdtPasswordConfirmRegister = view.findViewById(R.id.et_password_confirm_register);
        mEdtPhoneRegister = view.findViewById(R.id.et_phone_register);
        mLoading = view.findViewById(R.id.pb_register);
    }

    void validate(){
        String email = mEdtEmailRegister.getText().toString().trim();
        String fullname = mEdtFullnameRegister.getText().toString().trim();
        String password = mEdtPasswordRegister.getText().toString().trim();
        String passwordConfirm = mEdtPasswordConfirmRegister.getText().toString().trim();
        String phone = mEdtPhoneRegister.getText().toString().trim();

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

        if (TextUtils.isEmpty(phone)){
            mEdtPhoneRegister.setError(FIELD_REQUIRED);
            mEdtPhoneRegister.requestFocus();
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

        getResponse(email, fullname, password, phone);

    }

    private void getResponse(final String str_email, final String str_fullname, final String str_password, final String str_phone) {
        showLoading();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RegisterResponse> call = apiInterface.doRegist(str_fullname, str_email, str_password, str_password, str_phone);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()){

                    saveUser(response.body().user.id, str_email, str_password, response.body().user.name,
                            response.body().user.phone, response.body().user.profilePhoto,
                            response.body().user.userStatus, true);

                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();

                }else {
                    hideLoading();
                    Toast.makeText(getContext(), "failed to register account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                hideLoading();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveUser(int id, String email, String password, String fullname, String phoneno, String photo, String status_user, boolean status) {
        userModel.setId(id);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setFullname(fullname);
        userModel.setPhoneno(phoneno);
        userModel.setPhoto(photo);
        userModel.setStatus(status_user);
        userModel.setLoggedin(status);

        mPreference.setPreferences(userModel);

    }

    void showLoading(){
        mLoading.setVisibility(View.VISIBLE);

        mButtonRegister.setVisibility(View.GONE);
        mEdtEmailRegister.setVisibility(View.GONE);
        mEdtFullnameRegister.setVisibility(View.GONE);
        mEdtPasswordRegister.setVisibility(View.GONE);
        mEdtPasswordConfirmRegister.setVisibility(View.GONE);
        mEdtPhoneRegister.setVisibility(View.GONE);
        mTextLogin.setVisibility(View.GONE);
        mTextDummy.setVisibility(View.GONE);
    }

    void hideLoading(){
        mLoading.setVisibility(View.GONE);

        mButtonRegister.setVisibility(View.VISIBLE);
        mEdtEmailRegister.setVisibility(View.VISIBLE);
        mEdtFullnameRegister.setVisibility(View.VISIBLE);
        mEdtPasswordRegister.setVisibility(View.VISIBLE);
        mEdtPasswordConfirmRegister.setVisibility(View.VISIBLE);
        mEdtPhoneRegister.setVisibility(View.VISIBLE);
        mTextLogin.setVisibility(View.VISIBLE);
        mTextDummy.setVisibility(View.VISIBLE);
    }
}
