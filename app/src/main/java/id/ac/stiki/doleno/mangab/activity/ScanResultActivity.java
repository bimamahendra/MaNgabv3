package id.ac.stiki.doleno.mangab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import id.ac.stiki.doleno.mangab.R;

public class ScanResultActivity extends AppCompatActivity {

    private ImageView ivMedal;
    private TextView tvKeterangan;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        ivMedal = findViewById(R.id.ivMedal);
        tvKeterangan = findViewById(R.id.tvKeterangan);
        btnBack = findViewById(R.id.btnBack);

        if (getIntent().getBooleanExtra("error", false)) {
            ivMedal.setImageResource(R.drawable.success);
        } else {
            ivMedal.setImageResource(R.drawable.failure);
        }

        tvKeterangan.setText(getIntent().getStringExtra("message"));

        btnBack.setOnClickListener(view -> {
            finish();
        });
    }
}
