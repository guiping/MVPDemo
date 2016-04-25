package lvy.so.mvpdemo.api;

import lvy.so.mvpdemo.AppConfig.AppConfig;
import lvy.so.mvpdemo.model.WelfareEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author gping  email: gping.vip@gmail.com
 * @date Created by 2016/4/25.13:44
 * @filename HttpClientDataRetrofit.class
 * @description
 * @TODO
 */
public interface HttpClientDataRetrofit {
    /**Retrofit 获取数据*/
    @GET("data/{type}/" + AppConfig.LOAG_PAGER_SIZE + "/{pager}")
    Call<WelfareEntity> getData(@Path("type") String type, @Path("pager") int pager);

}
