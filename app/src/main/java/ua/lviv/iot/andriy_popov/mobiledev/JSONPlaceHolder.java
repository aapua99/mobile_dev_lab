package ua.lviv.iot.andriy_popov.mobiledev;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceHolder {
    @GET("read/")
    public Call<List<Game>> getAllGames();
}
