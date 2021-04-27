package id.ac.stiki.doleno.mangab.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryAbsensiResponse extends BaseResponse {
    @SerializedName("data")
    public List<HistoryAbsensiData> data;

    public static class HistoryAbsensiData {
        @SerializedName("kode_matkul")
        public String kodeMatkul;

        @SerializedName("nama_matkul")
        public String namaMatkul;

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
        //0 AKTIF
        //1 UDAH DI REKAP
        //2 UDAH DI VALIDASI

        @SerializedName("jadwal_absen")
        public String jadwalAbsen;

        @SerializedName("id_absen")
        public String idAbsen;
    }
}
