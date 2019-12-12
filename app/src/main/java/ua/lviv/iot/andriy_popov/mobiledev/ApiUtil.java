package ua.lviv.iot.andriy_popov.mobiledev;

public class ApiUtil {
    private static final String BASE_URL = "https://us-central1-reliable-cairn-256616.cloudfunctions.net/function-1/api/";

    public static JSONPlaceHolder getServiceClass(){
        return RetrofitAPI.getRetrofit(BASE_URL).create(JSONPlaceHolder.class);
    }
}
