package id.ac.stiki.doleno.mangab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.stiki.doleno.mangab.R;
import id.ac.stiki.doleno.mangab.api.Api;
import id.ac.stiki.doleno.mangab.api.ApiClient;
import id.ac.stiki.doleno.mangab.api.response.HistoryAbsensiResponse;
import id.ac.stiki.doleno.mangab.api.response.SummaryResponse;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryVH>{

    private List<SummaryResponse.SummaryData> list;

    public SummaryAdapter(List<SummaryResponse.SummaryData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SummaryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SummaryAdapter.SummaryVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_summary_dosen, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryAdapter.SummaryVH holder, int position) {
        holder.tvSubject.setText(list.get(position).namaMatkul + " | " + list.get(position).kelasMatkul);
        holder.tvIdSubject.setText(list.get(position).kodeMatkul);
        holder.tvDate.setText(list.get(position).hari + " " + list.get(position).waktuAwal + " - " + list.get(position).waktuAkhir);
        holder.tvRoom.setText(list.get(position).ruangMatkul.toLowerCase());
        holder.tvMeet.setText("Pertemuan ke :\n" + list.get(position).pertemuan);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SummaryVH extends RecyclerView.ViewHolder {
        public TextView tvSubject, tvIdSubject ,tvDate, tvRoom, tvMeet;
        public SummaryVH(@NonNull View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvIdSubject = itemView.findViewById(R.id.tvSubjectId);
            tvDate = itemView.findViewById(R.id.tvClassDateTime);
            tvRoom = itemView.findViewById(R.id.tvRoom);
            tvMeet = itemView.findViewById(R.id.tvMeet);

        }
    }
}
