package lvy.so.mvpdemo.presenter;

import android.content.Context;
import android.util.Log;

import lvy.so.mvpdemo.api.HttpClient;
import lvy.so.mvpdemo.model.WelfareEntity;
import lvy.so.mvpdemo.view.impl.ILoadView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author gping  email: gping.vip@gmail.com
 * @date Created by 2016/4/25.11:42
 * @filename LoadPresenter.class
 * @description 加载数据的Presenter
 * @TODO
 */
public class LoadPresenter extends BasePresenter<ILoadView> {
    private Call<WelfareEntity> welfareEntityCall;
    public LoadPresenter(Context context, ILoadView iBaseView) {
        super(context, iBaseView);
    }

    @Override
    public void init() {
        super.init();
    }

    public void loadData(String type, int pager) {  //请求数据
        if( mIBaseView != null ) {
            mIBaseView.showProgress();
        }
         welfareEntityCall = HttpClient.getDataRetrofitInstanse().getData(type, pager);
        welfareEntityCall.enqueue(new Callback<WelfareEntity>() {
            @Override
            public void onResponse(Call<WelfareEntity> call, Response<WelfareEntity> response) {
                if( mIBaseView != null ) {
                    mIBaseView.hideProgress();
                   mIBaseView.loadSucess(response.body().get福利List());
                }
            }

            @Override
            public void onFailure(Call<WelfareEntity> call, Throwable t) {
                Log.e("TAG",t.getMessage()+"");
                if( mIBaseView != null ) {
                    mIBaseView.hideProgress();
                    mIBaseView.loadFail();
                }
            }
        });
    }

    @Override
    public void replease() {  //释放资源 在View的onDestory()中调用
        super.replease();
        if (welfareEntityCall != null) {
            welfareEntityCall.cancel();   //取消网络请求
        }

    }
}
