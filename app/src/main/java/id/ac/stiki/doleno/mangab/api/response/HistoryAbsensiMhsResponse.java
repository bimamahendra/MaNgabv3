package id.ac.stiki.doleno.mangab.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryAbsensiMhsResponse extends BaseResponse {
    @SerializedName("data")
    public List<HistoryAbsensiData> data;

    public static class HistoryAbsensiData {
        @SerializedName("KODE_MATKUL")
        public String kodeMatkul;

        @SerializedName("NAMA_MATKUL")
        public String namaMatkul;

        @SerializedName("KELAS_PRTMN")
        public String kelasMatkul;

        @SerializedName("RUANG_KELAS")
        public String ruangMatkul;

        @SerializedName("ID_JADWAL")
        public String idJadwal;

        @SerializedName("TOPIK_ABSEN")
        public String topikMatkul;

        @SerializedName("TS_ABSEN")
        public String tsAbsen;

        @SerializedName("STATUS_DETABSEN")
        public int statusAbsen;
    }
}
