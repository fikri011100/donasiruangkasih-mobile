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
public class LoginFragment extends Fragment {
    private String TAG = LoginFragment.class.getSimpleName().toString();
    private Button mButtonLogin;
    private SharedPref mPreference;
    private TextView mTextRegister;
    private EditText mEdtEmailLogin, mEdtPasswordLogin;
    private String FIELD_REQUIRED = "This can't be empty";
    private PrefModel userModel;
    private boolean isPreferencesEmpty = false;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        mTextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goRegisterFragment();
            }
        });

        mPreference = new SharedPref(getContext());

        checkExistingPreferences();
    }

    private void checkExistingPreferences() {
        userModel = mPreference.getPreferences();
        if (!userModel.getEmail().isEmpty()) {
            //go to main_activity
            isPreferencesEmpty = false;

            startActivity(new Intent(getContext(), MainmenuActivity.class));
            getActivity().finish();
        } else {
            isPreferencesEmpty = true;
        }
    }

    private void goRegisterFragment() {
        Fragment mFragment = new RegisterFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_main, mFragment);
        transaction.commit();
    }

    void init(View view) {
        mButtonLogin = view.findViewById(R.id.btn_do_login);
        mTextRegister = view.findViewById(R.id.tv_register_login);
        mEdtEmailLogin = view.findViewById(R.id.et_email_login);
        mEdtPasswordLogin = view.findViewById(R.id.et_password_login);

    }

    void validate() {
        String email = mEdtEmailLogin.getText().toString().trim();
        String password = mEdtPasswordLogin.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            mEdtEmailLogin.setError(FIELD_REQUIRED);
            mEdtEmailLogin.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mEdtPasswordLogin.setError(FIELD_REQUIRED);
            mEdtPasswordLogin.requestFocus();
            return;
        }

        getResponse(email, password);

    }

    private void getResponse(final String str_email, final String str_password) {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<LoginResponse> call = apiInterface.doLogin(str_email, str_password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, final Response<LoginResponse> response) {
                if (response.isSuccessful()) {

                    saveUser(str_email, str_password, response.body().name, response.body().phone, true);

                    startActivity(new Intent(getContext(), MainmenuActivity.class));
                    getActivity().finish();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveUser(String email, String password, String fullname, String phoneno, boolean status) {
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setFullname(fullname);
        userModel.setPhoneno(phoneno);
        userModel.setLoggedin(status);

        mPreference.setPreferences(userModel);
        Toast.makeText(getContext(), "Data tersimpan", Toast.LENGTH_SHORT).show();

    }
}
