package id.ac.stiki.doleno.mangab.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.UnknownHostException;

import id.ac.stiki.doleno.mangab.R;
import id.ac.stiki.doleno.mangab.adapter.HistoryMhsAdapter;
import id.ac.stiki.doleno.mangab.adapter.ScheduleAdapter;
import id.ac.stiki.doleno.mangab.api.Api;
import id.ac.stiki.doleno.mangab.api.ApiClient;
import id.ac.stiki.doleno.mangab.api.response.MyLectureResponse;
import id.ac.stiki.doleno.mangab.model.User;
import id.ac.stiki.doleno.mangab.preference.AppPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends AppCompatActivity {

    private Api api = ApiClient.getClient();
    private User user;

    private RecyclerView rvSchedule;
    private SwipeRefreshLayout srlSchedule;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        user = AppPreference.getUser(this);
        rvSchedule = findViewById(R.id.rvSchedule);
        srlSchedule = findViewById(R.id.srlSchedule);
        progressBar = findViewById(R.id.progressbar);

        rvSchedule.setLayoutManager(new LinearLayoutManager(this));

        getSchedule();

        srlSchedule.setOnRefreshListener(() ->{
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                getSchedule();
                srlSchedule.setRefreshing(false);
            }, 2500);
        });


    }

    private void getSchedule() {
        api.myLecture(user.noInduk).enqueue(new Callback<MyLectureResponse>() {
            @Override
            public void onResponse(Call<MyLectureResponse> call, Response<MyLectureResponse> response) {
                progressBar.setVisibility(View.GONE);
                if(!response.body().error){
                    rvSchedule.setAdapter(new ScheduleAdapter(response.body().data));
                }else {
                    Toast.makeText(ScheduleActivity.this, response.body().message, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<MyLectureResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if(t instanceof UnknownHostException){
                    Toast.makeText(ScheduleActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }else {
                    t.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}