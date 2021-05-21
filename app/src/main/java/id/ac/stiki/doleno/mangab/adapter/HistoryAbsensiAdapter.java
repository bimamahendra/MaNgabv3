package id.ac.stiki.doleno.mangab.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.stiki.doleno.mangab.R;

import id.ac.stiki.doleno.mangab.activity.ResultActivity;
import id.ac.stiki.doleno.mangab.api.Api;
import id.ac.stiki.doleno.mangab.api.ApiClient;
import id.ac.stiki.doleno.mangab.api.response.GenerateQrCodeResponse;
import id.ac.stiki.doleno.mangab.api.response.HistoryAbsensiResponse;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.ac.stiki.doleno.mangab.activity.GenerateActivity.GenerateResponse;
import static id.ac.stiki.doleno.mangab.activity.GenerateActivity.UrlImgValue;
import static id.ac.stiki.doleno.mangab.activity.GenerateActivity.idAbsen;

public class HistoryAbsensiAdapter extends RecyclerView.Adapter<HistoryAbsensiAdapter.HistoriAbsensiVH> {

    private List<HistoryAbsensiResponse.HistoryAbsensiData> dataHistory;

    public HistoryAbsensiAdapter(List<HistoryAbsensiResponse.HistoryAbsensiData> dataHistory) {
        this.dataHistory = dataHistory;
    }

    @NonNull
    @Override
    public HistoriAbsensiVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryAbsensiAdapter.HistoriAbsensiVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoriAbsensiVH holder, int position) {
        holder.tvMataKuliahClass.setText(dataHistory.get(position).namaMatkul + " | " + dataHistory.get(position).kelasMatkul);
        holder.tvClassDateTime.setText(parseDate(dataHistory.get(position).tsAbsen));
        holder.tvRoom.setText(dataHistory.get(position).ruangMatkul);
        holder.tvTopik.setText(dataHistory.get(position).topikMatkul);
        holder.tvHadir.setText("Hadir\n"+dataHistory.get(position).hadir);
        holder.tvSakit.setText("Sakit\n"+dataHistory.get(position).sakit);
        holder.tvIzin.setText("Izin\n"+dataHistory.get(position).izin);
        holder.tvAlpha.setText("Alpha\n"+dataHistory.get(position).alpha);
    }

    public String parseDate(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMMM yyyy HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public int getItemCount() {
        return dataHistory.size();
    }

    public class HistoriAbsensiVH extends RecyclerView.ViewHolder {

        TextView tvMataKuliahClass, tvClassDateTime, tvTopik, tvRoom, tvHadir, tvSakit, tvIzin, tvAlpha;
        LinearLayout llBodyCard;

        public HistoriAbsensiVH(@NonNull View itemView) {
            super(itemView);
            tvMataKuliahClass = itemView.findViewById(R.id.tvMataKuliahClass);
            tvClassDateTime = itemView.findViewById(R.id.tvClassDateTime);
            tvTopik = itemView.findViewById(R.id.tvTopik);
            tvRoom = itemView.findViewById(R.id.tvRoom);
            llBodyCard = itemView.findViewById(R.id.llBodyCard);
            tvHadir = itemView.findViewById(R.id.tvHadir);
            tvSakit = itemView.findViewById(R.id.tvSakit);
            tvIzin = itemView.findViewById(R.id.tvIzin);
            tvAlpha = itemView.findViewById(R.id.tvAlpha);
        }

    }

}
