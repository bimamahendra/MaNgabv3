package id.ac.stiki.doleno.mangab.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import id.ac.stiki.doleno.mangab.R;

import id.ac.stiki.doleno.mangab.adapter.HistoryAbsensiAdapter;
import id.ac.stiki.doleno.mangab.adapter.HistoryMhsAdapter;
import id.ac.stiki.doleno.mangab.api.Api;
import id.ac.stiki.doleno.mangab.api.ApiClient;
import id.ac.stiki.doleno.mangab.api.response.HistoryAbsensiMhsResponse;
import id.ac.stiki.doleno.mangab.api.response.HistoryAbsensiResponse;
import id.ac.stiki.doleno.mangab.model.User;
import id.ac.stiki.doleno.mangab.preference.AppPreference;

import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecentHistoryFragment extends Fragment {

    private Api api = ApiClient.getClient();
    private User user;

    private RecyclerView rvHistory;
    private SwipeRefreshLayout srlHistory;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_history, container, false);

        user = AppPreference.getUser(getContext());

        rvHistory = view.findViewById(R.id.rvHistory);
        srlHistory = view.findViewById(R.id.srlHistory);
        progressBar = view.findViewById(R.id.progressbarHis);

        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        getHistory();

        srlHistory.setOnRefreshListener(() -> {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                getHistory();
                srlHistory.setRefreshing(false);
            }, 2500);
        });
        return view;
    }

    public void getHistory() {
        if (user.type.equalsIgnoreCase("mahasiswa")) {
            api.historyAbsensiMhs(user.noInduk).enqueue(new Callback<HistoryAbsensiMhsResponse>() {
                @Override
                public void onResponse(Call<HistoryAbsensiMhsResponse> call, Response<HistoryAbsensiMhsResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    if(!response.body().error){
                        rvHistory.setAdapter(new HistoryMhsAdapter(response.body().data));
                    }else {
                        Toast.makeText(getContext(), response.body().message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<HistoryAbsensiMhsResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    if(t instanceof UnknownHostException){
                        Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }else {
                        t.printStackTrace();
                    }
                }
            });
        } else {
            api.historyAbsensiDosen(user.noInduk).enqueue(new Callback<HistoryAbsensiResponse>() {
                @Override
                public void onResponse(Call<HistoryAbsensiResponse> call, Response<HistoryAbsensiResponse> response) {
                    if (response.code() == 200) {
                        if (!response.body().error) {
                            rvHistory.setAdapter(new HistoryAbsensiAdapter(response.body().data));
                        }else {
                            Toast.makeText(getContext(), response.body().message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<HistoryAbsensiResponse> call, Throwable t) {
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
