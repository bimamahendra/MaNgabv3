package id.ac.stiki.doleno.mangab.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ClassDetailResponse extends BaseResponse implements Serializable {
    @SerializedName("data")
    public List<ClassDetailResponse.DetailClassData> data;

    public class DetailClassData implements Serializable{
        @SerializedName("KODE_MATKUL")
        public String kodeMatkul;

        @SerializedName("KODE_PRODI")
        public String kodeProdi;

        @SerializedName("NAMA_MATKUL")
        public String namaMatkul;

        @SerializedName("KELAS_PRTMN")
        public String kelasMatkul;

    }
}
