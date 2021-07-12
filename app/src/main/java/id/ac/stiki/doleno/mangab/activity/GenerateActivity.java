package id.ac.stiki.doleno.mangab.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import id.ac.stiki.doleno.mangab.R;
import id.ac.stiki.doleno.mangab.adapter.ScheduleAdapter;
import id.ac.stiki.doleno.mangab.api.Api;
import id.ac.stiki.doleno.mangab.api.ApiClient;
import id.ac.stiki.doleno.mangab.api.response.GenerateQrCodeResponse;
import id.ac.stiki.doleno.mangab.api.response.MyClassResponse;
import id.ac.stiki.doleno.mangab.model.User;
import id.ac.stiki.doleno.mangab.preference.AppPreference;
import id.ac.stiki.doleno.mangab.preference.MyLocation;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import id.ac.stiki.doleno.mangab.service.GpsTracker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerateActivity extends AppCompatActivity implements View.OnClickListener {

    Api api = ApiClient.getClient();
    User user;
    Button btnGenerate;
    TextView tvSubject, tvDate;
    EditText etTopic, etMethod;
    RadioGroup rgType;
    RadioButton rbOffline, rbOnline;
    ProgressBar progressBar;

    private GpsTracker gpsTracker;
    private double latitude, longitude;

    public static final String UrlImgValue = "urlimg";
    public static final String idAbsen = "idabsen";
    public static final String GenerateResponse = "GenerateResponse";
    private Integer type;
    MyLocation myLocation = new MyLocation();
    AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        user = AppPreference.getUser(this);

        btnGenerate = findViewById(R.id.btnGenerate);
        tvSubject = findViewById(R.id.tvSubject);
        tvDate = findViewById(R.id.tvDate);
        etTopic = findViewById(R.id.etTopic);
        etMethod = findViewById(R.id.etMethod);
        rgType = findViewById(R.id.rgType);
        rbOffline = findViewById(R.id.rbOffline);
        rbOnline = findViewById(R.id.rbOnline);
        progressBar = findViewById(R.id.progressbarGen);

        getNewLocation();

        tvSubject.setText(getIntent().getStringExtra(ScheduleAdapter.Subject) + " | " + getIntent().getStringExtra(ScheduleAdapter.SubClass));
        tvDate.setText(new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                .format(Calendar.getInstance().getTime()));

        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbOffline:
                        type = 0;
                        break;
                    case R.id.rbOnline:
                        type = 1;
                        break;
                }
            }
        });


        btnGenerate.setOnClickListener(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View view) {
        if (view == btnGenerate) {
            view.startAnimation(buttonClick);
            progressBar.setVisibility(View.VISIBLE);

            if (etTopic.getText().toString().equals("")) {
                Toast.makeText(this, "Topic is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (etMethod.getText().toString().equals("")) {
                Toast.makeText(this, "Method is empty", Toast.LENGTH_SHORT).show();
                return;
            }


            if (rgType.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Class type is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            generateQrCode(latitude, longitude);

        }
    }

    MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
        @Override
        public void gotLocation(Location location) {
            // TODO Auto-generated method stub
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            Log.d("masuk", longitude + " - " + latitude);
            generateQrCode(latitude, longitude);

        }
    };

    private void generateQrCode(double latitude, double longitude) {
        api.generateQrCode(getIntent().getStringExtra(ScheduleAdapter.IdJadwal), user.noInduk, etTopic.getText().toString(),
                etMethod.getText().toString(), type, latitude, longitude).enqueue(new Callback<GenerateQrCodeResponse>() {
            @Override
            public void onResponse(Call<GenerateQrCodeResponse> call, Response<GenerateQrCodeResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (!response.body().error) {
                    Intent intent = new Intent(GenerateActivity.this, ResultActivity.class);
                    intent.putExtra(UrlImgValue, response.body().url);
                    intent.putExtra(idAbsen, response.body().idAbsen);
                    intent.putExtra(GenerateResponse, response.body());
                    startActivity(intent);
                } else {
                    Toast.makeText(GenerateActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenerateQrCodeResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (t instanceof UnknownHostException) {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    t.printStackTrace();
                }
            }
        });

    }

    public void getNewLocation(){
        gpsTracker = new GpsTracker(GenerateActivity.this);
        if( Settings.Secure.getInt(this.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED , 0) < 1){
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            try {
                Geocoder geocoder = new Geocoder(GenerateActivity.this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                Log.e("address", addresses.get(0).getAddressLine(0));
                this.latitude = latitude;
                this.longitude = longitude;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            gpsTracker.showSettingsAlert();
        }
    }else{
        gpsTracker.showDeveloperAlert();
    }
    }
}
