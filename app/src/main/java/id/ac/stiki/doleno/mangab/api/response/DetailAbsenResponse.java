package id.ac.stiki.doleno.mangab.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DetailAbsenResponse extends BaseResponse implements Serializable {
    @SerializedName("data")
    public List<MhsData> data;

    public static class MhsData implements Serializable{
        @SerializedName("GET_NRP")
        public String nrp;

        @SerializedName("NAMA_MHS")
        public String nama;

        @SerializedName("EMAIL_MHS")
        public String email;

        @SerializedName("STATUS_DETABSEN")
        public int statusAbsen;
    }
}
