package com.titi.mj.fragment.mydonation;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.titi.mj.R;
import com.titi.mj.model.CategoryResponse;
import com.titi.mj.utils.network.APIClient;
import com.titi.mj.utils.network.APIInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddonationFragment extends Fragment {
    private EditText mEdtTitle, mEdtDeadline, mEdtImage, mEdtDescription;
    private Button mBtnImage, mBtnMake;
    private ImageView mImgDeadline;
    private Spinner mSpnCategory;
    private String FIELD_REQUIRED = "This can't be empty";


    public AddonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addonation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        loadspinner();

        mBtnMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        mBtnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdir();
            }
        });

        mImgDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate();
            }
        });


    }

    private void init(View view) {
        mEdtTitle = view.findViewById(R.id.et_title_donation);
        mEdtImage = view.findViewById(R.id.et_image_donation);
        mEdtDeadline = view.findViewById(R.id.et_deadline_donation);
        mEdtDescription = view.findViewById(R.id.et_desc_donation);
        mImgDeadline = view.findViewById(R.id.iv_deadline_donation);
        mBtnImage = view.findViewById(R.id.btn_image_donation);
        mBtnMake = view.findViewById(R.id.btn_make_donation);
        mSpnCategory = view.findViewById(R.id.sp_category_donation);

        mEdtDeadline.setEnabled(false);
        mEdtImage.setEnabled(false);
    }

    private void pickdate() {
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        final int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date_format = year + "-" + month + "-" + dayOfMonth;
                mEdtDeadline.setText("deadline : " + date_format);
            }
        };

        DatePickerDialog mDateDialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault, mDateListener, year, month, day);
        mDateDialog.show();

    }

    private void pickdir() {

    }

    private void validate() {
        String tittle = mEdtTitle.getText().toString().trim();
        String description = mEdtDescription.getText().toString().trim();
        String deadline = mEdtDeadline.getText().toString().trim();
        String image = mEdtImage.getText().toString().trim();

        if (TextUtils.isEmpty(tittle)) {
            mEdtTitle.setError(FIELD_REQUIRED);
            mEdtTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            mEdtDescription.setError(FIELD_REQUIRED);
            mEdtDescription.requestFocus();
            return;
        }

        if (TextUtils.equals(deadline, "deadline : dd/mm/yyyy")) {
            mEdtDeadline.setError("");
            mEdtDeadline.requestFocus();
            return;
        }

        if (TextUtils.equals(image, "image : empty.jpg")) {
            mEdtDeadline.setError("");
            mEdtDeadline.requestFocus();
            return;
        }

    }

    private void loadspinner() {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<CategoryResponse> call = apiInterface.getCategory();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                ArrayList<String> arr_spinner = new ArrayList<String>();
                arr_spinner.add("-select category-");
                for (int i = 0; i < response.body().data.size(); i++) {
                    arr_spinner.add(response.body().data.get(i).nameCategory);
                }
                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, arr_spinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpnCategory.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });

    }
}
