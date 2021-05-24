package id.ac.stiki.doleno.mangab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.ac.stiki.doleno.mangab.R;
import id.ac.stiki.doleno.mangab.api.response.HistoryAbsensiMhsResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryMhsAdapter extends RecyclerView.Adapter<HistoryMhsAdapter.HistoryAdapterVH> {

    private List<HistoryAbsensiMhsResponse.HistoryAbsensiData> list;

    public HistoryMhsAdapter(List<HistoryAbsensiMhsResponse.HistoryAbsensiData> list){
        this.list = list;
    }

    @NonNull
    @Override
    public HistoryAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryAdapterVH(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.row_history_mhs, parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapterVH holder, int position) {

        holder.tvSubject.setText(list.get(position).namaMatkul + " | " + list.get(position).kelasMatkul);
        holder.tvRoom.setText(list.get(position).ruangMatkul);
        holder.tvDate.setText(parseDate(list.get(position).tsAbsen));
        holder.tvTopic.setText(list.get(position).topikMatkul);
        if(list.get(position).statusAbsen == 0){
            holder.tvAttend.setText("Alpa");
        }else if(list.get(position).statusAbsen == 1){
            holder.tvAttend.setText("Hadir");
        }else if(list.get(position).statusAbsen == 2){
            holder.tvAttend.setText("Sakit");
        }else if(list.get(position).statusAbsen == 3){
            holder.tvAttend.setText("Izin");
        }
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
        return list.size();
    }

    class HistoryAdapterVH extends RecyclerView.ViewHolder{
        public TextView tvSubject, tvDate, tvTopic, tvRoom, tvAttend;
        public HistoryAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvSubjectM);
            tvDate = itemView.findViewById(R.id.tvClassDateM);
            tvTopic = itemView.findViewById(R.id.tvTopicM);
            tvRoom = itemView.findViewById(R.id.tvRoomM);
            tvAttend = itemView.findViewById(R.id.tvAttend);
        }
    }
}
