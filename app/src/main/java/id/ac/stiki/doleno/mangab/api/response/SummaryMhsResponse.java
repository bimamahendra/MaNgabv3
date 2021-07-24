package id.ac.stiki.doleno.mangab.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SummaryMhsResponse extends BaseResponse{

    @SerializedName("data")
    public List<SummaryMhsData> data;

    public class SummaryMhsData {
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

        @SerializedName("alpha")
        public int alpha;

        @SerializedName("hadir")
        public int hadir;

        @SerializedName("sakit")
        public int sakit;

        @SerializedName("izin")
        public int izin;

        @SerializedName("pertemuanke")
        public int pertemuan;
    }
}
