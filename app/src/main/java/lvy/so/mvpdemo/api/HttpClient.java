package lvy.so.mvpdemo.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author gping  email: gping.vip@gmail.com
 * @date Created by 2016/4/25.13:38
 * @filename HttpClient.class
 * @description
 * @TODO
 */
public class HttpClient {
    private static final String BASE_HOST = "http://gank.io/api/";
    private static Retrofit mRetrofit;
    private static HttpClientDataRetrofit mHttpClientDataRetrofit;
    private static Object monity = new Object();

    private HttpClient() {
    }

    static {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        mRetrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public static HttpClientDataRetrofit getDataRetrofitInstanse() {
        synchronized (monity) {
            if (mHttpClientDataRetrofit == null) {
                mHttpClientDataRetrofit = mRetrofit.create(HttpClientDataRetrofit.class);
            }
        }
        return mHttpClientDataRetrofit;
    }
}
