package id.ac.stiki.doleno.mangab.api.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("error")
    public boolean error;

    @SerializedName("message")
    public String message;
}
