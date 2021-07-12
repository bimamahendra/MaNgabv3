package id.ac.stiki.doleno.mangab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;

import id.ac.stiki.doleno.mangab.R;
import id.ac.stiki.doleno.mangab.api.Api;
import id.ac.stiki.doleno.mangab.api.ApiClient;
import id.ac.stiki.doleno.mangab.api.response.BaseResponse;
import id.ac.stiki.doleno.mangab.model.User;
import id.ac.stiki.doleno.mangab.preference.AppPreference;
import id.ac.stiki.doleno.mangab.preference.MyLocation;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Locale;

import id.ac.stiki.doleno.mangab.service.GpsTracker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterCodeActivity extends AppCompatActivity {
  Api api = ApiClient.getClient();
  User user;

  Button btnSubmit;
  EditText etCode1, etCode2, etCode3, etCode4, etCode5, etCode6;
  AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

  private GpsTracker gpsTracker;
  private double latitude, longitude;

  MyLocation myLocation = new MyLocation();

  @SuppressLint("MissingPermission")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_enter_code);

    etCode1 = findViewById(R.id.etCode1);
    etCode2 = findViewById(R.id.etCode2);
    etCode3 = findViewById(R.id.etCode3);
    etCode4 = findViewById(R.id.etCode4);
    etCode5 = findViewById(R.id.etCode5);
    etCode6 = findViewById(R.id.etCode6);
    btnSubmit = findViewById(R.id.btnSubmit);
    user = AppPreference.getUser(this);

    getNewLocation();

    etCode1.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (etCode1.getText().toString().length() == 1) {
          etCode2.requestFocus();
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

    etCode2.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (etCode2.getText().toString().length() == 1) {
          etCode3.requestFocus();
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

    etCode3.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (etCode3.getText().toString().length() == 1) {
          etCode4.requestFocus();
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

    etCode4.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (etCode4.getText().toString().length() == 1) {
          etCode5.requestFocus();
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

    etCode5.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (etCode5.getText().toString().length() == 1) {
          etCode6.requestFocus();
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

    btnSubmit.setOnClickListener(v -> {
      v.startAnimation(buttonClick);
//            myLocation.getLocation(getApplicationContext(), locationResult);
      enterCode(latitude, longitude);
    });

  }

  MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
    @Override
    public void gotLocation(Location location) {
      double longitude = location.getLongitude();
      double latitude = location.getLatitude();
      enterCode(latitude, longitude);
    }
  };

  private void enterCode(double latitude, double longitude) {
    String code = etCode1.getText().toString()
            + etCode2.getText().toString()
            + etCode3.getText().toString()
            + etCode4.getText().toString()
            + etCode5.getText().toString()
            + etCode6.getText().toString();

    api.absenMhs(code, user.noInduk, 1, latitude, longitude).enqueue(new Callback<BaseResponse>() {
      @Override
      public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
        finish();
        Intent intent = new Intent(getApplicationContext(), ScanResultActivity.class);
        intent.putExtra("error", response.body().error);
        intent.putExtra("message", response.body().message);
        startActivity(intent);
      }

      @Override
      public void onFailure(Call<BaseResponse> call, Throwable t) {
        if (t instanceof JsonSyntaxException) {
          finish();
          Intent intent = new Intent(getApplicationContext(), ScanResultActivity.class);
          intent.putExtra("error", true);
          intent.putExtra("message", "Invalid Code");
          startActivity(intent);
        } else if (t instanceof UnknownHostException) {
          Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        } else {
          t.printStackTrace();
        }
      }
    });
  }

  public void getNewLocation() {
    gpsTracker = new GpsTracker(EnterCodeActivity.this);

    if (gpsTracker.canGetLocation()) {
      double latitude = gpsTracker.getLatitude();
      double longitude = gpsTracker.getLongitude();
      try {
        Geocoder geocoder = new Geocoder(EnterCodeActivity.this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        Log.e("address", addresses.get(0).getAddressLine(0));
//                address = addresses.get(0).getAddressLine(0);
//                textViewLokasi.setText(address);
        this.latitude = latitude;
        this.longitude = longitude;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      gpsTracker.showSettingsAlert();
    }

  }
}
