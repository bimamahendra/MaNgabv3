package id.ac.stiki.doleno.mangab.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;

public class MyLectureResponse extends BaseResponse {
    @SerializedName("data")
    public List<MyLectureData> data;

    public static class MyLectureData{
        @SerializedName("KODE_MATKUL")
        public String kode;

        @SerializedName("NAMA_MATKUL")
        public String nama;

        @SerializedName("KELAS_PRTMN")
        public String kelas;

        @SerializedName("RUANG_PRTMN")
        public String ruang;

        @SerializedName("ID_JADWAL")
        public String idJadwal;

        @SerializedName("HARI_KELAS")
        public String hari;

        @SerializedName("WAKTU_AWAL")
        public String waktuAwal;

        @SerializedName("WAKTU_AKHIR")
        public String waktuAkhir;

        @SerializedName("check_active")
        public int checkActive;

    }
}
