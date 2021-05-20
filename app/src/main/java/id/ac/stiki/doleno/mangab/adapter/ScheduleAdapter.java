package id.ac.stiki.doleno.mangab.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.UnknownHostException;
import java.util.List;

import id.ac.stiki.doleno.mangab.R;
import id.ac.stiki.doleno.mangab.activity.GenerateActivity;
import id.ac.stiki.doleno.mangab.activity.ResultActivity;
import id.ac.stiki.doleno.mangab.activity.ScanActivity;
import id.ac.stiki.doleno.mangab.api.Api;
import id.ac.stiki.doleno.mangab.api.ApiClient;
import id.ac.stiki.doleno.mangab.api.response.GenerateQrCodeResponse;
import id.ac.stiki.doleno.mangab.api.response.HistoryAbsensiMhsResponse;
import id.ac.stiki.doleno.mangab.api.response.MyLectureResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.ac.stiki.doleno.mangab.activity.GenerateActivity.GenerateResponse;
import static id.ac.stiki.doleno.mangab.activity.GenerateActivity.UrlImgValue;
import static id.ac.stiki.doleno.mangab.activity.GenerateActivity.idAbsen;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleAdapterVH> {

    private List<MyLectureResponse.MyLectureData> list;
    private Context context;
    public static final String IdJadwal = "idjadwal";
    public static final String Subject = "subject";
    public static final String SubClass = "subclass";
    public static final int CheckActive = 0;
    Api api = ApiClient.getClient();

    public ScheduleAdapter(List<MyLectureResponse.MyLectureData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ScheduleAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScheduleAdapterVH(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.row_schedule, parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapterVH holder, int position) {
        holder.tvSubject.setText(list.get(position).nama + " | " + list.get(position).kelas);
        holder.tvIdSubject.setText(list.get(position).kode);
        holder.tvDate.setText(list.get(position).hari + "   " + list.get(position).waktuAwal + " - " + list.get(position).waktuAkhir);
        holder.tvRoom.setText(list.get(position).ruang + " " +list.get(position).checkActive);
        if (list.get(position).checkActive > 0) {
            holder.llShcedule.setBackgroundResource(R.color.colorGreenCard);
        }
        holder.itemView.setOnClickListener(v -> {
            if(list.get(position).checkActive == 0) {
                Intent intent = new Intent(v.getContext(), GenerateActivity.class);
                intent.putExtra(idAbsen, list.get(position).idJadwal);
                intent.putExtra(Subject, list.get(position).nama);
                intent.putExtra(SubClass, list.get(position).kelas);
                v.getContext().startActivity(intent);
            }else{
                api.regenerateQrCode(list.get(position).idJadwal).enqueue(new Callback<GenerateQrCodeResponse>() {
                    @Override
                    public void onResponse(Call<GenerateQrCodeResponse> call, Response<GenerateQrCodeResponse> response) {
                        if(!response.body().error){
                            Intent intent = new Intent(v.getContext(), ResultActivity.class);
                            intent.putExtra(UrlImgValue, response.body().url);
                            intent.putExtra(idAbsen, response.body().idAbsen);
                            intent.putExtra(GenerateResponse, response.body());
                            v.getContext().startActivity(intent);
                        }else {
                            Toast.makeText(v.getContext(), response.body().message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenerateQrCodeResponse> call, Throwable t) {
                        if(t instanceof UnknownHostException){
                            Toast.makeText(v.getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }else {
                            t.printStackTrace();
                        }

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ScheduleAdapterVH extends RecyclerView.ViewHolder {
        public TextView tvSubject, tvIdSubject ,tvDate, tvRoom;
        public LinearLayout llShcedule;
        public ScheduleAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvScheduleSubject);
            tvIdSubject = itemView.findViewById(R.id.tvScheduleId);
            tvDate = itemView.findViewById(R.id.tvScheduleDate);
            tvRoom = itemView.findViewById(R.id.tvScheduleRoom);
            llShcedule = itemView.findViewById(R.id.llScheduleCard);
        }
    }
}
