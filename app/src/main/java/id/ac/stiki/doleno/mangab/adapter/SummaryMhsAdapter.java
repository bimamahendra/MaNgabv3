package id.ac.stiki.doleno.mangab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.stiki.doleno.mangab.R;
import id.ac.stiki.doleno.mangab.api.response.SummaryMhsResponse;
import id.ac.stiki.doleno.mangab.api.response.SummaryResponse;

public class SummaryMhsAdapter extends RecyclerView.Adapter<SummaryMhsAdapter.SummaryMhsVH> {
    private List<SummaryMhsResponse.SummaryMhsData> list;

    public SummaryMhsAdapter(List<SummaryMhsResponse.SummaryMhsData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SummaryMhsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SummaryMhsAdapter.SummaryMhsVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_summary_mhs, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull SummaryMhsVH holder, int position) {
        holder.tvSubject.setText(list.get(position).namaMatkul + " | " + list.get(position).kelasMatkul);
        holder.tvRoom.setText(list.get(position).ruangMatkul);
        holder.tvHadir.setText("Hadir\n"+list.get(position).hadir);
        holder.tvSakit.setText("Sakit\n"+list.get(position).sakit);
        holder.tvIzin.setText("Izin\n"+list.get(position).izin);
        holder.tvAlpa.setText("Alpa\n"+list.get(position).alpha);
        double attendance = (list.get(position).hadir / list.get(position).pertemuan) * 100;
        holder.tvPercent.setText("Attendance\n"+String.format("%.2f", attendance)+"%");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SummaryMhsVH extends RecyclerView.ViewHolder {
        public TextView tvSubject, tvRoom, tvHadir, tvSakit, tvIzin, tvAlpa, tvPercent;
        public SummaryMhsVH(@NonNull View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvSubjectS);
            tvRoom = itemView.findViewById(R.id.tvRoomS);
            tvHadir = itemView.findViewById(R.id.tvHadirS);
            tvSakit = itemView.findViewById(R.id.tvSakitS);
            tvIzin = itemView.findViewById(R.id.tvIzinS);
            tvAlpa = itemView.findViewById(R.id.tvAlpaS);
            tvPercent = itemView.findViewById(R.id.tvPercent);
        }
    }
}
