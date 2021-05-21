package id.ac.stiki.doleno.mangab.api;

import id.ac.stiki.doleno.mangab.api.response.BaseResponse;
import id.ac.stiki.doleno.mangab.api.response.CheckStatusLoginResponse;
import id.ac.stiki.doleno.mangab.api.response.DetailAbsenResponse;
import id.ac.stiki.doleno.mangab.api.response.GenerateQrCodeResponse;
import id.ac.stiki.doleno.mangab.api.response.HistoryAbsensiMhsResponse;
import id.ac.stiki.doleno.mangab.api.response.HistoryAbsensiResponse;
import id.ac.stiki.doleno.mangab.api.response.LoginResponse;
import id.ac.stiki.doleno.mangab.api.response.MyClassResponse;
import id.ac.stiki.doleno.mangab.api.response.MyLectureResponse;

import id.ac.stiki.doleno.mangab.api.response.SummaryResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @POST("auth/checkStatusLogin")
    @FormUrlEncoded
    Call<CheckStatusLoginResponse> checkStatusLogin(@Field("id_device") String idDevice);

    @POST("auth/login")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password, @Field("device_id") String deviceId);

    @POST("auth/logout")
    @FormUrlEncoded
    Call<BaseResponse> logout(@Field("email") String email);

    @POST("auth/changePassword")
    @FormUrlEncoded
    Call<BaseResponse> changePassword(@Field("email") String email, @Field("new_password") String newPassword);

    @POST("matkul/myLecture")
    @FormUrlEncoded
    Call<MyLectureResponse> myLecture(@Field("nip") String nip);

    @POST("matkul/myClass")
    @FormUrlEncoded
    Call<MyClassResponse> myClass(@Field("kode_matkul") String kodeMatkul);

    @POST("absen/generate")
    @FormUrlEncoded
    Call<GenerateQrCodeResponse> generateQrCode(@Field("id_jadwal") String idJadwal,
                                                @Field("nip") String nip,
                                                @Field("topik") String topik,
                                                @Field("metode") String metode,
                                                @Field("tipe") Integer tipe,
                                                @Field("latitude") double latitude,
                                                @Field("longitude") double longitude);

    @POST("absen/regenerate")
    @FormUrlEncoded
    Call<GenerateQrCodeResponse> regenerateQrCode(@Field("id_jadwal") String idJadwal);

    @POST("absen/absenMhs")
    @FormUrlEncoded
    Call<BaseResponse> absenMhs(@Field("qr_code") String qrCode,
                                @Field("nrp") String nrp,
                                @Field("status_absen") Integer statusAbsen,
                                @Field("latitude_mhs") double latitude,
                                @Field("longitude_mhs") double longitude);

    @POST("absen/detailAbsen")
    @FormUrlEncoded
    Call<DetailAbsenResponse> detailAbsen(@Field("id_absen") String idAbsen);

    @POST("absen/historyAbsensiDosen")
    @FormUrlEncoded
    Call<HistoryAbsensiResponse> historyAbsensiDosen(@Field("nip") String noInduk);

    @POST("absen/historyAbsensiMhs")
    @FormUrlEncoded
    Call<HistoryAbsensiMhsResponse> historyAbsensiMhs(@Field("nrp") String noInduk);

    @POST("absen/rekap")
    @FormUrlEncoded
    Call<BaseResponse> rekap(@Field("id_absen") String idAbsen, @Field("note") String note);

    @POST("absen/summaryAbsensiDosen")
    @FormUrlEncoded
    Call<SummaryResponse> summary(@Field("nip") String noInduk);

}
