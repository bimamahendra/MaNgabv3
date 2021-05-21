package id.ac.stiki.doleno.mangab.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SummaryResponse extends BaseResponse{

    @SerializedName("data")
    public List<SummaryResponse.SummaryData> data;

    public static class SummaryData {
        @SerializedName("KODE_MATKUL")
        public String kodeMatkul;

        @SerializedName("NAMA_MATKUL")
        public String namaMatkul;

        @SerializedName("KELAS_PRTMN")
        public String kelasMatkul;

        @SerializedName("RUANG_PRTMN")
        public String ruangMatkul;

        @SerializedName("ID_JADWAL")
        public String idJadwal;

        @SerializedName("HARI_KELAS")
        public String hari;

        @SerializedName("WAKTU_AWAL")
        public String waktuAwal;

        @SerializedName("WAKTU_AKHIR")
        public String waktuAkhir;

        @SerializedName("PERTEMUANKE")
        public String pertemuan;


    }
}
