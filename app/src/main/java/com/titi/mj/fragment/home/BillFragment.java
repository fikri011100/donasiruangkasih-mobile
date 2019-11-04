package com.titi.mj.fragment.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.titi.mj.BuildConfig;
import com.titi.mj.R;
import com.titi.mj.activity.MainActivity;
import com.titi.mj.model.ConfirmationResponse;
import com.titi.mj.model.DonatedResponse;
import com.titi.mj.model.locale.PrefModel;
import com.titi.mj.utils.SharedPref;
import com.titi.mj.utils.network.APIClient;
import com.titi.mj.utils.network.APIInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillFragment extends Fragment {
    public static final String EXTRA_B = "qqq";
    private Button mBtnSend, mBtnImage;
    private TextView mTxtNominal, mTxtName, mTxtNorek, mTxtDeadline, mTxtTitle, mTxtImage;
    private ImageView mImgDonation, mImgPayment;
    private ProgressBar mLoading;
    private Integer user_id, donation_id;
    private File file;
    private static int CAMERA_REQUEST = 101;

    public BillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPref sharedPref = new SharedPref(Objects.requireNonNull(getContext()));
        final PrefModel model = sharedPref.getPreferences();

        init(view);

        assert getArguments() != null;
        final DonatedResponse.Data xxx = getArguments().getParcelable("xxx");
        DonatedResponse.User xxx2 = getArguments().getParcelable("xxx2");
        DonatedResponse.Donation xxx3 = getArguments().getParcelable("xxx3");
        DonatedResponse.Paymentmethod xxx4 = getArguments().getParcelable("xxx4");
        setContent(xxx, xxx2, xxx3, xxx4);

        user_id = model.getId();
        donation_id = xxx.idDonator;

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file != null){
                    uploadImage();
                    return;
                }

                Toast.makeText(getContext(), "pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
            }
        });

        mBtnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

    }

    private void uploadImage() {
        mLoading.setVisibility(View.VISIBLE);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("id_donator", createPartFromString(String.valueOf(donation_id)));
        map.put("id_user", createPartFromString(String.valueOf(user_id)));

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo_struk", file.getName(), reqFile);

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ConfirmationResponse> call = apiInterface.sendConfirmation(body, map);
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(@NonNull Call<ConfirmationResponse> call, @NonNull Response<ConfirmationResponse> response) {
                mLoading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "sukses mengirim bukti transfer", Toast.LENGTH_SHORT).show();

                    Objects.requireNonNull(getActivity()).startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();

                } else {
                    Toast.makeText(getContext(), "gagal mengirim bukti transfer, silahkan coba lagi", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<ConfirmationResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                mLoading.setVisibility(View.GONE);

            }
        });
    }


    private void init(View view) {
        mImgDonation = view.findViewById(R.id.iv_poster_donation_bill);
        mImgPayment = view.findViewById(R.id.iv_payment_bill);
        mTxtTitle = view.findViewById(R.id.tv_title_donation_bill);
        mTxtNominal = view.findViewById(R.id.tv_nominal_bill);
        mTxtName = view.findViewById(R.id.tv_payment_bill);
        mTxtNorek = view.findViewById(R.id.tv_norek_bill);
        mTxtDeadline = view.findViewById(R.id.tv_date_bill);
        mTxtImage = view.findViewById(R.id.tv_dummy_bill_1);
        mBtnSend = view.findViewById(R.id.btn_send_bill);
        mBtnImage = view.findViewById(R.id.btn_image_bill);
        mLoading = view.findViewById(R.id.pb_bill);
        mLoading.setVisibility(View.GONE);

    }

    private void setContent(DonatedResponse.Data data, DonatedResponse.User user, DonatedResponse.Donation donation, DonatedResponse.Paymentmethod paymentmethod) {

        mTxtTitle.setText(donation.donationTitle);
        Glide.with(Objects.requireNonNull(getContext())).load(BuildConfig.HST + "image/" + donation.donationImage).into(mImgDonation);

        mTxtDeadline.setText(data.createdAt.substring(0, 10));

        mTxtName.setText(paymentmethod.paymentmethodName + " an/ Mrs. Cindy Yuvia");
        mTxtNorek.setText(paymentmethod.paymentmethodNoRek);
        Glide.with(getContext()).load(BuildConfig.HST + "image" + paymentmethod.paymentmethodImage).into(mImgPayment);
        mTxtNominal.setText("Rp. " + data.donationAmount + ",00-");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            Bitmap mphoto = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            //panggil method uploadImage
            assert mphoto != null;
            file = createTempFile(mphoto);
            mTxtImage.setText(file.getName());
        }
    }

    private File createTempFile(Bitmap bitmap) {
        File file = new File(Objects.requireNonNull(getContext()).getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() + "_image.webp");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.WEBP, 0, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private RequestBody createPartFromString(String value_str) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, value_str);
    }

    private void pickImage() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

}
