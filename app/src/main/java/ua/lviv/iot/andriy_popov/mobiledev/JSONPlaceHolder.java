package ua.lviv.iot.andriy_popov.mobiledev;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JSONPlaceHolder {
    @GET("read/")
    public Call<List<Game>> getAllGames();
    @POST("create/")
    public Call<Game> addGame(@Body Game game);
}
