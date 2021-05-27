package id.ac.stiki.doleno.mangab.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import id.ac.stiki.doleno.mangab.R;
import id.ac.stiki.doleno.mangab.adapter.DetailAbsensiAdapter;
import id.ac.stiki.doleno.mangab.api.Api;
import id.ac.stiki.doleno.mangab.api.ApiClient;
import id.ac.stiki.doleno.mangab.api.response.DetailAbsenResponse;
import id.ac.stiki.doleno.mangab.api.response.GenerateQrCodeResponse;

import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity implements Callback<DetailAbsenResponse> {
    Api api = ApiClient.getClient();
    Call<DetailAbsenResponse> request;

    ImageView ivQR;
    RecyclerView rvList;
    Button btnDone;
    ImageButton btnShare;
    TextView tvCode;

    GenerateQrCodeResponse generateQrCodeResponse;
    ProgressBar progressBar;
    AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(request == null){
                request = api.detailAbsen(generateQrCodeResponse.idAbsen);
            }

            if(request.isExecuted()){
                request = api.detailAbsen(generateQrCodeResponse.idAbsen);
            }

            request.enqueue(ResultActivity.this);
            handler.postDelayed(this, 5000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        generateQrCodeResponse = (GenerateQrCodeResponse) getIntent()
                .getSerializableExtra(GenerateActivity.GenerateResponse);

        ivQR = findViewById(R.id.ivQRCode);
        rvList = findViewById(R.id.rvList);
        btnDone = findViewById(R.id.btnDone);
        btnShare = findViewById(R.id.btnShare);
        tvCode = findViewById(R.id.tvCode);
        progressBar = findViewById(R.id.progressbarRes);

        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(new DetailAbsensiAdapter(generateQrCodeResponse.dataMhs));

        Picasso.get()
                .load(getIntent().getStringExtra(GenerateActivity.UrlImgValue))
                .resize(500, 500)
                .centerCrop()
                .into(ivQR);



        tvCode.setText(getIntent().getStringExtra(GenerateActivity.idAbsen));

        btnShare.setOnClickListener(v -> {
            BitmapDrawable drawable = (BitmapDrawable) ivQR.getDrawable();
            Bitmap icon = drawable.getBitmap();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "title");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values);
            OutputStream outstream;
            try {
                outstream = getContentResolver().openOutputStream(uri);
                icon.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                outstream.close();
            } catch (Exception e) {
                System.err.println(e.toString());
            }

            share.putExtra(Intent.EXTRA_STREAM, uri);
            share.putExtra(Intent.EXTRA_TEXT,
                    "[MaNgab] Silahkan melakukan presensi untuk perkuliahan :" +
                    "\n \nKode Prodi : "+ generateQrCodeResponse.dataClass.get(0).kodeProdi +
                    "\nKode Matakuliah : "+ generateQrCodeResponse.dataClass.get(0).kodeMatkul +
                    "\nNama Matakuliah : "+ generateQrCodeResponse.dataClass.get(0).namaMatkul + " - " + generateQrCodeResponse.dataClass.get(0).kelasMatkul +
                    "\n \nMaNgab QR Code : "+ tvCode.getText());
            startActivity(Intent.createChooser(share, "Share Image"));

        });
        btnDone.setOnClickListener(v -> {
                v.startAnimation(buttonClick);
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getApplicationContext(), RekapActivity.class);
                intent.putExtra("absen", (ArrayList)((DetailAbsensiAdapter) rvList.getAdapter()).getDataMhs());
                intent.putExtra("idabsen", generateQrCodeResponse.idAbsen);
                startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to leave this page?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 5000);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    public void onResponse(Call<DetailAbsenResponse> call, Response<DetailAbsenResponse> response) {
        if(!response.body().error){
            ((DetailAbsensiAdapter) rvList.getAdapter()).setNewData(response.body().data);
        }else {
            //Toast.makeText(this, response.body().message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<DetailAbsenResponse> call, Throwable t) {
        if(t instanceof UnknownHostException){
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }else {
            t.printStackTrace();
        }
    }

}
