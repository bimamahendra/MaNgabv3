package id.ac.stiki.doleno.mangab.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.UnknownHostException;

import id.ac.stiki.doleno.mangab.R;
import id.ac.stiki.doleno.mangab.adapter.HistoryAbsensiAdapter;
import id.ac.stiki.doleno.mangab.adapter.SummaryAdapter;
import id.ac.stiki.doleno.mangab.api.Api;
import id.ac.stiki.doleno.mangab.api.ApiClient;
import id.ac.stiki.doleno.mangab.api.response.SummaryResponse;
import id.ac.stiki.doleno.mangab.model.User;
import id.ac.stiki.doleno.mangab.preference.AppPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryHistoryFragment extends Fragment {

    private Api api = ApiClient.getClient();
    private User user;

    private RecyclerView rvSummary;
    private SwipeRefreshLayout srlSummary;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary_history, container, false);

        user = AppPreference.getUser(getContext());

        rvSummary = view.findViewById(R.id.rvSummary);
        srlSummary = view.findViewById(R.id.srlSummary);
        progressBar = view.findViewById(R.id.progressbarSum);

        rvSummary.setLayoutManager(new LinearLayoutManager(getContext()));

        getSummary();

        srlSummary.setOnRefreshListener(() -> {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                getSummary();
                srlSummary.setRefreshing(false);
            }, 2500);
        });

        return view;
    }

    private void getSummary() {
        if (user.type.equalsIgnoreCase("mahasiswa")) {}
        else{
            api.summary(user.noInduk).enqueue(new Callback<SummaryResponse>() {
                @Override
                public void onResponse(Call<SummaryResponse> call, Response<SummaryResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    if (!response.body().error) {
                        rvSummary.setAdapter(new SummaryAdapter(response.body().data));
                    }else {
                        Toast.makeText(getContext(), response.body().message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SummaryResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    if(t instanceof UnknownHostException){
                        Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }else {
                        t.printStackTrace();
                    }

                }
            });
        }
    }
}
