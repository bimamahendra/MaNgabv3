package id.ac.stiki.doleno.mangab.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryAbsensiMhsResponse extends BaseResponse {
    @SerializedName("data")
    public List<HistoryAbsensiData> data;

    public static class HistoryAbsensiData {
        @SerializedName("kode_matkul")
        public String kodeMatkul;

        @SerializedName("nama_matkul")
        public String namaMatkul;

        @SerializedName("nama_dosen")
        public String namaDosen;

        @SerializedName("kelas_matkul")
        public String kelasMatkul;

        @SerializedName("topik_matkul")
        public String topikMatkul;

        @SerializedName("jenis_absen")
        public int jenisAbsen;

        @SerializedName("jadwal_kelas")
        public String jadwalKelas;

        @SerializedName("status_absen")
        public int statusAbsen;

        @SerializedName("jadwal_absen")
        public String jadwalAbsen;
    }
}
