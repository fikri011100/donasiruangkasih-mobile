package com.titi.mj.fragment.home;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.titi.mj.R;
import com.titi.mj.fragment.cover.LoginFragment;
import com.titi.mj.model.DonatedResponse;
import com.titi.mj.model.DonationResponse;
import com.titi.mj.model.PaymentResponse;
import com.titi.mj.model.locale.PrefModel;
import com.titi.mj.utils.SharedPref;
import com.titi.mj.utils.network.APIClient;
import com.titi.mj.utils.network.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountDonateFragment extends Fragment {
    EditText mEdtNominal, mEdtReferal;
    RadioGroup mRgPayment;
    Button mBtnDonate;
    DonationResponse.Data xxyz;
    int payment_id;
    APIInterface apiInterface;
    PrefModel model;
    List<PaymentResponse.Data> mData;

    public CountDonateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_count_donate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPref pref = new SharedPref(getContext());
        model = pref.getPreferences();

        init(view);

        xxyz = getArguments().getParcelable("xxx");

        getResponse();

        mBtnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateField();
            }
        });

    }

    private void getResponse() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<PaymentResponse> call = apiInterface.getPayment();
        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(@NonNull Call<PaymentResponse> call, @NonNull Response<PaymentResponse> response) {
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    mData = response.body().data;
                    int btnn = response.body().data.size();
                    for (int i = 0; i < btnn; i++) {
                        RadioButton rbn = new RadioButton(getContext());
                        rbn.setId(View.generateViewId());
                        rbn.setText(response.body().data.get(i).paymentmethodName);
                        rbn.setWidth(1000);
                        rbn.setBackground(getContext().getResources().getDrawable(R.drawable.bg_edit_text));
                        mRgPayment.addView(rbn);

                        payment_id = response.body().data.get(i).id;
                    }

                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validateField() {
        String nominal = mEdtNominal.getText().toString().trim();

        if (TextUtils.isEmpty(nominal)) {
            mEdtNominal.setError(LoginFragment.FIELD_REQUIRED);
            return;
        }

        int id_rgb = mRgPayment.getCheckedRadioButtonId();

        sendDonation(model.getId(), xxyz.id, nominal, mData.get(id_rgb - 1).id);

    }

    private void sendDonation(int user_id, int donation_id, String nominal, int payment_id) {
        Call<DonatedResponse> call = apiInterface.doDonation(user_id, donation_id, Integer.valueOf(nominal), payment_id);
        call.enqueue(new Callback<DonatedResponse>() {
            @Override
            public void onResponse(Call<DonatedResponse> call, Response<DonatedResponse> response) {
                if (response.isSuccessful()) {
                    Fragment fragment = new BillFragment();
                    Bundle xxyz = new Bundle();
                    xxyz.putString(BillFragment.EXTRA_B, "count");
                    xxyz.putParcelable("xxx", response.body().data.get(0));
                    xxyz.putParcelable("xxx2", response.body().data.get(0).user);
                    xxyz.putParcelable("xxx3", response.body().data.get(0).donation);
                    xxyz.putParcelable("xxx4", response.body().data.get(0).paymentmethod);
                    fragment.setArguments(xxyz);

                    getFragmentManager().beginTransaction().replace(R.id.container_detail, fragment).commit();
                }

            }

            @Override
            public void onFailure(Call<DonatedResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void init(View view) {
        mEdtNominal = view.findViewById(R.id.et_nominal_donate);
        mEdtReferal = view.findViewById(R.id.et_referal_donate);
        mRgPayment = view.findViewById(R.id.rg_payment_donate);
        mBtnDonate = view.findViewById(R.id.btn_donation_donate);
    }
}
